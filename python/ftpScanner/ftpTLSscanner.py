#!/usr/bin/env python3
from database.DBconnector import DBconnector
from ftpScanner import parse_response, get_time, status_report
from ftplib import FTP_TLS
import multiprocessing

def openDBconnection():
    connector = DBconnector()
    cursor = connector.getCursor()
    connection = connector.getConnection()
    if not cursor or not connection:
        print("Error connecting to database.")
        exit()

    return cursor, connection


def worker(shared_dict, ip_stack, lock):
    """
    This defines the worker process used to scan TLS enabled FTP servers.
    """

    # create a unique connection for each worker
    cursor, connection = openDBconnection()

    while True:
        ftps_conn = None
        lock.acquire()
        shared_dict['count'] += 1
        lock.release()

        # exits the worker if all IPs have been processed
        try:
            server_ip = ip_stack.pop()
        except IndexError:
            break

        # if the connection doesn't work, log the error and continue
        try:
            ftps_conn = FTP_TLS(server_ip, timeout=15)
            # try to get the welcome message
            try:
                welcome = ftps_conn.getwelcome()
                code, msg = parse_response(welcome)
                cursor.execute("""insert into welcome_msgs values (%s, %s, %s);""", (server_ip, code, msg))
                lock.acquire()
                shared_dict['welcomes'] += 1
                lock.release()
            except Exception as err:
                code, msg = parse_response(str(err))
                cursor.execute("""insert into errors values (%s, %s, %s);""", (server_ip, code, msg))

            # try to log in whether welcome fails or not
            try:
                login = ftps_conn.login()
                code, msg = parse_response(login)
                cursor.execute("""insert into tls_logins(ip_address, status_code, message) values (%s, %s, %s);""", (server_ip, code, msg))
                lock.acquire()
                shared_dict['logins'] += 1
                lock.release()
            except Exception as err:
                code, msg = parse_response(str(err))
                cursor.execute("""insert into errors values (%s, %s, %s);""", (server_ip, code, msg))

        # if login fails, store error message in errors table
        except Exception as err:
            code, msg = parse_response(str(err))
            try:
                cursor.execute("""insert into errors values (%s, %s, %s);""", (server_ip, code, msg))
            except Exception as e:
                print(e)
        finally:
            try:
                ftps_conn.close()
            except:
                pass

        # commit all insertions to the database
        connection.commit()






serverFile = open('ftpPortScans/5-2-17', 'r')
all_servers = []
for line in serverFile:
	all_servers.append(line.strip())

serverFile.close()

# can be used to define a range of servers
all_servers = all_servers[5000000:7000000]
all_servers.reverse()
total_ips = len(all_servers)

# defines the number of worker processes to spawn
number_of_processes = 149

# create a shared dictionary to be used by all the worker processes for storing statistics
manager = multiprocessing.Manager()
shared_dict = manager.dict()
shared_dict['count'] = 0
shared_dict['welcomes'] = 0
shared_dict['logins'] = 0
shared_dict['total'] = total_ips
shared_dict['done'] = False
lock = multiprocessing.Lock()

# create a shared stack so that the workers can each pop ip addresses until finished
ip_stack = manager.list(all_servers)

# spawn and handle the worker processes
jobs = []
for i in range(number_of_processes):
    p = multiprocessing.Process(target=worker, args=(shared_dict, ip_stack, lock, ))
    jobs.append(p)
    p.start()

status = multiprocessing.Process(target=status_report, args=(shared_dict, ))
status.start()

# wait for all processes to finish
for process in jobs:
    process.join()

shared_dict['done'] = True
status.join()
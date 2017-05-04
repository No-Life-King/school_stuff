#!/usr/bin/env python3
from ftplib import FTP
import multiprocessing
import psycopg2
import time
import re


    

def parse_response(msg):
    '''
    This function parses server responses. If a status code is returned with the response,
    it is removed from the message and returned with the message. If a code is not present
    '0' is returned. 
    '''

    # matches a 3 digit number followed by a space or dash
    pattern = re.compile('^\d{3}[ \-]')

    isStatusCode = re.search(pattern, msg)
    if isStatusCode:
        code = msg[:3]
        msg = msg[4:]
    else:
        code = 0

    return code, msg


def worker(shared_dict, ip_stack, lock):

    try:
        connect_str = "dbname='ftpservers' user='phil' host='localhost' port='31416' password='...'"
        db_conn = psycopg2.connect(connect_str)
    except Exception as e:
        print(e)
        exit()

    # create a cursor for the worker to use
    cursor = db_conn.cursor()

    while True:
        connection = None


        try:
            server_ip, server_id = ip_stack.pop()
        except IndexError:
            break

        lock.acquire()
        shared_dict['server_count'] += 1
        lock.release()

        #server_ip = "41.33.205.239"
        #server_id = 999999999
        try:
            connection = FTP(server_ip, timeout=22)
            try:
                connection.login()
                lock.acquire()
                shared_dict['logins'] += 1
                lock.release()

                directories = [[None]]
                results = []
                dirs = []
                files = []
                connection.dir(results.append)
                for result in results:
                    if result.startswith('d'):
                        dirs.append(result)
                    else:
                        files.append(result)

                for file in files:
                    cursor.execute("""INSERT INTO files_raw(server_id, path, file_info) VALUES (%s, %s, %s);""",
                                  (server_id, '/', file))

                all_names = list(connection.nlst())
                if '..' in all_names:
                    all_names.remove('..')
                if '.' in all_names:
                    all_names.remove('.')

                for directory in dirs:
                    for name in all_names:
                        if name in directory:
                            directories[0].append(name)
                            all_names.remove(name)
                            break

                currentDir = directories[0].pop()
                tier = 1
                relPath = currentDir
                dirsScanned = 0
                while currentDir != None and dirsScanned < 1000:
                    time.sleep(.25)
                    connection.cwd(currentDir)
                    results = []
                    dirs = []
                    connection.dir(results.append)
                    files = []
                    all_names = list(connection.nlst())
                    if '..' in all_names:
                        all_names.remove('..')
                    if '.' in all_names:
                        all_names.remove('.')

                    for result in results:
                        if result.startswith('d'):
                            for name in all_names:
                                if name in result:
                                    dirs.append(name)
                                    all_names.remove(name)
                                    break
                        else:
                            files.append(result)

                    for file in files:
                        cursor.execute("""INSERT INTO files_raw(server_id, path, file_info) VALUES (%s, %s, %s);""",
                                       (server_id, relPath, file))

                    db_conn.commit()
                    directories.append(dirs)

                    while directories[tier] == []:
                        directories.pop()
                        tier -= 1
                        connection.cwd('..')
                        relPath = relPath.rpartition('/')[0]

                    currentDir = directories[tier].pop()
                    tier += 1
                    relPath += '/' + currentDir

                    dirsScanned += 1


            except Exception as err:
                code, msg = parse_response(str(err))
                cursor.execute("""INSERT INTO directory_errors(ip_address, status_code, message) VALUES (%s, %s, %s);""",
                               (server_ip, code, msg))

        except Exception as err:
            code, msg = parse_response(str(err))
            cursor.execute("""INSERT INTO directory_errors(ip_address, status_code, message) VALUES (%s, %s, %s);""",
                           (server_ip, code, msg))
        finally:
            try:
                connection.close()
            except:
                pass

        db_conn.commit()
        #break


def get_time(seconds):
    '''
    Accepts a time (in seconds) and returns hours, minutes, and seconds.
    '''

    hours = seconds // 3600
    seconds = seconds - hours * 3600
    minutes = seconds // 60
    seconds = seconds - minutes * 60

    return hours, minutes, seconds


def status_report(shared_dict):
    '''
    Prints a status report every 10 seconds.
    '''

    start = time.time()
    while shared_dict['done'] == False:
        time.sleep(10)
        print("Servers crawled: " + str(shared_dict['server_count']) +
              ", successful logins: " + str(shared_dict['logins']))
        end = time.time() - start
        hours, mins, secs = get_time(int(end))
        print('\t' + str(int(shared_dict['server_count'] / shared_dict['total'] * 100))
              + "% finished in " + str(hours) + "h " + str(mins) + "m " + str(secs) +
              "s. That's " + str(int(shared_dict['server_count'] / (end / 60))) + " servers per minute.")

    print("Scan Complete!")


try:
    connect_str = "dbname='ftpservers' user='phil' host='localhost' port='31416' password='...'"
    db_conn = psycopg2.connect(connect_str)
    cursor = db_conn.cursor()
except Exception as e:
    print(e)
    exit()

cursor.execute("""select * from directory_errors inner join logins on directory_errors.ip_address=logins.ip_address;""")
results = cursor.fetchall()

all_servers = []
for server in results:
    if server[3] == 'timed out':
        all_servers.append((server[1], server[7]))

all_servers = all_servers[:]
all_servers.reverse()
total_ips = len(all_servers)

manager = multiprocessing.Manager()
lock = multiprocessing.Lock()
ip_stack = manager.list(all_servers)

shared_dict = manager.dict()
shared_dict['server_count'] = 0
shared_dict['logins'] = 0
shared_dict['total'] = total_ips
shared_dict['done'] = False

# defines the number of worker processes to spawn
number_of_processes = 30

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

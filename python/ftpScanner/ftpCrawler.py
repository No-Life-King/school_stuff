#!/usr/bin/env python3
from ftplib import FTP
from ftpScanner import openDBconnection, parse_response, get_time
import multiprocessing
import time


def worker(shared_dict, ip_stack, lock):

    # create a unique connection for each worker
    cursor, connection = openDBconnection()

    while True:
        ftp_conn = None


        try:
            server_ip, server_id = ip_stack.pop()
        except IndexError:
            break

        lock.acquire()
        shared_dict['server_count'] += 1
        lock.release()

        server_ip = "192.168.2.4"
        server_id = 999999999
        try:
            ftp_conn = FTP(server_ip, timeout=22)
            try:
                ftp_conn.login()
                lock.acquire()
                shared_dict['logins'] += 1
                lock.release()

                directories = [[None]]
                results = []
                dirs = []
                files = []
                ftp_conn.dir(results.append)
                for result in results:
                    if result.startswith('d') or '<DIR>' in result:
                        dirs.append(result)
                    else:
                        files.append(result)

                if '..' in files:
                    files.remove('..')
                if '.' in files:
                    files.remove('.')

                for file in files:
                    cursor.execute("""INSERT INTO files_raw(server_id, path, file_info) VALUES (%s, %s, %s);""",
                                  (server_id, '/', file))

                all_names = list(ftp_conn.nlst())
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
                dirsScanned = 1
                while currentDir != None and dirsScanned < 100000:
                    time.sleep(.25)
                    failed = False
                    try:
                        ftp_conn.cwd(currentDir)
                        results = []
                        dirs = []
                        ftp_conn.dir(results.append)

                        if '..' in results:
                            results.remove('..')
                        if '.' in results:
                            results.remove('.')

                        files = []
                        all_names = list(ftp_conn.nlst())
                        if '..' in all_names:
                            all_names.remove('..')
                        if '.' in all_names:
                            all_names.remove('.')

                        for result in results:
                            if result.startswith('d') or '<DIR>' in result:
                            # if result.startswith('d'):
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

                        connection.commit()
                        directories.append(dirs)

                    except:
                        tier -= 1
                        relPath = relPath.rpartition('/')[0]

                    while directories[tier] == []:
                        directories.pop()
                        tier -= 1
                        ftp_conn.cwd('..')
                        relPath = relPath.rpartition('/')[0]
                        time.sleep(.25)

                    currentDir = directories[tier].pop()
                    tier += 1
                    if currentDir != None:
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
                ftp_conn.close()
                print(dirsScanned)
            except:
                pass

        connection.commit()
        break


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


cursor, connection = openDBconnection()
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
number_of_processes = 1

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

#!/usr/bin/env python3
from ftplib import FTP
from database.DBconnector import DBconnector
import multiprocessing, psycopg2, time, re



def openDBconnection():
    connector = DBconnector()
    cursor = connector.getCursor()
    connection = connector.getConnection()
    if not cursor or not connection:
        print("Error connecting to database.")
        exit()

    return cursor, connection


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

	if msg == '' or msg == None:
		msg = '(No Message.)'
		
	return code, msg


def worker(shared_dict, ip_stack, lock):
	"""
	This defines the worker process used to scan ftp servers. 
	"""
	
	# Try to connect to database, end program if this fails. Each worker will have its own database connection.
	cursor, db_conn = openDBconnection()
		
	# create a cursor for the worker to use
	cursor = db_conn.cursor()
	
	while True:
		connection = None
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
			connection = FTP(server_ip, timeout=15)
			# try to get the welcome message
			try:
				code, msg = 0, ''
				welcome = connection.getwelcome()
				code, msg = parse_response(welcome)
				if msg != '':
					cursor.execute("""INSERT INTO welcome_msgs VALUES (%s, %s, %s);""", (server_ip, code, msg))
					lock.acquire()
					shared_dict['welcomes'] += 1
					lock.release()
			except Exception as err:
				code, msg = parse_response(str(err))
				cursor.execute("""INSERT INTO errors VALUES (%s, %s, %s);""", (server_ip, code, msg))
			
			# try to log in whether welcome fails or not
			try:
				code, msg = 0, ''
				login = connection.login()
				code, msg = parse_response(login)
				if msg != '':
					cursor.execute("""INSERT INTO logins VALUES (%s, %s, %s);""", (server_ip, code, msg))
					lock.acquire()
					shared_dict['logins'] += 1
					lock.release()
			except Exception as err:
				code, msg = parse_response(str(err))
				cursor.execute("""INSERT INTO errors VALUES (%s, %s, %s);""", (server_ip, code, msg))
			
		# if login fails, store error message in errors table
		except Exception as err:
			code, msg = parse_response(str(err))
			if msg == '' or msg == None:
				msg = '(No Message.)'
			try:
				cursor.execute("""INSERT INTO errors VALUES (%s, %s, %s);""", (server_ip, code, msg))
			except Exception as e:
				print(e)
		finally:
			try:
				connection.close()
			except:
				pass
			
		# commit all insertions to the database
		db_conn.commit()
		
			
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
		print("Servers scanned: " + str(shared_dict['count']) +
			  ", welcome messages found: " + str(shared_dict['welcomes']) +
			  ", successful logins: " + str(shared_dict['logins']))
		end = time.time() - start
		hours, mins, secs = get_time(int(end))
		print('\t' + str(int(shared_dict['count']/shared_dict['total'] * 100))
			  + "% finished in " + str(hours) + "h " + str(mins) + "m " + str(secs) +
			  "s. That's " + str(int(shared_dict['count']/(end/60))) + " servers per minute.")
	
	print("Scan Complete!")
	

if __name__ == '__main__':
	# load the list of servers
	serverFile = open('unscanned1', 'r')
	all_servers = []
	for line in serverFile:
		all_servers.append(line.strip())
	serverFile.close()

	# can be used to define a range of servers
	all_servers = all_servers[:]
	total_ips = len(all_servers)

	# defines the number of worker processes to spawn
	number_of_processes = 199

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
		p = multiprocessing.Process(target=worker, args=(shared_dict, ip_stack, lock))
		jobs.append(p)
		p.start()

	status = multiprocessing.Process(target=status_report, args=(shared_dict, ))
	status.start()

	# wait for all processes to finish
	for process in jobs:
		process.join()

	shared_dict['done'] = True
	status.join()

	

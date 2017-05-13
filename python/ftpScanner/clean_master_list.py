#!/usr/bin/env python3
import psycopg2

d = {}
masterFile = open('ftpservers2', 'r')

for ip in masterFile:
    d[ip.strip()] = None
    
print(len(d), "IPs loaded.")

try:
    connect_str = "dbname='ftpservers' user='phil' host='localhost' port='31416' password='...'"
    db_conn = psycopg2.connect(connect_str)
    cursor = db_conn.cursor()
    
    welcomes = cursor.execute("""SELECT * FROM logins;""")
    welcomes = cursor.fetchall()
    welcome_count = 0
    for entry in welcomes:
        if entry[0] in d:
            d.pop(entry[0])
            welcome_count += 1
            
    print(welcome_count, 'logins cleaned.')
    
except Exception as e:
    print(e)
    
print('Writing ', len(d), ' IP addresses to file... ', end='')
ftpservers = open('ftpservers', 'w')
for address in d:
    ftpservers.write(address + '\n')
    
print("Done!")
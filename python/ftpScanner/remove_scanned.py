#!/usr/bin/env python3
import psycopg2

try:
    connect_str = "dbname='ftpservers' user='phil' host='localhost' port='31416' password='amorestpotestas'"
    db_conn = psycopg2.connect(connect_str)
except Exception as e:
    print(e)
    exit()

cursor = db_conn.cursor()
cursor.execute("""SELECT * FROM logins;""")
results = cursor.fetchall()

new_list = open('unscanned1','r')
ip_dict = {}
for ip_addr in new_list:
    ip_dict[ip_addr.strip()] = None

for entry in results:
    if entry[0] in ip_dict:
        ip_dict.pop(entry[0])

unscanned = open('unscanned', 'w')
for address in ip_dict:
    unscanned.write(address + '\n')
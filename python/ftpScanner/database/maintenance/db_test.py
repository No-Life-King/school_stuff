#!/usr/bin/env python3
import psycopg2
import ipaddress

ipaddr = '192.168.2.2'
code = '220'
msg = '(vsFTPd 3.0.3)'

try:
    connect_str = "dbname='ftpservers' user='phil' host='localhost' password='***'"
    db_conn = psycopg2.connect(connect_str)
    cursor = db_conn.cursor()
    cursor.execute("""INSERT INTO welcome_msgs VALUES (%s, %s, %s);""", (ipaddr, code, msg))
    db_conn.commit()
    cursor.execute("""SELECT * FROM welcome_msgs;""")
    for entity in cursor.fetchall():
        print(entity[0])
        
except Exception as e:
    print(e)

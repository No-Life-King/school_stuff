#!/usr/bin/env python3
import psycopg2

try:
    connect_str = "dbname='ftpservers' user='phil' host='localhost' port='31416' password='...'"
    db_conn = psycopg2.connect(connect_str)
    cursor = db_conn.cursor()
except Exception as e:
    print(e)
    exit()

cursor.execute("""SELECT * FROM files_raw where file_info = 'total 0';""")
results = cursor.fetchall()

count = 0
for result in results:
    cursor.execute("""delete from files_raw where id = %s;""", [result[0]])
    if count % 1000 == 0:
        db_conn.commit()
        print(count, "files deleted.")

    count += 1

db_conn.commit()
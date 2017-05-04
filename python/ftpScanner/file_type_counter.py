#!/usr/bin/env python3
import psycopg2

try:
    connect_str = "dbname='ftpservers' user='phil' host='localhost' port='31416' password='...'"
    db_conn = psycopg2.connect(connect_str)
    cursor = db_conn.cursor()
except Exception as e:
    print(e)
    exit()

cursor.execute("""SELECT * FROM files_raw where file_info like '%.PHP';""")
results = cursor.fetchall()
print('Total:',len(results))

count = 0
for result in results:
    cursor.execute("""insert into files_sorted VALUES (%s, %s, %s, %s);""", (result[0], result[1], result[2], result[3]))
    cursor.execute("""delete from files_raw where id = %s;""", [result[0]])

    if count % 100000 == 0:
        print(count, 'records processed')
        db_conn.commit()

    count += 1

db_conn.commit()
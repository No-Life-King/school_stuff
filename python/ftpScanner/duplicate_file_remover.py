#!/usr/bin/env python3
import psycopg2

try:
    connect_str = "dbname='ftpservers' user='phil' host='localhost' port='31416' password='...'"
    db_conn = psycopg2.connect(connect_str)
    cursor = db_conn.cursor()
except Exception as e:
    print(e)
    exit()

cursor.execute("""select * from files_raw order by server_id asc limit 100000;""")
all_files = cursor.fetchall()

index = 0
dupe_count = 0
while True:
    current = all_files[index]
    i2 = index + 1
    while True:
        next = all_files[i2]
        if current[1] == next[1] and current[2] == next[2] and current[3] == next[3]:
            cursor.execute("""delete from files_raw where id = %s;""", next[0])
            i2 += 1
            dupe_count += 1
        else:
            break


    if index % 200000 == 0:
        print(index, 'records processed', dupe_count, "duplicates found.")
    db_conn.commit()
    index += 1


#!/usr/bin/env python3
import psycopg2

try:
    connect_str = "dbname='ftpservers' user='phil' host='localhost' port='31416' password='...'"
    db_conn = psycopg2.connect(connect_str)
    cursor = db_conn.cursor()
except Exception as e:
    print(e)
    exit()

cursor.execute("""SELECT * FROM unique_welcomes_cleaned;""")
results = cursor.fetchall()

messages = {}
processed = 0
for result in results:
    message = result[1]
    if message in messages:
        messages[message] += result[2]
    else:
        messages[message] = result[2]

    if processed % 1000000 == 0:
        print(processed, "messages processed.")

    processed += 1

row_count = 0
for entry in messages:
    cursor.execute("""INSERT INTO unique_welcomes_cleaned1(message, count) VALUES (%s, %s);""",
                   (entry, messages[entry]))
    row_count += 1
    if row_count % 100000 == 0:
        db_conn.commit()
        print(row_count, "messages committed.")

db_conn.commit()
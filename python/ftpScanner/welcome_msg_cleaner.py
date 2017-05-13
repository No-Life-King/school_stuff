#!/usr/bin/env python3
import psycopg2

try:
    connect_str = "dbname='ftpservers' user='phil' host='localhost' port='31416' password='...'"
    db_conn = psycopg2.connect(connect_str)
    cursor = db_conn.cursor()
except Exception as e:
    print(e)
    exit()

cursor.execute("""SELECT * FROM unique_welcomes;""")
results = cursor.fetchall()

entries = []
for result in results:
    words = result[1].split()
    message = ' '
    for word in words:
        cleaned_word = ''
        for letter in word:
            if letter not in '0123456789.-:':
                cleaned_word += letter

        message += cleaned_word + ' '

    entries.append((message, result[2]))

count = 0
for entry in entries:
    cursor.execute("""INSERT INTO unique_welcomes_cleaned(message, count) VALUES (%s, %s);""",
                   (entry[0], entry[1]))
    if count % 100000 == 0:
        print(count, "messages cleaned.")
        db_conn.commit()

    count += 1

db_conn.commit()
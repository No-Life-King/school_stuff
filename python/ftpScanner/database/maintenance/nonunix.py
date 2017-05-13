#!/usr/bin/env python3
from database.DBconnector import DBconnector
import re


def openDBconnection():
    connector = DBconnector()
    cursor = connector.getCursor()
    connection = connector.getConnection()
    if not cursor or not connection:
        print("Error connecting to database.")
        exit()

    return cursor, connection

cursor, connection = openDBconnection()

cursor.execute("""select * from files_raw limit 10000000;""")
all_files = cursor.fetchall()

pattern = re.compile('^\d{2}-\d{2}-\d{2}')

count = 0
for file in all_files:
    startsWithDate = re.search(pattern, file[3])
    if file[3].startswith('l') or file[3].startswith('-') or startsWithDate:
        continue
    else:
        count += 1
        print(file[3])

    if count == 1000:
        break
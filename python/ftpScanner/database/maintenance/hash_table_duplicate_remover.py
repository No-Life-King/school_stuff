#!/usr/bin/env python3
import psycopg2
from database.DBconnector import DBconnector

connector = DBconnector()
cursor = connector.getCursor()
connection = connector.getConnection()
if not cursor or not connection:
    print("Error connecting to database.")
    exit()

table = 'welcome_msgs'
cursor.execute("""select * from """ + table + """ order by id desc;""")
all_files = cursor.fetchall()
print("Removing duplicates from", len(all_files), 'files.')

file_dict = {}
count = 0
last_printed = 0
for file in all_files:
    key = file[0]
    if key not in file_dict:
        file_dict[key] = None
    else:
        #cursor.execute("""delete from """ + table + """ where id = %s;""", [file[3]])
        count += 1

    if count % 10000 == 0 and count != last_printed:
        print(count, "duplicates found and removed.")
        last_printed = count
        connection.commit()


print(count, "duplicates found and removed.")
connection.commit()
print("Finished!")

#!/usr/bin/env python3
from database.DBconnector import DBconnector

connector = DBconnector()
cursor = connector.getCursor()
connection = connector.getConnection()
if not cursor or not connection:
    print("Error connecting to database.")
    exit()


total = 0
cursor.execute("""SELECT * FROM errors where message like '%TLS%' or message like '%SSL%';""")
results = cursor.fetchall()
total += len(results)
print(len(results))


print('Total:',len(results))

"""
count = 0
for result in results:
    cursor.execute('''insert into files_sorted VALUES (%s, %s, %s, %s);''', (result[0], result[1], result[2], result[3]))
    cursor.execute('''delete from files_raw where id = %s;''', [result[0]])

    if count % 100000 == 0:
        print(count, 'records processed')
        connection.commit()

    count += 1

print(count, 'records processed')
connection.commit()
"""
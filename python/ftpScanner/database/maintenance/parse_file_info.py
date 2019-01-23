import re

from database.DBconnector import DBconnector


def openDBconnection():
    connector = DBconnector()
    cursor = connector.getCursor()
    connection = connector.getConnection()
    if not cursor or not connection:
        print("Error connecting to database.")
        exit()

    return cursor, connection

source_table = 'files_sorted'

for x in range(1):
    cursor, connection = openDBconnection()
    cursor.execute("""select * from """ + source_table + """;""")
    all_files = cursor.fetchall()
    #pattern = re.compile('^([l-][r-][w-][x-][r-][w-][x-][r-][w-][x-])\s+\d\s+\w+\s+\w+\s+(\d+)\s+(\w{3}\s+\d+\s+\d{2}:\d{2})\s+(.+)')
    #pattern = re.compile('^([l-][r-][w-][x-][r-][w-][x-][r-][w-][x-])\s+\d+\w+\s+\w+\s+(\d+)\s+(\w{3}\s+\d+\s+\d{4})\s+(.+)')
    pattern = re.compile('^(\d{2}-\d{2}-\d{2}\s+\d{2}:\d{2}(AM|PM))\s+(\d+)\s+(.+)')
    count = 0
    match_count = 0
    print('matching', len(all_files), 'files')

    for file in all_files:
        match = re.fullmatch(pattern, file[3])
        count += 1
        if match:
            #permissions = True if match.group(1)[-3] == 'r' else False
            permissions = True
            size = match.group(3)
            modify_date = match.group(1)
            name = match.group(4)
            match_count += 1

            cursor.execute("""insert into file_table(server_id, path, readable, size, modify_date, name) values (%s, %s, %s, %s, %s, %s);""",
                           (file[1], file[2], permissions, size, modify_date, name))
            cursor.execute("""delete from """ + source_table + """ where id=%s;""", [file[0]])

        if count % 10000 == 0:
            print(match_count, 'files matched out of', count, 'records')
            connection.commit()

    connection.commit()
    print(match_count, 'files matched')
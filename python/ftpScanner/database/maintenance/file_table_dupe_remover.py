from database.DBconnector import DBconnector


def openDBconnection():
    connector = DBconnector()
    cursor = connector.getCursor()
    connection = connector.getConnection()
    if not cursor or not connection:
        print("Error connecting to database.")
        exit()

    return cursor, connection

table = 'file_table'
cursor, connection = openDBconnection()
cursor.execute("""select (server_id) from """ + table + """;""")
file_table_server_ids = cursor.fetchall()
id_set = set()

for id in file_table_server_ids:
    id_set.add(id)

dupe_count = 0
total_files = 0
for id in id_set:
    cursor.execute("""select * from """ + table + """ where server_id = %s;""", [id[0]])
    server_files = cursor.fetchall()
    total_files += len(server_files)
    file_set = set()
    for file in server_files:
        key = file[2] + '/' + file[6]
        if key in file_set:
            dupe_count += 1
            cursor.execute("""delete from """ + table + """ where id = %s;""", [file[0]])

        file_set.add(key)

    connection.commit()
    if total_files % 100 == 0:
        print(dupe_count, 'dupes detected out of', total_files, 'files')






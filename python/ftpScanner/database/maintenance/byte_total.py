from database.DBconnector import DBconnector


def openDBconnection():
    connector = DBconnector('ftp_search_engine')
    cursor = connector.getCursor()
    connection = connector.getConnection()
    if not cursor or not connection:
        print("Error connecting to database.")
        exit()

    return cursor, connection


cursor, connection = openDBconnection()
cursor.execute("""select (size) from search_file;""")
all_files = cursor.fetchall()
byte_total = 0

for file in all_files:
    byte_total += file[0]

print(str(byte_total/1024/1024/1024/1024) + " TB worth of files indexed by the file database.")
print(str(byte_total) + "B")
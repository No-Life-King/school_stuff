from database.DBconnector import DBconnector


def openDBconnection(dbname):
    connector = DBconnector(dbname)
    cursor = connector.getCursor()
    connection = connector.getConnection()
    if not cursor or not connection:
        print("Error connecting to database.")
        exit()

    return cursor, connection


ftp_cursor, ftp_connection = openDBconnection('ftpservers')
django_cursor, django_connection = openDBconnection('ftp_search_engine')
ftp_cursor.execute("""select * from file_table where id >= 120000000;""")
all_files = ftp_cursor.fetchall()
count = 0
id_dict = {}

for file in all_files:
    id = None
    if file[1] not in id_dict:
        ftp_cursor.execute("""select (ip_address) from logins where id=%s;""", [file[1]])
        ip = ftp_cursor.fetchone()[0]
        django_cursor.execute("""select (id) from search_server where ip_address='""" + ip + """';""")
        id = django_cursor.fetchone()[0]
        id_dict[file[1]] = id
    else:
        id = id_dict[file[1]]

    if id == None:
        print('your id shit fucked up')
        break

    django_cursor.execute("""insert into search_file (path, readable, size, modify_date, filename, server_id) values (%s, %s, %s, %s, %s, %s);""",
                          (file[2], file[3], file[4], file[5], file[6], id))

    if count % 10000 == 0:
        print(count, 'records copied')
        django_connection.commit()
    count += 1

django_connection.commit()
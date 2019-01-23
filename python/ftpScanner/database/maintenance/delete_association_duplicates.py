import time
from database.DBconnector import DBconnector


def openDBconnection():
    connector = DBconnector('ftp_search_engine')
    cursor = connector.getCursor()
    connection = connector.getConnection()
    if not cursor or not connection:
        print("Error connecting to database.")
        exit()

    return cursor, connection


key_count = 0
dupe_count = 0
start = time.time()
cursor, connection = openDBconnection()
cursor.execute("""select id from search_word;""")
word_ids = cursor.fetchall()
for id in word_ids:
    key_set = set()
    cursor.execute("""select * from search_filewordassociation where word_id = %s;""", [id[0]])
    for association in cursor.fetchall():
        key = str(association[1]) + ' ' + str(association[2])
        if key in key_set:
            cursor.execute("""delete from search_filewordassociation where id = %s;""", [association[0]])
            dupe_count += 1
        else:
            key_set.add(key)
            key_count += 1

        if key_count % 10000 == 0:
            connection.commit()
            print(dupe_count, 'duplicates deleted in ' + str(int(time.time() - start)) + ' seconds. ' + str(key_count) + ' records processed.')

    connection.commit()
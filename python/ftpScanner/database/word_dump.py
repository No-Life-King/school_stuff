import re
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


cursor, connection = openDBconnection()
cursor.execute("""select * from search_file where id >= 100000000 and id < 105000000;""")
all_filenames = cursor.fetchall()
file_count = 0
cursor.execute("""select (word) from search_word;""")
all_words = cursor.fetchall()
word_set = set()
for word in all_words:
    word_set.add(word[0])

start = time.time()
for filename in all_filenames:
    file_count += 1
    for word in re.split("[~`!@#$%<>?^&*() \-_=+\\\|;:\"',./\]\[]+", filename[5]):
        word = word.lower()
        if word != '':
            word_exists = True if word in word_set else False
            word_set.add(word)

            if not word_exists:
                cursor.execute("""insert into search_word (word, count) values ('""" + word + """', 1);""")

            else:
                cursor.execute("""update search_word set count=count + 1 where word='""" + word + """';""")

            cursor.execute("""select (id) from search_word where word='""" + word + """';""")
            try:
                word_id = int(cursor.fetchall()[0][0])
            except:
                print(word)
            cursor.execute("""insert into search_filewordassociation (file_id, word_id) values(%s, %s);""", (filename[0], word_id))

    if file_count % 5000 == 0:
        connection.commit()
        print(file_count, 'filenames parsed in ' + str(int(time.time() - start)) + ' seconds.')


connection.commit()
print(time.time()-start)
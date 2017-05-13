#!/usr/bin/env python3
import psycopg2

try:
    connect_str = "dbname='ftpservers' user='phil' host='localhost' port='31416' password='...'"
    db_conn = psycopg2.connect(connect_str)
    cursor = db_conn.cursor()

    cursor.execute("""SELECT * FROM errors;""")
    results = cursor.fetchall()
    dupe_count = 0
    count = 0
    
    addresses = {}
    
    for entry in results:
        count += 1
        if entry[0] in addresses:
            dupe_count += 1
            cursor.execute("""delete from errors where id = %s;""", (entry[3], ))
            
        addresses[entry[0]] = None
        
    db_conn.commit()
            
except Exception as e:
    print(e)
    exit()

print(dupe_count, 'duplicates deleted out of', count, 'records.')
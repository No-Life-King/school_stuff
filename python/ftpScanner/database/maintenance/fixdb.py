#!/usr/bin/env python3
import psycopg2
import re

try:
    connect_str = "dbname='ftpservers' user='phil' host='localhost' password='.....'"
    db_conn = psycopg2.connect(connect_str)
    cursor = db_conn.cursor()
    
    table = 'errors'
    cursor.execute("""SELECT * FROM %s where status_code = '0';""", (table))
    results = cursor.fetchall()

    fixed = 0
    for entry in results:
        msg = entry[2]
        pattern = re.compile('^\d{3}-')
        if re.match(pattern, msg):
            fixed += 1
            code = msg[:3]
            msg = msg[4:]
            cursor.execute("""UPDATE %s set status_code = %s, message = %s where ip_address = %s;""", (table, code, msg, entry[0]))
            db_conn.commit()
        
except Exception as e:
    print(e)
    
    
print("Finished! Fixed", fixed, "records.")
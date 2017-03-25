#!/usr/bin/env python3
import psycopg2
import re

try:
    connect_str = "dbname='ftpservers' user='phil' host='localhost' password='.....'"
    db_conn = psycopg2.connect(connect_str)
    cursor = db_conn.cursor()
    
    cursor.execute("""SELECT * FROM welcome_msgs where status_code = '0';""")
    results = cursor.fetchall()
    fixed = 0
    for entry in results:
        if fixed % 1000 == 0:
            print(fixed, 'records fixed.')
            print('On record #' + str(entry[3]))
        msg = entry[2]
        pattern = re.compile('^\d{3}-')
        if re.match(pattern, msg):
            fixed += 1
            code = msg[:3]
            msg = msg[4:]
            cursor.execute("""UPDATE welcome_msgs set status_code = %s, message = %s where id = %s;""", (code, msg, entry[3]))
            db_conn.commit()
        
except Exception as e:
    print(e)
    
    
print("Finished! Fixed "  + str(fixed) +" records.")
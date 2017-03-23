#!/usr/bin/env python3
from ftplib import FTP


def worker():
    
    try:
        connect_str = "dbname='ftpservers' user='phil' host='localhost' password='.....'"
        db_conn = psycopg2.connect(connect_str)
    except Exception as e:
        print(e)
        exit()
        
    # create a cursor for the worker to use
    cursor = db_conn.cursor()
    
    try:
        connection = FTP('localhost', timeout=15)
    except:
        # log to db
        pass





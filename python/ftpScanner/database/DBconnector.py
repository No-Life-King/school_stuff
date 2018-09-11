import psycopg2


class DBconnector:
    '''
    A connector class for a PostgreSQL database server. Simply allows static database connection
    information to be stored in one location. Each instance of the class contains a unique 
    connection. The connection is initiated upon instantiation or 'None' is returned thus 
    conditionals may be used to determine if the database login succeeded. 
    '''


    def __init__(self):
        self.dbname = 'ftpservers'
        self.username = 'phil'
        self.host = 'localhost'
        self.port = '31416'
        self.password = 'amorestpotestas'
        self.login_string = "dbname='%s' user='%s' host='%s' port='%s' password='%s'" \
                         % (self.dbname, self.username, self.host, self.port, self.password)

        # try to set the connection by logging in
        try:
            self.connection = psycopg2.connect(self.login_string)
        except Exception as e:
            self.connection = None
            print(e)

        # try to set the cursor
        try:
            self.cursor = self.connection.cursor()
        except Exception as e:
            self.cursor = None
            print(e)


    def getConnection(self):
        '''
        Returns the connection object.
        '''
        return self.connection


    def getCursor(self):
        '''
        Returns the cursor object.
        '''
        return self.cursor

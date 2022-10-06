#!/usr/bin/python
import psycopg2

def table_exist(cur,table):
    cur.execute("select exists(select * from information_schema.tables where table_name=%s)",(table,))
    return cur.fetchone()[0]

def database_exist(cur,database):
    sql = '''
    select exists()
    
    '''

try:
    conn = psycopg2.connect(database = "testdb", host = "localhost", port = "5432")
except:
    print("Database not found...")
    sql =   '''
            CREATE database hoge
            '''
    print("Database created.") 
    
cur = conn.cursor()

try:
    if not table_exist(cur,"history"):
        print("we create history")
        sql =   '''
                CREATE TABLE history (
                    id serial primary key,
                    name varchar(20),
                    item varchar(20),
                    price integer,
                    date varchar(50)
                )
                '''
        cur.execute(sql)
    if not table_exist(cur,"item"):
        sql =   '''
                CREATE TABLE item (
                    name varchar(20),
                    sellingprice integer,
                    costprice integer,
                    grouping varchar(20),
                    salesfigure integer
                )
                '''
        cur.execute(sql)
        print("we create item")
    if not table_exist(cur,"member"):
        sql =   '''
                CREATE TABLE member(
                    name varchar(20),
                    displayname varchar(20),
                    unpayedamount integer,
                    attribute varchar(20)
                )
                '''
        cur.execute(sql)
        print("we create member")
except:
    print("I cant make our test database")

conn.commit()
conn.close()
cur.close()
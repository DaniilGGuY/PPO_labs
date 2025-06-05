import psycopg2
from pymongo import MongoClient

pg_conn = psycopg2.connect(
    host="localhost",
    database="ppo",
    user="daniil",
    password="spaik090949"
)

mongo_client = MongoClient('mongodb://localhost:27017/')
mongo_db = mongo_client['ppo_mongo']

tables = [
    'client',
    'consultant',
    'manager',
    'message',
    'orderproduct',
    'orders',
    'product',
    'review'
]

for table in tables:
    pg_cursor = pg_conn.cursor()
    try:
        pg_cursor.execute(f"SELECT * FROM {table}")
        columns = [desc[0] for desc in pg_cursor.description]
        records = []

        for row in pg_cursor:
            records.append(dict(zip(columns, row)))
        if records:
            mongo_db[table].insert_many(records)
            print(f"Перенесено {len(records)} записей в коллекцию {table}")
        else:
            print(f"Таблица {table} пуста, пропускаем")
    except Exception as e:
        print(f"Ошибка при переносе таблицы {table}: {str(e)}")
    finally:
        pg_cursor.close()

pg_conn.close()
print("Перенос всех данных завершен!")
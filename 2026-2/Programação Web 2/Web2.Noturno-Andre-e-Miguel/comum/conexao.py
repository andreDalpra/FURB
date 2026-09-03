"""
    Unit que conecta no banco de dados MySQL
"""
import os
from pathlib import Path

import pymysql
from dotenv import load_dotenv

load_dotenv(dotenv_path=Path.cwd() / ".env", override=True)

def obter_conexao():
    return pymysql.connect(
        host=os.getenv("DB_HOST", "localhost"),
        port=int(os.getenv("DB_PORT", "3306")),
        user=os.getenv("DB_USER", "root"),
        password=os.getenv("DB_PASSWORD", ""),
        database=os.getenv("DB_NAME", "delivery_db"),
        cursorclass=pymysql.cursors.DictCursor,
        autocommit=False,
    )

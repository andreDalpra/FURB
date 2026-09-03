# Classe de produto.
from decimal import Decimal, InvalidOperation

class Tcadpro:
    # Variaveis que representam os campos da tabela
    codpro = None
    despro = None
    vlrpro = None

    def __init__(self, conn):
        self.conn = conn

    # Le os dados pela chave
    def le(self, p_codpro: int):
        with self.conn.cursor() as cur:
            cur.execute(
                f"""
                    SELECT {self.colunas_do_select()}
                      FROM cadpro
                     WHERE codpro = %s
                """,
                (p_codpro,),
            )
            row = cur.fetchone()

        if row:
            self.carrega_do_dataset(row)
            return True

        return False

    def valida(self):
        if not self.codpro:
            return "Codigo do produto nao pode ser vazio."
        if not self.despro:
            return "Descricao do produto nao pode ser vazia."
        if not self.vlrpro:
            return "Valor do produto nao pode ser vazio."

        return ""

     # Insere os dados na tabela
    def insere(self):
        with self.conn.cursor() as cur:
            cur.execute(
                f"""
                    INSERT INTO cadpro (codpro, despro, vlrpro)
                    VALUES (%s, %s, %s)
                """,
                (self.codpro, self.despro, self.vlrpro),
            )

        self.conn.commit()
        return f"Produto cadastrado com codigo {self.codpro}."

    # Exclui os dados da tabela
    def remove(self):
        with self.conn.cursor() as cur:
            cur.execute(
                """
                    DELETE FROM cadpro
                    WHERE codpro = %s
                """,
                (self.codpro,),
            )
            removeu = cur.rowcount > 0

        self.conn.commit()
        if removeu:
            return "Produto removido com sucesso."

        return f"Produto com codigo {self.codpro} nao encontrado."
        
    # Carrega os dados do dataset para os atributos da classe
    def carrega_do_dataset(self, row):
        self.codpro = row["codpro"]
        self.despro = row["despro"]
        self.vlrpro = row["vlrpro"]

    # Adiciona ao SQL SELECT <coluna1>,<coluna2>, etc
    def colunas_do_select(self):
        return " cadpro.codpro, cadpro.despro, cadpro.vlrpro "

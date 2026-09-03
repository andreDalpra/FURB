# Classe de pedido.
from .cadcli import Tcadcli

class Tcadped:
    # Variaveis que representam os campos da tabela
    codped = None
    codcli = None
    datped = None
    staped = None
    obsped = None
    vlrped = None
    # Variaveis de instancia
    _cadcli = None

    def __init__(self, conn):
        self.conn = conn

    # Le os dados pela chave
    def le(self, p_codped: int):
        with self.conn.cursor() as cur:
            cur.execute(
                f"""
                    SELECT {self.colunas_do_select()}
                      FROM cadped
                     WHERE codped = %s
                """,
                (p_codped,),
            )
            row = cur.fetchone()

        if row:
            self.carrega_do_dataset(row)
            return True

        return False

    # Le os dados pelo indice unico
    def le_uk1(self, p_numite: int, p_codpro: int):
        with self.conn.cursor() as cur:
            cur.execute(
                f"""
                    SELECT {self.colunas_do_select()}
                      FROM cadped
                     WHERE numite = %s
                       AND codpro = %s
                """,
                (p_numite, p_codpro),
            )
            row = cur.fetchone()

        if row:
            self.carrega_do_dataset(row)
            return True

        return False

    def valida(self):
        if  not self.codcli:
            return "Codigo do cliente nao pode ser vazio."
        if  not self.datped:
            return "Data do pedido nao pode ser vazio."
        if  not self.staped:
            return "Status do pedido nao pode ser vazio."
        if  self.vlrped is None:
            return "Valor do pedido nao pode ser vazio."

        l_msgerr = self._valida_cliente(self.codcli)
        if  l_msgerr:
            return l_msgerr

        return ""

    def _valida_cliente(self, p_codcli: int):
        if  not self.cadcli.le(p_codcli):
            return f"Cliente com codigo {p_codcli} nao encontrado."

        return ""

    # Insere os dados na tabela
    def insere(self):
        with self.conn.cursor() as cur:
            cur.execute(
                f"""
                    INSERT INTO cadped (codcli, datped, staped, obsped, vlrped)
                    VALUES (%s, %s, %s, %s, %s)
                """,
                (self.codcli, self.datped, self.staped, self.obsped, self.vlrped),
            )

            self.codped = cur.lastrowid # Pega a sequence

        self.conn.commit()
        return f"Pedido cadastrado com codigo {self.codped}."

    # Exclui os dados da tabela
    def remove(self):
        with self.conn.cursor() as cur:
            cur.execute(
                """
                    DELETE FROM cadped
                    WHERE codped = %s
                """,
                (self.codped,),
            )
            removeu = cur.rowcount > 0

        self.conn.commit()
        if removeu:
            return "Pedido removido com sucesso."

        return f"Pedido com codigo {self.codped} nao encontrado."

    # Carrega os dados do dataset para os atributos da classe
    def carrega_do_dataset(self, row):
        self.codped = row["codped"]
        self.codcli = row["codcli"]
        self.datped = row["datped"]
        self.staped = row["staped"]
        self.obsped = row["obsped"]
        self.vlrped = row["vlrped"]

    @property 
    def cadcli(self):
        if  not self._cadcli:
            self._cadcli = Tcadcli(self.conn)
        return self._cadcli

    @cadcli.setter
    def cadcli(self, value):
        self._cadcli = value

    # Adiciona ao SQL SELECT <coluna1>,<coluna2>, etc
    def colunas_do_select(self):
        return (
            " cadped.codped, cadped.codcli, cadped.datped, "
            "cadped.staped, cadped.obsped, cadped.vlrped "
        )

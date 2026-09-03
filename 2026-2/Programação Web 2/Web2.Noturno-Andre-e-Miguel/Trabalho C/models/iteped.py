from models.cadped import Tcadped
from models.cadpro import Tcadpro

# Classe de item do pedido.

class Titeped:
    # Variaveis que representam os campos da tabela
    seqiteped = None
    codped = None
    numite = None
    codpro = None
    qtdite = None
    vlrite = None
    # Variaveis de instancia
    _cadped = None
    _cadpro = None

    def __init__(self, conn):
        self.conn = conn

    # Le os dados pela chave
    def le(self, p_seqiteped: int):
        with self.conn.cursor() as cur:
            cur.execute(
                f"""
                    SELECT {self.colunas_do_select()}
                      FROM iteped
                     WHERE seqiteped = %s
                """,
                (p_seqiteped,),
            )
            row = cur.fetchone()

        if row:
            self.carrega_do_dataset(row)
            return True

        return False

    # Le os dados pelo indice unico da tabela
    def le_uk1(self, p_codped: int, p_numite: int):
        with self.conn.cursor() as cur:
            cur.execute(
                f"""
                    SELECT {self.colunas_do_select()}
                      FROM iteped
                     WHERE codped = %s 
                       AND numite = %s
                """,
                (p_codped, p_numite),
            )
            row = cur.fetchone()

        if row:
            self.carrega_do_dataset(row)
            return True

        return False

    def valida(self):
        if  not self.codped:
            return "Codigo do pedido nao pode ser vazio."
        if  not self.numite:
            return "Numero do item nao pode ser vazio."
        if  not self.codpro:
            return "Codigo do produto nao pode ser vazio."
        if  not self.qtdite:
            return "Quantidade do item nao pode ser vazio."

        # Valida se o pedido existe
        if  not self.valida_pedido():
            return f"Pedido com codigo {self.codped} nao encontrado."

        # Valida se o produto existe
        if  not self.valida_produto():
            return f"Produto com codigo {self.codpro} nao encontrado."

        if  self.le_uk1(self.codped, self.numite):
            return f"Item {self.numite} do pedido {self.codped} ja cadastrado."
        
        # Copia o valor do produto para o item do pedido
        self.vlrite = self.cadpro.vlrpro

        return ""

    def valida_pedido(self):
        if  not self.cadped.le(self.codped):
            return False
        return True

    def valida_produto(self):
        if  not self.cadpro.le(self.codpro):
            return False
        return True

    # Insere os dados na tabela
    def insere(self):

        with self.conn.cursor() as cur:
            cur.execute(
                f"""
                    INSERT INTO iteped (codped, numite, codpro, qtdite, vlrite)
                    VALUES (%s, %s, %s, %s, %s)
                """,
                (self.codped, self.numite, self.codpro, self.qtdite, self.vlrite),
            )
            self.seqiteped = cur.lastrowid # Pega a sequence

        self.conn.commit()
        return f"Item do pedido cadastrado com codigo {self.seqiteped}."

    # Exclui os dados da tabela
    def remove(self):
        with self.conn.cursor() as cur:
            cur.execute(
                """
                    DELETE FROM iteped
                    WHERE seqiteped = %s
                """,
                (self.seqiteped,),
            )
            removeu = cur.rowcount > 0

        self.conn.commit()
        if removeu:
            return "Item do pedido removido com sucesso."

        return f"Item do pedido com codigo {self.seqiteped} nao encontrado."
        
    # Carrega os dados do dataset para os atributos da classe
    def carrega_do_dataset(self, row):
        self.seqiteped = row["seqiteped"]
        self.numite = row["numite"]
        self.codpro = row["codpro"]
        self.qtdite = row["qtdite"]
        self.codped = row["codped"]
        self.vlrite = row["vlrite"]

    # Adiciona ao SQL SELECT <coluna1>,<coluna2>, etc
    def colunas_do_select(self):
        return " iteped.seqiteped, iteped.numite, iteped.codpro, iteped.qtdite, iteped.codped, iteped.vlrite "

    @property
    def cadpro(self):
        if  not self._cadpro:
            self._cadpro = Tcadpro(self.conn)
        return self._cadpro

    @cadpro.setter
    def cadpro(self, value):
        self._cadpro = value

    @property
    def cadped(self):
        if  not self._cadped:
            self._cadped = Tcadped(self.conn)
        return self._cadped

    @cadped.setter
    def cadped(self, value):
        self._cadped = value

# Classe que lista de itens do pedido.
class Tlisiteped:
    def __init__(self, p_conn=None):
        self.conn = p_conn
        self.sql = ""
        self.params = ()
        self.itens: list[Titeped] = []

    def __getitem__(self, p_indice: int) -> Titeped:
        return self.itens[p_indice]

    # Carrega os itens da classe em uma lista
    def carrega_itens(self):
        self.itens.clear()

        if  not self.conn or not self.sql:
            return

        # Executa o SQL e carrega os itens na lista
        with self.conn.cursor() as cur:
            cur.execute(self.sql, self.params)
            rows = cur.fetchall()

            for row in rows:
                l_item = Titeped(self.conn)
                l_item.carrega_do_dataset(row)
                self.itens.append(l_item)

    # Adiciona ao SQL SELECT <coluna1>,<coluna2>, etc
    def colunas_do_select(self):
        return " iteped.seqiteped, iteped.numite, iteped.codpro, iteped.qtdite, iteped.codped, iteped.vlrite "

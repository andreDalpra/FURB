from models.cadped import Tcadped
from models.iteped import Titeped, Tlisiteped

class PedidoService:
    def __init__(self, conn):
        self.conn = conn
        self._cadped = None
        self._iteped = None
        self._lisiteped = None

    def cadastra_pedido(self, p_cadped: Tcadped):
        p_cadped.conn = self.conn

        # Valida os dados do pedido
        op_infmsg = p_cadped.valida()
        if op_infmsg:
            return op_infmsg

        op_infmsg = p_cadped.insere()
        if op_infmsg:
            return op_infmsg

        self.cadped = p_cadped
        return ""

    def busca_pedido(self, p_codped: int):
        if not self.cadped.le(p_codped):
            return None

        return self.cadped

    def remove_pedido(self, p_codped: int):
        pedido = self.busca_pedido(p_codped)

        if not pedido:
            return f"Pedido nao encontrado com o codigo {p_codped}."

        return self.cadped.remove()

    def altera_status_pedido(self, p_codped: int, p_staped: str):
        pedido = self.busca_pedido(p_codped)

        if not pedido:
            return f"Pedido nao encontrado com o codigo {p_codped}."

        if not p_staped:
            return "Status do pedido nao pode ser vazio."

        p_staped = p_staped.upper()
        if p_staped not in ("A", "P", "E"):
            return "Status invalido. Use A para Aberto, P para Pendente ou E para Entregue."

        with self.conn.cursor() as cur:
            cur.execute(
                """
                    UPDATE cadped
                       SET staped = %s
                     WHERE codped = %s
                """,
                (p_staped, p_codped),
            )

        self.conn.commit()
        self.cadped.staped = p_staped
        return f"Status do pedido {p_codped} alterado para {p_staped}."

    def cria_item_pedido(self, p_iteped: Titeped):
        p_iteped.conn = self.conn

        # Valida os dados do item do pedido
        op_infmsg = p_iteped.valida()
        if op_infmsg:
            return op_infmsg

        op_infmsg = p_iteped.insere()
        if op_infmsg:
            msg_atualiza = self.atualiza_valor_pedido(p_iteped.codped)
            return f"{op_infmsg} {msg_atualiza}"

        return ""

    def busca_item_pedido(self, p_codped: int, p_numite: int):
        if not self.iteped.le_uk1(p_codped, p_numite):
            return None

        return self.iteped

    def lista_itens_pedido(self, p_codped: int):
        pedido = self.busca_pedido(p_codped)

        if not pedido:
            return None

        self.carrega_itens_do_pedido(p_codped)
        return self.lisiteped.itens
    def remove_item_pedido(self, p_codped: int, p_numite: int):
        item = self.busca_item_pedido(p_codped, p_numite)

        if not item:
            return f"Item {p_numite} do pedido {p_codped} nao encontrado."

        msg_remove = item.remove()
        msg_atualiza = self.atualiza_valor_pedido(p_codped)
        return f"{msg_remove} {msg_atualiza}"

    def valor_total_pedido(self, p_codped: int):
        return self.calcula_valor_total_pedido(p_codped)

    def carrega_itens_do_pedido(self, p_codped: int):
        self.lisiteped.params = (p_codped,)
        self.lisiteped.carrega_itens()

    def calcula_valor_total_pedido(self, p_codped: int):
        self.carrega_itens_do_pedido(p_codped)

        l_total = 0
        for item in self.lisiteped.itens:
            l_total += item.vlrite * item.qtdite

        return l_total

    def atualiza_valor_pedido(self, p_codped: int):
        pedido = self.busca_pedido(p_codped)

        if not pedido:
            return f"Pedido nao encontrado com o codigo {p_codped}."

        l_total = self.calcula_valor_total_pedido(p_codped)

        with self.conn.cursor() as cur:
            cur.execute(
                """
                    UPDATE cadped
                       SET vlrped = %s
                     WHERE codped = %s
                """,
                (l_total, p_codped),
            )

        self.conn.commit()
        self.cadped.vlrped = l_total
        return f"Valor total do pedido atualizado para {l_total}."

    @property
    def cadped(self):
        if not self._cadped:
            self._cadped = Tcadped(self.conn)
        return self._cadped

    @cadped.setter
    def cadped(self, value):
        self._cadped = value

    @property
    def iteped(self):
        if not self._iteped:
            self._iteped = Titeped(self.conn)
        return self._iteped

    @iteped.setter
    def iteped(self, value):
        self._iteped = value

    @property
    def lisiteped(self):
        if not self._lisiteped:
            self._lisiteped = Tlisiteped(self.conn)
            self._lisiteped.sql = f"""
                SELECT {self._lisiteped.colunas_do_select()}
                  FROM iteped
                 WHERE codped = %s
            """
        return self._lisiteped

    @lisiteped.setter
    def lisiteped(self, value):
        self._lisiteped = value



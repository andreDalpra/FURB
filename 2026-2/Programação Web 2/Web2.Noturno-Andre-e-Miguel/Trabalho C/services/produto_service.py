from models.cadpro import Tcadpro

class ProdutoService:
    def __init__(self, conn):
        self.conn = conn
        self._cadpro = None

    def cadastra_produto(self, p_cadpro: Tcadpro):
        p_cadpro.conn = self.conn

        # Valida os dados do produto
        op_infmsg = p_cadpro.valida()
        if  op_infmsg:
            return op_infmsg

        op_infmsg = p_cadpro.insere()
        if  op_infmsg:
            return op_infmsg
        
        self.cadpro = p_cadpro
        return ""

    def busca_produto(self, p_codpro: int):
        if not self.cadpro.le(p_codpro):
            return None

        return self.cadpro

    def remove_produto(self, p_codpro: int):
        produto = self.busca_produto(p_codpro)

        if not produto:
            return f"Produto com codigo {p_codpro} nao encontrado."

        return self.cadpro.remove()

    @property
    def cadpro(self):
        if self._cadpro is None:
            self._cadpro = Tcadpro(self.conn)
        return self._cadpro

    @cadpro.setter
    def cadpro(self, value):
        self._cadpro = value
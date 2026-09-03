from models.cadcli import Tcadcli

class ClienteService:
    def __init__(self, conn):
        self.conn = conn
        self._cadcli = None

    def cadastra_cliente(self, p_cadcli: Tcadcli):
        p_cadcli.conn = self.conn

        # Valida os dados do cliente
        op_infmsg = p_cadcli.valida()
        if  op_infmsg:
            return op_infmsg

        op_infmsg = p_cadcli.insere()
        if  op_infmsg:
            return op_infmsg
        
        self.cadcli = p_cadcli
        return ""

    def busca_cliente(self, p_codcli: int):
        if not self.cadcli.le(p_codcli):
            return None

        return self.cadcli

    def remove_cliente(self, p_codcli: int):
        cliente = self.busca_cliente(p_codcli)

        if not cliente:
            return f"Cliente com codigo {p_codcli} nao encontrado."

        return self.cadcli.remove()

    @property
    def cadcli(self):
        if self._cadcli is None:
            self._cadcli = Tcadcli(self.conn)
        return self._cadcli

    @cadcli.setter
    def cadcli(self, value):
        self._cadcli = value

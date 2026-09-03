class Tcadcli:
    # Variaveis que representam os campos da tabela
    codcli = None
    nomcli = None
    endcli = None
    telcli = None

    def __init__(self, conn):
        self.conn = conn

    # Le os dados pela chave
    def le(self, p_codcli: int):
        with self.conn.cursor() as cur:
            cur.execute(
                f"""
                    SELECT {self.colunas_do_select()}
                      FROM cadcli
                     WHERE codcli = %s
                """,
                (p_codcli,),
            )
            row = cur.fetchone()

        if row:
            self.carrega_do_dataset(row)
            return True

        return False

    def valida(self):
        if not self.nomcli:
            return "Nome do cliente nao pode ser vazio."
        if not self.endcli:
            return "Endereco do cliente nao pode ser vazio."
        if not self.telcli:
            return "Telefone do cliente nao pode ser vazio."

        return ""

    # Insere os dados na tabela
    def insere(self):
        with self.conn.cursor() as cur:
            cur.execute(
                f"""
                    INSERT INTO cadcli (nomcli, endcli, telcli)
                    VALUES (%s, %s, %s)
                """,
                (self.nomcli, self.endcli, self.telcli),
            )
            self.codcli = cur.lastrowid # Pega a sequence

        self.conn.commit()
        return f"Cliente cadastrado com codigo {self.codcli}."

    # Exclui os dados da tabela
    def remove(self):
        with self.conn.cursor() as cur:
            cur.execute(
                """
                    DELETE FROM cadcli
                    WHERE codcli = %s
                """,
                (self.codcli,),
            )
            removeu = cur.rowcount > 0

        self.conn.commit()
        if removeu:
            return "Cliente removido com sucesso."

        return f"Cliente {self.codcli} nao encontrado."

    # Carrega os dados do dataset para os atributos da classe
    def carrega_do_dataset(self, row):
        self.codcli = row["codcli"]
        self.nomcli = row["nomcli"]
        self.endcli = row["endcli"]
        self.telcli = row["telcli"]

    # Adiciona ao SQL SELECT <coluna1>,<coluna2>, etc  
    def colunas_do_select(self):
        return " cadcli.codcli, cadcli.nomcli, cadcli.endcli, cadcli.telcli "

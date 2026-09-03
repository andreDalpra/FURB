"""
Classe principal do SOAP, responsavel por montar os servicos.
"""
import os
import sys
from datetime import datetime

sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), "..")))

from flask import Flask
from spyne import Application, Integer, ServiceBase, Unicode, rpc
from spyne.protocol.soap import Soap11
from spyne.server.wsgi import WsgiApplication
from werkzeug.middleware.dispatcher import DispatcherMiddleware

import comum.conexao as cx
from models.cadcli import Tcadcli
from models.cadped import Tcadped
from models.cadpro import Tcadpro
from models.iteped import Titeped
from services.pedido_service import PedidoService
from services.cliente_service import ClienteService
from services.produto_service import ProdutoService

# Conexao global do banco de dados utilizada pelo SOAP
conn = cx.obter_conexao()

# ClienteSoapService é a classe que define os métodos SOAP relacionados aos clientes.
class ClienteSoapService(ServiceBase):
    @rpc(Unicode, Unicode, Unicode, _returns=Unicode)
    def cadastrar_cliente(ctx, nomcli, endcli, telcli):
        try:
            service = ClienteService(conn)
            cliente = Tcadcli(conn)
            cliente.nomcli = nomcli
            cliente.endcli = endcli
            cliente.telcli = telcli

            return service.cadastra_cliente(cliente)

        except Exception as erro:
            return str(erro)

    @rpc(Integer, _returns=Unicode)
    def buscar_cliente(ctx, codcli):
        try:
            service = ClienteService(conn)
            cliente = service.busca_cliente(codcli)

            if not cliente:
                return f"Cliente com codigo {codcli} nao encontrado."

            return (
                f"Codigo: {cliente.codcli} | "
                f"Nome: {cliente.nomcli} | "
                f"Endereco: {cliente.endcli} | "
                f"Telefone: {cliente.telcli}"
            )

        except Exception as erro:
            return str(erro)

    @rpc(Integer, _returns=Unicode)
    def remover_cliente(ctx, codcli):
        try:
            service = ClienteService(conn)
            return service.remove_cliente(codcli)

        except Exception as erro:
            return str(erro)

# ProdutoSoapService é a classe que define os métodos SOAP relacionados aos produtos.
class ProdutoSoapService(ServiceBase):
    @rpc(Integer, Unicode, Unicode, _returns=Unicode)
    def cadastrar_produto(ctx, codpro, despro, vlrpro):
        try:
            service = ProdutoService(conn)
            produto = Tcadpro(conn)
            produto.codpro = codpro
            produto.despro = despro
            produto.vlrpro = vlrpro

            return service.cadastra_produto(produto)

        except Exception as erro:
            return str(erro)

    @rpc(Integer, _returns=Unicode)
    def buscar_produto(ctx, codpro):
        try:
            service = ProdutoService(conn)
            produto = service.busca_produto(codpro)

            if not produto:
                return f"Produto com codigo {codpro} nao encontrado."

            return (
                f"Codigo: {produto.codpro} | "
                f"Descricao: {produto.despro} | "
                f"Valor: {produto.vlrpro}"
            )

        except Exception as erro:
            return str(erro)

    @rpc(Integer, _returns=Unicode)
    def remover_produto(ctx, codpro):
        try:
            service = ProdutoService(conn)
            return service.remove_produto(codpro)

        except Exception as erro:
            return str(erro)


# PedidoSoapService é a classe que define os métodos SOAP relacionados aos pedidos.
class PedidoSoapService(ServiceBase):
    @rpc(Integer, _returns=Unicode)
    def cadastrar_pedido(ctx, codcli):
        try:
            service = PedidoService(conn)
            pedido = Tcadped(conn)
            pedido.codcli = codcli
            pedido.datped = datetime.now().strftime("%Y-%m-%d")  # Data do pedido como data atual
            pedido.staped = "A"  # Status do pedido como "Aberto" como padrao
            pedido.obsped = None
            pedido.vlrped = 0.0  # Valor do pedido como 0.0 como padrao

            return service.cadastra_pedido(pedido)

        except Exception as erro:
            return str(erro)

    @rpc(Integer, _returns=Unicode)
    def buscar_pedido(ctx, codped):
        try:
            service = PedidoService(conn)
            pedido = service.busca_pedido(codped)

            if not pedido:
                return f"Pedido com codigo {codped} nao encontrado."

            return (
                f"Codigo: {pedido.codped} | "
                f"Codigo do Cliente: {pedido.codcli} | "
                f"Data: {pedido.datped} | "
                f"Status: {pedido.staped} | "
                f"Observacao: {pedido.obsped} | "
                f"Valor: {pedido.vlrped}"
            )

        except Exception as erro:
            return str(erro)

    @rpc(Integer, _returns=Unicode)
    def remover_pedido(ctx, codped):
        try:
            service = PedidoService(conn)
            return service.remove_pedido(codped)

        except Exception as erro:
            return str(erro)

    @rpc(Integer, Integer, Integer, Integer, _returns=Unicode)
    def cadastrar_item_pedido(ctx, codped, numite, codpro, qtdite):
        try:
            service = PedidoService(conn)
            item = Titeped(conn)
            item.codped = codped
            item.numite = numite
            item.codpro = codpro
            item.qtdite = qtdite

            return service.cria_item_pedido(item)

        except Exception as erro:
            return str(erro)

    @rpc(Integer, Integer, _returns=Unicode)
    def buscar_item_pedido(ctx, codped, numite):
        try:
            service = PedidoService(conn)
            item = service.busca_item_pedido(codped, numite)

            if not item:
                return f"Item {numite} do pedido {codped} nao encontrado."

            return (
                f"Codigo: {item.seqiteped} | "
                f"Pedido: {item.codped} | "
                f"Numero do Item: {item.numite} | "
                f"Produto: {item.codpro} | "
                f"Quantidade: {item.qtdite} | "
                f"Valor Unitario: {item.vlrite}"
            )

        except Exception as erro:
            return str(erro)

    @rpc(Integer, _returns=Unicode)
    def listar_itens_pedido(ctx, codped):
        try:
            service = PedidoService(conn)
            itens = service.lista_itens_pedido(codped)

            if itens is None:
                return f"Pedido com codigo {codped} nao encontrado."

            if not itens:
                return f"Pedido {codped} nao possui itens."

            retorno = []
            for item in itens:
                retorno.append(
                    f"Item: {item.numite} | "
                    f"Produto: {item.codpro} | "
                    f"Quantidade: {item.qtdite} | "
                    f"Valor Unitario: {item.vlrite}"
                )

            return " || ".join(retorno)

        except Exception as erro:
            return str(erro)

    @rpc(Integer, Integer, _returns=Unicode)
    def remover_item_pedido(ctx, codped, numite):
        try:
            service = PedidoService(conn)
            return service.remove_item_pedido(codped, numite)

        except Exception as erro:
            return str(erro)

    @rpc(Integer, Unicode, _returns=Unicode)
    def alterar_status_pedido(ctx, codped, staped):
        try:
            service = PedidoService(conn)
            return service.altera_status_pedido(codped, staped)

        except Exception as erro:
            return str(erro)

soap_app = Application(
    [ClienteSoapService, PedidoSoapService, ProdutoSoapService],
    tns="pudim_lanches.soap",
    in_protocol=Soap11(validator="lxml"),
    out_protocol=Soap11(),
)

flask_app = Flask(__name__)
flask_app.wsgi_app = DispatcherMiddleware(
    flask_app.wsgi_app,
    {
        "/soap": WsgiApplication(soap_app),
    },
)


@flask_app.route("/")
def index():
    return "API SOAP PUDIM_LANCHES rodando. Acesse /soap?wsdl"





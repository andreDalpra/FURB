package main.furb.mensagem;

import main.furb.enums.TipoMensagem;

public class CadastroMensagem {
	public static void cadastro() {
    	Mensagem.cadastrarMensagem(1, "Usuário deve ter login : Usuário: %s", TipoMensagem.WARNING);
    	Mensagem.cadastrarMensagem(2, "Usuário deve ter senha \n Usuário: %s", TipoMensagem.WARNING);
    	Mensagem.cadastrarMensagem(3, "Erro ao cadastrar usuário: %s", TipoMensagem.ERROR);
    	Mensagem.cadastrarMensagem(4, "O usuário deve ter um tipo ! \n Usuário: %s    Tipo: %s ", TipoMensagem.WARNING);
    	Mensagem.cadastrarMensagem(5, "Usuario ou senha inválidos", TipoMensagem.WARNING);
    	Mensagem.cadastrarMensagem(6, "Este usuario não pode ser excluído!\n Usuário: %s    Tipo: %s", TipoMensagem.WARNING);
    	Mensagem.cadastrarMensagem(7, "Senha inválida ",TipoMensagem.ERROR);
    	Mensagem.cadastrarMensagem(8, "Você precisa ser ADM, para acessar! ",TipoMensagem.WARNING);
    	Mensagem.cadastrarMensagem(10, "Usuário salvo com sucesso! \n Usuário: %s", TipoMensagem.OK);
    	Mensagem.cadastrarMensagem(11, "Usuário removido com sucesso! \n Usuário: %s", TipoMensagem.OK);
    	Mensagem.cadastrarMensagem(12, "Usuário não encontrado para exclusão! \n Usuário: %s", TipoMensagem.WARNING);
    	Mensagem.cadastrarMensagem(13, "Usuário já existe! \n Usuário: %s", TipoMensagem.ERROR);
    	Mensagem.cadastrarMensagem(14, "O produto deve ter um código! \n Produto: %s", TipoMensagem.ERROR);
    	Mensagem.cadastrarMensagem(15, "O produto deve ter um tipo ! \n Produto: %s    Tipo: %s ", TipoMensagem.WARNING);
    	Mensagem.cadastrarMensagem(16, "O produto deve ter um preço ! \n Produto: %s    Preço: %s ", TipoMensagem.WARNING);
       	Mensagem.cadastrarMensagem(17, "A quantidade não pode ser menor ou igual 0! \n Quantidade: %s", TipoMensagem.ERROR);
       	Mensagem.cadastrarMensagem(18, "Produto salvo com sucesso! \n Produto: %s", TipoMensagem.OK);
       	Mensagem.cadastrarMensagem(19, "Movimento salvo com sucesso! \n Movimento: %s", TipoMensagem.OK);
       	Mensagem.cadastrarMensagem(20, "Selecione um produto! \n Produto: %s", TipoMensagem.WARNING);
       	Mensagem.cadastrarMensagem(21, "Usuário inválido para a operação! \n Usuário: %s", TipoMensagem.WARNING);
       	Mensagem.cadastrarMensagem(22, "0 valor unitário não pode ser menor ou igual 0! \n Valor: %s", TipoMensagem.ERROR);
       	Mensagem.cadastrarMensagem(23, "Quantidade a movimentar é inválida ! \n Produto: %s    Quantidade: %s ", TipoMensagem.WARNING);
       	Mensagem.cadastrarMensagem(24, "Produto já existe! \n Produto: %s", TipoMensagem.ERROR);
    }
}

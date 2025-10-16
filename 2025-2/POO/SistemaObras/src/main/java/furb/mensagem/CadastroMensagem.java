package main.java.furb.mensagem;

import main.java.furb.enums.TipoMensagem;

public class CadastroMensagem {
	public static void cadastro() {
    	Mensagem.cadastrarMensagem(1, "Usuário deve ter login : Usuário: %s", TipoMensagem.WARNING);
    	Mensagem.cadastrarMensagem(2, "Usuário deve ter senha \n Usuário: %s", TipoMensagem.WARNING);
    	Mensagem.cadastrarMensagem(3, "Erro ao cadastrar usuário: %s", TipoMensagem.ERROR);
    	Mensagem.cadastrarMensagem(4, "O usuário deve ter um tipo ! \n Usuário: %s    Tipo: %s ", TipoMensagem.WARNING);
    	Mensagem.cadastrarMensagem(5, "O Instrutor deve ter um nome! \n Instrutor: %s", TipoMensagem.ERROR);
    	Mensagem.cadastrarMensagem(6, "Erro ao cadastrar aluno: %s", TipoMensagem.ERROR);
    	Mensagem.cadastrarMensagem(10, "Usuário salvo com sucesso! \n Usuário: %s", TipoMensagem.OK);
    	Mensagem.cadastrarMensagem(11, "Usuário removido com sucesso! \n Usuário: %s", TipoMensagem.OK);
    	Mensagem.cadastrarMensagem(12, "Usuário não encontrado para exclusão! \n Usuário: %s", TipoMensagem.WARNING);



    }
}

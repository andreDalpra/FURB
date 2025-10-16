package main.java.furb.app;
import static main.java.furb.controle.Sistema.*;

import main.java.furb.banco.Banco;
import main.java.furb.entidades.Usuario;
import main.java.furb.enums.TipoUsuario;
import main.java.furb.funcionalidades.UsuarioDAO;
import main.java.furb.mensagem.CadastroMensagem;

public class Main {

	public static void main(String[] args) {
		abreTela();
		CadastroMensagem.cadastro();
        UsuarioDAO dao = new UsuarioDAO();
        dao.deletar(2);
        

	}

}

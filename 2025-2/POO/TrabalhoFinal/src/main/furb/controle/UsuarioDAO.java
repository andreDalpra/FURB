package main.furb.controle;

import main.furb.entidades.Usuario;
import static main.furb.mensagem.Mensagem.*;

import main.furb.banco.Banco;

public class UsuarioDAO {

	public boolean inserir(Usuario p_usuario) {
		inicializaMensagem();
		if (!p_usuario.before_post()) {
			return false;
		}
		Banco.insert(p_usuario, Usuario.class);
		montaMensagem(10, new String[] { p_usuario.getCodusu() });
		mostrarMensagem();

		return true;

	}
}

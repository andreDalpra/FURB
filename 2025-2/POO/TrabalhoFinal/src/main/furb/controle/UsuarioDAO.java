package main.furb.controle;

import static main.furb.mensagem.Mensagem.inicializaMensagem;
import static main.furb.mensagem.Mensagem.montaMensagem;
import static main.furb.mensagem.Mensagem.mostrarMensagem;

import java.util.List;

import main.furb.banco.Banco;
import main.furb.entidades.Usuario;

public class UsuarioDAO {

	public boolean inserir(Usuario p_usuario) {
		inicializaMensagem();
		if (!p_usuario.before_post()) {
			return false;
		}
		Banco.insert(p_usuario, Usuario.class);
		montaMensagem(10, p_usuario.getCodusu());
		mostrarMensagem();

		return true;
	}

	public static Usuario obtemPelaSequence(int p_sequsu) {
		inicializaMensagem();
		List<Usuario> lista = Banco.listar(Usuario.class);

		for (Usuario u : lista) {
			if (u.getSequsu() == p_sequsu) {
				return u;
			}
		}
		return null;
	}

	public boolean excluir(Usuario p_usuario) {
		inicializaMensagem();
		if (!p_usuario.before_delete()) {
			return false;
		}

		return Banco.delete(Usuario.class, u -> u.getSequsu() == p_usuario.getSequsu());
	}

	public Usuario validaLogin(Usuario p_usuario) {
		List<Usuario> l_usuarios = Banco.listar(Usuario.class);

		for (Usuario u : l_usuarios) {
			if (u.getCodusu().equals(p_usuario.getCodusu()) && u.getSenusu() == p_usuario.getSenusu()) {

				return u; 
			}
		}

		return null;
	}

}

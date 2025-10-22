package main.java.furb.controle;

import static main.java.furb.mensagem.Mensagem.*;

import main.java.furb.banco.Banco;
import main.java.furb.entidades.Usuario;

public class UsuarioDAO {

	public boolean inserir(Usuario p_usu) {
		inicializaMensagem();
		if (!p_usu.before_post()) {

			return false;
		}

		Banco.salvar(p_usu, Usuario.class);
		montaMensagem(10, new String[] { p_usu.getCodusu() });
		mostrarMensagem();
		return true;
	}

	public static void listar() {

		System.out.println("\nUsuários cadastrados:");
		var l_usuarios = Banco.listar(Usuario.class);

		if (l_usuarios.isEmpty()) {
			System.out.println("Nenhum usuário encontrado.\n");
			return;
		}

		for (Usuario u : l_usuarios) {
			System.out.printf("%d - %s%n", u.getSequsu(), u.getCodusu());
		}
	}

	public boolean deletar(int p_sequsu) {
		inicializaMensagem();
		boolean l_removido = Banco.excluir(Usuario.class, u -> ((Usuario) u).getSequsu() == p_sequsu);

		if (!l_removido) {
			montaMensagem(12, new String[] { String.valueOf(p_sequsu) });
		}
		mostrarMensagem();
		return l_removido;
	}
}

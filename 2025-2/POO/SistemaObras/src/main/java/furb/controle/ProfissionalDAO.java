package main.java.furb.controle;

import static main.java.furb.mensagem.Mensagem.inicializaMensagem;
import static main.java.furb.mensagem.Mensagem.montaMensagem;
import static main.java.furb.mensagem.Mensagem.mostrarMensagem;

import main.java.furb.banco.Banco;
import main.java.furb.entidades.Profissional;
import main.java.furb.entidades.Usuario;

public class ProfissionalDAO {

	public boolean inserir(Profissional p_pro) {
		inicializaMensagem();
		if (!p_pro.before_post())
			return false;

		Banco.salvar(p_pro, Profissional.class);
		montaMensagem(16, new String[] { p_pro.getNompro() });
		mostrarMensagem();
		return true;

	}

	public void listar() {

		var l_pro = Banco.listar(Profissional.class);
		if (l_pro.isEmpty()) {
			System.out.println("Nenhum profissional encontrado\n");
			return;
		}

		for (Profissional p : l_pro) {
			System.out.printf("%d - %s%n,", p.getNompro());
		}
	}

	public boolean deletar(int p_seqpro) {
		inicializaMensagem();
		boolean l_removido = Banco.excluir(Profissional.class, p -> ((Profissional) p).getSeqpro() == p_seqpro);
		if (!l_removido) {
			montaMensagem(17, new String[] { String.valueOf(p_seqpro) });
		}
		mostrarMensagem();
		return l_removido;
	}
}

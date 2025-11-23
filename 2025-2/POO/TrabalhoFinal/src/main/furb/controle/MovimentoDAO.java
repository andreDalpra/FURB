package main.furb.controle;

import static main.furb.mensagem.Mensagem.inicializaMensagem;
import static main.furb.mensagem.Mensagem.montaMensagem;
import static main.furb.mensagem.Mensagem.mostrarMensagem;

import java.util.List;

import main.furb.banco.Banco;
import main.furb.entidades.MovimentoEstoque;
import main.furb.entidades.Usuario;

public class MovimentoDAO {

	public boolean inserir(MovimentoEstoque p_mov) {
		inicializaMensagem();
		if (!p_mov.before_post()) {
			return false;
		}
		Banco.insert(p_mov, MovimentoEstoque.class);
		montaMensagem(10, String.valueOf(p_mov.getSeqmov()));
		mostrarMensagem();

		return true;
	}
	
	public static MovimentoEstoque obtemPelaSequence(int p_seqmov) {
		inicializaMensagem();
		List<MovimentoEstoque> lista = Banco.listar(MovimentoEstoque.class);

		for (MovimentoEstoque m : lista) {
			if (m.getSeqmov() == p_seqmov) {
				return m;
			}
		}
		return null;
	}
	
	public boolean excluir(MovimentoEstoque p_mov) {
		inicializaMensagem();
		if (!p_mov.before_delete()) {
			return false;
		}

		return Banco.delete(MovimentoEstoque.class, m -> m.getSeqmov() == p_mov.getSeqmov());
	}
	
}

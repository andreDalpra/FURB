package main.furb.controle;

import static main.furb.mensagem.Mensagem.inicializaMensagem;
import static main.furb.mensagem.Mensagem.montaMensagem;
import static main.furb.mensagem.Mensagem.mostrarMensagem;

import java.util.List;

import main.furb.banco.Banco;
import static main.furb.banco.Banco.*;
import main.furb.entidades.Produto;
import main.furb.entidades.Usuario;

public class ProdutoDAO {
	
	public boolean inserir(Produto p_produto) {
		inicializaMensagem();
		if (!p_produto.before_post()) {
			return false;
		}
		Banco.insert(p_produto, Produto.class);
		montaMensagem(10, p_produto.getCodpro());
		mostrarMensagem();

		return true;
	} 

	public static Produto obtemPelaSequence(int p_seqpro) {
        inicializaMensagem();
		List<Produto> lista = Banco.listar(Produto.class);

		for (Produto p : lista) {
			if (p.getSeqpro() == p_seqpro) {
				return p;
			}
		}
		return null;
	}
}

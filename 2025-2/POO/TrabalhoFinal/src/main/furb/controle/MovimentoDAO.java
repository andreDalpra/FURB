package main.furb.controle;

import static main.furb.mensagem.Mensagem.inicializaMensagem;
import static main.furb.mensagem.Mensagem.montaMensagem;
import static main.furb.mensagem.Mensagem.mostrarMensagem;

import java.util.List;

import main.furb.banco.Banco;
import main.furb.entidades.MovimentoEstoque;
import main.furb.entidades.Produto;
import main.furb.enums.TipoMovimento;

public class MovimentoDAO {

    public boolean inserir(MovimentoEstoque p_mov) {
        inicializaMensagem();

        if (!p_mov.before_post()) {
            return false;
        }

     // ATUALIZA ESTOQUE
        Produto prod = p_mov.getSeqpro();
        int qtdMov = p_mov.getQtdmov();

        if (prod != null) {
            if (p_mov.getTipmov() == TipoMovimento.ENTRADA) {
                prod.setQtdproduto(prod.getQtdproduto() + qtdMov);
            } else if (p_mov.getTipmov() == TipoMovimento.SAIDA) {
                prod.setQtdproduto(prod.getQtdproduto() - qtdMov);

                if (prod.getQtdproduto() < 0) {
                    prod.setQtdproduto(0);
                }
            }

            // ATUALIZA NO BANCO
            Banco.update(
                Produto.class,
                x -> x.getSeqpro() == prod.getSeqpro(), 
                prod                                   
            );
        }

        // SALVA O MOVIMENTO       
        Banco.insert(p_mov, MovimentoEstoque.class);

        montaMensagem(19, String.valueOf(p_mov.getSeqmov()));
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

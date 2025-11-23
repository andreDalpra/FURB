package main.furb.controle;

import static main.furb.mensagem.Mensagem.inicializaMensagem;
import static main.furb.mensagem.Mensagem.montaMensagem;
import static main.furb.mensagem.Mensagem.mostrarMensagem;

import java.util.List;

import main.furb.banco.Banco;
import main.furb.entidades.MovimentoEstoque;
import main.furb.entidades.Produto;
import main.furb.enums.TipoMovimento;

/**
 * DAO responsável pelo gerenciamento de movimentos de estoque.
 * <p>
 * Realiza o cadastro do movimento, executa as validações da entidade
 * e atualiza o estoque do produto conforme o tipo de movimento
 * (entrada ou saída).
 * </p>
 */
public class MovimentoDAO {

    /**
     * Insere um movimento de estoque no sistema.
     * <p>
     * Antes da gravação, executa regras definidas em {@code before_post()}.  
     * Caso o movimento seja válido, ajusta o estoque do produto associado:
     * <ul>
     *   <li><b>ENTRADA</b>: soma quantidade</li>
     *   <li><b>SAIDA</b>: subtrai quantidade (não permite valor negativo)</li>
     * </ul>
     * Após o ajuste, o movimento é salvo e uma mensagem de sucesso é exibida.
     * </p>
     *
     * @param p_mov movimento a ser registrado
     * @return true se o movimento foi salvo com sucesso
     */
    public boolean inserir(MovimentoEstoque p_mov) {
        inicializaMensagem();

        if (!p_mov.before_post()) {
            return false;
        }

        // Atualiza estoque do produto
        Produto prod = p_mov.getSeqpro();
        int qtdMov = p_mov.getQtdmov();

        if (prod != null) {

            if (p_mov.getTipmov() == TipoMovimento.ENTRADA) {
                prod.setQtdproduto(prod.getQtdproduto() + qtdMov);

            } else if (p_mov.getTipmov() == TipoMovimento.SAIDA) {
                prod.setQtdproduto(prod.getQtdproduto() - qtdMov);

                // evita estoque negativo
                if (prod.getQtdproduto() < 0) {
                    prod.setQtdproduto(0);
                }
            }

            // Atualiza produto no banco
            Banco.update(
                Produto.class,
                x -> x.getSeqpro() == prod.getSeqpro(),
                prod
            );
        }

        // Salva movimento
        Banco.insert(p_mov, MovimentoEstoque.class);

        montaMensagem(19, String.valueOf(p_mov.getSeqmov()));
        mostrarMensagem();

        return true;
    }

    /**
     * Obtém um movimento pelo número de sequence.
     *
     * @param p_seqmov sequence do movimento
     * @return instância de {@link MovimentoEstoque} ou null se não encontrado
     */
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
}

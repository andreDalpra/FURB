package main.furb.controle;

import static main.furb.mensagem.Mensagem.inicializaMensagem;
import static main.furb.mensagem.Mensagem.montaMensagem;
import static main.furb.mensagem.Mensagem.mostrarMensagem;

import java.util.List;

import main.furb.banco.Banco;
import main.furb.entidades.Produto;

/**
 * DAO responsável pelo gerenciamento de produtos.
 * <p>
 * Permite inserir, alterar, excluir e consultar produtos por sequence.
 * Toda a persistência é delegada à classe {@link Banco}.
 * </p>
 */
public class ProdutoDAO {

    /**
     * Insere um novo produto no sistema.
     * <p>
     * Antes do cadastro, executa as regras definidas em {@code before_post()}
     * para validação e geração de sequence. Ao final, exibe mensagem de sucesso.
     * </p>
     *
     * @param p_produto produto a ser inserido
     * @return true se o produto foi salvo com sucesso
     */
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

    /**
     * Exclui um produto do sistema.
     *
     * @param p_produto produto que será removido
     * @return true se o produto foi excluído
     */
    public boolean excluir(Produto p_produto) {
        inicializaMensagem();

        return Banco.delete(
            Produto.class,
            p -> p.getSeqpro() == p_produto.getSeqpro()
        );
    }

    /**
     * Atualiza os dados de um produto existente.
     * <p>
     * O produto é localizado pela sua sequence, substituindo
     * completamente os dados anteriores.
     * </p>
     *
     * @param p_produto produto contendo os novos dados
     * @return true se a alteração foi realizada
     */
    public boolean alterar(Produto p_produto) {
        return Banco.update(
            Produto.class,
            x -> x.getSeqpro() == p_produto.getSeqpro(),
            p_produto
        );
    }

    /**
     * Obtém um produto a partir de sua sequence.
     *
     * @param seqpro sequence do produto desejado
     * @return instância de {@link Produto} ou null se não encontrado
     */
    public static Produto obtemPelaSequence(int seqpro) {
        inicializaMensagem();

        List<Produto> lista = Banco.listar(Produto.class);

        for (Produto p : lista) {
            if (p.getSeqpro() == seqpro) {
                return p;
            }
        }

        return null;
    }
}

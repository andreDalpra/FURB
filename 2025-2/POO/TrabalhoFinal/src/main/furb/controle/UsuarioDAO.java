package main.furb.controle;

import static main.furb.mensagem.Mensagem.inicializaMensagem;
import static main.furb.mensagem.Mensagem.montaMensagem;
import static main.furb.mensagem.Mensagem.mostrarMensagem;

import java.util.List;

import main.furb.banco.Banco;
import main.furb.entidades.Usuario;

/**
 * DAO responsável pelo gerenciamento de usuários.
 * <p>
 * Fornece operações de inserção, exclusão, consulta por sequence e
 * validação de login. Toda comunicação com o banco de dados é feita por meio
 * da classe {@link Banco}.
 * </p>
 */
public class UsuarioDAO {

    /**
     * Insere um novo usuário no sistema.
     * <p>
     * Antes de salvar, executa as regras definidas em {@code before_post()}.
     * Caso o cadastro seja bem-sucedido, uma mensagem é montada e exibida.
     * </p>
     *
     * @param p_usuario usuário a ser inserido
     * @return true se o usuário foi salvo com sucesso
     */
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

    /**
     * Obtém um usuário a partir de sua sequence.
     *
     * @param p_sequsu valor da sequence do usuário
     * @return instância de {@link Usuario} ou null se não encontrado
     */
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

    /**
     * Exclui um usuário do sistema.
     * <p>
     * Antes da exclusão, executa as regras definidas em {@code before_delete()}.
     * </p>
     *
     * @param p_usuario usuário a ser removido
     * @return true se o usuário foi excluído
     */
    public boolean excluir(Usuario p_usuario) {
        inicializaMensagem();

        if (!p_usuario.before_delete()) {
            return false;
        }

        return Banco.delete(Usuario.class, u -> u.getSequsu() == p_usuario.getSequsu());
    }

    /**
     * Valida o login de um usuário.
     * <p>
     * Compara o código e a senha com os usuários cadastrados.
     * </p>
     *
     * @param p_usuario usuário contendo código e senha informados na tela
     * @return o usuário encontrado ou null caso inválido
     */
    public Usuario validaLogin(Usuario p_usuario) {
        List<Usuario> l_usuarios = Banco.listar(Usuario.class);

        for (Usuario u : l_usuarios) {
            if (u.getCodusu().equals(p_usuario.getCodusu())
                    && u.getSenusu() == p_usuario.getSenusu()) {

                return u;
            }
        }

        return null;
    }
}

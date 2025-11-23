package main.furb.app;

import main.furb.entidades.Usuario;

/**
 * Interface genérica para telas do sistema.
 * <p>
 * Define operações básicas para carregar dados entre a interface
 * gráfica e o objeto correspondente.
 * </p>
 *
 * @param <T> tipo da entidade manipulada pela tela
 */
public interface Tela<T> {

    /**
     * Lê os dados presentes na tela e retorna um objeto preenchido.
     *
     * @return objeto preenchido com os dados inseridos na interface
     */
    T carrega_no_objeto();

    /**
     * Preenche os componentes da tela com os dados de um objeto.
     *
     * @param p_obj objeto contendo os dados que serão exibidos na tela
     */
    void carrega_do_objeto(T p_obj);

    /**
     * Carrega os dados do usuário na tela de login, caso aplicável.
     *
     * @param p_usuario usuário autenticado
     */
    default void carrega_login(Usuario p_usuario) {
    }
}

package main.furb.app;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import main.furb.mensagem.CadastroMensagem;

/**
 * Interface base para entidades do sistema, todas as entidades implementam essas interface.
 * <p>
 * Define operações comuns como conversão para CSV, leitura de CSV,
 * validações e eventos executados antes de operações de persistência.
 * Também fornece utilitários estáticos para inicialização do programa
 * e conversão simples para HTML.
 * </p>
 */
public interface Sistema {

    /**
     * Converte o objeto para uma linha no formato CSV.
     *
     * @return string contendo os dados separados por delimitador
     */
    String toCSV();

    /**
     * Carrega os dados do objeto a partir de uma linha CSV.
     *
     * @param linha linha contendo os valores separados por delimitador
     */
    void fromCSV(String linha);

    /**
     * Inicializa o programa executando configurações essenciais
     * e carregando mensagens padrão.
     */
    public static void abrePrograma() {
        SwingUtilities.invokeLater(() -> new JFrame().dispose());
        CadastroMensagem.cadastro();
    }

    /**
     * Converte um texto simples para HTML, incluindo quebra de linha.
     *
     * @param p_txtHTML texto a ser convertido
     * @return versão HTML do texto
     */
    public static String converteHTML(String p_txtHTML) {
        return "<html>" + p_txtHTML.replace("\n", "<br>") + "</html>";
    }

    /**
     * Executa validações referentes ao objeto.
     *
     * @return true se os dados forem válidos
     */
    boolean valida();

    /**
     * Método executado antes de salvar um objeto.
     *
     * @return true para permitir o salvamento
     */
    default boolean before_post() {
        return false;
    }

    /**
     * Método executado antes de excluir um objeto.
     *
     * @return true para permitir a exclusão
     */
    default boolean before_delete() {
        return false;
    }

    /**
     * Valida a senha do objeto, caso aplicável.
     *
     * @return true se a senha for válida
     */
    default boolean validaSenha() {
        return false;
    }
}

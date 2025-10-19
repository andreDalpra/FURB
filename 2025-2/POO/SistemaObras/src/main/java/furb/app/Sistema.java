package main.java.furb.app;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.util.List;

import main.java.furb.banco.Banco;
import main.java.furb.mensagem.CadastroMensagem;

public interface Sistema {

	public static void abrePrograma() {
		SwingUtilities.invokeLater(() -> new JFrame().dispose());
	    CadastroMensagem.cadastro();
	}

	boolean valida();

	default boolean cadastrar() {
		return false;

	}

	default boolean before_post() {
		return false;
	}

	default boolean before_delete() {
		return false;
	}

	default boolean validaSenha() {
		return false;
	}
	
	default <T> int obtem_proximaSequencia(Class<T> p_tipo, java.util.function.ToIntFunction<T> p_getter) {
        List<T> l_lista = Banco.listar(p_tipo);
        return l_lista.stream()
                .mapToInt(p_getter)
                .max()
                .orElse(0) + 1;
    }

	public static String converteHTML(String p_txtHTML) {
		return "<html>" + p_txtHTML.replace("\n", "<br>") + "</html>";
	}

}

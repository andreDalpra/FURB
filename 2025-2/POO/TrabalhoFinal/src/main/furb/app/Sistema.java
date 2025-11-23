package main.furb.app;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import main.furb.mensagem.CadastroMensagem;

public interface Sistema {
	String toCSV();

	void fromCSV(String linha);
	
	public static void abrePrograma() {
		SwingUtilities.invokeLater(() -> new JFrame().dispose());
		CadastroMensagem.cadastro();
	}

	public static String converteHTML(String p_txtHTML) {
		return "<html>" + p_txtHTML.replace("\n", "<br>") + "</html>";
	}

	boolean valida();

	default boolean before_post() {
		return false;
	}

	default boolean before_delete() {
		return false;
	}

	default boolean validaSenha() {
		return false;
	}

}

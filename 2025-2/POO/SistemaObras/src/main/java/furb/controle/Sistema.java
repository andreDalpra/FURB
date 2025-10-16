package main.java.furb.controle;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public interface Sistema {
	
	boolean valida();
	
	default boolean validaSenha() {
		return false;
	}
	
	public static String converteHTML(String p_txtHTML) {
		return "<html>" + p_txtHTML.replace("\n", "<br>") + "</html>";
	}
	
	public static void abreTela() {
		SwingUtilities.invokeLater(() -> new JFrame().dispose());
	}
}

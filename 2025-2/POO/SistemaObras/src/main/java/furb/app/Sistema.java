package main.java.furb.app;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public interface Sistema {
	
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
	
	public static String converteHTML(String p_txtHTML) {
		return "<html>" + p_txtHTML.replace("\n", "<br>") + "</html>";
	}
	
	public static void abreTela() {
		SwingUtilities.invokeLater(() -> new JFrame().dispose());
	}
}

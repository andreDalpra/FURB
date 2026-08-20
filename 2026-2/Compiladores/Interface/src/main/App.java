/*
 * Classe que roda a aplicação 
 * 
 */
package main;

import javax.swing.SwingUtilities;

import compilador.Interface;

public class App {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Interface tela = new Interface();
			tela.setVisible(true);
		});
	}
}

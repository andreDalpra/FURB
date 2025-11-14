package main.furb.app;

import java.awt.EventQueue;

import ui.Login;
import static main.furb.app.Sistema.abrePrograma;
public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					abrePrograma();
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}

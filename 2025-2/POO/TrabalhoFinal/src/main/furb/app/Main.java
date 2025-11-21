/* TRABALHO FINAL DE POO
 * PROFESSOR : ANDRÉ FELIPE BURGUER
 * 
 * ALUNOS : ANDRÉ LUIZ E JOÃO VITOR MICK
 * 
 * 
 * O PROJETO É UM SISTEMA DE MOVIMENTAÇÃO DE ESTOQUE, ONDE É POSSIVEL CRIAR SEU USUARIO LOGAR, CRIAR PRODUTO, MVOIMENTAR O ESTOQUE DO PRODUTO, ETC..
 * 
 */
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

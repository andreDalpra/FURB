package main.java.furb.app;

import static main.java.furb.app.Sistema.abrePrograma;

import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.google.gson.Gson;

import main.java.furb.controle.UsuarioDAO;
import main.java.furb.entidades.Usuario;
import main.java.furb.mensagem.CadastroMensagem;

import static main.java.furb.mensagem.Mensagem.*;

public class Main {
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JFrame().dispose());
	    CadastroMensagem.cadastro();

		System.out.println("BEM VINDO AO SISTEMA DE OBRAS\n\n");
		UsuarioDAO dao = new UsuarioDAO();
		Usuario usu = new Usuario();
		usu.cadastrar();
		if (isTemMensagem()) {
			mostrarMensagem();
		}
		System.out.println("FIM");

	}

}

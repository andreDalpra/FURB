package main.java.furb.app;


import static main.java.furb.mensagem.Mensagem.isTemMensagem;
import static main.java.furb.mensagem.Mensagem.mostrarMensagem;
import static main.java.furb.app.Sistema.*;

import java.util.Scanner;

import main.java.furb.controle.UsuarioDAO;
import main.java.furb.entidades.Usuario;

public class Main {
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		abrePrograma();

		System.out.println("BEM VINDO AO SISTEMA DE OBRAS\n\n");
		Usuario usu = new Usuario();
		UsuarioDAO usudao = new UsuarioDAO();
		usu.excluir();
		if (isTemMensagem()) {
			mostrarMensagem();
		}
		System.out.println("FIM");

	}

}

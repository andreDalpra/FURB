package main.java.furb.app;

import main.java.furb.entidades.Engenheiro;
import main.java.furb.entidades.Profissional;
import main.java.furb.entidades.Usuario;

public class Menu {
    public static void Sistema(){
    	
		System.out.println("BEM VINDO AO SISTEMA DE OBRAS\n\n");
		Profissional pro = new Engenheiro();
		Usuario usu = new Usuario();
		//pro.cadastrar();
		usu.excluir();
		usu.cadastrar();
		
		System.out.println("FIM");
    }
}

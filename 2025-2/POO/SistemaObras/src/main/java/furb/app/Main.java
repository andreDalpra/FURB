package main.java.furb.app;

import static main.java.furb.app.Sistema.abrePrograma;

import java.util.Scanner;

public class Main {
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		abrePrograma();
        Menu.Sistema();
	}

}

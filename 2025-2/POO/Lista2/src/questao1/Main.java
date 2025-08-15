package questao1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    	 Scanner sc = new Scanner(System.in);
         Pessoa p = new Pessoa();
         //Capturando os DADOS
         for (int i = 0; i < p.altura.length; i++) {
             System.out.println("Qual seu nome? ");
             p.nome[i] = sc.next();
             System.out.println("Qual seu peso? ");
             p.peso[i] = sc.nextDouble();
             System.out.println("Qual sua altura? ");
             p.altura[i] = sc.nextDouble();           
         }
         System.out.println("Relatório IMC");
         //IMPRIMINDO OS DADOS
         for (int i = p.altura.length - 1 ; i >= 0 ; i--) {
             System.out.println("Nome: "+ p.nome[i]);
             System.out.println("\nPeso: "+ p.peso[i]);
             System.out.println("\nAltura: "+ p.altura[i]);
             System.out.println("\nIMC: "+ p.calcularIMC(i));
         }
         sc.close();
	}
}

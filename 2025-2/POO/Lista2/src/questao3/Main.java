package questao3;

import java.util.Scanner;

public class Main {
	String carlos = "";
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        ContaBancaria conta1 = new ContaBancaria();
        ContaBancaria conta2 = new ContaBancaria();
        ContaBancaria.teste = 1.0;      
        Math.round(ContaBancaria.teste);
        System.out.print("num conta1: ");
        conta1.setNumero(sc.nextLine());
        System.out.print("titular conta1: ");
        conta1.setTitular(sc.nextLine());
        
        System.out.print("num conta2: ");
        conta2.setNumero(sc.nextLine());
        System.out.print("titular conta2: ");
        conta2.setTitular(sc.nextLine());

        conta1.depositar(1000);
        conta1.depositar(700);

        conta2.depositar(5000);
        conta2.sacar(3000);
        
        
        
        
        
        
        
        
        conta2.transferir(conta1, 1800);


        System.out.println("saldo final");
        System.out.println(conta1.getTitular() + ": R$" + conta1.getSaldo());
        System.out.println(conta2.getTitular() + ": R$" + conta2.getSaldo());

        sc.close();
    }
}
import java.util.Scanner;

public class EX16 {
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        double vlrCompra, vlrCliente, vlrTroco;

        
        System.out.print("Informe o valor da compra: ");
        vlrCompra = sc.nextDouble();
        System.out.print("Informe o valor dado pelo cliente: ");
        vlrCliente = sc.nextDouble();

            vlrTroco = vlrCliente - vlrCompra;

            
            int notas100 = (int) (vlrTroco / 100); 
            double sobra = vlrTroco % 100;         

            int notas10 = (int) (sobra / 10);      
            sobra = sobra % 10; 

            int notas1 = (int) sobra;             
           
            int totalNotas = notas100 + notas10 + notas1;

            System.out.println("\nO número mínimo de notas de troco é: " + totalNotas);
            System.out.println("Quantidade de notas de 100 necessárias é: " + notas100);
            System.out.println("Quantidade de notas de 10 necessárias é: " + notas10);
            System.out.println("Quantidade de notas de 1 necessárias é: " + notas1);

            sc.close();
        }

       
    }


import java.util.Scanner;
public class EX15 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int numero, centenas, dezenas, unidades;

        System.out.println("Informe um número");
        numero = sc.nextInt();

        centenas = numero / 100;
        dezenas = (numero % 100) / 10;
        unidades = numero % 10; 
        
        System.out.println("O número "+numero+" possui "+centenas+ " centenas - "+dezenas+" dezenas - "+unidades+" unidades");
        sc.close();
    }
}
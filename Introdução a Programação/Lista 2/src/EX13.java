import java.util.Scanner;
public class EX13 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        double altura, comprimento, area, qtdAzulejos, vlrCompra;

        System.out.println("Informe a altura: ");
        altura = sc.nextDouble();
        System.out.println("Informe o comprimento: ");
        comprimento = sc.nextDouble();

        area = comprimento * altura;
        qtdAzulejos = Math.ceil(area / 9);
        
        vlrCompra = qtdAzulejos * 12.5;

        System.out.println("O valor final é de: "+ vlrCompra);


        
        
        sc.close();
    }
}
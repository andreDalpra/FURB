import java.util.Locale;
import java.util.Scanner;
public class EX6 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);//Tem que isso locale para ler um decimal :D
        double pesoPrato, custoPrato;

        System.out.println("Qual o preso do prato?(KG) ");
        pesoPrato = sc.nextDouble();

        custoPrato = 25 * pesoPrato;
        System.out.println("O preço do prato do cliente é de: "+custoPrato);
        sc.close();
    }
}
import java.util.Scanner;
public class EX9 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        double raio,altura,volume;

        System.out.println("Informe o raio");
        raio = sc.nextDouble();
        System.out.println("Informe a altura");
        altura = sc.nextDouble();

        volume = 3.1415 * (raio*raio) * altura;

        System.out.println("O volume da lata de óleo é de: "+volume);
        sc.close();
    }
}

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Pessoa p1 = new Pessoa();

        System.out.println("Qual seu Peso?");
        p1.peso = sc.nextDouble();
        System.out.println("Qual sua altura? ");
        p1.altura = sc.nextDouble();

        double imc = p1.calcularIMC();
        System.out.println("Seu IMC é" + imc);

    }
}

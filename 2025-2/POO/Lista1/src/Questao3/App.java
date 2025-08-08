
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Pessoa p1 = new Pessoa();
        Pessoa p2 = new Pessoa();
        Pessoa p3 = new Pessoa();

        System.out.println("Qual seu Peso?");
        p1.peso = sc.nextDouble();
        System.out.println("Qual sua altura? ");
        p1.altura = sc.nextDouble();
        System.out.println("Qual seu Peso?");
        p2.peso = sc.nextDouble();
        System.out.println("Qual sua altura? ");
        p2.altura = sc.nextDouble();
        System.out.println("Qual seu Peso?");
        p3.peso = sc.nextDouble();
        System.out.println("Qual sua altura? ");
        p3.altura = sc.nextDouble();

        double imc1 = p1.calcularIMC();
        double imc2 = p2.calcularIMC();
        double imc3 = p3.calcularIMC();

        System.out.println("Pessoa 1 IMC é" + imc1);
        System.out.println("Pessoa 2 IMC é" + imc2);
        System.out.println("Pessoa 3 IMC é" + imc3);
    }
}

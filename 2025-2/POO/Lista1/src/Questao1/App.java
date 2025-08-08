package Questao1;

public class App {

    public static void main(String[] args) {
        Pessoa p1 = new Pessoa();
        p1.peso = 75;
        p1.altura = 1.78;
        double imc = p1.calcularIMC();
        System.out.println("Seu IMC é de: " + imc);
    }
}

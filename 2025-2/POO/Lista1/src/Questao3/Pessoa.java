package Questao3;

public class Pessoa {

    double peso;
    double altura;

    double calcularIMC() {
        return peso / (altura * altura);
    }
}

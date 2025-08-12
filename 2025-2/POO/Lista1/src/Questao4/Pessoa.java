package Questao4;

public class Pessoa {

    String nome[] = new String[3];
    double peso[] = new double[3];
    double altura[] = new double[3];

    double calcularIMC(int i) {
        return peso[i] * (altura[i] * altura[i]);
    }
}

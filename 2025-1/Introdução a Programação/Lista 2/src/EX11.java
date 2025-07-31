import java.util.Scanner;
public class EX11 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double tempCelsius, tempFahre;

        System.out.println("Informe a temperatura em Celsius: ");
        tempCelsius = sc.nextDouble();

        tempFahre = (tempCelsius * (9.0/5.0)) + 32;
        System.out.println(tempFahre);
        sc.close();
    }
}
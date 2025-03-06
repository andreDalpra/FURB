import java.util.Scanner;
public class EX8 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double vlrDolar, vlrReal;

    System.out.println("Qual valor em dolares recebidos?");
    vlrDolar = sc.nextDouble();
    vlrReal = vlrDolar * 5.65;

    System.out.println("O atendente deve devolver R$"+vlrReal+" para o cliente");
        sc.close();
    }
}
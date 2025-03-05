import java.util.Scanner;
public class EX3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double vlrLitro, vlrPag, litrosAbastecidos;

        System.out.println("Informe o preço do litro: ");
        vlrLitro = sc.nextDouble();
        System.out.println("Informe o valor do pagamento: ");
        vlrPag = sc.nextDouble();

        litrosAbastecidos = vlrPag / vlrLitro;

        System.out.println(litrosAbastecidos);
        sc.close();
    }
}
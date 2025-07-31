import java.util.Scanner;
public class EX2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double vlr, vlrDesconto, vlrNovo;

        System.out.println("Informe o valor do sapato");
        vlr = sc.nextInt();

        vlrDesconto = vlr * 0.12;
        vlrNovo = vlr - vlrDesconto;

        System.out.println("O valor do desconto é de"+vlrDesconto);
        System.out.println("O preço do par de sapatos com desconto é"+vlrNovo);

        sc.close();

    }
}

import java.util.Scanner;
public class EX5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double custoChip, custoAlimento, totalCusto;
        int totalFrangos;

        System.out.println("Qual o total de frangos na sua granja");
        totalFrangos = sc.nextInt();

        custoChip = totalFrangos * 4;
        custoAlimento = totalFrangos * 3.5;
        totalCusto = custoAlimento + custoChip;
        System.out.println(totalCusto);


        sc.close();
    }
}
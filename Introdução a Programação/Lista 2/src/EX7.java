import java.util.Scanner;
public class EX7 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        double qtd350, qtd600, qtd2000;
        System.out.println("Quantas garrafas de 350 ml? ");
        qtd350 = sc.nextDouble();
        System.out.println("Quantas garrafas de 600 ml? ");
        qtd600 = sc.nextDouble();
        System.out.println("Quantas garrafas de 2L ? ");
        qtd2000 = sc.nextDouble();

        double totalLitros = ((qtd350 * 350) + (qtd600 * 600) + (qtd2000 * 2000)) / 1000;
        System.out.println("Total de litros: "+totalLitros);
        sc.close();
    }
}
import java.util.Scanner;
public class EX12 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String nome;
        int qtdHoras, nrDependentes;
        double salBruto, salLiquido;

        System.out.println("Informe o nome do funcionário: ");
        nome = sc.next();
        System.out.println("Informe a quantidade de horas trabalhadas por "+nome+": ");
        qtdHoras = sc.nextInt();
        System.out.println("Informe a quantidade de dependentes de  "+nome+": ");
        nrDependentes = sc.nextInt();

        salBruto = (10 * qtdHoras) + (60 * nrDependentes);
        salLiquido = salBruto - (salBruto * 0.135);

        System.out.println("Funcionário :" +nome);
        System.out.println("Horas Trabalhadas :" +qtdHoras);
        System.out.println("Dependentes :" +nrDependentes);
        System.out.println("Salário Bruto :" +salBruto);
        System.out.println("Salário Líquido :" +salLiquido);
        sc.close();
    }
}
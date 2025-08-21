
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Funcionario[] f_funcionario = new Funcionario[5];
        for (int i = 0; i < f_funcionario.length; i++) {

            f_funcionario[i] = new Funcionario(); 

            System.out.print("Informe o nome do funcionário " + (i + 1) + ": ");
            f_funcionario[i].setNome(sc.next());
            System.out.println("Informe o salário do(a) "+ f_funcionario[i].getNome()+":");
            f_funcionario[i].setSalario(sc.nextDouble());
        }

    }
}

import java.util.Scanner; 

public class EX1 {
  
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int largura, comprimento, area;

        System.out.println("Informe a largura");
        largura = sc.nextInt();
        System.out.println("Informe o comprimento");
        comprimento = sc.nextInt();

        area = largura * comprimento;
        System.out.println(area);

        sc.close();
    }

}

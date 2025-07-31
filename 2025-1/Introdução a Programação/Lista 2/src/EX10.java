import java.util.Scanner;
public class EX10 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double cat1, cat2, hipotenusa;

        System.out.println("Qual o valor do cateto 1?");
        cat1 = sc.nextDouble();
        System.out.println("Qual o valor do cateto 2?");
        cat2 = sc.nextDouble();

        hipotenusa = Math.sqrt((cat1 * cat1) + (cat2*cat2));
        System.out.println("A hipotenusa vale: "+hipotenusa);
        sc.close();
    }
}
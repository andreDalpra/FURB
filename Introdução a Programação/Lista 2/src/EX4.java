import java.util.Scanner;
public class EX4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        double nota1, nota2, nota3;
        double media;

        System.out.println("Nota 1:");
        nota1 = sc.nextInt();
        System.out.println("Nota 2:");
        nota2 = sc.nextInt();
        System.out.println("Nota 3:");
        nota3 = sc.nextInt();

        media = ((nota1 * 5) + (nota2 * 3) + (nota3 * 2)) / 10;

        System.out.println(media);



        sc.close();
    }
}

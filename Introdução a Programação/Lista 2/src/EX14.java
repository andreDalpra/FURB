import java.util.Scanner;
public class EX14 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        double distancia, tempo, veloMedia, qtdCombustivel;

        System.out.println("Informe a distância: ");
        distancia = sc.nextDouble();
        System.out.println("Informe o tempo: ");
        tempo = sc.nextDouble();

        veloMedia = distancia / tempo;

        qtdCombustivel = distancia / 12;

        System.out.println("A velocidade média "+veloMedia+" Km/h");
        System.out.println("A quantidade de combustivel usado foi de "+qtdCombustivel);
        sc.close();
    }
}

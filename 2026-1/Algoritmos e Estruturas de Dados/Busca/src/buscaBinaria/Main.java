package buscaBinaria;
public class Main {

    public static void main(String[] args) {
        Integer[] numeros = { 1, 3, 5, 7, 9 };

        BuscaLinear<Integer> buscaLinear = new BuscaLinear<>();
        buscaLinear.setInfo(numeros);
        System.out.println(buscaLinear.buscar(7));

        BuscaBinaria<Integer> buscaBinaria = new BuscaBinaria<>();
        buscaBinaria.setInfo(numeros);
        System.out.println(buscaBinaria.buscar(7));

        BuscaLinearVetorOrdenado<Integer> buscaOrdenada = new BuscaLinearVetorOrdenado<>();
        buscaOrdenada.setInfo(numeros);
        System.out.println(buscaOrdenada.buscar(7));
    }
}

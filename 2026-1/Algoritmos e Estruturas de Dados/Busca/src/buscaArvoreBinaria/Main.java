package buscaArvoreBinaria;

public class Main {

    public static void main(String[] args) {
        ArvoreBinariaBusca<Integer> arvore = new ArvoreBinariaBusca<>();

        arvore.inserir(10);
        arvore.inserir(5);
        arvore.inserir(15);
        arvore.inserir(3);
        arvore.inserir(7);

        System.out.println(arvore);
        System.out.println(arvore.contarNos());
        System.out.println(arvore.pertence(7));
        System.out.println(arvore.pertence(20));
        System.out.println(arvore.buscar(15).getInfo());
    }
}

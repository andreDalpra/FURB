package buscaBinaria;
public class BuscaBinaria<T extends Comparable<T>> extends BuscaAbstract {

    public int buscar(T valor) {
        Object[] info = getInfo();
        int inicio = 0;
        int fim = info.length - 1;

        while (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            @SuppressWarnings("unchecked")
            T item = (T) info[meio];
            int comparacao = item.compareTo(valor);

            if (comparacao == 0) {
                return meio;
            }

            if (comparacao < 0) {
                inicio = meio + 1;
            } else {
                fim = meio - 1;
            }
        }

        return -1;
    }
}

package buscaBinaria;
public class BuscaLinearVetorOrdenado<T extends Comparable<T>> extends BuscaAbstract {

    public int buscar(T valor) {
        Object[] info = getInfo();

        for (int i = 0; i < info.length; i++) {
            @SuppressWarnings("unchecked")
            T item = (T) info[i];
            int comparacao = item.compareTo(valor);

            if (comparacao == 0) {
                return i;
            }

            if (comparacao > 0) {
                return -1;
            }
        }

        return -1;
    }
}

package buscaBinaria;
import java.util.Objects;

public class BuscaLinear<T> extends BuscaAbstract {

    public int buscar(T valor) {
        Object[] info = getInfo();

        for (int i = 0; i < info.length; i++) {
            if (Objects.equals(info[i], valor)) {
                return i;
            }
        }

        return -1;
    }
}

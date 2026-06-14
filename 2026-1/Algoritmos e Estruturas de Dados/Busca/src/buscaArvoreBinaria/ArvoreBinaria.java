package buscaArvoreBinaria;

import java.util.Objects;

public class ArvoreBinaria<T> extends ArvoreBinariaAbstract<T> {

    @Override
    public void setRaiz(NoArvoreBinaria<T> raiz) {
        super.setRaiz(raiz);
    }

    @Override
    public NoArvoreBinaria<T> buscar(T info) {
        return buscar(getRaiz(), info);
    }

    private NoArvoreBinaria<T> buscar(NoArvoreBinaria<T> no, T info) {
        if (no == null) {
            return null;
        }

        if (Objects.equals(no.getInfo(), info)) {
            return no;
        }

        NoArvoreBinaria<T> noEncontrado = buscar(no.getEsquerda(), info);

        if (noEncontrado != null) {
            return noEncontrado;
        }

        return buscar(no.getDireita(), info);
    }
}

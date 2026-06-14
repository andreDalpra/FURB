package buscaArvoreBinaria;

public class ArvoreBinariaBusca<T extends Comparable<T>> extends ArvoreBinariaAbstract<T> {

    public void inserir(T info) {
        NoArvoreBinaria<T> novoNo = new NoArvoreBinaria<>(info);

        if (estaVazia()) {
            setRaiz(novoNo);
            return;
        }

        NoArvoreBinaria<T> noAtual = getRaiz();
        NoArvoreBinaria<T> noPai = null;

        while (noAtual != null) {
            noPai = noAtual;

            if (info.compareTo(noAtual.getInfo()) < 0) {
                noAtual = noAtual.getEsquerda();
            } else {
                noAtual = noAtual.getDireita();
            }
        }

        if (info.compareTo(noPai.getInfo()) < 0) {
            noPai.setEsquerda(novoNo);
        } else {
            noPai.setDireita(novoNo);
        }
    }

    @Override
    public NoArvoreBinaria<T> buscar(T info) {
        NoArvoreBinaria<T> noAtual = getRaiz();

        while (noAtual != null) {
            int comparacao = info.compareTo(noAtual.getInfo());

            if (comparacao == 0) {
                return noAtual;
            }

            if (comparacao < 0) {
                noAtual = noAtual.getEsquerda();
            } else {
                noAtual = noAtual.getDireita();
            }
        }

        return null;
    }
}

package main;

public class FilaLista<T> {

    private ListaEncadeada<T> lista;

    public FilaLista() {
        this.lista = new ListaEncadeada<>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fila: [");

        NoLista<T> p = lista.getPrimeiro();

        while (p != null) {
            sb.append(p.getInfo());

            if (p.getProximo() != null) {
                sb.append(",");
            }

            p = p.getProximo();
        }

        sb.append("]");
        return sb.toString();
    }
}
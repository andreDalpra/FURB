package main.java.furb.enums;

public enum TipoProfissional {
    INTERNO(true),
    EXTERNO(false);

    private final boolean exgusu;

    TipoProfissional(boolean p_exgusu) {
        this.exgusu = p_exgusu;
    }

    public boolean exigeUsuario() {
        return exgusu;
    }
}

package main.furb.entidades;

import main.furb.app.Sistema;
import main.furb.enums.TipoUsuario;
import static main.furb.mensagem.Mensagem.*;
import static main.furb.banco.Banco.*;

/**
 * Representa um usuário do sistema.
 * <p>
 * Contém dados de identificação, senha, e tipo de perfil. 
 * Implementa validações de cadastro, regras de senha, conversão para CSV
 * e eventos executados antes de operações de gravação e exclusão.
 * </p>
 */
public class Usuario implements Sistema {

    private int sequsu;
    private String codusu;
    private int senusu;
    private String nomusu;
    private String emlusu;
    private TipoUsuario tipusu;

    /**
     * Realiza validações gerais do usuário.
     *
     * @return true se todos os campos estiverem válidos
     */
    @Override
    public boolean valida() {
        if (codusu == null || codusu.isBlank()) {
            montaMensagem(1, codusu);
            return false;
        }

        if (!validaSenha()) {
            return false;
        }

        if (tipusu == null) {
            montaMensagem(4, codusu, tipusu.name());
        }

        return true;
    }

    /**
     * Executa regras antes de salvar (post) um usuário.
     * <p>
     * Gera sequence caso o usuário seja novo, valida dados
     * e evita duplicidade de código.
     * </p>
     *
     * @return true se o cadastro puder continuar
     */
    @Override
    public boolean before_post() {
        inicializaMensagem();

        if (sequsu == 0) {
            sequsu = obtemSequence(Usuario.class);
        }

        if (!valida()) {
            return false;
        }

        var l_usuarios = listar(Usuario.class);
        boolean l_existe = l_usuarios.stream()
            .anyMatch(u -> u.getCodusu().equalsIgnoreCase(this.codusu));

        if (l_existe) {
            montaMensagem(13, this.codusu);
            return false;
        }

        return true;
    }

    /**
     * Regras executadas antes de excluir um usuário.
     * <p>
     * Usuários do tipo ADM não podem ser removidos.
     * </p>
     *
     * @return true se a exclusão for permitida
     */
    @Override
    public boolean before_delete() {
        if (tipusu == TipoUsuario.ADM) {
            montaMensagem(6, codusu, String.valueOf(tipusu));
            return false;
        }
        return true;
    }

    /**
     * Valida a senha do usuário.
     * <p>
     * Verifica faixa permitida, repetição, sequência crescente e
     * sequência decrescente.
     * </p>
     *
     * @return true se a senha for considerada segura
     */
    @Override
    public boolean validaSenha() {

        String l_senhaStr = String.valueOf(senusu);

        if (senusu < 1000 || senusu > 9999) {
            montaMensagem(7, codusu, l_senhaStr);
            return false;
        }

        boolean l_todosIguais = l_senhaStr.chars()
            .allMatch(c -> c == l_senhaStr.charAt(0));
        if (l_todosIguais) {
            montaMensagem(7, codusu, l_senhaStr);
            return false;
        }

        boolean l_sequencialCrescente = true;
        for (int l_i = 0; l_i < l_senhaStr.length() - 1; l_i++) {
            if (l_senhaStr.charAt(l_i + 1) != l_senhaStr.charAt(l_i) + 1) {
                l_sequencialCrescente = false;
                break;
            }
        }
        if (l_sequencialCrescente) {
            montaMensagem(7, codusu, l_senhaStr);
            return false;
        }

        boolean l_sequencialDecrescente = true;
        for (int l_i = 0; l_i < l_senhaStr.length() - 1; l_i++) {
            if (l_senhaStr.charAt(l_i + 1) != l_senhaStr.charAt(l_i) - 1) {
                l_sequencialDecrescente = false;
                break;
            }
        }
        if (l_sequencialDecrescente) {
            montaMensagem(7, codusu, l_senhaStr);
            return false;
        }

        if (l_senhaStr.equals("0000") || l_senhaStr.equals("1234")) {
            montaMensagem(7, codusu, l_senhaStr);
            return false;
        }

        return true;
    }

    // ---------------- GETTERS E SETTERS ---------------- //

    public int getSequsu() {
        return sequsu;
    }

    public void setSequsu(int sequsu) {
        this.sequsu = sequsu;
    }

    public String getCodusu() {
        return codusu;
    }

    public void setCodusu(String codusu) {
        this.codusu = codusu;
    }

    public int getSenusu() {
        return senusu;
    }

    public void setSenusu(int senusu) {
        this.senusu = senusu;
    }

    public String getNomusu() {
        return nomusu;
    }

    public void setNomusu(String nomusu) {
        this.nomusu = nomusu;
    }

    public String getEmlusu() {
        return emlusu;
    }

    public void setEmlusu(String emlusu) {
        this.emlusu = emlusu;
    }

    public TipoUsuario getTipusu() {
        return tipusu;
    }

    public void setTipusu(TipoUsuario tipusu) {
        this.tipusu = tipusu;
    }

    /**
     * Converte o usuário para uma linha CSV.
     *
     * @return linha contendo os campos do usuário separados por ponto e vírgula
     */
    @Override
    public String toCSV() {
        return sequsu + ";" + codusu + ";" + senusu + ";" + nomusu + ";" + emlusu + ";" + tipusu.name();
    }

    /**
     * Carrega os dados do usuário a partir de uma linha CSV.
     *
     * @param linha linha contendo dados separados por ponto e vírgula
     */
    @Override
    public void fromCSV(String linha) {
        String[] partes = linha.split(";");

        this.sequsu = Integer.parseInt(partes[0]);
        this.codusu = partes[1];
        this.senusu = Integer.parseInt(partes[2]);
        this.nomusu = partes[3];
        this.emlusu = partes[4];
        this.tipusu = TipoUsuario.valueOf(partes[5]);
    }
}

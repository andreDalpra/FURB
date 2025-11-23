package main.furb.entidades;

import static main.furb.banco.Banco.listar;
import static main.furb.banco.Banco.obtemSequence;
import static main.furb.mensagem.Mensagem.montaMensagem;

import main.furb.app.Sistema;
import main.furb.controle.UsuarioDAO;
import main.furb.enums.TipoProduto;

/**
 * Representa um produto do sistema de estoque.
 * <p>
 * Contém código, descrição, preço unitário, quantidade, tipo e o usuário
 * responsável pelo cadastro. Implementa validações, geração de sequence
 * e conversão para CSV.
 * </p>
 */
public class Produto implements Sistema {

    private int seqpro;
    private String codpro;
    private String despro;
    private double prrunt;
    private int qtdproduto;
    private Usuario sequsu;
    private TipoProduto tipro;

    /**
     * Valida os dados principais do produto.
     *
     * @return true se os dados estiverem corretos
     */
    @Override
    public boolean valida() {

        if (codpro == null || codpro.isBlank()) {
            montaMensagem(14, codpro);
            return false;
        }

        if (tipro == null) {
            montaMensagem(15, codpro, tipro.name());
        }

        if (prrunt <= 0) {
            montaMensagem(16, codpro, String.valueOf(prrunt));
            return false;
        }

        if (qtdproduto <= 0) {
            montaMensagem(17, String.valueOf(qtdproduto));
            return false;
        }

        return true;
    }

    /**
     * Regras executadas antes de salvar um produto.
     * <p>
     * Gera sequence quando necessário, valida os dados e evita duplicidade pelo código.
     * </p>
     *
     * @return true se o cadastro puder continuar
     */
    @Override
    public boolean before_post() {

        if (seqpro == 0) {
            seqpro = obtemSequence(Produto.class);
        }

        if (!valida()) {
            return false;
        }

        var l_produtos = listar(Produto.class);
        boolean l_existe = l_produtos.stream()
            .anyMatch(p -> p.getCodpro().equalsIgnoreCase(this.codpro));

        if (l_existe) {
            montaMensagem(24, this.codpro);
            return false;
        }

        return true;
    }

    // ---------------- GETTERS E SETTERS ---------------- //

    public int getSeqpro() {
        return seqpro;
    }

    public void setSeqpro(int seqpro) {
        this.seqpro = seqpro;
    }

    public String getCodpro() {
        return codpro;
    }

    public void setCodpro(String codpro) {
        this.codpro = codpro;
    }

    public String getDespro() {
        return despro;
    }

    public void setDespro(String despro) {
        this.despro = despro;
    }

    public double getPrrunt() {
        return prrunt;
    }

    public void setPrrunt(double prrunt) {
        this.prrunt = prrunt;
    }

    public int getQtdproduto() {
        return qtdproduto;
    }

    public void setQtdproduto(int qtdproduto) {
        this.qtdproduto = qtdproduto;
    }

    public Usuario getSequsu() {
        return sequsu;
    }

    public void setSequsu(Usuario sequsu) {
        this.sequsu = sequsu;
    }

    public TipoProduto getTipro() {
        return tipro;
    }

    public void setTipro(TipoProduto tipro) {
        this.tipro = tipro;
    }

    /**
     * Converte o produto para uma linha CSV.
     *
     * @return linha contendo os atributos separados por ponto e vírgula
     */
    @Override
    public String toCSV() {
        return seqpro + ";" + codpro + ";" + despro + ";" + prrunt + ";" + qtdproduto + ";"
                + (sequsu != null ? sequsu.getSequsu() : 0) + ";" + tipro;
    }

    /**
     * Preenche o objeto produto a partir de uma linha CSV.
     *
     * @param linha linha contendo os dados do produto
     */
    @Override
    public void fromCSV(String linha) {
        String[] partes = linha.split(";");

        this.seqpro = Integer.parseInt(partes[0]);
        this.codpro = partes[1];
        this.despro = partes[2];
        this.prrunt = Double.parseDouble(partes[3]);
        this.qtdproduto = Integer.parseInt(partes[4]);

        int seqUsuario = Integer.parseInt(partes[5]);
        this.sequsu = UsuarioDAO.obtemPelaSequence(seqUsuario);

        this.tipro = TipoProduto.valueOf(partes[6]);
    }
}

package main.furb.entidades;

import static main.furb.banco.Banco.obtemSequence;
import static main.furb.mensagem.Mensagem.montaMensagem;

import java.time.LocalDate;

import main.furb.app.Sistema;
import main.furb.controle.ProdutoDAO;
import main.furb.controle.UsuarioDAO;
import main.furb.enums.TipoMovimento;
import main.furb.enums.TipoRetirada;

/**
 * Representa um movimento de estoque, contendo dados sobre produto,
 * quantidade movimentada, valores, tipo da operação, retirada e usuário.
 * <p>
 * Implementa validações, geração automática de sequence e conversão
 * para CSV.
 * </p>
 */
public class MovimentoEstoque implements Sistema {

    private int seqmov;
    private LocalDate datmov;
    private TipoRetirada tipret;
    private Produto seqpro;
    private Usuario sequsu;
    private int qtdmov;
    private double vlrunt;
    private double vlrtot;
    private TipoMovimento tipmov;

    /**
     * Valida os dados essenciais do movimento de estoque.
     *
     * @return true se todos os dados estiverem válidos
     */
    @Override
    public boolean valida() {

        if (seqpro == null) {
            montaMensagem(20, String.valueOf(seqpro));
            return false;
        }

        if (sequsu == null) {
            montaMensagem(21, String.valueOf(sequsu));
            return false;
        }

        if (qtdmov <= 0) {
            montaMensagem(17, String.valueOf(qtdmov));
            return false;
        }

        if (vlrunt <= 0) {
            montaMensagem(22, String.valueOf(vlrunt));
            return false;
        }

        if (tipmov == TipoMovimento.SAIDA) {
            if (seqpro.getQtdproduto() < qtdmov) {
                montaMensagem(23, String.valueOf(seqpro), String.valueOf(qtdmov));
                return false;
            }
        }

        return true;
    }

    /**
     * Regras executadas antes de salvar um movimento.
     * <p>
     * Gera sequence automaticamente e valida a operação.
     * </p>
     *
     * @return true se o movimento puder ser salvo
     */
    @Override
    public boolean before_post() {

        if (seqmov == 0) {
            seqmov = obtemSequence(MovimentoEstoque.class);
        }

        if (!valida()) {
            return false;
        }

        return true;
    }

    // ---------------- GETTERS E SETTERS ---------------- //

    public int getSeqmov() {
        return seqmov;
    }

    public void setSeqmov(int seqmov) {
        this.seqmov = seqmov;
    }

    public LocalDate getDatmov() {
        return datmov;
    }

    public void setDatmov(LocalDate datmov) {
        this.datmov = datmov;
    }

    public TipoRetirada getTipret() {
        return tipret;
    }

    public void setTipret(TipoRetirada tipret) {
        this.tipret = tipret;
    }

    public Produto getSeqpro() {
        return seqpro;
    }

    public void setSeqpro(Produto seqpro) {
        this.seqpro = seqpro;
    }

    public Usuario getSequsu() {
        return sequsu;
    }

    public void setSequsu(Usuario sequsu) {
        this.sequsu = sequsu;
    }

    public int getQtdmov() {
        return qtdmov;
    }

    public void setQtdmov(int qtdmov) {
        this.qtdmov = qtdmov;
    }

    public double getVlrunt() {
        return vlrunt;
    }

    public void setVlrunt(double vlrunt) {
        this.vlrunt = vlrunt;
    }

    public double getVlrtot() {
        return vlrtot;
    }

    public void setVlrtot(double vlrtot) {
        this.vlrtot = vlrtot;
    }

    public TipoMovimento getTipmov() {
        return tipmov;
    }

    public void setTipmov(TipoMovimento tipmov) {
        this.tipmov = tipmov;
    }

    /**
     * Converte o movimento para uma linha CSV.
     *
     * @return linha contendo os dados separados por ponto e vírgula
     */
    @Override
    public String toCSV() {

        String l_tipretCSV = (tipret != null ? tipret.toString() : "");

        return seqmov + ";" + datmov + ";" + l_tipretCSV + ";" 
                + (seqpro != null ? seqpro.getSeqpro() : 0) + ";"
                + (sequsu != null ? sequsu.getSequsu() : 0) + ";" 
                + qtdmov + ";" + vlrunt + ";" + vlrtot + ";" + tipmov + ";";
    }

    /**
     * Preenche o objeto movimento a partir de uma linha CSV.
     *
     * @param linha linha contendo os dados do movimento
     */
    @Override
    public void fromCSV(String linha) {
        String[] p = linha.split(";");

        this.seqmov = Integer.parseInt(p[0]);
        this.datmov = LocalDate.parse(p[1]);
        this.tipret = p[2].isBlank() ? null : TipoRetirada.valueOf(p[2]);

        int seqProduto = Integer.parseInt(p[3]);
        this.seqpro = ProdutoDAO.obtemPelaSequence(seqProduto);

        int seqUsuario = Integer.parseInt(p[4]);
        this.sequsu = UsuarioDAO.obtemPelaSequence(seqUsuario);

        this.qtdmov = Integer.parseInt(p[5]);
        this.vlrunt = Double.parseDouble(p[6]);
        this.vlrtot = Double.parseDouble(p[7]);

        this.tipmov = TipoMovimento.valueOf(p[8]);
    }
}

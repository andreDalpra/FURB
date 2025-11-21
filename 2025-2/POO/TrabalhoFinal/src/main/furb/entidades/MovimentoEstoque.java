package main.furb.entidades;

import static main.furb.banco.Banco.obtemSequence;
import static main.furb.mensagem.Mensagem.montaMensagem;

import java.time.LocalDate;

import main.furb.app.Sistema;
import main.furb.controle.ProdutoDAO;
import main.furb.controle.UsuarioDAO;
import main.furb.enums.TipoMovimento;
import main.furb.enums.TipoRetirada;

public class MovimentoEstoque implements Sistema {

	private int seqmov; // SEQMOV
	private LocalDate datmov; // DATMOV
	private TipoRetirada tipret; // TIPRET // perguntar para o andré
	private Produto seqpro; // SEQPRO -> FK CADPRO
	private Usuario sequsu; // SEQUSU -> FK CADUSU
	private int qtdmov; // QTDMOV
	private double vlrunt; // VLRUNT
	private double vlrtot; // VLRTOT
	private TipoMovimento tipmov; // TIPMOV // vai ter o que nesse enum??? pdf nao fala

	@Override
	public boolean valida() {

		if (seqpro == null) {
			montaMensagem(1, new String[] { "Produto inválido" });
			return false;
		}

		if (sequsu == null) {
			montaMensagem(1, new String[] { "Usuário inválido" });
			return false;
		}

		if (qtdmov <= 0) {
			montaMensagem(1, new String[] { "Quantidade deve ser maior que zero" });
			return false;
		}

		if (vlrunt <= 0) {
			montaMensagem(1, new String[] { "Valor unitário inválido" });
			return false;
		}

		if (tipmov == TipoMovimento.SAIDA) {
			if (seqpro.getQtdproduto() < qtdmov) {
				montaMensagem(1, new String[] { "Estoque insuficiente para saída" });
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean before_post() {

		if (seqmov == 0) {
			seqmov = obtemSequence(MovimentoEstoque.class);
		}

		if (!valida()) {
			return false;
		}

		// falta coisa

		return true;
	}

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

	@Override
	public String toCSV() {
		return seqmov + ";" + datmov + ";" + tipret + ";" + (seqpro != null ? seqpro.getSeqpro() : 0) + ";"
				+ (sequsu != null ? sequsu.getSequsu() : 0) + ";" + qtdmov + ";" + vlrunt + ";" + vlrtot + ";" + tipmov
				+ ";";
	}

	@Override
	public void fromCSV(String linha) {
		String[] p = linha.split(";");

		this.seqmov = Integer.parseInt(p[0]);
		this.datmov = LocalDate.parse(p[1]);
		this.tipret = TipoRetirada.valueOf(p[2]);

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

package main.java.furb.entidades;

import static main.java.furb.mensagem.Mensagem.montaMensagem;

import main.java.furb.app.Sistema;

public abstract class Profissional implements Sistema {
	private int seqpro;
	private String nompro;
	private String cpfpro;
	private Usuario codusu;

	public Profissional(int seqpro, String nompro, String cpfpro, Usuario codusu) {
		this.seqpro = seqpro;
		this.nompro = nompro;
		this.cpfpro = cpfpro;
		this.codusu = codusu;
	}

	public Profissional() {

	}

	@Override
	public boolean valida() {

		if (nompro == null) {
			montaMensagem(14, new String[] { nompro });
			return false;
		}

		if (cpfpro == null) {
			montaMensagem(15, new String[] { cpfpro });
		}
		return true;

	}

	@Override
	public boolean before_post() {

		if (seqpro == 0) {
			seqpro = obtem_proximaSequencia(Profissional.class, Profissional::getSeqpro);
		}
		
		if(!valida()) {
			return false;
		}
		
		
		return true;

	}

	public int getSeqpro() {
		return seqpro;
	}

	public String getNompro() {
		return nompro;
	}

	public String getCpfpro() {
		return cpfpro;
	}

	public Usuario getCodusu() {
		return codusu;
	}

}

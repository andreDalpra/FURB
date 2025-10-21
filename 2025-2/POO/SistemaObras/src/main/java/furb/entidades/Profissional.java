package main.java.furb.entidades;

import static main.java.furb.mensagem.Mensagem.montaMensagem;

import java.util.List;

import main.java.furb.app.Sistema;

public abstract class Profissional implements Sistema {

	private int seqpro;
	private String nompro;
	private String cpfpro;
	private Usuario usuario;
	private List <Obra> obras;

	// 🔹 Construtor completo
	public Profissional(int p_seqpro, String p_nompro, String p_cpfpro, Usuario p_usuario, List<Obra> p_obras) { 
		this.seqpro = p_seqpro;
		this.nompro = p_nompro;
		this.cpfpro = p_cpfpro;
		this.usuario = p_usuario;
		this.obras = p_obras;
	}

	// 🔹 Construtor padrão
	public Profissional() {
	}

	@Override
	public boolean valida() {

		if (nompro == null || nompro.isBlank()) {
			montaMensagem(14, new String[] { "Nome do profissional" });
			return false;
		}

		if (cpfpro == null || cpfpro.isBlank()) {
			montaMensagem(15, new String[] { "CPF do profissional" });
			return false;
		}

		return true;
	}

	@Override
	public boolean before_post() {

		if (seqpro == 0) {
			seqpro = obtem_proximaSequencia(Profissional.class, Profissional::getSeqpro);
		}

		return valida();
	}

	// 🔹 Getters e Setters
	public int getSeqpro() {
		return seqpro;
	}

	public String getNompro() {
		return nompro;
	}

	public String getCpfpro() {
		return cpfpro;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Obra> getObras() {
		return obras;
	}

	public void setObras(List<Obra> obras) {
		this.obras = obras;
	}

	// 🔹 Representação textual
	@Override
	public String toString() {
		String vinculo = (usuario != null) ? "Vinculado a usuário: " + usuario.getCodusu() : "Sem vínculo de usuário";
		return String.format("[%d] %s (%s) - %s", seqpro, nompro, cpfpro, vinculo);
	}
}

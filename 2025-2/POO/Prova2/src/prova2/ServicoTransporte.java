package prova2;

public abstract class ServicoTransporte implements Transporte {

	private String nomeServico;
	private String descricao;

	public ServicoTransporte(String nomeServico, String descricao) {
		this.nomeServico = nomeServico;
		this.descricao = descricao;
	}

	public String getNomeServico() {
		return nomeServico;
	}

	public String getDescricao() {
		return descricao;
	}

}

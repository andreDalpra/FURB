package prova2;

public class TransporteOnibus extends ServicoTransporte {

	public TransporteOnibus(String nomeServico, String descricao) {
		super(nomeServico, descricao);
	}

	@Override
	public double calcularTarifa(double distancia) {

		return distancia * 1.20;
	}

	@Override
	public double calcularTempoEstimado(double distancia) {
		return distancia * 2.0;
	}

}

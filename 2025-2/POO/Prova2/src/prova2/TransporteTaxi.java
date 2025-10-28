package prova2;

public class TransporteTaxi extends ServicoTransporte {

	public TransporteTaxi(String nomeServico, String descricao) {
		super(nomeServico, descricao);
	}

	@Override
	public double calcularTarifa(double distancia) {
		return distancia * 3.5;
	}

	@Override
	public double calcularTempoEstimado(double distancia) {
		return distancia * 1.5;
	}

}

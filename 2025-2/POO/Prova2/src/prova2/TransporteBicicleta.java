package prova2;

public class TransporteBicicleta extends ServicoTransporte {

	public TransporteBicicleta(String nomeServico, String descricao) {
		super(nomeServico, descricao);
	}

	@Override
	public double calcularTarifa(double distancia) {
		return distancia * 0.8;
	}

	@Override
	public double calcularTempoEstimado(double distancia) {
		return distancia * 3.0;
	}

}

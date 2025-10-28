package prova2;

public class TransporteMetro extends ServicoTransporte {

	public TransporteMetro(String nomeServico, String descricao) {
		super(nomeServico, descricao);
	}

	@Override
	public double calcularTarifa(double distancia) {
		return distancia * 2.0;
	}

	@Override
	public double calcularTempoEstimado(double distancia) {
		return distancia * 1.0;
	}

}

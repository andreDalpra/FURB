package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import prova2.ServicoTransporte;
import prova2.TransporteBicicleta;
import prova2.TransporteOnibus;
import prova2.TransporteTaxi;

class Teste {

	@Test
	public void Teste01() {
		ServicoTransporte s1 = new TransporteOnibus("Onibus", "Transporte Coletivo convencional");
		assertEquals(18, s1.calcularTarifa(15));
	}

	@Test
	public void Teste02() {
		ServicoTransporte s1 = new TransporteTaxi("Táxi", "Transporte individual sob demanda");
		assertEquals(45, s1.calcularTempoEstimado(30));
	}

	@Test
	public void Teste03() {
		ServicoTransporte s1 = new TransporteBicicleta("Bicicleta", "Locação de Bicicletas compartilhadas");
		s1.calcularTarifa(18.25);
		assertEquals(14.4, s1.calcularTarifa(18));
	}
}

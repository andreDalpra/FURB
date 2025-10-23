package main.java.furb.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import main.java.furb.entidades.Engenheiro;
import main.java.furb.entidades.Obra;
import main.java.furb.entidades.ObraPublica;
import main.java.furb.entidades.Profissional;
import main.java.furb.enums.TipoObra;
import static main.java.furb.mensagem.Mensagem.*;

public class Teste {

	@Disabled
	@Test
	public void Teste01() {
		Profissional p1 = new Engenheiro();
		p1.setCpfpro("123456789");
		p1.setNompro("Joao Silva");
		assertEquals("123456789", p1.getCpfpro());
		assertEquals("Joao Silva", p1.getNompro());
	}

	@Disabled
	@Test
	public void Teste02() {
		Profissional p1 = new Engenheiro();
		p1.setCpfpro(null);
		p1.setNompro("");
		assertEquals(null, p1.getCpfpro());
		assertEquals("", p1.getNompro());
	}

	@Disabled
	@Test
	public void Teste03() {
		Profissional p1 = new Engenheiro();
		Obra prj = new ObraPublica(1, 1, "Teste", LocalDate.now(), TipoObra.PUBLICA, new ArrayList<>(), null, null);
		prj.adicionarResponsavel(p1);
		assertEquals(1, prj.getProobr().size());
	}
    @Disabled
	@Test
	public void Teste04() {
		ObraPublica prj = new ObraPublica(1, 101, "Teste", LocalDate.of(2025, 01, 01), TipoObra.PUBLICA,
				new ArrayList<>(), LocalDate.of(2023, 01, 01), LocalDate.of(2023, 01, 31));
		prj.calcularDuracao();
		assertEquals(30, prj.calcularDuracao());

	}
	
	@Test
	public void Teste05() {
		inicializaMensagem();
		ObraPublica prj = new ObraPublica(1, 101, "Teste", LocalDate.of(2025, 01, 01), TipoObra.PUBLICA,
				new ArrayList<>(), LocalDate.of(2023, 01, 01), LocalDate.of(2023, 01, 31));
		Profissional p1 = new Engenheiro();
		p1.setCpfpro("111");
		prj.adicionarResponsavel(p1);
		prj.adicionarResponsavel(p1);
		mostrarMensagem();
	}
}

package main.java.furb.entidades;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import main.java.furb.enums.TipoObra;

public class ObraPublica extends Obra {

	private LocalDate datini;
	private LocalDate datfim;

	// 🔹 Construtor padrão
	// 🔹 Construtor completo
	public ObraPublica(int p_seqobr, int p_codobr, String p_desobr, LocalDate p_datetr, TipoObra p_tipobr,
			List<Profissional> p_proobr, LocalDate p_datini, LocalDate p_datfim) {
		super(p_seqobr, p_codobr, p_desobr, p_datetr, p_tipobr, p_proobr);
		this.datini = p_datini;
		this.datfim = p_datfim;
	}

	public ObraPublica() {
		super();
		// TODO Auto-generated constructor stub
	}

	// 🔹 Getters e Setters
	public LocalDate getDatini() {
		return datini;
	}

	public void setDatini(LocalDate p_datini) {
		this.datini = p_datini;
	}

	public LocalDate getDatfim() {
		return datfim;
	}

	public void setDatfim(LocalDate p_datfim) {
		this.datfim = p_datfim;
	}

	// 🔹 Método para calcular duração em dias
	public long calcularDuracao() {
		if (datini != null && datfim != null) {
			return ChronoUnit.DAYS.between(datini, datfim);
		}
		return 0;
	}

	@Override
	public String toString() {
		return String.format("Obra Pública [%d - %s]: %s (Duração: %d dias)", codobr, desobr,
				datini != null && datfim != null ? String.format("%s até %s", datini, datfim) : "Período não informado",
				calcularDuracao());
	}
}

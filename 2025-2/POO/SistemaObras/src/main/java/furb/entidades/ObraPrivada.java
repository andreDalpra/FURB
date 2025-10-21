package main.java.furb.entidades;

import static main.java.furb.mensagem.Mensagem.montaMensagem;

import java.time.LocalDate;
import java.util.List;

import main.java.furb.enums.TipoObra;

public class ObraPrivada extends Obra {

	private boolean rururb;
	private double metter;
	private String locter;

	public ObraPrivada(int p_seqobr, int p_codobr, String p_desobr, LocalDate p_datetr, TipoObra p_tipobr,
			List<Profissional> p_proobr, boolean p_rururb, double p_metter, String p_locter) {
		super(p_seqobr, p_codobr, p_desobr, p_datetr, p_tipobr, p_proobr);
		this.rururb = p_rururb;
		this.metter = p_metter;
		this.locter = p_locter;
	}

	/**
	 * Retorna um resumo textual com informações da localização e metragem.
	 */
	public String resumo() {
		String l_local = rururb ? "Área Rural" : "Área Urbana";
		return String.format("Localização: %s | Metragem total: %.2f m²", l_local, metter);
	}

	@Override
	public boolean valida() {
		if (!super.valida())
			return false;

		if (metter <= 0) {
			montaMensagem(37, new String[] { "A metragem total deve ser maior que zero." });
			return false;
		}

		return true;
	}

	public boolean isRururb() {
		return rururb;
	}

	public void setRururb(boolean rururb) {
		this.rururb = rururb;
	}

	public double getMetter() {
		return metter;
	}

	public void setMetter(double metter) {
		this.metter = metter;
	}

	public String getLocter() {
		return locter;
	}

	public void setLocter(String locter) {
		this.locter = locter;
	}

	@Override
	public String toString() {
		return super.toString() + " [" + resumo() + "]";
	}
}

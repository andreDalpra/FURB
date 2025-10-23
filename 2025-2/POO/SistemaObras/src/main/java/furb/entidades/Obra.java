package main.java.furb.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import main.java.furb.enums.TipoObra;
import static main.java.furb.mensagem.Mensagem.*;

public abstract class Obra {
	protected int seqobr; // sequência única
	protected int codobr; // código identificador
	protected String desobr; // descrição detalhada
	protected LocalDate datetr; // data de entrada
	protected TipoObra tipobr; // tipo da obra (enum)
	protected List<Profissional> proobr = new ArrayList<>(); // profissionais responsáveis

	public Obra(int seqobr, int codobr, String desobr, LocalDate datetr, TipoObra tipobr, List<Profissional> proobr) {
		this.seqobr = seqobr;
		this.codobr = codobr;
		this.desobr = desobr;
		this.datetr = datetr;
		this.tipobr = tipobr;
		this.proobr = proobr;
	}

	public Obra() {
	}

	public void adicionarResponsavel(Profissional p) {
		if (!proobr.contains(p)) {
			proobr.add(p);
			return;
		}
		montaMensagem(18, new String[] { p.getNompro(), desobr });

	}

	public void removerFuncionario(Profissional p) {
		proobr.remove(p);
	}

	// --- Validação comum a todas as obras ---
	public boolean valida() {
		inicializaMensagem();

		if (codobr <= 0) {
			montaMensagem(30, new String[] { "Código da obra inválido." });
			return false;
		}
		if (desobr == null || desobr.isBlank()) {
			montaMensagem(31, new String[] { "Descrição da obra é obrigatória." });
			return false;
		}
		if (datetr == null) {
			montaMensagem(32, new String[] { "Data de entrada não informada." });
			return false;
		}
		if (tipobr == null) {
			montaMensagem(33, new String[] { "Tipo da obra não definido." });
			return false;
		}
		if (proobr == null || proobr.isEmpty()) {
			montaMensagem(34, new String[] { "É necessário informar ao menos um profissional responsável." });
			return false;
		}

		return true;
	}

	public int getSeqobr() {
		return seqobr;
	}

	public void setSeqobr(int seqobr) {
		this.seqobr = seqobr;
	}

	public int getCodobr() {
		return codobr;
	}

	public void setCodobr(int codobr) {
		this.codobr = codobr;
	}

	public String getDesobr() {
		return desobr;
	}

	public void setDesobr(String desobr) {
		this.desobr = desobr;
	}

	public LocalDate getDatetr() {
		return datetr;
	}

	public void setDatetr(LocalDate datetr) {
		this.datetr = datetr;
	}

	public TipoObra getTipobr() {
		return tipobr;
	}

	public void setTipobr(TipoObra tipobr) {
		this.tipobr = tipobr;
	}

	public List<Profissional> getProobr() {
		return proobr;
	}

	public void setProobr(List<Profissional> proobr) {
		this.proobr = proobr;
	}

	@Override
	public String toString() {
		return "Obra [codobr=" + codobr + ", tipo=" + tipobr + ", descricao=" + desobr + "]";
	}
}

package main.java.furb.relatorio;

import java.util.List;

import main.java.furb.entidades.Obra;
import main.java.furb.entidades.Profissional;

public class RelatorioPorProfissional implements Relatorio {
	private final Profissional profissional;

	public RelatorioPorProfissional(Profissional p_profissional) {
		this.profissional = p_profissional;
	}

	@Override
	public String gerar(List<Obra> p_obras) {
		StringBuilder l_sb = new StringBuilder(
				"=== RELATÓRIO POR PROFISSIONAL: " + profissional.getNompro() + " ===\n\n");

		p_obras.stream().filter(o -> o.getProobr().contains(profissional)).forEach(o -> l_sb.append(o).append("\n\n"));

		return l_sb.toString();
	}
}

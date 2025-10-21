package main.java.furb.relatorio;

import java.util.List;
import main.java.furb.entidades.Obra;

public class RelatorioResumido implements Relatorio {
	@Override
	public String gerar(List<Obra> p_obras) {
		StringBuilder l_sb = new StringBuilder("=== RELATÓRIO RESUMIDO ===\n\n");

		p_obras.forEach(o -> l_sb.append("Obra: ").append(o.getCodobr()).append(" | Tipo: ").append(o.getTipobr())
				.append(" | Descrição: ").append(o.getDesobr()).append("\n"));

		return l_sb.toString();
	}
}

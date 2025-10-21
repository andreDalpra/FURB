package main.java.furb.relatorio;

import java.util.List;

import main.java.furb.entidades.Obra;
import main.java.furb.enums.TipoObra;

public class RelatorioPorTipo implements Relatorio {

	private final TipoObra tipobr;

	public RelatorioPorTipo(TipoObra p_tipobr) {
		this.tipobr = p_tipobr;
	}

	@Override
    public String gerar(List<Obra> p_obras) {
        StringBuilder l_sb = new StringBuilder("=== RELATÓRIO POR TIPO: " + tipobr + " ===\n\n");

        p_obras.stream()
               .filter(o -> o.getTipobr() == tipobr)
               .forEach(o -> l_sb.append(o).append("\n\n"));

        return l_sb.toString();
    }

}

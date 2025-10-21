package main.java.furb.relatorio;

import java.util.List;

import main.java.furb.entidades.Obra;

public interface Relatorio {
    
	String gerar(List<Obra> p_obras);
}

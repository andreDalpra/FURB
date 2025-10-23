package main.java.furb.entidades;

import java.util.List;

import main.java.furb.enums.TipoProfissional;

public class Engenheiro extends Profissional{

	public Engenheiro() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Engenheiro(int p_seqpro, String p_nompro, String p_cpfpro, TipoProfissional p_tippro, Usuario p_usuario,
			List<Obra> p_obras) {
		super(p_seqpro, p_nompro, p_cpfpro, p_tippro, p_usuario, p_obras);
		// TODO Auto-generated constructor stub
	}
    
}

package subclasses;

import entidades.Instrutor;

public class InstrutorPilates extends Instrutor{

	public InstrutorPilates(String nome, String cref, String especialidade, Instrutor mentor) {
		super(nome, cref, especialidade, mentor);
		
	}
	
	@Override
	public String getResumo (String resumo) {
	
		return "Este é um isntrutor de pilates" + super.getResumo(resumo);
	}
	
	@Override
	public String toString() {
		String l_resumo = "";
		return getResumo(l_resumo);
	}

}

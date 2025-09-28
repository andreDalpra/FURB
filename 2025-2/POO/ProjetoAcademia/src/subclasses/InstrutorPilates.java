package subclasses;

import entidades.Instrutor;

public class InstrutorPilates extends Instrutor {

	public InstrutorPilates(String nome, String cref, String especialidade, Instrutor mentor) {
		super(nome, cref, especialidade, mentor);

	}

	@Override
	public String getResumo() {

		return "Este é um isntrutor de pilates" + super.getResumo();
	}

}

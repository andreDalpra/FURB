package subclasses;

import entidades.Instrutor;

public class InstrutorPilates extends Instrutor {

	public InstrutorPilates(String nome, String cpf, String cref, String especialidade, Instrutor mentor) {
		super(nome, cref, cpf, especialidade, mentor);

	}

	@Override
	public String getResumo() {

		return "Este é um istrutor de pilates" + super.getResumo() + "\n";
	}

}

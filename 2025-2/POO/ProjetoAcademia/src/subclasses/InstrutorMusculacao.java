package subclasses;
import entidades.Instrutor;

public class InstrutorMusculacao extends Instrutor{
	
	public InstrutorMusculacao(String nome, String cpf, String cref, String especialidade, Instrutor mentor) {
		super(nome, cpf, cref, especialidade, mentor);
	}

	@Override
	public String getResumo () {	
		return "Este é um instrutor de musculação " + super.getResumo() + "\n";
	}

}

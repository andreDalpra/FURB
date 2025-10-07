package subclasses;
import entidades.Instrutor;

public class InstrutorMusculacao extends Instrutor{
	
	public InstrutorMusculacao(String nome, String cref, String especialidade, Instrutor mentor) {
		super(nome, cref, especialidade, mentor);
	}

	@Override
	public String getResumo () {	
		return "Este é um isntrutor de musculação" + super.getResumo();
	}

}

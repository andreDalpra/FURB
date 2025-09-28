package subclasses;

import entidades.Aluno;

public class AlunoVip extends Aluno {

	private double descontoMensalidade;

	public AlunoVip(String nome, String matricula, int idade, double peso, double altura) {
		super(nome, matricula, idade, peso, altura);
	}

	public AlunoVip(String nome, String matricula, double descontoMensalidade) {
		super(nome, matricula);
		this.descontoMensalidade = descontoMensalidade;
	}

	@Override
	public String getResumo(String resumo) {
		return "Este é um aluno VIP, ele tem benefícios" + super.getResumo(resumo);
	}

	public double getDescontoMensalidade() {
		return descontoMensalidade;
	}

	public void setDescontoMensalidade(double descontoMensalidade) {
		this.descontoMensalidade = descontoMensalidade;
	}

}

package subclasses;

import entidades.Aluno;

public class AlunoVip extends Aluno {

	private double descontoMensalidade;

	public AlunoVip(String nome, String cpf, String matricula, int idade, double peso, double altura) {
		super(nome, cpf, matricula, idade, peso, altura);
	}

	public AlunoVip(String nome, String cpf, String matricula, double descontoMensalidade) {
		super(nome, cpf, matricula);
		this.descontoMensalidade = descontoMensalidade;
	}

	@Override
	public String getResumo() {
		return "Este é um aluno VIP, ele tem benefícios" + super.getResumo();
	}

	public double getDescontoMensalidade() {
		return descontoMensalidade;
	}

	public void setDescontoMensalidade(double descontoMensalidade) {
		this.descontoMensalidade = descontoMensalidade;
	}

}

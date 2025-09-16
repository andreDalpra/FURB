
package classes;

public class Funcionario {
	private String nome;
	private String cpf;
	private double salario;

	public void aplicaAumento(double percentual) {
        salario += salario * percentual/100;
	}

	public Funcionario(String nome, String cpf, double salario) {
		this.nome = nome;
		this.cpf = cpf;
		if (cpf.isBlank()) {
			throw new IllegalArgumentException("CPF não pode ser nulo.");
		}
		this.salario = salario;
		if (salario <= 0) {
			throw new IllegalArgumentException("Salário não pode ser vazio.");
		}
	}

	public String getNome() {
		return nome;
	}

	public String getCpf() {
		return cpf;
	}

	public double getSalario() {
		return salario;
	}
	
	public String exibirDados() {
		return "Nome: " + nome + " CPF: " + cpf ;
				
	}

}

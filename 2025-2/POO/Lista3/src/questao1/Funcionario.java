package questao1;
import java.lang.*;

public class Funcionario {

	private String nome;
	private double salario;

	public double calculaIRPF() {
		double vlripo = 0.0;

		    //2ª Faixa
		if (salario > 1903.98 && salario <= 2826.65) {

			vlripo = (2826.65 - 1903.98) * 0.075;
			//3ª Faixa
		} else if (salario > 2826.65 && salario <= 3751.05) {// REVER

			vlripo = (2826.65 - 1903.98) * 0.075;
			vlripo += (salario - 2826.65) * 0.15;
			//4ª Faixa
		} else if (salario > 3751.05 && salario <= 4664.68) {
			vlripo = (2826.65 - 1903.98) * 0.075;
			vlripo += (3751.05 - 2826.65) * 0.15;
			vlripo += (salario - 3751.05) * 0.225;
			//5ª Faixa
		} else if (salario > 4664.68) {
			vlripo = (2826.65 - 1903.98) * 0.075;
			vlripo += (3751.05 - 2826.65) * 0.15;
			vlripo += (4664.68 - 3751.05) * 0.225;
			vlripo += salario * 0.275;
			//1ª Faixa
		} else {
            vlripo = 0;
		}

		return vlripo;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		if(salario < 0) {
			throw new IllegalArgumentException();
		}
		this.salario = salario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}

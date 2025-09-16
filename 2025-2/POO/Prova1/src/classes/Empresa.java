package classes;

import java.util.ArrayList;

public class Empresa {
	private String nome;
	private String cnpj;
	private ArrayList<Funcionario> funcionarios = new ArrayList<>();
	private Endereco endereco;

	public boolean buscaFuncionarioPeloNome(String p_nome) {
		for (Funcionario f : funcionarios) {
			if (f.getNome().equals(p_nome)) {
				System.out.println("ENCONTRAMOS O " +p_nome);
				return true;
			}
		}
		System.out.println("SEM RESULTADOS D:");
		return false;
	}
	public void listarFuncionarios() {
		for (Funcionario f : funcionarios) {
			System.out.println(f.getNome());
		}
	}
	public double calcularFolhaSalarial() {
		double l_salarios = 0.0;
		for (Funcionario f : funcionarios) {
			l_salarios += f.getSalario();
		}
		return l_salarios;
	}

	public void aplicarAumento(double percentual) {
		for (Funcionario f : funcionarios) {
			f.aplicaAumento(percentual);
		}
	}

	public void adicionarFuncionario(Funcionario f) {
		this.funcionarios.add(f);
	}

	public void removerFuncionario(Funcionario f) {
		this.funcionarios.remove(f);
	}

	public Empresa(String nome, String cnpj) {
		this.nome = nome;
		this.cnpj = cnpj;
		if (cnpj.isBlank()) {
			throw new IllegalArgumentException("CNPJ não pode ser nulo.");
		}
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public ArrayList<Funcionario> getFuncionario() {
		return funcionarios;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public String exibirDados() {
		return "Nome: " + nome + " | CNPJ: " + cnpj ;
	}

}
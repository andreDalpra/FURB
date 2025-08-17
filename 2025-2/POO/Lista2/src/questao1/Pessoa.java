package questao1;

public class Pessoa {

	private String nome[] = new String[3];
	private double peso[] = new double[3];
	private double altura[] = new double[3];
	
	double calcularIMC(int i) {
		return peso[i] * (altura[i] * altura[i]);
	}

	public String getNome(int i) {
	    return this.nome[i];
	}

	public void setNome(int i, String nome) {
	    this.nome[i] = nome;
	}

	public void setPeso(int i, double peso) {
	    this.peso[i] = peso;
	}

	public double getPeso(int i) {
	    return this.peso[i];
	}

	public void setAltura(int i, double altura) {
	    this.altura[i] = altura;
	}

	public double getAltura(int i) {
	    return this.altura[i];
	}
	
	public double[] getAltura() {
	    return altura;
	}


	
}

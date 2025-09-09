package entidades;

public class Exercicios {
	private String nome;
	private int series;
	private int reps;
	private double carga;
	
	public Exercicios(String nome, int series, int reps, double carga) {
		this.nome = nome;
		this.series = series;
		this.reps = reps;
		this.carga = carga;
	}
	
	public Exercicios(String nome) {
		this.nome = nome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getSeries() {
		return series;
	}
	public void setSeries(int series) {
		this.series = series;
	}
	public int getReps() {
		return reps;
	}
	public void setReps(int reps) {
		this.reps = reps;
	}
	public double getCarga() {
		return carga;
	}
	public void setCarga(double carga) {
		this.carga = carga;
	}
	
}

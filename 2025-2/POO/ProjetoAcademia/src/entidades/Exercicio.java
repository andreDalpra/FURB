package entidades;

public class Exercicio {
	private String nome;
	private int series;
	private int reps;
	private double carga;
	
	public String getResumo(String p_resumo) {
		p_resumo = "Nome: " + nome + " / Séries: " + series + " / Repetições: " + reps + " / Carga: " + carga + "\n";
		return p_resumo;
	}
	
	public Exercicio(String nome, int series, int reps, double carga) {
		this.nome = nome;
		this.series = series;
		this.reps = reps;
		this.carga = carga;
	}
	
	public Exercicio(String nome) {
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
	
	@Override
	public String toString() {
	    return String.format("Exercicio: %s | Séries: %d | Reps: %d | Carga: %.2fkg",
	                         nome, series, reps, carga);
	}

	
}

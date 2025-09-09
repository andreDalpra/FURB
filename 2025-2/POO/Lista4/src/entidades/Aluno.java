package entidades;

/**
 * Representa um aluno de uma academia, armazenando informações pessoais como
 * nome, matrícula, idade, peso e altura. Permite acessar e modificar esses
 * dados de forma controlada.
 * 
 * @author
 */
public class Aluno {

	private String nome;
	private String matricula;
	private int idade;
	private double peso;
	private double altura;
	private Instrutor instrutor;
	private PlanoTreino treino;

	/**
	 * Constrói um aluno com todas as informações fornecidas.
	 * 
	 * @param nome      Nome do aluno.
	 * @param matricula Matrícula do aluno.
	 * @param idade     Idade do aluno. Deve ser maior ou igual a 0.
	 * @param peso      Peso do aluno em quilogramas.
	 * @param altura    Altura do aluno em metros.
	 * @throws IllegalArgumentException se a idade for negativa.
	 */
	public Aluno(String nome, String matricula, int idade, double peso, double altura) {
		this.nome = nome;
		this.matricula = matricula;
		setIdade(idade);
		this.peso = peso;
		this.altura = altura;
	}

	/**
	 * Constrói um aluno apenas com nome e matrícula.
	 * 
	 * @param nome      Nome do aluno.
	 * @param matricula Matrícula do aluno.
	 */
	public Aluno(String nome, String matricula) {
		this.nome = nome;
		this.matricula = matricula;
	}

	/**
	 * Retorna o nome do aluno.
	 * 
	 * @return Nome do aluno.
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Define o nome do aluno.
	 * 
	 * @param nome Nome a ser definido.
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Retorna a matrícula do aluno.
	 * 
	 * @return Matrícula do aluno.
	 */
	public String getMatricula() {
		return matricula;
	}

	/**
	 * Define a matrícula do aluno.
	 * 
	 * @param matricula Matrícula a ser definida.
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	/**
	 * Retorna a idade do aluno.
	 * 
	 * @return Idade do aluno.
	 */
	public int getIdade() {
		return idade;
	}

	/**
	 * Define a idade do aluno.
	 * 
	 * @param idade Idade a ser definida. Deve ser maior ou igual a 0.
	 * @throws IllegalArgumentException se a idade for negativa.
	 */
	public void setIdade(int idade) {
		if (idade < 0) {
			throw new IllegalArgumentException("Idade não pode ser negativa.");
		}
		this.idade = idade;
	}

	/**
	 * Retorna o peso do aluno.
	 * 
	 * @return Peso do aluno em quilogramas.
	 */
	public double getPeso() {
		return peso;
	}

	/**
	 * Define o peso do aluno.
	 * 
	 * @param peso Peso a ser definido em quilogramas.
	 */
	public void setPeso(double peso) {
		this.peso = peso;
	}

	/**
	 * Retorna a altura do aluno.
	 * 
	 * @return Altura do aluno em metros.
	 */
	public double getAltura() {
		return altura;
	}

	/**
	 * Define a altura do aluno.
	 * 
	 * @param altura Altura a ser definida em metros.
	 */
	public void setAltura(double altura) {
		this.altura = altura;
	}

	/**
	 * Retorna uma representação textual do aluno, incluindo nome, matrícula,
	 * idade, peso e altura.
	 * 
	 * @return String com informações do aluno.
	 */
	@Override
	public String toString() {
		return "Aluno: " + nome + " (Matrícula: " + matricula + "), idade: " + idade + ", peso: " + peso + ", altura: "
				+ altura;
	}
	
	public Instrutor getInstrutor() {
		return instrutor;
	}

	public void setInstrutor(Instrutor instrutor) {
		this.instrutor = instrutor;
	}
}
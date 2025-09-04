package entidades;

/**
 * Classe principal para testar o sistema da academia.
 * 
 * <p>Essa classe cria alguns objetos de {@link Aluno} e {@link PlanoTreino},
 * imprime suas informações e também demonstra o lançamento e captura de
 * exceções com valores inválidos.</p>
 * 
 * @author Andre Santos
 */
public class Main {

    /**
     * Método principal que executa os testes de criação de alunos e planos.
     * 
     * @param args argumentos da linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        System.out.println("Testando Alunos e Planos:\n");

        Aluno a1 = new Aluno("Ana", "231", 25, 62.0, 1.68);
        Aluno a2 = new Aluno("Andre", "322");
        a2.setIdade(30);
        a2.setPeso(82.5);
        a2.setAltura(1.80);

        PlanoTreino p1 = new PlanoTreino("Treino de força", true);
        PlanoTreino p2 = new PlanoTreino("Cardio");
        p2.ativar();

        System.out.println(a1);
        System.out.println(a2);
        System.out.println(p1);
        System.out.println(p2);

        System.out.println("\nTestando Exceções:\n");
        try {
            new Aluno("Pedro", "302", -5, 70, 1.75);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro (idade): " + e.getMessage());
        }

        try {
            new PlanoTreino("   ");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro (descrição): " + e.getMessage());
        }
    }
}
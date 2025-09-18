package entidades;
import mensagem.Mensagem;

@SuppressWarnings("unused")
public class Main {

    public static void main(String[] args) {
        System.out.println("Testando Alunos e Planos:\n");

        Aluno a1 = new Aluno("Ana", "231", 25, 62.0, 1.68);
        Instrutor i1 = new Instrutor("JOSILDO", "024", "PARKOUR POV", null);
        Instrutor i2 = new Instrutor("JOSILDO PAI", "025", "PARKOUR MESTRE", i1);
        PlanoTreino pt1 = new PlanoTreino("Treino de Hipertrofia");
        pt1.incluirTreino(a1);
        System.out.println(a1.getTreino());        
        //TESTANDO PLANO TREINO
        
        
        PlanoTreino p1 = new PlanoTreino("Treino de força");
        p1.incluirTreino(a1);
        System.out.println(p1.toString());
        p1.ativar();
        p1.adicionarExercicio(new Exercicio("TESTE MAIN", 1, 0, 0.0));
        System.out.println(p1.toString());
        p1.adicionarExercicio(new Exercicio("TESTE CORRDIA", 2, 3, 0.0));
        System.out.println(p1.toString());
        Mensagem.mostrarMensagem(101, new String[] {"Parabéns!", "Você completou seu treino!"});
        
     
        
    }
}
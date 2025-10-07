package entidades;
import javax.swing.SwingUtilities;

import mensagem.CadastraMensagem;
import mensagem.MensagemUI;
import mensagem.TipoMensagem;

@SuppressWarnings("unused")
public class Main {

    public static void main(String[] args) {
    	CadastraMensagem.cadastro();
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
        
        //InstrutorMusculacao im = new InstrutorMusculacao("TESTE", "123", "MUSCULACAO", i2);
        //System.out.println(im.getResumo());
        i1.adicionarAluno(a1);
        i1.adicionarAluno(a1); // Tentativa de adicionar o mesmo aluno novamente
        SwingUtilities.invokeLater(() -> {
            new MensagemUI(
                "Erro Crítico",
                "Não foi possível cadastrar o aluno no sistema.<br>Tente novamente mais tarde.",
                TipoMensagem.ERROR
            ).setVisible(true);

            new MensagemUI(
                "Aviso",
                "Aluno já cadastrado anteriormente!",
                TipoMensagem.WARNING
            ).setVisible(true);

            new MensagemUI(
                "Sucesso",
                "Aluno cadastrado com sucesso! 🎉",
                TipoMensagem.OK
            ).setVisible(true);
        });
        
    }
}
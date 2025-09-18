package mensagem;

public class CadastraMensagem {
    public static void cadastrarTodas() {
        Mensagem.cadastrarMensagem(101, "Aluno cadastrado com sucesso");
        Mensagem.cadastrarMensagem(102, "Erro ao cadastrar aluno");
        Mensagem.cadastrarMensagem(201, "Instrutor cadastrado com sucesso");
        Mensagem.cadastrarMensagem(202, "Erro ao cadastrar instrutor");
        // Adicione mais mensagens conforme necessário
    }
}

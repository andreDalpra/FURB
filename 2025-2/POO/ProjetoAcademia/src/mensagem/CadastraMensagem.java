package mensagem;

public class CadastraMensagem {
    public static void cadastro() {
        Mensagem.cadastrarMensagem(101, "Aluno cadastrado com sucesso para o instrutor \n    Nome: %s    Matrícula %s");
        Mensagem.cadastrarMensagem(102, "Erro ao cadastrar aluno para o instrutor \n   Nome: %s");
    }
}
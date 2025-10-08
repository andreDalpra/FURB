package mensagem;

public class CadastraMensagem {
    public static void cadastro() {
    	Mensagem.cadastrarMensagem(101, "Aluno cadastrado com sucesso: Aluno: %s    Matrícula : %s", TipoMensagem.OK);
    	Mensagem.cadastrarMensagem(102, "Aluno já existe! \n Aluno: %s", TipoMensagem.WARNING);
    	Mensagem.cadastrarMensagem(103, "Erro ao cadastrar aluno: %s", TipoMensagem.ERROR);


    }
}
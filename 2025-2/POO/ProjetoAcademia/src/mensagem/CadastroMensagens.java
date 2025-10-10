package mensagem;

public class CadastroMensagens {
    public static void cadastro() {
    	Mensagem.cadastrarMensagem(100, "Aluno cadastrado com sucesso: Aluno: %s    Matrícula : %s", TipoMensagem.OK);
    	Mensagem.cadastrarMensagem(102, "Aluno já existe! \n Aluno: %s", TipoMensagem.WARNING);
    	Mensagem.cadastrarMensagem(103, "Erro ao cadastrar aluno: %s", TipoMensagem.ERROR);
    	Mensagem.cadastrarMensagem(104, "O Aluno deve ter um nome ! \n Aluno: %s", TipoMensagem.ERROR);
    	Mensagem.cadastrarMensagem(105, "O Instrutor deve ter um nome! \n Instrutor: %s", TipoMensagem.ERROR);
    	Mensagem.cadastrarMensagem(106, "Erro ao cadastrar aluno: %s", TipoMensagem.ERROR);


    }
}
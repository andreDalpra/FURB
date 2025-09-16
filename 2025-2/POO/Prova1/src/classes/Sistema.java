package classes;

public class Sistema {
	public void gerarRelatorioCompleto() {
		//EMPRESAS
        Empresa e1 = new Empresa("FURB","126789-98");
        Empresa e2 = new Empresa("SENAI","147589-07");
        //FUNCIONARIOS
        Funcionario f1 = new Funcionario("JOSILDO","123456-78",2330);
        Funcionario f2 = new Funcionario("AMARILDO","12324-78",2456);
        Funcionario f3 = new Funcionario("EVANILDO","123423-78",9000);
        Funcionario f4 = new Funcionario("JAVANILDO","67856-78",1500);
        Funcionario f5 = new Funcionario("CSSNILDO","562342-78",12);
        Funcionario f6 = new Funcionario("HTMLNILDO","354664-78",7500);
        //ENDERECOS
        Endereco end1 = new Endereco("Antonio da Veiga", 12345,"Blumenau");
        Endereco end2 = new Endereco("Rua São Paulo", 54321,"Brusque");
        
        //ADICIONANDO OS FUNCIONARIOS A EMPRESA E SEU ENDERECO
        e1.adicionarFuncionario(f1);
        e1.adicionarFuncionario(f2);
        e1.adicionarFuncionario(f4);
        e1.setEndereco(end2);
        //
        e2.adicionarFuncionario(f3);
        e2.adicionarFuncionario(f5);
        e2.adicionarFuncionario(f6);
        e2.setEndereco(end1);
        
        
        System.out.println("IMPRIMINDO OS RESULTADOS:");
        System.out.println("===========================");
        System.out.println("PRIMEIRA EMPRESA");
        System.out.println(e1.exibirDados());
        System.out.println("FOLHA DA SALARIAL DA: " + e1.getNome());
        System.out.println(e1.calcularFolhaSalarial());
        System.out.println("REALIZANDO AUMENTO DE 10% :");
        e1.aplicarAumento(10);
        System.out.println("APÓS O AUMENTO: "+ e1.calcularFolhaSalarial());
        System.out.println("REMOVENDO O " +f4.getNome() + " da "+ e1.getNome());
        System.out.println("Funcionário agora: ");
        e1.listarFuncionarios();
        System.out.println("\n-----Removendo o -----" +f4.getNome());
        e1.removerFuncionario(f4);
        System.out.println("APOS A REMOÇÃO");
        e1.listarFuncionarios();
        System.out.println("\nTentando achar um funcionario:");
        e1.buscaFuncionarioPeloNome("JOSILDO");
        System.out.println();
        System.out.println();
        System.out.println("==============IMPRIMINDO DA OUTRA EMPRESA================:");
        System.out.println("SEGUNDA EMPRESA");
        System.out.println(e2.exibirDados());
        System.out.println("FOLHA DA SALARIAL DA: " + e2.getNome());
        System.out.println(e2.calcularFolhaSalarial());
        System.out.println("REALIZANDO AUMENTO DE 15% :");
        e1.aplicarAumento(15);
        System.out.println("APÓS O AUMENTO: "+ e2.calcularFolhaSalarial());
        System.out.println("REMOVENDO O " +f3.getNome() + " da "+ e2.getNome());
        System.out.println("Funcionário agora: ");
        e1.listarFuncionarios();
        System.out.println("\n-----Removendo o -----" +f3.getNome());
        e1.removerFuncionario(f3);
        System.out.println("APOS A REMOÇÃO");
        e1.listarFuncionarios();
        System.out.println("\nTentando achar um funcionario:");
        e1.buscaFuncionarioPeloNome("EVANILDO (Não deve achar pois ele foi removido");
	}
}

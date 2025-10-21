package main.java.furb.entidades;

import static main.java.furb.app.Main.sc;
import static main.java.furb.mensagem.Mensagem.inicializaMensagem;
import static main.java.furb.mensagem.Mensagem.montaMensagem;

import main.java.furb.app.Sistema;
import main.java.furb.banco.Banco;
import main.java.furb.controle.UsuarioDAO;
import main.java.furb.enums.TipoUsuario;

public class Usuario implements Sistema {
	// Atributos
	private int sequsu;
	private String codusu;
	private int senusu;
	private TipoUsuario tipusu;
	private String nomusu;
	private String emlusu;

	public Usuario(int p_sequsu, String p_codusu, int p_senusu, TipoUsuario p_tipusu, String p_nomusu,
			String p_emlusu) {
		this.sequsu = p_sequsu;
		this.codusu = p_codusu;
		this.senusu = p_senusu;
		this.tipusu = p_tipusu;
		this.nomusu = p_nomusu;
		this.emlusu = p_emlusu;
	}

	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean cadastrar() {
		System.out.println("Cadastre o usuário no Sistema");
		System.out.println("Informe o código do usuário: ");
		codusu = sc.nextLine().toUpperCase().trim();
		System.out.println("Informe a senha (4 dígitos)");
		senusu = sc.nextInt();
		sc.nextLine();

		tipusu = escolherTipoUsuario();

		System.out.println("Informe o nome do usuário: ");
		nomusu = sc.nextLine();
		System.out.println("Informe o email do usuário: ");
		emlusu = sc.nextLine();

		// só chama o DAO
		return new UsuarioDAO().inserir(this);

	}

	public boolean excluir() {
		boolean l_tentarNovamente = true;

		while (l_tentarNovamente) {
			System.out.println("\nUsuários cadastrados:");
			var l_usuarios = Banco.listar(Usuario.class);

			if (l_usuarios.isEmpty()) {
				System.out.println("Nenhum usuário encontrado.\n");
				return false;
			}

			for (Usuario u : l_usuarios) {
				System.out.printf("%d - %s%n", u.getSequsu(), u.getCodusu());
			}

			System.out.print("\nSelecione o número do usuário para excluir: ");
			int l_opcao = sc.nextInt();
			sc.nextLine();

			boolean l_removido = new UsuarioDAO().deletar(l_opcao);

			if (l_removido) {
				montaMensagem(11, new String[] { String.valueOf(l_opcao) });
				return true;
			}

			System.out.print("Deseja tentar novamente? (S/N): ");
			String l_resposta = sc.nextLine().trim().toUpperCase();
			if (!l_resposta.equals("S")) {
				l_tentarNovamente = false;
			}
		}

		return false;
	}

	private TipoUsuario escolherTipoUsuario() {
		TipoUsuario[] l_tipos = TipoUsuario.values();
		System.out.println("\nSelecione o tipo de usuário:");
		for (int i = 0; i < l_tipos.length; i++) {
			System.out.printf("%d - %s%n", (i + 1), l_tipos[i]);
		}

		while (true) {
			System.out.print("Opção: ");
			try {
				int l_opcao = sc.nextInt();
				sc.nextLine(); // consome quebra de linha

				if (l_opcao >= 1 && l_opcao <= l_tipos.length) {
					return l_tipos[l_opcao - 1];
				} else {
					System.out.println("Opção inválida! Tente novamente.");
				}
			} catch (Exception e) {
				System.out.println("Digite um número válido!");
				sc.nextLine(); // limpa entrada inválida
			}
		}
	}

	@Override
	public boolean valida() {

		if (codusu == null || codusu.isBlank()) {
			montaMensagem(1, new String[] { codusu });
			return false;
		}

		if (!validaSenha()) {
			return false;
		}
		if (tipusu == null) {
			montaMensagem(4, new String[] { codusu, tipusu.name() });
		}
		return true;
	}

	public boolean validaSenha() {

		String l_senhaStr = String.valueOf(senusu);

		if (senusu < 1000 || senusu > 9999) {
			montaMensagem(7, new String[] { codusu, l_senhaStr });
			return false;
		}

		boolean l_todosIguais = l_senhaStr.chars().allMatch(c -> c == l_senhaStr.charAt(0));
		if (l_todosIguais) {
			montaMensagem(7, new String[] { codusu, l_senhaStr });
			return false;
		}

		boolean l_sequencialCrescente = true;
		for (int l_i = 0; l_i < l_senhaStr.length() - 1; l_i++) {
			if (l_senhaStr.charAt(l_i + 1) != l_senhaStr.charAt(l_i) + 1) {
				l_sequencialCrescente = false;
				break;
			}
		}
		if (l_sequencialCrescente) {
			montaMensagem(7, new String[] { codusu, l_senhaStr });
			return false;
		}

		boolean l_sequencialDecrescente = true;
		for (int l_i = 0; l_i < l_senhaStr.length() - 1; l_i++) {
			if (l_senhaStr.charAt(l_i + 1) != l_senhaStr.charAt(l_i) - 1) {
				l_sequencialDecrescente = false;
				break;
			}
		}
		if (l_sequencialDecrescente) {
			montaMensagem(7, new String[] { codusu, l_senhaStr });
			return false;
		}

		if (l_senhaStr.equals("0000") || l_senhaStr.equals("1234")) {
			montaMensagem(7, new String[] { codusu, l_senhaStr });
			return false;
		}

		return true;
	}

	@Override
	public boolean before_post() {
		inicializaMensagem();

		// 🔹 Gera automaticamente a próxima sequência se ainda não tiver
		if (sequsu == 0) {
			sequsu = obtem_proximaSequencia(Usuario.class, Usuario::getSequsu);
		}

		// 🔹 Primeiro, valida os campos obrigatórios
		if (!valida()) {
			return false;
		}

		// 🔹 Agora valida se o login (codusu) já existe no "banco"
		var l_usuarios = Banco.listar(Usuario.class);
		boolean l_existe = l_usuarios.stream().anyMatch(u -> u.getCodusu().equalsIgnoreCase(this.codusu));

		if (l_existe) {
			montaMensagem(13, new String[] { this.codusu });
			return false;
		}

		return true;
	}

	@Override
	public boolean before_delete() {
		// TODO Auto-generated method stub
		return false;
	}

	public int getSequsu() {
		return sequsu;
	}

	public String getCodusu() {
		return codusu;
	}

	public int getSenusu() {
		return senusu;
	}

	public TipoUsuario getTipusu() {
		return tipusu;
	}

	public String getNomusu() {
		return nomusu;
	}

	public String getEmlusu() {
		return emlusu;
	}

}

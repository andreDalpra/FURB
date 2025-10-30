package main.java.furb.entidades;

import static main.java.furb.mensagem.Mensagem.montaMensagem;
import static main.java.furb.app.Main.sc;

import java.util.List;

import main.java.furb.app.Sistema;
import main.java.furb.banco.Banco;
import main.java.furb.controle.ProfissionalDAO;
import main.java.furb.enums.TipoProfissional;

public abstract class Profissional implements Sistema {

	private int seqpro;
	private String nompro;
	private String cpfpro;
	private TipoProfissional tippro;
	private Usuario usuario;
	private List<Obra> obras;

	// 🔹 Construtor completo
	public Profissional(int p_seqpro, String p_nompro, String p_cpfpro, TipoProfissional p_tippro, Usuario p_usuario,
			List<Obra> p_obras) {
		this.seqpro = p_seqpro;
		this.nompro = p_nompro;
		this.cpfpro = p_cpfpro;
		this.tippro = p_tippro;
		this.usuario = p_usuario;
		this.obras = p_obras;
	}

	public Profissional() {

	}

	@Override
	public boolean cadastrar() {

		System.out.println("=== Cadastro de Profissional ===");

		System.out.print("Informe o nome do profissional: ");
		nompro = sc.nextLine().trim();

		System.out.print("Informe o CPF do profissional: ");
		cpfpro = sc.nextLine().trim();

		// 🔹 Seleciona tipo de profissional
		System.out.println("\nSelecione o tipo de profissional:");
		TipoProfissional[] tipos = TipoProfissional.values();
		for (int i = 0; i < tipos.length; i++) {
			System.out.printf("%d - %s%n", (i + 1), tipos[i]);
		}

		while (true) {
			System.out.print("Opção: ");
			try {
				int opcao = Integer.parseInt(sc.nextLine());
				if (opcao >= 1 && opcao <= tipos.length) {
					tippro = tipos[opcao - 1];
					break;
				} else {
					System.out.println("Opção inválida. Tente novamente.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Digite um número válido!");
			}
		}

		// 🔹 Se for interno, precisa vincular a um usuário existente
		if (tippro.exigeUsuario()) {
			System.out.println("\nEste profissional é interno. Deseja vincular a qual usuário?");
			List<Usuario> usuarios = Banco.listar(Usuario.class);

			if (usuarios.isEmpty()) {
				System.out.println("Nenhum usuário encontrado. É necessário cadastrar um antes.");
				return false;
			}

			for (Usuario u : usuarios) {
				System.out.printf("%d - %s%n", u.getSequsu(), u.getCodusu());
			}

			while (true) {
				System.out.print("Selecione o número do usuário: ");
				try {
					int l_sequsu = Integer.parseInt(sc.nextLine());
					usuario = usuarios.stream().filter(u -> u.getSequsu() == l_sequsu).findFirst().orElse(null);
					if (usuario != null)
						break;
					System.out.println("Usuário não encontrado. Tente novamente.");
				} catch (NumberFormatException e) {
					System.out.println("Digite um número válido!");
				}
			}

		}

		// 🔹 Valida e insere no banco
		if (!before_post())
			return false;
		return new ProfissionalDAO().inserir(this);
	}

	@Override
	public boolean valida() {

		if (nompro == null || nompro.isBlank()) {
			montaMensagem(14, new String[] { nompro });
			return false;
		}

		if (cpfpro == null || cpfpro.isBlank()) {
			montaMensagem(15, new String[] { cpfpro });
			return false;
		}

		return true;
	}

	@Override
	public boolean before_post() {

		if (seqpro == 0) {
			seqpro = obtem_proximaSequencia(Profissional.class, Profissional::getSeqpro);
		}

		return valida();
	}

	// 🔹 Getters e Setters
	public int getSeqpro() {
		return seqpro;
	}

	public String getNompro() {
		return nompro;
	}

	public String getCpfpro() {
		return cpfpro;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Obra> getObras() {
		return obras;
	}

	public void setObras(List<Obra> obras) {
		this.obras = obras;
	}

	public TipoProfissional getTippro() {
		return tippro;
	}

	public void setTippro(TipoProfissional tippro) {
		this.tippro = tippro;
	}

	public void setNompro(String nompro) {
		this.nompro = nompro;
	}

	public void setCpfpro(String cpfpro) {
		this.cpfpro = cpfpro;
	}

	// 🔹 Representação textual
	@Override
	public String toString() {
		String vinculo = (usuario != null) ? "Vinculado a usuário: " + usuario.getCodusu() : "Sem vínculo de usuário";
		return String.format("[%d] %s (%s) - %s", seqpro, nompro, cpfpro, vinculo);
	}
}

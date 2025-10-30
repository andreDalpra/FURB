package main.java.furb.app;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import main.java.furb.banco.Banco;
import main.java.furb.mensagem.CadastroMensagem;
import static main.java.furb.app.Main.sc;

public interface Sistema {

	public static void abrePrograma() {
		SwingUtilities.invokeLater(() -> new JFrame().dispose());
		CadastroMensagem.cadastro();
	}

	boolean valida();

	default boolean cadastrar() {
		return false;

	}

	default boolean excluir() {
		return false;
	}

	default boolean before_post() {
		return false;
	}

	default boolean before_delete() {
		return false;
	}

	default boolean validaSenha() {
		return false;
	}

	default boolean tentarNovamente() {
		while (true) {
			System.out.print("Deseja tentar novamente? (S/N): ");
			String l_resposta = sc.nextLine().trim().toUpperCase();

			if (l_resposta.equals("S")) {
				return true; // o usuário quer tentar de novo
			} else if (l_resposta.equals("N")) {
				return false; // o usuário NÃO quer tentar de novo
			} else {
				System.out.println("Opção inválida! Digite 'S' para Sim ou 'N' para Não.");
			}
		}
	}

	default <T> int obtem_proximaSequencia(Class<T> p_tipo, java.util.function.ToIntFunction<T> p_getter) {
		List<T> l_lista = Banco.listar(p_tipo);
		return l_lista.stream().mapToInt(p_getter).max().orElse(0) + 1;
	}

	public static String converteHTML(String p_txtHTML) {
		return "<html>" + p_txtHTML.replace("\n", "<br>") + "</html>";
	}

}

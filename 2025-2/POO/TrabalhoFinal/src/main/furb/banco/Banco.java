package main.furb.banco;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

import main.furb.app.Sistema;

public class Banco {
	private static final String BASE_PATH = "dados/";

	public static <T extends Sistema> int obtemSequence(Class<T> p_tipo) {
		String l_arquivoSeq = getCaminhoSequencia(p_tipo);
		File file = new File(l_arquivoSeq);
		int l_proximo = 1;

		try {
			if (file.exists()) {
				try (Scanner sc = new Scanner(file, StandardCharsets.UTF_8)) {
					if (sc.hasNextInt()) {
						l_proximo = sc.nextInt() + 1;
					}
				}
			}

			// Grava o novo valor
			try (PrintWriter pw = new PrintWriter(new FileWriter(file, StandardCharsets.UTF_8, false))) {
				pw.print(l_proximo);
			}

		} catch (IOException e) {
			System.err.println("[BANCO] Erro ao acessar sequência: " + e.getMessage());
		}

		return l_proximo;
	}

	// --- Salvar ---
	public static <T extends Sistema> void salvar(T p_objeto, Class<T> p_tipo) {
		String l_arquivo = getCaminhoArquivo(p_tipo);
		try {
			File dir = new File(BASE_PATH);
			if (!dir.exists())
				dir.mkdirs();

			try (PrintWriter pw = new PrintWriter(new FileWriter(l_arquivo, StandardCharsets.UTF_8, true))) {
				pw.println(p_objeto.toCSV());
			}

			System.out.println("[BANCO] Registro salvo em " + l_arquivo);
		} catch (IOException e) {
			System.err.println("[BANCO] Erro ao salvar: " + e.getMessage());
		}
	}

	// --- Listar ---
	public static <T extends Sistema> List<T> listar(Class<T> p_tipo) {
		List<T> lista = new ArrayList<>();
		File file = new File(getCaminhoArquivo(p_tipo));
		if (!file.exists())
			return lista;

		try (Scanner sc = new Scanner(file, StandardCharsets.UTF_8)) {
			while (sc.hasNextLine()) {
				String linha = sc.nextLine();
				T l_obj = p_tipo.getDeclaredConstructor().newInstance();
				l_obj.fromCSV(linha);
				lista.add(l_obj);
			}
		} catch (Exception e) {
			System.err.println("[BANCO] Erro ao listar: " + e.getMessage());
		}
		return lista;
	}

	// --- Excluir ---
	public static <T extends Sistema> boolean excluir(Class<T> p_tipo, Predicate<T> p_condicao) {
		List<T> l_lista = listar(p_tipo);
		boolean l_removido = l_lista.removeIf(p_condicao);
		if (l_removido)
			salvarLista(l_lista, p_tipo);
		return l_removido;
	}

	// --- Atualizar ---
	public static <T extends Sistema> boolean atualizar(Class<T> p_tipo, Predicate<T> p_condicao, T p_novoObjeto) {
		List<T> l_lista = listar(p_tipo);
		boolean l_atualizado = false;
		for (int i = 0; i < l_lista.size(); i++) {
			if (p_condicao.test(l_lista.get(i))) {
				l_lista.set(i, p_novoObjeto);
				l_atualizado = true;
				break;
			}
		}
		if (l_atualizado)
			salvarLista(l_lista, p_tipo);
		return l_atualizado;
	}

	// --- Auxiliares ---
	private static <T extends Sistema> void salvarLista(List<T> p_lista, Class<T> p_tipo) {
		String l_arquivo = getCaminhoArquivo(p_tipo);
		try (PrintWriter pw = new PrintWriter(new FileWriter(l_arquivo, StandardCharsets.UTF_8))) {
			for (T obj : p_lista)
				pw.println(obj.toCSV());
		} catch (IOException e) {
			System.err.println("[BANCO] Erro ao sobrescrever: " + e.getMessage());
		}
	}

	private static String getCaminhoArquivo(Class<?> p_tipo) {
		return BASE_PATH + p_tipo.getSimpleName().toLowerCase() + ".csv";
	}

	private static String getCaminhoSequencia(Class<?> p_tipo) {
		return BASE_PATH + p_tipo.getSimpleName().toLowerCase() + ".seq";
	}
}

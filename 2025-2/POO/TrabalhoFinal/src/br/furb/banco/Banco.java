package br.furb.banco;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class Banco {
    private static final String BASE_PATH = "dados/";

    /**
     * Salva um objeto (adiciona no final do arquivo CSV).
     */
    public static <T extends SerializableCSV> void salvar(T objeto, Class<T> tipo) {
        String arquivo = getCaminhoArquivo(tipo);
        try {
            File dir = new File(BASE_PATH);
            if (!dir.exists()) dir.mkdirs();

            FileWriter fw = new FileWriter(arquivo, StandardCharsets.UTF_8, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(objeto.toCSV());
            pw.close();
            System.out.println("[BANCO] Registro salvo em " + arquivo);
        } catch (IOException e) {
            System.err.println("[BANCO] Erro ao salvar: " + e.getMessage());
        }
    }

    /**
     * Lê todos os registros de um tipo.
     */
    public static <T extends SerializableCSV> List<T> listar(Class<T> tipo) {
        List<T> lista = new ArrayList<>();
        String arquivo = getCaminhoArquivo(tipo);
        File file = new File(arquivo);

        if (!file.exists()) return lista;

        try (Scanner sc = new Scanner(file, StandardCharsets.UTF_8)) {
            while (sc.hasNextLine()) {
                String linha = sc.nextLine();
                T obj = tipo.getDeclaredConstructor().newInstance();
                obj.fromCSV(linha);
                lista.add(obj);
            }
        } catch (Exception e) {
            System.err.println("[BANCO] Erro ao listar: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Exclui registros que satisfaçam o predicado.
     */
    public static <T extends SerializableCSV> boolean excluir(Class<T> tipo, Predicate<T> pred) {
        List<T> lista = listar(tipo);
        boolean removido = lista.removeIf(pred);
        if (removido) salvarLista(lista, tipo);
        return removido;
    }

    private static <T extends SerializableCSV> void salvarLista(List<T> lista, Class<T> tipo) {
        String arquivo = getCaminhoArquivo(tipo);
        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo, StandardCharsets.UTF_8))) {
            for (T obj : lista) {
                pw.println(obj.toCSV());
            }
        } catch (IOException e) {
            System.err.println("[BANCO] Erro ao sobrescrever: " + e.getMessage());
        }
    }

    private static String getCaminhoArquivo(Class<?> tipo) {
        return BASE_PATH + tipo.getSimpleName().toLowerCase() + ".csv";
    }
}

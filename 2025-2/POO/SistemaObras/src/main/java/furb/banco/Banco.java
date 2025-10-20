package main.java.furb.banco;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Banco {
    // GSON com formatação (pretty print)
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting() // <--- importante: ativa indentação e quebras de linha
            .serializeNulls()
            .disableHtmlEscaping()
            .create();

    private static final String BASE_PATH = "dados/";

    /**
     * Salva um objeto genérico no arquivo JSON correspondente.
     */
    public static <T> void salvar(T objeto, Class<T> tipo) {
        String arquivo = getCaminhoArquivo(tipo);
        List<T> lista = listar(tipo);
        lista.add(objeto);
        salvarLista(lista, tipo);
    }

    /**
     * Exclui todos os registros do tipo informado que satisfaçam o predicado.
     */
    public static <T> boolean excluir(Class<T> tipo, java.util.function.Predicate<T> pred) {
        List<T> lista = listar(tipo);
        boolean removido = lista.removeIf(pred);
        if (removido) {
            salvarLista(lista, tipo);
            System.out.println("[BANCO] Registro removido com sucesso.");
        } else {
            System.out.println("[BANCO] Nenhum registro encontrado para remoção.");
        }
        return removido;
    }

    /**
     * Retorna todos os objetos do tipo informado.
     */
    public static <T> List<T> listar(Class<T> tipo) {
        String arquivo = getCaminhoArquivo(tipo);
        try {
            if (!Files.exists(Paths.get(arquivo)))
                return new ArrayList<>();

            String json = Files.readString(Paths.get(arquivo));
            Type tipoLista = TypeToken.getParameterized(List.class, tipo).getType();
            List<T> lista = gson.fromJson(json, tipoLista);
            return lista != null ? lista : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("[BANCO] Erro ao listar: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Substitui todo o conteúdo do arquivo JSON por uma nova lista formatada.
     */
    private static <T> void salvarLista(List<T> lista, Class<T> tipo) {
        String arquivo = getCaminhoArquivo(tipo);
        try {
            Files.createDirectories(Paths.get(BASE_PATH));

            // Aqui garantimos que será salvo com UTF-8 e formatação
            String jsonFormatado = gson.toJson(lista);
            Files.writeString(Paths.get(arquivo), jsonFormatado, StandardCharsets.UTF_8);

            System.out.println("[BANCO] Arquivo salvo em formato bonito: " + arquivo);
        } catch (IOException e) {
            System.err.println("Erro ao salvar JSON: " + e.getMessage());
        }
    }

    private static <T> String getCaminhoArquivo(Class<T> tipo) {
        return BASE_PATH + tipo.getSimpleName().toLowerCase() + ".json";
    }
}

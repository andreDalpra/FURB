package main.java.furb.banco;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import main.java.furb.entidades.Usuario;

public class Banco {
    private static final Gson gson = new Gson();
    private static final String BASE_PATH = "dados/";

    /**
     * Salva um objeto genérico em seu arquivo JSON correspondente.
     * O nome do arquivo é baseado no nome da classe.
     */
    public static <T> void salvar(T objeto, Class<T> tipo) {
        String arquivo = getCaminhoArquivo(tipo);
        List<T> lista = listar(tipo);
        lista.add(objeto);
        salvarLista(lista, tipo);
    }
    
    /**
     * Exclui todos os registros do tipo informado que satisfaçam o predicado.
     * Exemplo de uso:
     * Banco.excluir(Usuario.class, u -> u.getSequsu() == 1);
     */
    public static <T> boolean excluir(Class<T> tipo, java.util.function.Predicate<T> pred) {
        List<T> lista = listar(tipo);
        boolean removido = lista.removeIf(pred);
        if (removido) {
            salvarLista(lista, tipo); // regrava o arquivo sem os removidos
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
            return new ArrayList<>();
        }
    }

    /**
     * Substitui todo o conteúdo do arquivo JSON por uma nova lista.
     */
    private static <T> void salvarLista(List<T> lista, Class<T> tipo) {
        String arquivo = getCaminhoArquivo(tipo);

        try {
            Files.createDirectories(Paths.get(BASE_PATH));
            Files.writeString(Paths.get(arquivo), gson.toJson(lista));
        } catch (IOException e) {
            System.err.println("Erro ao salvar JSON: " + e.getMessage());
        }
    }

    /**
     * Monta o caminho do arquivo com base no nome da classe (ex: Usuario → dados/Usuario.json)
     */
    private static <T> String getCaminhoArquivo(Class<T> tipo) {
        return BASE_PATH + tipo.getSimpleName().toLowerCase() + ".json";
    }

 


}

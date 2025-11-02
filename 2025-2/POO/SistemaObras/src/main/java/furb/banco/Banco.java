package main.java.furb.banco;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class Banco {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
                @Override
                public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
                    return new JsonPrimitive(src.format(FORMATTER));
                }
            })
            .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                @Override
                public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                        throws JsonParseException {
                    return LocalDate.parse(json.getAsString(), FORMATTER);
                }
            })
            .setPrettyPrinting()
            .serializeNulls()
            .disableHtmlEscaping()
            .create();

    private static final String BASE_PATH = "dados/";

    public static <T> void salvar(T objeto, Class<T> tipo) {
        String arquivo = getCaminhoArquivo(tipo);
        List<T> lista = listar(tipo);
        lista.add(objeto);
        salvarLista(lista, tipo);
    }

    public static <T> boolean excluir(Class<T> tipo, java.util.function.Predicate<T> pred) {
        List<T> lista = listar(tipo);
        boolean removido = lista.removeIf(pred);
        if (removido) {
            salvarLista(lista, tipo);
        }
        return removido;
    }

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

    private static <T> void salvarLista(List<T> lista, Class<T> tipo) {
        String arquivo = getCaminhoArquivo(tipo);
        try {
            Files.createDirectories(Paths.get(BASE_PATH));
            String jsonFormatado = gson.toJson(lista);
            Files.writeString(Paths.get(arquivo), jsonFormatado, StandardCharsets.UTF_8);
            System.out.println("[BANCO] Arquivo salvo em formato bonito: " + arquivo);
        } catch (IOException e) {
            System.err.println("Erro ao salvar JSON: " + e.getMessage());
        }
    }

    private static String getCaminhoArquivo(Class<?> tipo) {
        return BASE_PATH + tipo.getSimpleName().toLowerCase() + ".json";
    }
}

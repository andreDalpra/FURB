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
import main.furb.entidades.Usuario;

/**
 * Classe responsável pela persistência simples do sistema através de arquivos CSV.
 * <p>
 * Implementa operações básicas:
 * <ul>
 *   <li>Geração de sequence por tipo (arquivo .seq)</li>
 *   <li>Inserção (append)</li>
 *   <li>Listagem</li>
 *   <li>Atualização</li>
 *   <li>Exclusão</li>
 * </ul>
 * Cada entidade deve implementar {@link Sistema} para fornecer conversões
 * para e a partir de CSV.
 * </p>
 */
public class Banco {

    /** Diretório base onde os arquivos CSV e SEQ são armazenados. */
    private static final String BASE_PATH = "dados/";

    /** Usuário autenticado no sistema (alterado pelo fluxo de login). */
    public static Usuario usuarioLogado;

    /**
     * Obtém o próximo valor de sequência para um tipo específico.
     * <p>
     * O valor é lido do arquivo {@code <tipo>.seq}. Caso o arquivo exista, o valor
     * armazenado é incrementado. Caso não exista, a sequência inicia em 0.
     * O novo valor sempre é gravado de volta no arquivo.
     * </p>
     *
     * @param <T>   tipo da entidade
     * @param p_tipo classe da entidade
     * @return próximo número de sequência
     */
    public static <T extends Sistema> int obtemSequence(Class<T> p_tipo) {
        String l_arquivoSeq = getCaminhoSequencia(p_tipo);
        File file = new File(l_arquivoSeq);
        int l_proximo = 0;

        try {
            if (file.exists()) {
                try (Scanner sc = new Scanner(file, StandardCharsets.UTF_8)) {
                    if (sc.hasNextInt()) {
                        l_proximo = sc.nextInt() + 1;
                    }
                }
            }

            // grava novo valor
            try (PrintWriter pw = new PrintWriter(new FileWriter(file, StandardCharsets.UTF_8, false))) {
                pw.print(l_proximo);
            }

        } catch (IOException e) {
            System.err.println("[BANCO] Erro ao acessar sequência: " + e.getMessage());
        }

        return l_proximo;
    }

    /**
     * Insere um novo registro no arquivo CSV correspondente ao tipo da entidade.
     * O registro é adicionado ao final do arquivo.
     *
     * @param <T>       tipo da entidade
     * @param p_objeto  entidade a ser salva
     * @param p_tipo    classe da entidade
     */
    public static <T extends Sistema> void insert(T p_objeto, Class<T> p_tipo) {
        String l_arquivo = getCaminhoArquivo(p_tipo);

        try {
            File dir = new File(BASE_PATH);
            if (!dir.exists())
                dir.mkdirs();

            try (PrintWriter pw = new PrintWriter(new FileWriter(l_arquivo, StandardCharsets.UTF_8, true))) {
                pw.println(p_objeto.toCSV());
            }
        } catch (IOException e) {
            System.err.println("[BANCO] Erro ao salvar: " + e.getMessage());
        }
    }

    /**
     * Lê todos os registros do arquivo CSV e converte cada linha em objetos
     * da classe especificada.
     *
     * @param <T>   tipo da entidade
     * @param p_tipo classe da entidade
     * @return lista contendo as instâncias lidas do arquivo
     */
    public static <T extends Sistema> List<T> listar(Class<T> p_tipo) {
        List<T> lista = new ArrayList<>();
        String l_arquivo = getCaminhoArquivo(p_tipo);
        File file = new File(l_arquivo);

        if (!file.exists())
            return lista;

        try (Scanner sc = new Scanner(file, StandardCharsets.UTF_8)) {
            while (sc.hasNextLine()) {
                String linha = sc.nextLine().trim();

                if (linha.isEmpty())
                    continue;

                T l_obj = p_tipo.getDeclaredConstructor().newInstance();
                l_obj.fromCSV(linha);
                lista.add(l_obj);
            }
        } catch (Exception e) {
            System.err.println("[BANCO] Erro ao listar: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Remove todos os registros que satisfaçam a condição especificada.
     * Caso algum seja removido, o arquivo é regravado.
     *
     * @param <T> tipo da entidade
     * @param p_tipo classe da entidade
     * @param p_condicao condição usada para identificar registros a remover
     * @return true se ao menos um registro foi removido
     */
    public static <T extends Sistema> boolean delete(Class<T> p_tipo, Predicate<T> p_condicao) {
        List<T> l_lista = listar(p_tipo);
        boolean l_removido = l_lista.removeIf(p_condicao);

        if (l_removido)
            commit(l_lista, p_tipo);

        return l_removido;
    }

    /**
     * Atualiza o primeiro registro encontrado que satisfaça a condição,
     * substituindo-o pelo objeto informado.
     *
     * @param <T> tipo da entidade
     * @param p_tipo classe da entidade
     * @param p_condicao condição usada para localizar o registro a substituir
     * @param p_novoObjeto objeto que substituirá o antigo
     * @return true se o registro foi encontrado e atualizado
     */
    public static <T extends Sistema> boolean update(
            Class<T> p_tipo,
            Predicate<T> p_condicao,
            T p_novoObjeto) {

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
            commit(l_lista, p_tipo);

        return l_atualizado;
    }

    /**
     * Sobrescreve o arquivo CSV com o conteúdo da lista informada.
     *
     * @param <T> tipo da entidade
     * @param p_lista lista de objetos a serem gravados
     * @param p_tipo classe da entidade
     */
    private static <T extends Sistema> void commit(List<T> p_lista, Class<T> p_tipo) {
        String l_arquivo = getCaminhoArquivo(p_tipo);

        try (PrintWriter pw = new PrintWriter(new FileWriter(l_arquivo, StandardCharsets.UTF_8))) {
            for (T obj : p_lista)
                pw.println(obj.toCSV());
        } catch (IOException e) {
            System.err.println("[BANCO] Erro ao sobrescrever: " + e.getMessage());
        }
    }

    /**
     * Retorna o caminho do arquivo CSV referente ao tipo.
     */
    private static String getCaminhoArquivo(Class<?> p_tipo) {
        return BASE_PATH + p_tipo.getSimpleName().toLowerCase() + ".csv";
    }

    /**
     * Retorna o caminho do arquivo de sequência referente ao tipo.
     */
    private static String getCaminhoSequencia(Class<?> p_tipo) {
        return BASE_PATH + p_tipo.getSimpleName().toLowerCase() + ".seq";
    }
}

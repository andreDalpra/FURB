import compilador.*;
import funcionalidades.*;
import view.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

// Executar com o classpath contendo as classes compiladas de src.
public class Verificacao {
    static Path pasta;
    static int passou, falhou, numero;
    static PrintStream saida = System.out;
    interface Teste { void executar() throws Exception; }
    static void testar(String nome, Teste teste) {
        try { teste.executar(); passou++; saida.println("PASSOU " + nome); }
        catch (Throwable e) { falhou++; saida.println("FALHOU " + nome + ": " + e); }
    }
    static void exigir(boolean ok, String mensagem) { if (!ok) throw new AssertionError(mensagem); }
    static Semantico analisar(String fonte) throws Exception {
        Semantico s = new Semantico(); Lexico l = new Lexico();
        l.setInput(new StringReader(fonte)); new Sintatico().parse(l, s);
        for (String nome : new String[]{"pilhaTipos", "pilhaRotulos"}) {
            Field f = Semantico.class.getDeclaredField(nome); f.setAccessible(true);
            exigir(((Stack<?>)f.get(s)).empty(), nome + " nao ficou vazia");
        }
        return s;
    }
    static String processo(List<String> comando, String entrada) throws Exception {
        Path log = pasta.resolve("processo-" + (++numero) + ".log");
        Process p = new ProcessBuilder(comando).redirectErrorStream(true).redirectOutput(log.toFile()).start();
        try (OutputStream out = p.getOutputStream()) { out.write(entrada.getBytes(StandardCharsets.UTF_8)); }
        if (!p.waitFor(10, TimeUnit.SECONDS)) { p.destroyForcibly(); throw new AssertionError("timeout: " + comando); }
        String texto = Files.readString(log, StandardCharsets.ISO_8859_1).trim();
        exigir(p.exitValue() == 0, "processo falhou: " + texto);
        return texto;
    }
    static void executar(String nome, String fonte, String entrada, String esperado) {
        testar(nome, () -> {
            Path il = pasta.resolve("teste-" + (++numero) + ".il");
            Files.writeString(il, analisar(fonte).getCodigoObjeto());
            Path exe = Path.of(il.toString().replace(".il", ".exe"));
            processo(List.of("C:\\Windows\\Microsoft.NET\\Framework64\\v4.0.30319\\ilasm.exe", il.toString(), "/exe", "/output:" + exe), "");
            String obtido = processo(List.of(exe.toString()), entrada);
            exigir(obtido.replace(',', '.').equals(esperado.replace(',', '.')), "esperado [" + esperado + "] obtido [" + obtido + "]");
        });
    }
    static void erro(String nome, String fonte, Class<?> classe, int linha, String mensagem) {
        testar(nome, () -> {
            try { analisar(fonte); throw new AssertionError("erro nao detectado"); }
            catch (AnalysisError e) {
                exigir(classe.isInstance(e), "erro de outra classe: " + e);
                exigir(new LexicoCompiler(fonte).linhaDaPosicao(e.getPosition()) == linha, "linha incorreta: " + e.getPosition());
                if (mensagem != null) exigir(e.getMessage().equals(mensagem), "mensagem: " + e.getMessage());
            }
        });
    }
    static class ConsoleTeste extends view.Console {
        StringBuilder texto = new StringBuilder();
        public void clear() { if (texto != null) texto.setLength(0); super.clear(); }
        public void writeLine(String linha) { texto.append(linha).append('\n'); super.writeLine(linha); }
    }
    static void controller(String nome, String fonte, String mensagem, boolean sucesso) {
        testar(nome, () -> {
            Editor editor = new Editor(); ConsoleTeste console = new ConsoleTeste();
            ArquivoController arquivo = new ArquivoController(editor, console, new BarraStatus());
            Path txt = pasta.resolve("controller-" + (++numero) + ".txt");
            Field campo = ArquivoController.class.getDeclaredField("arquivoAtual"); campo.setAccessible(true); campo.set(arquivo, txt.toFile());
            editor.setText(fonte); new CompileController(console, editor, arquivo).compilar();
            exigir(console.texto.toString().contains(mensagem), "console: " + console.texto);
            Path il = Path.of(txt.toString().replace(".txt", ".il"));
            exigir(Files.exists(il) == sucesso, "geracao do IL incorreta");
            exigir(Files.readString(txt).equals(fonte), "nao salvou o fonte antes de analisar");
        });
    }
    public static void main(String[] args) throws Exception {
        pasta = Path.of(args[0]); Files.createDirectories(pasta);
        System.setOut(new PrintStream(OutputStream.nullOutputStream()));
        for (String literal : new String[]{"0", "1", "2", "10", "123", "1,5", "1.5"}) {
            try { Lexico l = new Lexico(); l.setInput(new StringReader(literal + ")")); Token t = l.nextToken(); saida.println("LEXEMA " + literal + " -> " + t); }
            catch (AnalysisError e) { saida.println("LEXEMA " + literal + " -> " + e); }
        }
        executar("roteiro module out true 1", "module { out(true, 1) }", "", "True1");
        executar("strings e logicos", "module { out(\"ok\", true and false, false or true, not true, not false) }", "", "okFalseTrueFalseTrue");
        executar("aritmetica precedencia", "module { out(2 + 3 * 4, \"|\", 10 - 3 - 2, \"|\", 8 / 2) }", "", "14|5|4");
        executar("unarios numericos", "module { out(+ 10, \"|\", - 10, \"|\", + (2 / 4)) }", "", "10|-10|0.5");
        executar("reais e mistos", "module { out(1,5 + 2,5, \"|\", 2 * 1,5) }", "", "4|3");
        executar("aritmetica sem ambiguidade lexical", "module { out((2 + 3 * 4), \"|\", (10 - 3 - 2), \"|\", (8 / 2)) }", "", "14|5|4");
        executar("unarios sem ambiguidade lexical", "module { out((+ 10), \"|\", (- 10), \"|\", (+ (2 / 4))) }", "", "10|-10|0.5");
        executar("comparacoes numericas delimitadas", "module { out((2 = 2), (2 <> 3), (2 < 3), (3 > 2), (2 > 3)) }", "", "TrueTrueTrueTrueFalse");
        executar("relacionais numericos", "module { out(2 = 2, 2 <> 3, 2 < 3, 3 > 2, 2 > 3) }", "", "TrueTrueTrueTrueFalse");
        executar("relacionais strings", "module { out(\"a\" = \"a\", \"a\" <> \"b\", \"a\" < \"b\", \"b\" > \"a\") }", "", "TrueTrueTrueTrue");
        executar("igualdade strings lidas", "module s_a, s_b {in(s_a, s_b) out(s_a = s_b)}", "igual\nigual\n", "True");
        executar("ordem strings lidas", "module s_a, s_b {in(s_a, s_b) out(s_b < s_a)}", "z\na\n", "True");
        executar("quatro tipos atribuicao", "module i_a, f_a, s_a, b_a { i_a <- 10 f_a <- i_a / 4 s_a <- \"ok\" b_a <- true out(i_a, \"|\", f_a, \"|\", s_a, \"|\", b_a) }", "", "10|2.5|ok|True");
        executar("quatro tipos entrada", "module i_a, f_a, s_a, b_a { in(i_a, f_a, s_a, b_a) out(i_a, \"|\", f_a, \"|\", s_a, \"|\", b_a) }", "8\n2\nAndre\ntrue\n", "8|2|Andre|True");
        executar("if todas variantes", "module { if(true) isTrueDo {out(\"A\")} if(false) isTrueDo {out(\"X\")} if(false) isFalseDo {out(\"B\")} if(true) isFalseDo {out(\"X\")} if(false) isTrueDo {out(\"X\")} isFalseDo {out(\"C\")} if(true) isFalseDo {out(\"X\")} isTrueDo {out(\"D\")} }", "", "ABCD");
        executar("if aninhado", "module { if(true) isTrueDo {if(false) isTrueDo {out(\"X\")} isFalseDo {out(\"A\")}} isFalseDo {out(\"X\")} out(\"B\") }", "", "AB");
        executar("while duas variantes", "module i_i {i_i <- 0 while(i_i < 3) isTrueDo {out(i_i) i_i <- i_i + 1} while(i_i = 0) isFalseDo {out(i_i) i_i <- i_i - 1}}", "", "012321");
        executar("while zero iteracoes", "module {while(false) isTrueDo {out(\"X\")} while(true) isFalseDo {out(\"X\")} out(\"ok\")}", "", "ok");
        executar("lacos e if aninhados", "module i_i, i_j {i_i <- 0 while(i_i < 2) isTrueDo {i_j <- 0 while(i_j < 2) isTrueDo {if(i_i = i_j) isTrueDo {out(\"A\")} isFalseDo {out(\"B\")} i_j <- i_j + 1} i_i <- i_i + 1}}", "", "ABBA");
        executar("vetor inteiro atribuicao", "module i_v[3] {i_v[0] <- 10 i_v[1] <- 20 i_v[2] <- i_v[0] + i_v[1] out(i_v[2])}", "", "30");
        executar("vetores quatro tipos entrada", "module i_v[2], f_v[2], s_v[2], b_v[2] {in(i_v[0], f_v[0], s_v[0], b_v[0]) out(i_v[0], \"|\", f_v[0], \"|\", s_v[0], \"|\", b_v[0])}", "8\n2\nAndre\nfalse\n", "8|2|Andre|False");
        executar("indice aninhado", "module i_v[3] {i_v[0] <- 1 i_v[i_v[0]] <- 9 out(i_v[i_v[0]])}", "", "9");
        String msg = "tipo incompativel em operador aritmetico unario +";
        erro("equipe string", "module {\n out(+ \"texto\")\n}", SemanticError.class, 2, msg);
        erro("equipe bool", "module {\n out(+ (true))\n}", SemanticError.class, 2, msg);
        erro("obrigatoria exemplo 3", "module\ni_lado, i_i\n{\n out(\"digite\")\n in(i_lado)\n out(i_lado)\n}", SemanticError.class, 7, "i_i identificador declarado, n\u00e3o utilizado");
        erro("apenas out nao conta como uso", "module\ni_a\n{out(i_a)\n}", SemanticError.class, 4, "i_a identificador declarado, n\u00e3o utilizado");
        erro("lexico linha", "module {\n out(@)\n}", LexicalError.class, 2, null);
        erro("sintatico linha", "module {\n in i_a)\n}", SyntaticError.class, 2, null);
        controller("interface sucesso e arquivo IL", "module {out(true)}", "programa compilado com sucesso", true);
        controller("interface erro lexico sem IL", "module {\n out(@)\n}", "linha 2: erro", false);
        controller("interface erro sintatico conforme enunciado", "module {\n in i_a)\n}", "linha 2: encontrado i_a, erro sint", false);
        controller("interface erro equipe sem IL", "module {\n out(+ \"texto\")\n}", "linha 2: " + msg, false);
        testar("arquivo IL junto ao fonte e salvar alteracoes", () -> {
            Editor editor = new Editor(); view.Console console = new view.Console();
            ArquivoController arquivo = new ArquivoController(editor, console, new BarraStatus());
            Path fonte = pasta.resolve("teste.nome.txt"); editor.setText("module {out(true)}");
            Field f = ArquivoController.class.getDeclaredField("arquivoAtual"); f.setAccessible(true); f.set(arquivo, fonte.toFile());
            exigir(arquivo.salvar(), "nao salvou");
            Path il = arquivo.salvarCodigoObjeto(analisar(editor.getText()).getCodigoObjeto()).toPath();
            exigir(il.equals(pasta.resolve("teste.nome.il")), "nome ou pasta incorretos");
            exigir(Files.readString(fonte).equals(editor.getText()), "fonte diferente do editor");
            editor.setText("module {out(false)}"); exigir(arquivo.salvar(), "nao salvou alteracao");
            exigir(Files.readString(fonte).equals(editor.getText()), "alteracao nao persistida");
        });
        testar("sem fonte salvo nao gera IL", () -> {
            ArquivoController arquivo = new ArquivoController(new Editor(), new view.Console(), new BarraStatus());
            try {arquivo.salvarCodigoObjeto("x"); throw new AssertionError("permitiu IL sem fonte");} catch(IOException esperado) {}
        });
        saida.println("TOTAL: " + passou + " passaram; " + falhou + " falharam.");
        if (falhou > 0) System.exit(1);
    }
}

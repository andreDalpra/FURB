import compilador.*;
import funcionalidades.*;
import view.*;
import java.lang.reflect.Field;
import java.nio.file.*;
import java.util.List;

public class ExemplosEnunciado {
    public static void main(String[] args) throws Exception {
        Verificacao.pasta = Path.of(args[0]); Files.createDirectories(Verificacao.pasta);
        String[] fontes = {
            "module\ni_lado, i_i\n{\n out (\"digite um valor para lado: )\n in i_lado)\n out (\"o valor digitado foi: \", i_lado)\n}",
            "module\ni_lado, i_i\n{\n out (\"digite um valor para lado: \")\n in i_lado)\n out (\"o valor digitado foi: \", i_lado)\n}",
            "module\ni_lado, i_i\n{\n out (\"digite um valor para lado: \")\n in (i_lado)\n out (\"o valor digitado foi: \", i_lado)\n}",
            "module\ni_lado\n{\n out (\"digite um valor para lado: \")\n in (i_lado)\n out (\"o valor digitado foi: \", i_lado)\n}"
        };
        String[] mensagens = {"linha 4: erro l\u00e9xico", "linha 5: encontrado i_lado, erro sint\u00e1tico",
            "linha 7: i_i identificador declarado, n\u00e3o utilizado", "programa compilado com sucesso"};
        for (int i = 0; i < fontes.length; i++) {
            Editor editor = new Editor(); Verificacao.ConsoleTeste console = new Verificacao.ConsoleTeste();
            ArquivoController arquivo = new ArquivoController(editor, console, new BarraStatus());
            Path txt = Verificacao.pasta.resolve("exemplo" + (i + 1) + ".txt");
            Field campo = ArquivoController.class.getDeclaredField("arquivoAtual"); campo.setAccessible(true); campo.set(arquivo, txt.toFile());
            editor.setText(fontes[i]); new CompileController(console, editor, arquivo).compilar();
            Verificacao.exigir(console.texto.toString().trim().equals(mensagens[i]), "Exemplo " + (i+1) + ": " + console.texto);
            Path il = Verificacao.pasta.resolve("exemplo" + (i+1) + ".il");
            Verificacao.exigir(Files.exists(il) == (i == 3), "IL gerado em caso de erro ou ausente no sucesso");
            System.out.println("PASSOU exemplo " + (i+1) + ": " + console.texto.toString().trim());
            if (i == 3) {
                Path exe = Verificacao.pasta.resolve("exemplo4.exe");
                Verificacao.processo(List.of("C:\\Windows\\Microsoft.NET\\Framework64\\v4.0.30319\\ilasm.exe", il.toString(), "/exe", "/output:" + exe), "");
                String saida = Verificacao.processo(List.of(exe.toString()), "8\n");
                Verificacao.exigir(saida.equals("digite um valor para lado: o valor digitado foi: 8"), saida);
                System.out.println("PASSOU execucao do IL do exemplo 4");
            }
        }
        Verificacao.erro("out nao marca uso", "module\ni_a\n{out(i_a)\n}", SemanticError.class, 4, "i_a identificador declarado, n\u00e3o utilizado");
        Verificacao.erro("equipe mantida", "module {\n out(+ \"texto\")\n}", SemanticError.class, 2, "tipo incompativel em operador aritmetico unario +");
        if (Verificacao.falhou > 0) System.exit(1);
    }
}

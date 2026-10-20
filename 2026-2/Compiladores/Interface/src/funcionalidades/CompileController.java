package funcionalidades;

import java.io.StringReader;

import compilador.LexicalError;
import compilador.Lexico;
import compilador.SemanticError;
import compilador.Semantico;
import compilador.Sintatico;
import compilador.SyntaticError;
import view.Console;
import view.Editor;

public class CompileController {

	private Console console;
	private Editor editor;

	public CompileController(Console console, Editor editor) {
		this.console = console;
		this.editor = editor;
	}

	public void compilar() {
		String codigo = editor.getText();
		Lexico lexico = new Lexico();
		Sintatico sintatico = new Sintatico();
		Semantico semantico = new Semantico();
		LexicoCompiler apoioLexico = new LexicoCompiler(codigo);

		lexico.setInput(new StringReader(codigo));
		console.clear();

		try {
			sintatico.parse(lexico, semantico);
			console.writeLine("programa compilado com sucesso");
		} catch (LexicalError erro) {
			console.writeLine(String.format("linha %d: erro léxico",
					apoioLexico.linhaDaPosicao(erro.getPosition())));
		} catch (SyntaticError erro) {
			console.writeLine(String.format("linha %d: encontrado %s esperado %s",
					apoioLexico.linhaDaPosicao(erro.getPosition()),
					apoioLexico.simboloEncontrado(erro.getPosition()),
					erro.getMessage()));
		} catch (SemanticError erro) {
			throw new IllegalStateException("Erro semântico inesperado.", erro);
		}
	}
}

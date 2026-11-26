package funcionalidades;

import java.io.StringReader;
import java.io.File;
import java.io.IOException;

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
	private ArquivoController arquivoController;

	public CompileController(Console console, Editor editor, ArquivoController arquivoController) {
		this.console = console;
		this.editor = editor;
		this.arquivoController = arquivoController;
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
			File arquivoGerado = arquivoController.salvarCodigoObjeto(semantico.getCodigoObjeto());
			console.writeLine("programa compilado com sucesso");
			console.writeLine("código objeto salvo em: " + arquivoGerado.getAbsolutePath());
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
		} catch (IOException erro) {
			console.writeLine("Erro ao salvar o código objeto.");
		}
	}
}

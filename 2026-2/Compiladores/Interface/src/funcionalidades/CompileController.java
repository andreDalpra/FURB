package funcionalidades;

import view.Console;
import view.Editor;
//Compiladores
import funcionalidades.LexicoCompiler;

public class CompileController {

	private Console console;
	private Editor editor;

	public CompileController(Console console, Editor editor) {
		this.console = console;
		this.editor = editor;
	}

	public void compilar() {
		LexicoCompiler lexico = new LexicoCompiler(editor.getText());
		console.clear();

		for (String mensagem : lexico.compilar()) {
			if (mensagem.equals(LexicoCompiler.CABECALHO)) {
				console.writeHeader(mensagem);
			} else {
				console.writeLine(mensagem);
			}
		}
	}
	
	
}

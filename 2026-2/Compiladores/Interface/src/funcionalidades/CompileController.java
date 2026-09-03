package funcionalidades;

import view.Console;

public class CompileController {

	private static final String MENSAGEM_COMPILACAO_NAO_IMPLEMENTADA = "compilação de programas ainda não foi implementada";

	private Console console;

	public CompileController(Console console) {
		this.console = console;
	}

	public void compilar() {
		console.clear();
		console.writeLine(MENSAGEM_COMPILACAO_NAO_IMPLEMENTADA);
	}
}

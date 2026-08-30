package funcionalidades;

import compilador.Console;

public class EquipeController {

	private static final String[] NOMES_EQUIPE = {
			"Andre Luiz Dalprá dos Santos",
			"Miguel Vieiro da Silva"
	};

	private Console console;

	public EquipeController(Console console) {
		this.console = console;
	}

	public void mostrarEquipe() {
		console.clear();
		console.writeLine("Equipes do trabalho:");
		
		for (String nome : NOMES_EQUIPE) {
			console.writeLine(nome);
		}
	}
}

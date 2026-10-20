package funcionalidades;

import java.io.StringReader;

import compilador.Constants;
import compilador.LexicalError;
import compilador.Lexico;
import compilador.Token;

public class LexicoCompiler {

	private String codigo;

	public LexicoCompiler(String codigo) {
		this.codigo = codigo;
	}

	public int linhaDaPosicao(int posicao) {
		int linha = 1;
		int limite = Math.min(Math.max(posicao, 0), codigo.length());

		for (int indice = 0; indice < limite; indice++) {
			if (codigo.charAt(indice) == '\n') {
				linha++;
			}
		}
		return linha;
	}

	public String simboloEncontrado(int posicao) {
		Lexico lexico = new Lexico();
		lexico.setInput(new StringReader(codigo));

		try {
			Token token;
			while ((token = lexico.nextToken()) != null) {
				if (token.getPosition() == posicao) {
					if (token.getId() == Constants.t_cte_string) {
						return "constante_string";
					}
					return token.getLexeme();
				}
			}
		} catch (LexicalError erro) {
			// O erro léxico é tratado antes do erro sintático.
		}

		return "EOF";
	}
}

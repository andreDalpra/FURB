package funcionalidades;

import java.util.ArrayList;
import java.util.List;
import java.io.StringReader;

import compilador.lexico.Constants;
import compilador.lexico.LexicalError;
import compilador.lexico.Lexico;
import compilador.lexico.Token;

public class LexicoCompiler {
	public static final String CABECALHO = String.format("%-8s%-22s%s", "linha", "classe", "lexema");

    private String codigo;

	public LexicoCompiler(String codigo) {
		this.codigo = codigo;
	}
	
	public List<String> compilar() {
		Lexico lexico = new Lexico();
		lexico.setInput(new StringReader(codigo));
		List<Token> tokens = new ArrayList<>();

		try {
			Token token;
			while ((token = lexico.nextToken()) != null) {
				if (token.getId() == Constants.t_palavra) {
					if (ehInicioDeIdentificadorInvalido(token)) {
						return List.of(String.format("linha %d: identificador inválido",
								linhaDaPosicao(token.getPosition())));
					}
					return List.of(formatarPalavraReservadaInvalida(token));
				}
				tokens.add(token);
			}
		} catch (LexicalError erro) {
			return List.of(formatarErro(erro));
		}

		List<String> mensagens = new ArrayList<>();
		mensagens.add(CABECALHO);

		for (Token token : tokens) {
			mensagens.add(String.format("%-8d%-22s%s",
					linhaDaPosicao(token.getPosition()),
					classeDoToken(token.getId()),
					token.getLexeme()));
		}

		mensagens.add("");
		mensagens.add("programa compilado com sucesso");
		return mensagens;
	}

	private String classeDoToken(int id) {
		if (id >= Constants.t_TOKEN_3 && id <= Constants.t_TOKEN_20) {
			return "símbolo especial";
		}
		if (id >= Constants.t_and && id <= Constants.t_while) {
			return "palavra reservada";
		}
		if (id >= Constants.t_id_int && id <= Constants.t_id_bool) {
			return "identificador";
		}
		if (id == Constants.t_cte_int) {
			return "constante_int";
		}
		if (id == Constants.t_cte_float) {
			return "constante_float";
		}
		if (id == Constants.t_cte_string) {
			return "constante_string";
		}
		return "token desconhecido";
	}

	private String formatarPalavraReservadaInvalida(Token token) {
		return String.format("linha %d: %s palavra reservada inválida",
				linhaDaPosicao(token.getPosition()), token.getLexeme());
	}

	private boolean ehInicioDeIdentificadorInvalido(Token token) {
		int posicaoSeguinte = token.getPosition() + token.getLexeme().length();
		if (posicaoSeguinte >= codigo.length() || codigo.charAt(posicaoSeguinte) != '_') {
			return false;
		}

		return token.getLexeme().equals("i")
				|| token.getLexeme().equals("f")
				|| token.getLexeme().equals("s")
				|| token.getLexeme().equals("b");
	}

	private String formatarErro(LexicalError erro) {
		int posicao = Math.max(0, erro.getPosition());
		int linha = linhaDaPosicao(posicao);
		String mensagem = erro.getMessage() == null ? "" : erro.getMessage();

		if (mensagem.contains("palavra")) {
			return String.format("linha %d: %s palavra reservada inválida",
					linha, sequenciaNaPosicao(posicao));
		}
		if (mensagem.contains("id_") || mensagem.contains("identificador inválido")) {
			return String.format("linha %d: identificador inválido", linha);
		}
		if (mensagem.contains("cte_string") || mensagem.contains("constante_string inválida")) {
			return String.format("linha %d: constante_string inválida", linha);
		}
		if (mensagem.contains("<ignorar>") || mensagem.contains("comentário inválido")) {
			return String.format("linha %d: comentário inválido ou não finalizado", linha);
		}

		return String.format("linha %d: %s símbolo inválido",
				linha, simboloNaPosicao(posicao));
	}

	private int linhaDaPosicao(int posicao) {
		int linha = 1;
		int limite = Math.min(Math.max(posicao, 0), codigo.length());

		for (int indice = 0; indice < limite; indice++) {
			if (codigo.charAt(indice) == '\n') {
				linha++;
			}
		}
		return linha;
	}

	private String simboloNaPosicao(int posicao) {
		if (posicao >= codigo.length()) {
			return "";
		}
		return String.valueOf(codigo.charAt(posicao));
	}

	private String sequenciaNaPosicao(int posicao) {
		int fim = posicao;
		while (fim < codigo.length() && Character.isLetter(codigo.charAt(fim))) {
			fim++;
		}
		return codigo.substring(posicao, fim);
	}
}

package main.java.furb.entidades;

import static main.java.furb.mensagem.Mensagem.*;
import main.java.furb.controle.Sistema;
import main.java.furb.enums.TipoUsuario;

public class Usuario implements Sistema {
	// Atributos
	private int sequsu;
	private String codusu;
	private int senusu;
	private TipoUsuario tipusu;
	private String nomusu;
	private String emlusu;

	public Usuario(int p_sequsu, String p_codusu, int p_senusu, TipoUsuario p_tipusu, String p_nomusu,
			String p_emlusu) {
		this.sequsu = p_sequsu;
		this.codusu = p_codusu;
		this.senusu = p_senusu;
		this.tipusu = p_tipusu;
		this.nomusu = p_nomusu;
		this.emlusu = p_emlusu;
	}

	@Override
	public boolean valida() {

		inicializaMensagem();

		if (codusu == null || codusu.isBlank()) {
			montaMensagem(1, new String[] { codusu });
			return false;
		}

		if (!validaSenha()) {
			return false;
		}
		if (tipusu == null) {
			montaMensagem(4, new String[] { codusu, tipusu.name() });
		}
		return true;
	}

	public boolean validaSenha() {
		return true;

	}

	public int getSequsu() {
		return sequsu;
	}

	public String getCodusu() {
		return codusu;
	}

	public int getSenusu() {
		return senusu;
	}

	public TipoUsuario getTipusu() {
		return tipusu;
	}

	public String getNomusu() {
		return nomusu;
	}

	public String getEmlusu() {
		return emlusu;
	}

}

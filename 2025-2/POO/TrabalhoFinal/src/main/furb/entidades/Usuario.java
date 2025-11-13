package main.furb.entidades;

import main.furb.app.Sistema;
import main.furb.enums.TipoUsuario;
import static main.furb.mensagem.Mensagem.*;
import static main.furb.banco.Banco.*;

public class Usuario implements Sistema {
	private int sequsu;
	private String codusu;
	private int senusu;
	private String nomusu;  
	private String emlusu;
	private TipoUsuario tipusu;

	@Override
	public boolean valida() {
		if (codusu == null || codusu.isBlank()) {
			montaMensagem(1, codusu );
			return false;
		}

		if (!validaSenha()) {
			return false;
		}
		if (tipusu == null) {
			montaMensagem(4, codusu, tipusu.name() );
		}
		return true;
	}

	@Override
	public boolean before_post() {
		//NAO PRECISA INICIALIZAR MENSAGEM
		inicializaMensagem();
		
		if (sequsu == 0) {
			sequsu = obtemSequence(Usuario.class);
		}
		
		if (!valida()) {
			return false;
		}

		var l_usuarios = listar(Usuario.class);
		boolean l_existe = l_usuarios.stream().anyMatch(u -> u.getCodusu().equalsIgnoreCase(this.codusu));

		if (l_existe) {
			montaMensagem(13,this.codusu );
			return false;
		}

		return true;
	}

	public boolean validaSenha() {

		String l_senhaStr = String.valueOf(senusu);

		if (senusu < 1000 || senusu > 9999) {
			montaMensagem(7, codusu, l_senhaStr );
			return false;
		}

		boolean l_todosIguais = l_senhaStr.chars().allMatch(c -> c == l_senhaStr.charAt(0));
		if (l_todosIguais) {
			montaMensagem(7, codusu, l_senhaStr );
			return false;
		}

		boolean l_sequencialCrescente = true;
		for (int l_i = 0; l_i < l_senhaStr.length() - 1; l_i++) {
			if (l_senhaStr.charAt(l_i + 1) != l_senhaStr.charAt(l_i) + 1) {
				l_sequencialCrescente = false;
				break;
			}
		}
		if (l_sequencialCrescente) {
			montaMensagem(7, codusu, l_senhaStr );
			return false;
		}

		boolean l_sequencialDecrescente = true;
		for (int l_i = 0; l_i < l_senhaStr.length() - 1; l_i++) {
			if (l_senhaStr.charAt(l_i + 1) != l_senhaStr.charAt(l_i) - 1) {
				l_sequencialDecrescente = false;
				break;
			}
		}
		if (l_sequencialDecrescente) {
			montaMensagem(7,  codusu, l_senhaStr );
			return false;
		}

		if (l_senhaStr.equals("0000") || l_senhaStr.equals("1234")) {
			montaMensagem(7, codusu, l_senhaStr );
			return false;
		}

		return true;
	}

	public int getSequsu() {
		return sequsu;
	}

	public void setSequsu(int sequsu) {
		this.sequsu = sequsu;
	}

	public String getCodusu() {
		return codusu;
	}

	public void setCodusu(String codusu) {
		this.codusu = codusu;
	}

	public int getSenusu() {
		return senusu;
	}

	public void setSenusu(int senusu) {
		this.senusu = senusu;
	}

	public String getNomusu() {
		return nomusu;
	}

	public void setNomusu(String nomusu) {
		this.nomusu = nomusu;
	}

	public String getEmlusu() {
		return emlusu;
	}

	public void setEmlusu(String emlusu) {
		this.emlusu = emlusu;
	}

	public TipoUsuario getTipusu() {
		return tipusu;
	}

	public void setTipusu(TipoUsuario tipusu) {
		this.tipusu = tipusu;
	}

	@Override
	public String toCSV() {
		StringBuilder sb = new StringBuilder();
		sb.append("sequsu : ").append(sequsu).append(";\n");
		sb.append("codusu : ").append(codusu).append(";\n");
		sb.append("senusu : ").append(senusu).append(";\n");
		sb.append("nomusu : ").append(nomusu).append(";\n");
		sb.append("emlusu : ").append(emlusu).append(";\n");
		sb.append("tipusu : ").append(tipusu.name()).append(";\n");
		sb.append("---"); // separador entre registros (opcional)
		return sb.toString();
	}

	@Override
	public void fromCSV(String linha) {
		String[] partes = linha.split(";");
		this.sequsu = Integer.parseInt(partes[0]);
		this.codusu = partes[1];
		this.senusu = Integer.parseInt(partes[2]);
		this.nomusu = partes[3];
		this.emlusu = partes[4];
		this.tipusu = TipoUsuario.valueOf(partes[5]);
	}

}

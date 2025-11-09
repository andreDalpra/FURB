package main.furb.entidades;

import main.furb.app.Sistema;
import main.furb.enums.TipoUsuario;

public class Usuario implements Sistema {
	private int sequsu;
	private String codusu;
	private int senusu;
	private String nomusu;
	private String emlusu;
	private TipoUsuario tipusu;

	@Override
	public boolean valida() {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fromCSV(String linha) {
		// TODO Auto-generated method stub

	}

}

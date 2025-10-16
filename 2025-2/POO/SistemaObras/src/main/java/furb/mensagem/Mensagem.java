package main.java.furb.mensagem;

import static main.java.furb.controle.Sistema.converteHTML;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import main.java.furb.controle.Sistema;
import main.java.furb.enums.TipoMensagem;

public class Mensagem implements Sistema{
	private static Mensagem msg;
	private static int codmsg;
	private static String desmsg;
	private static boolean temMensagem;
	public static TipoMensagem tipmsg;

	// {código → [descrição, tipo]}
	private static Map<Integer, Object[]> mensagens = new HashMap<>();

	// inicaliza as variaveis de mensagem
	public static void inicializaMensagem() {
		codmsg = 0;
		desmsg = "";
		temMensagem = false;
		tipmsg = TipoMensagem.OK;
	}

	// Monta a mensagem com base no código e os parametros passados na classe
	public static String montaMensagem(int p_codmsg, String[] p_parametros) {
		Object[] carrega_msg = mensagens.get(p_codmsg);

		if (carrega_msg == null) {
			desmsg = "Mensagem não cadastrada (cód. " + p_codmsg + ")";
			tipmsg = TipoMensagem.WARNING;
		} else {
			desmsg = (String) carrega_msg[0];
			tipmsg = (TipoMensagem) carrega_msg[1];
		}

		String msgFormatada;
		try {
			msgFormatada = String.format(desmsg, (Object[]) p_parametros);
		} catch (Exception e) {
			msgFormatada = desmsg + "\n(cód. " + p_codmsg + ")";
		}

		desmsg = msgFormatada;
		temMensagem = true;

		return desmsg;
	}

	public static void mostrarMensagem() {
	    if (temMensagem) {
	        String textoHTML = converteHTML(desmsg);
	        new MensagemUI(tipmsg.name(), textoHTML, tipmsg).setVisible(true);	        
	        temMensagem = false;	  
	    }
	}
	
	@Override
	public boolean valida() {
		// TODO Auto-generated method stub
		return false;
	}

	// mostra no JOptionPane, se nao houve um codmsg cadastrado
	public static void mostrarMensagem(String p_mensagem) {
		JOptionPane.showMessageDialog(null, p_mensagem);
	}
	
	//Cadastra uma nova Mensagem
	public static void cadastrarMensagem(int p_codmsg, String p_desmsg, TipoMensagem p_tipo) {
		mensagens.put(p_codmsg, new Object[] { p_desmsg, p_tipo });
	}


	public int getCodmsg() {
		return codmsg;
	}

	public void setCodmsg(int p_codmsg) {
		this.codmsg = p_codmsg;
	}

	public String getDesmsg() {
		return desmsg;
	}

	public void setDesmsg(String p_desmsg) {
		this.desmsg = p_desmsg;
	}

	public Mensagem getmsg() {
		return msg;
	}

	public void setOp_msg(Mensagem p_msg) {
		this.msg = p_msg;
	}

	public static Mensagem getMsg() {
		return msg;
	}

	public static void setMsg(Mensagem p_msg) {
		Mensagem.msg = p_msg;
	}

	public static boolean isTemMensagem() {
		return temMensagem;
	}

	public static void setTemMensagem(boolean temMensagem) {
		Mensagem.temMensagem = temMensagem;
	}

	public static Map<Integer, Object[]> getMensagens() {
		return mensagens;
	}

	public static void setMensagens(Map<Integer, Object[]> p_mensagens) {
		Mensagem.mensagens = p_mensagens;
	}

	public TipoMensagem getTipmsg() {
		return tipmsg;
	}

	public void setTipmsg(TipoMensagem p_tipmsg) {
		this.tipmsg = p_tipmsg;
	}

}
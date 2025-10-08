package mensagem;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

public class Mensagem {
	private static Mensagem msg;
	private static int codmsg;
	private static String desmsg;
	private static boolean temMensagem;
	public static TipoMensagem tipmsg;

	// {código → [descrição, tipo]}
	private static Map<Integer, Object[]> mensagens = new HashMap<>();

	//Cadastra uma nova Mensagem
	public static void cadastrarMensagem(int codmsg, String desmsg, TipoMensagem tipo) {
		mensagens.put(codmsg, new Object[] { desmsg, tipo });
	}

	// inicaliza as variaveis de mensagem
	public static void inicializaMensagem() {
		codmsg = 0;
		desmsg = "";
		temMensagem = false;
		tipmsg = TipoMensagem.OK;
	}

	// Monta a mensagem com base no código e os parametros passados na classe
	public static String montaMensagem(int codmsg, String[] params) {
		Object[] dados = mensagens.get(codmsg);

		String formato;
		if (dados == null) {
			formato = "Mensagem não cadastrada (cód. " + codmsg + ")";
			tipmsg = TipoMensagem.WARNING;
		} else {
			formato = (String) dados[0];
			tipmsg = (TipoMensagem) dados[1];
		}

		String msgFormatada;
		try {
			msgFormatada = String.format(formato, (Object[]) params);
		} catch (Exception e) {
			msgFormatada = formato + "\n(cód. " + codmsg + ")";
		}

		desmsg = msgFormatada;
		temMensagem = true;

		return msgFormatada;
	}

	// Mostra a mensagem cadastrada com desing JDialog
	public static void mostrarMensagem() {
		if (temMensagem) {
			String textoHTML = converteHTML(desmsg);
			new MensagemUI(tipmsg.name(), textoHTML, tipmsg).setVisible(true);
			temMensagem = false;
		}
	}

	// mostra no JOptionPane, se nao houve um codmsg cadastrado
	public static void mostrarMensagem(String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem);
	}

	private static String converteHTML(String p_txtHTML) {
		return "<html>" + p_txtHTML.replace("\n", "<br>") + "</html>";
	}

	public int getCodmsg() {
		return codmsg;
	}

	public void setCodmsg(int codmsg) {
		this.codmsg = codmsg;
	}

	public String getDesmsg() {
		return desmsg;
	}

	public void setDesmsg(String desmsg) {
		this.desmsg = desmsg;
	}

	public Mensagem getmsg() {
		return msg;
	}

	public void setOp_msg(Mensagem msg) {
		this.msg = msg;
	}

	public static Mensagem getMsg() {
		return msg;
	}

	public static void setMsg(Mensagem msg) {
		Mensagem.msg = msg;
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

	public static void setMensagens(Map<Integer, Object[]> mensagens) {
		Mensagem.mensagens = mensagens;
	}

	public TipoMensagem getTipmsg() {
		return tipmsg;
	}

	public void setTipmsg(TipoMensagem tipmsg) {
		this.tipmsg = tipmsg;
	}
}

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
	private static Map<Integer, String> mensagens = new HashMap<>();

	public static void cadastrarMensagem(int codmsg, String desmsg) {
		mensagens.put(codmsg, desmsg);
	}

	// 🔹 Inicializa (zera estado)
	public static void inicializaMensagem() {
		codmsg = 0;
		desmsg = "";
		temMensagem = false;
		tipmsg = TipoMensagem.OK;
	}

	public static String montaMensagem(int codmsg, String[] params) {
		String formato = mensagens.getOrDefault(codmsg, "Mensagem não cadastrada");
		String msg;
		try {
			msg = String.format(formato, (Object[]) params);
		} catch (Exception e) {
			msg = formato + "\n(cód. " + codmsg + ")";
		}
		desmsg = msg;
		temMensagem = true;
		return msg;
	}

	// 🎨 Mostra mensagem com o design moderno
	public static void mostrarMensagem(TipoMensagem tipo) {
		if (temMensagem) {
			new MensagemUI(tipo.name(), desmsg, tipo).setVisible(true);
			temMensagem = false;
		}
	}

	public static void mostrarMensagem(String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem);
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

	public static Map<Integer, String> getMensagens() {
		return mensagens;
	}

	public static void setMensagens(Map<Integer, String> mensagens) {
		Mensagem.mensagens = mensagens;
	}

	public TipoMensagem getTipmsg() {
		return tipmsg;
	}

	public void setTipmsg(TipoMensagem tipmsg) {
		this.tipmsg = tipmsg;
	}

}
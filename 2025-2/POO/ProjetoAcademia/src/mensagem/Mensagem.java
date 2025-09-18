package mensagem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

public class Mensagem {
	private int codmsg;
	private String desmsg;
	private List<String> cadmsg = new ArrayList<>();
	private static Map<Integer, String> mensagens = new HashMap<>();

	public static void cadastrarMensagem(int codmsg, String desmsg) {
		mensagens.put(codmsg, desmsg);
	}

	public static String montaMensagem(int p_codmsg, String[] p_params) {
		String desmsg = mensagens.getOrDefault(p_codmsg, "Mensagem não cadastrada");
		String msg = desmsg + "\n" + p_codmsg;
		for (int i = 0; i < p_params.length; i++) {
			msg += " p_param" + (i + 1) + ": " + p_params[i];
		}
		return msg;
	}

	public static void mostrarMensagem(int p_codmsg, String[] params) {
		String mensagem = montaMensagem(p_codmsg, params);
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

	public List<String> getCadmsg() {
		return cadmsg;
	}

	public void setCadmsg(List<String> cadmsg) {
		this.cadmsg = cadmsg;
	}

}
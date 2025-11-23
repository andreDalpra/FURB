package main.furb.mensagem;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import static main.furb.app.Sistema.*;

import main.furb.app.Sistema;
import main.furb.enums.TipoMensagem;
import ui.MensagemUI;

/**
 * Gerencia mensagens do sistema, permitindo cadastro, formatação e exibição. A
 * classe trabalha com mensagens parametrizadas, tipos de aviso e apresentação
 * visual.
 */
public class Mensagem implements Sistema {

	/** Instância atual de Mensagem, usada como referência global. */
	private static Mensagem msg;

	/** Código da mensagem formatada. */
	private static int codmsg;

	/** Texto final da mensagem formatada. */
	private static String desmsg;

	/** Indica se há mensagem pendente de exibição. */
	private static boolean temMensagem;

	/** Tipo visual da mensagem (OK, ERRO, ALERTA, etc.). */
	public static TipoMensagem tipmsg;

	/** Armazena mensagens cadastradas: código → [descrição, tipo]. */
	private static Map<Integer, Object[]> mensagens = new HashMap<>();

	/**
	 * Reinicia o estado de mensagem. Define código, descrição e tipo como valores
	 * padrão.
	 */
	public static void inicializaMensagem() {
		codmsg = 0;
		desmsg = "";
		temMensagem = false;
		tipmsg = TipoMensagem.OK;
	}

	/**
	 * Monta uma mensagem a partir de um código cadastrado. Permite substituição de
	 * parâmetros no texto via {@link String#format}.
	 *
	 * @param p_codmsg     código da mensagem cadastrada
	 * @param p_parametros valores para preenchimento do texto
	 * @return mensagem formatada
	 */
	public static String montaMensagem(int p_codmsg, String... p_parametros) {
		Object[] carrega_msg = mensagens.get(p_codmsg);

		if (carrega_msg == null) {
			desmsg = "Mensagem não cadastrada (cód. " + p_codmsg + ")";
			tipmsg = TipoMensagem.WARNING;
		} else {
			desmsg = (String) carrega_msg[0];
			tipmsg = (TipoMensagem) carrega_msg[1];
		}

		try {
			desmsg = String.format(desmsg, (Object[]) p_parametros);
		} catch (Exception e) {
			desmsg = desmsg + "\n(cód. " + p_codmsg + ")";
		}

		temMensagem = true;
		return desmsg;
	}

	/**
	 * Exibe a mensagem atual utilizando a interface gráfica {@link MensagemUI}. A
	 * mensagem é convertida para HTML antes da exibição.
	 */
	public static void mostrarMensagem() {
		if (temMensagem) {
			String textoHTML = converteHTML(desmsg);
			new MensagemUI(tipmsg.name(), textoHTML, tipmsg).setVisible(true);
			temMensagem = false;
		}
	}

	/**
	 * Exibe uma mensagem simples através de um {@link JOptionPane}.
	 *
	 * @param p_mensagem texto a ser mostrado
	 */
	public static void mostrarMensagem(String p_mensagem) {
		JOptionPane.showMessageDialog(null, p_mensagem);
	}

	/**
	 * Cadastra uma nova mensagem no repositório interno.
	 *
	 * @param p_codmsg código da mensagem
	 * @param p_desmsg texto base (pode conter parâmetros)
	 * @param p_tipo   tipo visual da mensagem
	 */
	public static void cadastrarMensagem(int p_codmsg, String p_desmsg, TipoMensagem p_tipo) {
		mensagens.put(p_codmsg, new Object[] { p_desmsg, p_tipo });
	}

	@Override
	public boolean valida() {
		return false;
	}

	// ========================= GETTERS / SETTERS ========================= //

	public int getCodmsg() {
		return codmsg;
	}

	public void setCodmsg(int p_codmsg) {
		codmsg = p_codmsg;
	}

	public String getDesmsg() {
		return desmsg;
	}

	public void setDesmsg(String p_desmsg) {
		desmsg = p_desmsg;
	}

	public Mensagem getmsg() {
		return msg;
	}

	public void setOp_msg(Mensagem p_msg) {
		msg = p_msg;
	}

	public static Mensagem getMsg() {
		return msg;
	}

	public static void setMsg(Mensagem p_msg) {
		msg = p_msg;
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
		mensagens = p_mensagens;
	}

	public TipoMensagem getTipmsg() {
		return tipmsg;
	}

	public void setTipmsg(TipoMensagem p_tipmsg) {
		tipmsg = p_tipmsg;
	}

	@Override
	public String toCSV() {
		return null;
	}

	@Override
	public void fromCSV(String linha) {
	}
}

package mensagem;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

public class Mensagem {
	private Mensagem msg; 
	private int codmsg;
	private String desmsg;
	boolean temMensagem;
	private static Map<Integer, String> mensagens = new HashMap<>();

	public static void cadastrarMensagem(int codmsg, String desmsg) {
        mensagens.put(codmsg, desmsg);
    }

    public void inicializaMensagem(Mensagem msg) {
        // Usar em classes
    	msg = new Mensagem();
    	codmsg = 0;
    	desmsg = "";
    	temMensagem = false;   	
    }

	public static String montaMensagem(int codmsg, String[] params) {
        String desmsg = mensagens.getOrDefault(codmsg, "Mensagem não cadastrada");
        String msg;
        try {
            msg = String.format(desmsg, (Object[]) params);
        } catch (Exception e) {
            msg = desmsg + "\n" + codmsg;
            for (int i = 0; i < params.length; i++) {
                msg += " p_param" + (i + 1) + ": " + params[i];
            }
        }
        return msg;
    }

	public static void mostrarMensagem(int p_codmsg, String[] params) {
		String mensagem = montaMensagem(p_codmsg, params);
		JOptionPane.showMessageDialog(null, mensagem);
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

}
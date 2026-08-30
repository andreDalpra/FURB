/*
 * Classe da Barra de Ferramentas lateral 
 * 
 */
package compilador;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import icones.IconeFerramenta;

public class BarraFerramentas extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final Dimension TAMANHO_BOTAO = new Dimension(150, 80);

	private JButton botaoNovo;
	private JButton botaoAbrir;
	private JButton botaoSalvar;
	private JButton botaoCopiar;
	private JButton botaoColar;
	private JButton botaoRecortar;
	private JButton botaoCompilar;
	private JButton botaoEquipe;

	public BarraFerramentas() {
		setPreferredSize(new Dimension(150, 0));
		setMinimumSize(new Dimension(150, 0));
		setMaximumSize(new Dimension(150, Integer.MAX_VALUE));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		botaoNovo = criarBotao("novo [ctrl-n]", IconeFerramenta.Tipo.NOVO);
		botaoAbrir = criarBotao("abrir [ctrl-o]", IconeFerramenta.Tipo.ABRIR);
		botaoSalvar = criarBotao("salvar [ctrl-s]", IconeFerramenta.Tipo.SALVAR);
		botaoCopiar = criarBotao("copiar [ctrl-c]", IconeFerramenta.Tipo.COPIAR);
		botaoColar = criarBotao("colar [ctrl-v]", IconeFerramenta.Tipo.COLAR);
		botaoRecortar = criarBotao("recortar [ctrl-x]", IconeFerramenta.Tipo.RECORTAR);
		botaoCompilar = criarBotao("compilar [F7]", IconeFerramenta.Tipo.COMPILAR);
		botaoEquipe = criarBotao("equipe [F1]", IconeFerramenta.Tipo.EQUIPE);

		add(botaoNovo);
		add(botaoAbrir);
		add(botaoSalvar);
		add(botaoCopiar);
		add(botaoColar);
		add(botaoRecortar);
		add(botaoCompilar);
		add(botaoEquipe);
	}

	private JButton criarBotao(String texto, IconeFerramenta.Tipo tipoIcone) {
		JButton botao = new JButton(texto, new IconeFerramenta(tipoIcone));
		botao.setPreferredSize(TAMANHO_BOTAO);
		botao.setMinimumSize(TAMANHO_BOTAO);
		botao.setMaximumSize(TAMANHO_BOTAO);
		botao.setAlignmentX(CENTER_ALIGNMENT);
		botao.setHorizontalAlignment(SwingConstants.CENTER);
		botao.setHorizontalTextPosition(SwingConstants.CENTER);
		botao.setVerticalTextPosition(SwingConstants.BOTTOM);
		botao.setIconTextGap(5);
		botao.setFocusable(false);
		return botao;
	}

	public JButton getBotaoNovo() {
		return botaoNovo;
	}

	public JButton getBotaoAbrir() {
		return botaoAbrir;
	}

	public JButton getBotaoSalvar() {
		return botaoSalvar;
	}

	public JButton getBotaoCopiar() {
		return botaoCopiar;
	}

	public JButton getBotaoColar() {
		return botaoColar;
	}

	public JButton getBotaoRecortar() {
		return botaoRecortar;
	}

	public JButton getBotaoCompilar() {
		return botaoCompilar;
	}

	public JButton getBotaoEquipe() {
		return botaoEquipe;
	}
}

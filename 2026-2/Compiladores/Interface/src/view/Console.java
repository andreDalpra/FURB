package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Console extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextPane consoleTextPane;
	private Style estiloNormal;
	private Style estiloNegrito;

	public Console() {
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(0, 100));

		consoleTextPane = new JTextPane();
		consoleTextPane.setEditable(false);
		consoleTextPane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

		StyledDocument documento = consoleTextPane.getStyledDocument();
		estiloNormal = documento.addStyle("normal", null);
		StyleConstants.setFontFamily(estiloNormal, Font.MONOSPACED);
		StyleConstants.setFontSize(estiloNormal, 12);

		estiloNegrito = documento.addStyle("negrito", estiloNormal);
		StyleConstants.setBold(estiloNegrito, true);

		JScrollPane scrollPane = new JScrollPane(consoleTextPane);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane, BorderLayout.CENTER);
	}

	public void clear() {
		consoleTextPane.setText("");
	}

	public void writeLine(String message) {
		appendLine(message, estiloNormal);
	}

	public void writeHeader(String message) {
		appendLine(message, estiloNegrito);
	}

	private void appendLine(String message, Style estilo) {
		StyledDocument documento = consoleTextPane.getStyledDocument();
		try {
			documento.insertString(documento.getLength(),
					message + System.lineSeparator(), estilo);
		} catch (BadLocationException erro) {
			throw new IllegalStateException("Não foi possível escrever no console.", erro);
		}
	}
}

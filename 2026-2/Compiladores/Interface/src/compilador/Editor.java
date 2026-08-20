/*
 * Classe do Editor de Código Principal 
 * 
 */
package compilador;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Editor extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextArea editorTextArea;

	public Editor() {
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(0, 120));

		editorTextArea = new JTextArea();
		editorTextArea.setTabSize(4);
		editorTextArea.setBorder(new NumberedBorder());

		add(new JScrollPane(editorTextArea), BorderLayout.CENTER);
	}

	public String getText() {
		return editorTextArea.getText();
	}

	public void setText(String text) {
		editorTextArea.setText(text);
	}

	public JTextArea getEditorTextArea() {
		return editorTextArea;
	}
}

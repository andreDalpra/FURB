package compilador;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Console extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextArea consoleTextArea;

	public Console() {
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(0, 100));

		consoleTextArea = new JTextArea();
		consoleTextArea.setEditable(false);

		add(new JScrollPane(consoleTextArea), BorderLayout.CENTER);
	}

	public void clear() {
		consoleTextArea.setText("");
	}

	public void writeLine(String message) {
		consoleTextArea.append(message + System.lineSeparator());
	}
}

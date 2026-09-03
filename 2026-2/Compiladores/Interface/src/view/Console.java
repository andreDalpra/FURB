package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class Console extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextArea consoleTextArea;

	public Console() {
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(0, 100));

		consoleTextArea = new JTextArea();
		consoleTextArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(consoleTextArea);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane, BorderLayout.CENTER);
	}

	public void clear() {
		consoleTextArea.setText("");
	}

	public void writeLine(String message) {
		consoleTextArea.append(message + System.lineSeparator());
	}
}

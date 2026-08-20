/*
 * Classe Principal que junta a barra de Ferramentas, com o Editor e o Console 
 * 
 */
package compilador;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

public class Interface extends JFrame {

	private static final long serialVersionUID = 1L;

	private BarraFerramentas toolbarPanel;
	private Editor editorPanel;
	private Console consolePanel;
	private BarraStatus statusPanel;

	public Interface() {
		setTitle("Interface Compilador");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1500, 800);
		setResizable(false);
		setLocationRelativeTo(null);

		toolbarPanel = new BarraFerramentas();
		editorPanel = new Editor();
		consolePanel = new Console();
		statusPanel = new BarraStatus();

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, editorPanel, consolePanel);
		splitPane.setResizeWeight(0.75);
		splitPane.setOneTouchExpandable(false);
		splitPane.setContinuousLayout(true);

		setLayout(new BorderLayout());
		add(toolbarPanel, BorderLayout.WEST);
		add(splitPane, BorderLayout.CENTER);
		add(statusPanel, BorderLayout.SOUTH);
	}

	public BarraFerramentas getToolbarPanel() {
		return toolbarPanel;
	}

	public Editor getEditorPanel() {
		return editorPanel;
	}

	public Console getConsolePanel() {
		return consolePanel;
	}

	public BarraStatus getStatusPanel() {
		return statusPanel;
	}
}

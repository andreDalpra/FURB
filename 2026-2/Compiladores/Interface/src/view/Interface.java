/*
 * Classe Principal que junta a barra de Ferramentas, com o Editor e o Console 
 * 
 */
package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;

import funcionalidades.ArquivoController;
import funcionalidades.CompileController;
import funcionalidades.EditorController;
import funcionalidades.EquipeController;

public class Interface extends JFrame {

	private static final long serialVersionUID = 1L;

	private BarraFerramentas toolbarPanel;
	private Editor editorPanel;
	private Console consolePanel;
	private BarraStatus statusPanel;
	private ArquivoController arquivoController;
	private EditorController editorController;
	private CompileController compileController;
	private EquipeController equipeController;

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
		arquivoController = new ArquivoController(editorPanel, consolePanel, statusPanel);
		editorController = new EditorController(editorPanel);
		compileController = new CompileController(consolePanel, editorPanel);
		equipeController = new EquipeController(consolePanel);

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, editorPanel, consolePanel);
		splitPane.setResizeWeight(0.75);
		splitPane.setOneTouchExpandable(false);
		splitPane.setContinuousLayout(true);

		setLayout(new BorderLayout(5, 0));
		add(toolbarPanel, BorderLayout.WEST);
		add(splitPane, BorderLayout.CENTER);
		add(statusPanel, BorderLayout.SOUTH);

		configurarAcoes();
	}

	private void configurarAcoes() {
		toolbarPanel.getBotaoNovo().addActionListener(event -> arquivoController.novo());
		toolbarPanel.getBotaoAbrir().addActionListener(event -> arquivoController.abrir());
		toolbarPanel.getBotaoSalvar().addActionListener(event -> arquivoController.salvar());
		toolbarPanel.getBotaoCopiar().addActionListener(event -> editorController.copiar());
		toolbarPanel.getBotaoColar().addActionListener(event -> editorController.colar());
		toolbarPanel.getBotaoRecortar().addActionListener(event -> editorController.recortar());
		toolbarPanel.getBotaoCompilar().addActionListener(event -> compileController.compilar());
		toolbarPanel.getBotaoEquipe().addActionListener(event -> equipeController.mostrarEquipe());

		configurarAtalho("control N", "novo", () -> arquivoController.novo());
		configurarAtalho("control O", "abrir", () -> arquivoController.abrir());
		configurarAtalho("control S", "salvar", () -> arquivoController.salvar());
		configurarAtalho("control C", "copiar", () -> editorController.copiar());
		configurarAtalho("control V", "colar", () -> editorController.colar());
		configurarAtalho("control X", "recortar", () -> editorController.recortar());
		configurarAtalho("F7", "compilar", () -> compileController.compilar());
		configurarAtalho("F1", "equipe", () -> equipeController.mostrarEquipe());
	}

	private void configurarAtalho(String tecla, String nomeAcao, Runnable acao) {
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(tecla), nomeAcao);
		getRootPane().getActionMap().put(nomeAcao, new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent event) {
				acao.run();
			}
		});
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

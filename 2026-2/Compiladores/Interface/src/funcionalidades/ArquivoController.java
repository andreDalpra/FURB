package funcionalidades;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import compilador.BarraStatus;
import compilador.Console;
import compilador.Editor;

public class ArquivoController {

	private Editor editor;
	private Console console;
	private BarraStatus barraStatus;
	private File arquivoAtual;

	public ArquivoController(Editor editor, Console console, BarraStatus barraStatus) {
		this.editor = editor;
		this.console = console;
		this.barraStatus = barraStatus;
	}

	public void novo() {
		editor.clear();
		console.clear();
		barraStatus.clear();
		arquivoAtual = null;
	}

	public void abrir() {
		JFileChooser seletorArquivo = new JFileChooser();
		seletorArquivo.setFileFilter(new FileNameExtensionFilter("Arquivos de texto (*.txt)", "txt"));
		seletorArquivo.setAcceptAllFileFilterUsed(false);

		int resultado = seletorArquivo.showOpenDialog(null);

		if (resultado != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File arquivo = seletorArquivo.getSelectedFile();

		if (!arquivo.getName().toLowerCase().endsWith(".txt")) {
			JOptionPane.showMessageDialog(null, "Selecione um arquivo .txt.");
			return;
		}

		try {
			String conteudo = lerArquivoTexto(arquivo);
			editor.setText(conteudo);
			console.clear();
			arquivoAtual = arquivo;
			barraStatus.setArquivoAberto(arquivo);
		} catch (IOException erro) {
			console.clear();
			console.writeLine("Erro ao abrir o arquivo.");
		}
	}

	public void salvar() {
		if (arquivoAtual == null) {
			salvarArquivoNovo();
			return;
		}

		try {
			gravarArquivo(arquivoAtual);
			console.clear();
		} catch (IOException erro) {
			console.clear();
			console.writeLine("Erro ao salvar o arquivo.");
		}
	}

	private void salvarArquivoNovo() {
		JFileChooser seletorArquivo = new JFileChooser();
		seletorArquivo.setFileFilter(new FileNameExtensionFilter("Arquivos de texto (*.txt)", "txt"));
		seletorArquivo.setAcceptAllFileFilterUsed(false);

		int resultado = seletorArquivo.showSaveDialog(null);

		if (resultado != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File arquivo = garantirExtensaoTxt(seletorArquivo.getSelectedFile());

		try {
			gravarArquivo(arquivo);
			console.clear();
			arquivoAtual = arquivo;
			barraStatus.setArquivoAberto(arquivo);
		} catch (IOException erro) {
			console.clear();
			console.writeLine("Erro ao salvar o arquivo.");
		}
	}

	private void gravarArquivo(File arquivo) throws IOException {
		Files.writeString(arquivo.toPath(), editor.getText(), StandardCharsets.UTF_8);
	}

	private File garantirExtensaoTxt(File arquivo) {
		if (arquivo.getName().toLowerCase().endsWith(".txt")) {
			return arquivo;
		}

		return new File(arquivo.getParentFile(), arquivo.getName() + ".txt");
	}

	private String lerArquivoTexto(File arquivo) throws IOException {
		try {
			return Files.readString(arquivo.toPath(), StandardCharsets.UTF_8);
		} catch (MalformedInputException erro) {
			return Files.readString(arquivo.toPath(), Charset.forName("windows-1252"));
		}
	}
}

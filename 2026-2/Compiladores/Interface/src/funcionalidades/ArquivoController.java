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

import view.BarraStatus;
import view.Console;
import view.Editor;

public class ArquivoController {

	private Editor editor;
	private Console console;
	private BarraStatus barraStatus;
	private File arquivoAtual;
	private File arquivoCodigoObjeto;

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

	public boolean salvar() {
		if (arquivoAtual == null) {
			return salvarArquivoNovo();
		}

		try {
			gravarArquivo(arquivoAtual);
			console.clear();
			return true;
		} catch (IOException erro) {
			console.clear();
			console.writeLine("Erro ao salvar o arquivo.");
			return false;
		}
	}

	private boolean salvarArquivoNovo() {
		JFileChooser seletorArquivo = new JFileChooser();
		seletorArquivo.setFileFilter(new FileNameExtensionFilter("Arquivos de texto (*.txt)", "txt"));
		seletorArquivo.setAcceptAllFileFilterUsed(false);

		int resultado = seletorArquivo.showSaveDialog(null);

		if (resultado != JFileChooser.APPROVE_OPTION) {
			return false;
		}

		File arquivo = garantirExtensaoTxt(seletorArquivo.getSelectedFile());

		try {
			gravarArquivo(arquivo);
			console.clear();
			arquivoAtual = arquivo;
			barraStatus.setArquivoAberto(arquivo);
			return true;
		} catch (IOException erro) {
			console.clear();
			console.writeLine("Erro ao salvar o arquivo.");
			return false;
		}
	}

	private void gravarArquivo(File arquivo) throws IOException {
		Files.writeString(arquivo.toPath(), editor.getText(), StandardCharsets.UTF_8);
	}

	public File salvarCodigoObjeto(String codigoObjeto) throws IOException {
		arquivoCodigoObjeto = definirArquivoCodigoObjeto();
		Files.writeString(arquivoCodigoObjeto.toPath(), codigoObjeto, StandardCharsets.UTF_8);
		return arquivoCodigoObjeto;
	}

	public File getArquivoCodigoObjeto() {
		return arquivoCodigoObjeto;
	}

	private File definirArquivoCodigoObjeto() throws IOException {
		if (arquivoAtual == null) {
			throw new IOException("Salve o programa fonte antes de compilar.");
		}

		String nome = arquivoAtual.getName();
		int extensao = nome.lastIndexOf('.');
		if (extensao >= 0) {
			nome = nome.substring(0, extensao);
		}

		return new File(arquivoAtual.getParentFile(), nome + ".il");
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

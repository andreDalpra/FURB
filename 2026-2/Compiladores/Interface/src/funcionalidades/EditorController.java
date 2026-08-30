package funcionalidades;

import compilador.Editor;

public class EditorController {

	private Editor editor;

	public EditorController(Editor editor) {
		this.editor = editor;
	}

	public void copiar() {
		editor.getEditorTextArea().copy();
	}

	public void colar() {
		editor.getEditorTextArea().paste();
	}

	public void recortar() {
		editor.getEditorTextArea().cut();
	}
}

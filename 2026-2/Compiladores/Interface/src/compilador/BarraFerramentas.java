/*
 * Classe da Barra de Ferramentas lateral 
 * 
 */
package compilador;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class BarraFerramentas extends JPanel {

	private static final long serialVersionUID = 1L;

	public BarraFerramentas() {
		setPreferredSize(new Dimension(150, 0));
		setMinimumSize(new Dimension(150, 0));
		setMaximumSize(new Dimension(150, Integer.MAX_VALUE));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
}

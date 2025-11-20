package ui;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class CriarProduto extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public CriarProduto() {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Código");
		lblNewLabel.setBounds(63, 50, 46, 14);
		add(lblNewLabel);

	}

}

package compilador;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class BarraStatus extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel statusLabel;

	public BarraStatus() {
		setPreferredSize(new Dimension(0, 25));
		setMinimumSize(new Dimension(0, 25));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
		setLayout(new BorderLayout());

		statusLabel = new JLabel("Pronto");
		add(statusLabel, BorderLayout.WEST);
	}

	public void setStatus(String status) {
		statusLabel.setText(status);
	}
}

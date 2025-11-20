package ui;

import javax.swing.JPanel;

import main.furb.app.Tela;
import static main.furb.banco.Banco.*;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class BemVindo extends JPanel implements Tela {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public BemVindo() {
		setLayout(null);

		JLabel lblNewLabel = new JLabel("BEM VINDO");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 34));
		lblNewLabel.setBounds(109, 107, 239, 89);
		add(lblNewLabel);

		JLabel LBusuarioLogado = new JLabel("");
		LBusuarioLogado.setFont(new Font("Tahoma", Font.PLAIN, 26));
		LBusuarioLogado.setHorizontalAlignment(SwingConstants.CENTER);
		LBusuarioLogado.setBounds(118, 193, 192, 66);
		add(LBusuarioLogado);

		LBusuarioLogado.setText(usuarioLogado.getCodusu());

	}

	@Override
	public Object carrega_no_objeto() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void carrega_do_objeto(Object p_obj) {
		// TODO Auto-generated method stub
	}
}

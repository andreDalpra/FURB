package ui;

import java.awt.Button;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import main.furb.app.Tela;
import main.furb.controle.UsuarioDAO;
import main.furb.entidades.Usuario;
import main.furb.enums.TipoUsuario;
import static main.furb.mensagem.Mensagem.*;
import javax.swing.JPasswordField;
//import static main.furb.app.Sistema.*;

public class CadastroUsuario extends JFrame implements Tela {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField EDcodusu;
	private JTextField EDnomusu;
	private JTextField EDemlusu;
	private JComboBox<TipoUsuario> CBtipusu;

	private JPasswordField EDsenusu;

	@Override
	public Usuario carrega_no_objeto() {
		Usuario u = new Usuario();
		u.setCodusu(EDcodusu.getText());
		u.setSenusu(Integer.parseInt(new String(EDsenusu.getPassword())));
		u.setNomusu(EDnomusu.getText());
		u.setEmlusu(EDemlusu.getText());
		u.setTipusu((TipoUsuario) CBtipusu.getSelectedItem());
		return u;
	}

	@Override
	public void carrega_do_objeto(Object obj) {
		if (obj instanceof Usuario) {
			Usuario u = (Usuario) obj;
			EDcodusu.setText(u.getCodusu());
			EDsenusu.setText(String.valueOf(u.getSenusu()));
			EDnomusu.setText(u.getNomusu());
			EDemlusu.setText(u.getEmlusu());
			CBtipusu.setSelectedItem(u.getTipusu());
		}
	}
	
	private void limparCampos() {
	    EDcodusu.setText("");
	    EDsenusu.setText("");
	    EDnomusu.setText("");
	    EDemlusu.setText("");
	    CBtipusu.setSelectedIndex(-1); // Deseleciona o combo
	    EDcodusu.requestFocus(); // volta o foco pro primeiro campo
	}


	/**
	 * Create the frame.
	 */
	public CadastroUsuario() {
		setTitle("Cadastro de Usuário");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Código");
		lblNewLabel.setBounds(91, 52, 46, 14);
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		contentPane.add(lblNewLabel);

		EDcodusu = new JTextField();
		EDcodusu.setBounds(147, 49, 86, 20);
		contentPane.add(EDcodusu);
		EDcodusu.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Senha");
		lblNewLabel_1.setBounds(91, 77, 46, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setVerticalAlignment(SwingConstants.TOP);
		lblNome.setBounds(91, 104, 46, 14);
		contentPane.add(lblNome);

		EDnomusu = new JTextField();
		EDnomusu.setColumns(10);
		EDnomusu.setBounds(147, 101, 86, 20);
		contentPane.add(EDnomusu);

		JLabel lblNewLabel_1_1 = new JLabel("Email");
		lblNewLabel_1_1.setBounds(91, 129, 46, 14);
		contentPane.add(lblNewLabel_1_1);

		EDemlusu = new JTextField();
		EDemlusu.setColumns(10);
		EDemlusu.setBounds(147, 126, 86, 20);
		contentPane.add(EDemlusu);

		JLabel lblNewLabel_1_1_1 = new JLabel("Tipo");
		lblNewLabel_1_1_1.setBounds(91, 159, 46, 14);
		contentPane.add(lblNewLabel_1_1_1);

		CBtipusu = new JComboBox<>();
		for (TipoUsuario tipo : TipoUsuario.values()) {
			CBtipusu.addItem(tipo);
		}
		CBtipusu.setBounds(147, 156, 86, 20);
		contentPane.add(CBtipusu);

		Button BTcriausu = new Button("Criar");
		BTcriausu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TELA -> Usuario -> UsuarioDAO -> Banco -> Arquivo
				Usuario u = carrega_no_objeto();
				UsuarioDAO dao = new UsuarioDAO();
				dao.inserir(u);
				if (isTemMensagem()) {
					mostrarMensagem();
				}
				limparCampos();
			}
		});
		BTcriausu.setBounds(147, 182, 70, 22);
		contentPane.add(BTcriausu);
		
		EDsenusu = new JPasswordField();
		EDsenusu.setBounds(147, 74, 86, 20);
		contentPane.add(EDsenusu);
		
		Button BTvolta = new Button("Voltar");
		BTvolta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login telaLogin = new Login();
			    telaLogin.setVisible(true);
			    dispose();
			}
		});
		BTvolta.setBounds(147, 209, 70, 22);
		contentPane.add(BTvolta);
	}
}

package ui;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.furb.app.Tela;
import main.furb.banco.Banco;
import main.furb.controle.UsuarioDAO;
import main.furb.entidades.Usuario;
import static main.furb.banco.Banco.*;
import static main.furb.mensagem.Mensagem.*;

public class Login extends JFrame implements Tela {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField EDcodusu;
	private JPasswordField EDsenusu;

	@Override
	public Object carrega_no_objeto() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void carrega_do_objeto(Object p_obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void carrega_login(Usuario p_usuario) {
		p_usuario.setCodusu(EDcodusu.getText());
		p_usuario.setSenusu(Integer.parseInt(new String(EDsenusu.getPassword())));
	}

	public Login() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(100, 85, 53, 30);
		contentPane.add(lblNewLabel);

		EDcodusu = new JTextField();
		EDcodusu.setBounds(171, 86, 101, 28);
		contentPane.add(EDcodusu);
		EDcodusu.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Bem Vindo");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblNewLabel_1.setBounds(158, 11, 127, 58);
		contentPane.add(lblNewLabel_1);

		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSenha.setBounds(100, 125, 53, 30);
		contentPane.add(lblSenha);

		Button BTlogar = new Button("Login");
		BTlogar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuario usuarioDigitado = new Usuario();
				carrega_login(usuarioDigitado);

				UsuarioDAO dao = new UsuarioDAO();
				Usuario usuarioValidado = dao.validaLogin(usuarioDigitado);

				if (usuarioValidado != null) {

					usuarioLogado = usuarioValidado; // <-- AQUI É SETADO O USUARIOLOGADO DO BANCO, Q É USUADO NO SISTEMA INTEIRO

					Home telaHome = new Home();
					telaHome.setVisible(true);
					dispose();

				} else {
					montaMensagem(5, usuarioDigitado.getCodusu());
					mostrarMensagem();
				}
			}

		});
		BTlogar.setBackground(new Color(192, 192, 192));
		BTlogar.setBounds(182, 168, 79, 30);
		contentPane.add(BTlogar);

		Button BTcadastrar = new Button("Cadastrar");
		BTcadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CadastroUsuario telaCadastro = new CadastroUsuario();
				telaCadastro.setVisible(true);
				dispose();
			}
		});
		BTcadastrar.setBackground(Color.LIGHT_GRAY);
		BTcadastrar.setBounds(182, 206, 79, 30);
		contentPane.add(BTcadastrar);

		EDsenusu = new JPasswordField();
		EDsenusu.setBounds(170, 125, 102, 30);
		contentPane.add(EDsenusu);

	}
}

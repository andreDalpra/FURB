package ui;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.furb.app.Tela;
import main.furb.entidades.Usuario;
import javax.swing.JPasswordField;

public class Login extends JFrame implements Tela{

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
		Usuario u = new Usuario();
		u.setCodusu(EDcodusu.getText());
		u.setSenusu(Integer.parseInt(new String(EDsenusu.getPassword())));
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
		lblNewLabel.setBounds(129, 85, 53, 30);
		contentPane.add(lblNewLabel);
		
		EDcodusu = new JTextField();
		EDcodusu.setBounds(192, 86, 101, 28);
		contentPane.add(EDcodusu);
		EDcodusu.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Bem Vindo");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblNewLabel_1.setBounds(156, 11, 127, 58);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSenha.setBounds(129, 125, 53, 30);
		contentPane.add(lblSenha);
		
		Button BTlogar = new Button("Login");
		BTlogar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		BTlogar.setBackground(new Color(192, 192, 192));
		BTlogar.setBounds(192, 168, 79, 30);
		contentPane.add(BTlogar);
		
		Button BTcadastrar = new Button("Cadastrar");
		BTcadastrar.setBackground(Color.LIGHT_GRAY);
		BTcadastrar.setBounds(192, 204, 79, 30);
		contentPane.add(BTcadastrar);
		
		EDsenusu = new JPasswordField();
		EDsenusu.setBounds(192, 125, 102, 30);
		contentPane.add(EDsenusu);

	}
}

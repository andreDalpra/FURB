package ui;

import static main.furb.banco.Banco.usuarioLogado;

import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import main.furb.app.Tela;
import main.furb.enums.*;
import static main.furb.mensagem.Mensagem.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Home extends JFrame implements Tela {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private JPanel PNcentral;
	private CardLayout cardLayout;
	private CriarProduto telaProduto;

	private void carregar_telas() {
		// INSTANCIANDO AS TELAS NO CARD PRINCIPAL
		JPanel telaInicial = new BemVindo();

	    telaProduto = new CriarProduto();

		JPanel telaEntrada = new Entrada();
		
		JPanel telaSaida = new Saida();
		
		JPanel telaMovimentos = new ListaMovimentos();
		
		JPanel telaUsuarios = new Usuarios();
		
		JPanel telaSaldo = new ConsultaSaldos();

		PNcentral.add(telaProduto, "Produto");
		PNcentral.add(telaEntrada, "Entrada");
		PNcentral.add(telaSaida, "Saida");
		PNcentral.add(telaInicial, "Inicio");
		PNcentral.add(telaUsuarios, "Usuarios");
		PNcentral.add(telaMovimentos,"Movimentos");
		PNcentral.add(telaSaldo, "Saldos");

	}

	public Home() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 450);

		// PRIMEIRO: criar contentPane
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// PAINEL LATERAL
		JPanel PNlateral = new JPanel();
		PNlateral.setLayout(null);
		PNlateral.setBounds(5, 5, 131, 395);
		contentPane.add(PNlateral);

		// BOTÕES DO MENU
		JButton BTproduto = new JButton("Produto");
		BTproduto.setHorizontalAlignment(SwingConstants.LEFT);
		BTproduto.setBounds(10, 11, 111, 44);
		PNlateral.add(BTproduto);

		JButton BTentrada = new JButton("Entrada");
		BTentrada.setHorizontalAlignment(SwingConstants.LEFT);
		BTentrada.setBounds(10, 66, 111, 44);
		PNlateral.add(BTentrada);

		JButton BTsaida = new JButton("Saída");
		BTsaida.setHorizontalAlignment(SwingConstants.LEFT);
		BTsaida.setBounds(10, 121, 111, 44);
		PNlateral.add(BTsaida);

		JButton BTmovimentos = new JButton("<html>Lista de<br> Movimentos</html>");
		BTmovimentos.setHorizontalAlignment(SwingConstants.LEFT);
		BTmovimentos.setBounds(10, 176, 111, 44);
		PNlateral.add(BTmovimentos);

		JButton BTusuarios = new JButton("<html>Gerenciador<br> de Usuários</html>");
		BTusuarios.setHorizontalAlignment(SwingConstants.LEFT);
		BTusuarios.setBounds(10, 282, 111, 44);
		PNlateral.add(BTusuarios);

		JButton BTlogout = new JButton("Sair");
		BTlogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usuarioLogado = null; // <-- AQUI EU REMOVO A SESSION DO USUARIO.
				Login telaLogin = new Login();
			    telaLogin.setVisible(true);
			    dispose();
			}
		});
		BTlogout.setHorizontalAlignment(SwingConstants.LEFT);
		BTlogout.setBounds(10, 340, 111, 44);
		PNlateral.add(BTlogout);

		JButton BTconsultasaldo = new JButton("<html>Consulta de<br> Saldo</html>");
		BTconsultasaldo.setHorizontalAlignment(SwingConstants.LEFT);
		BTconsultasaldo.setBounds(10, 227, 111, 44);
		PNlateral.add(BTconsultasaldo);

		// PAINEL CENTRAL (AGORA CORRETO)
		PNcentral = new JPanel();
		PNcentral.setBounds(131, 0, 453, 411);
		contentPane.add(PNcentral);

		cardLayout = new CardLayout();
		PNcentral.setLayout(cardLayout);

		// CARREGAR TODAS AS TELAS
		carregar_telas();

		// AÇÕES DOS BOTÕES
		BTproduto.addActionListener(e -> {
		    cardLayout.show(PNcentral, "Produto");
		    telaProduto.recarregarTabela(); // <-- agora funciona!
		});
		BTentrada.addActionListener(e -> cardLayout.show(PNcentral, "Entrada"));
		BTusuarios.addActionListener(e -> { 
		    if (usuarioLogado.getTipusu() != TipoUsuario.ADM) {
		        montaMensagem(8);
		        mostrarMensagem();
		        return;
		    }

		    // Permite abrir a tela
		    cardLayout.show(PNcentral, "Usuarios");
		});
		
		BTsaida.addActionListener(e -> cardLayout.show(PNcentral, "Saida"));
		BTmovimentos.addActionListener(e -> cardLayout.show(PNcentral, "Movimentos"));
		BTconsultasaldo.addActionListener(e -> cardLayout.show(PNcentral, "Saldos"));

		// MOSTRA A TELA AO LOGAR
		cardLayout.show(PNcentral, "Inicio");
	}

	@Override
	public Object carrega_no_objeto() {
		return null;
	}

	@Override
	public void carrega_do_objeto(Object p_obj) {
	}
}

package ui;

import static main.furb.banco.Banco.listar;
import static main.furb.banco.Banco.usuarioLogado;
import static main.furb.mensagem.Mensagem.isTemMensagem;
import static main.furb.mensagem.Mensagem.mostrarMensagem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import main.furb.app.Tela;
import main.furb.banco.Banco;
import main.furb.controle.MovimentoDAO;
import main.furb.controle.ProdutoDAO;
import main.furb.entidades.MovimentoEstoque;
import main.furb.entidades.Produto;
import main.furb.enums.TipoMovimento;

public class Entrada extends JPanel implements Tela {

	private static final long serialVersionUID = 1L;

	private JTextField EDcodusu;
	private JTextField CBtipmov;
	private JTextField LOVcodpro;
	private JTextField EDqtdpro;
	private JTextField EDvlrunt;
	private JTextField EDvlrtot;
	private JTextField EDdtamov;

	private Produto produtoSelecionado;

	public Entrada() {

		setLayout(new BorderLayout(10, 10));

		JPanel form = new JPanel(new GridLayout(0, 2, 10, 10));
		form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// Usuário
		form.add(new JLabel("Usuário:"));
		EDcodusu = new JTextField();
		EDcodusu.setEditable(false);
		EDcodusu.setBackground(Color.WHITE);
		EDcodusu.setForeground(Color.BLACK);
		EDcodusu.setDisabledTextColor(Color.BLACK);
		EDcodusu.setText(usuarioLogado.getCodusu());
		form.add(EDcodusu);

		// Data
		form.add(new JLabel("Data:"));
		EDdtamov = new JTextField();
		EDdtamov.setEditable(false);
		EDdtamov.setBackground(Color.WHITE);
		EDdtamov.setText(LocalDate.now().toString());
		form.add(EDdtamov);

		// Tipo movimento
		form.add(new JLabel("Tipo Movimento:"));
		CBtipmov = new JTextField();
		CBtipmov.setEditable(false);
		CBtipmov.setBackground(Color.WHITE);
		CBtipmov.setText(TipoMovimento.ENTRADA.toString());
		form.add(CBtipmov);

		// Produto (campo + botão)
		form.add(new JLabel("Produto:"));

		JPanel pnlProduto = new JPanel(new BorderLayout());
		LOVcodpro = new JTextField();
		LOVcodpro.setEditable(false);
		LOVcodpro.setBackground(Color.WHITE);
		pnlProduto.add(LOVcodpro, BorderLayout.CENTER);

		JButton btnLov = new JButton("...");
		btnLov.addActionListener(e -> abrirLovProdutos());
		pnlProduto.add(btnLov, BorderLayout.EAST);

		form.add(pnlProduto);

		// Quantidade
		form.add(new JLabel("Quantidade:"));
		EDqtdpro = new JTextField();
		form.add(EDqtdpro);

		// Valor Unitário
		form.add(new JLabel("Valor Unitário:"));
		EDvlrunt = new JTextField();
		EDvlrunt.setEditable(false);
		EDvlrunt.setBackground(Color.WHITE);
		EDvlrunt.setForeground(Color.BLACK);
		form.add(EDvlrunt);

		// Valor Total
		form.add(new JLabel("Valor Total:"));
		EDvlrtot = new JTextField();
		EDvlrtot.setEditable(false);
		EDvlrtot.setBackground(Color.WHITE);
		form.add(EDvlrtot);

		// Painel de botões
		JPanel botoes = new JPanel(new GridLayout(1, 2, 10, 10));

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(e -> salvarMovimento());
		botoes.add(btnSalvar);

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(e -> limpaTela());
		botoes.add(btnLimpar);

		JPanel centro = new JPanel(new BorderLayout());
		centro.add(form, BorderLayout.NORTH);
		centro.add(botoes, BorderLayout.CENTER);

		add(centro, BorderLayout.NORTH);



		// Calcula total ao digitar
		KeyAdapter calc = new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				calcularTotal();
			}
		};

		EDqtdpro.addKeyListener(calc);
		EDvlrunt.addKeyListener(calc);
	}
	
	private void salvarMovimento() {
	    try {
	        MovimentoDAO dao = new MovimentoDAO();

	        // --- NOVO MOVIMENTO SEMPRE ---
	        MovimentoEstoque mov_novo = carrega_no_objeto();

	        if (dao.inserir(mov_novo)) {
	            if (isTemMensagem()) {
	                mostrarMensagem();
	            }
	        }

	        limpaTela();
	        mostrarMensagem();

	    } catch (Exception ex) {
	        ex.printStackTrace();
	        System.out.println("Erro ao salvar movimento: " + ex.getMessage());
	    }
	}



	private void calcularTotal() {

		try {
			if (produtoSelecionado == null) {
				EDvlrtot.setText("");
				return;
			}

			int qtd = Integer.parseInt(EDqtdpro.getText());
			double vlr = produtoSelecionado.getPrrunt(); // sempre do produto

			EDvlrtot.setText(String.format("%.2f", qtd * vlr));

		} catch (Exception ex) {
			EDvlrtot.setText("");
		}
	}

	// ==========================================================
	// LOV FUNCIONANDO
	// ==========================================================
	private void abrirLovProdutos() {

		JDialog dialog = new JDialog((Frame) null, "Selecionar Produto", true);
		dialog.setLayout(new BorderLayout());
		dialog.setSize(600, 350);
		dialog.setLocationRelativeTo(this);

		String[] colunas = { "Seq", "Código", "Descrição" };

		List<Produto> lista = listar(Produto.class);
		DefaultTableModel model = new DefaultTableModel(colunas, 0);

		for (Produto p : lista) {
			model.addRow(new Object[] { p.getSeqpro(), p.getCodpro(), p.getDespro() });
		}

		JTable tabela = new JTable(model) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tabela.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2) {

					int selected = tabela.getSelectedRow();
					if (selected >= 0) {

						Object valor = tabela.getValueAt(selected, 0);

						int seq = (valor instanceof Number) ? ((Number) valor).intValue()
								: Integer.parseInt(valor.toString());

						produtoSelecionado = ProdutoDAO.obtemPelaSequence(seq);

						if (produtoSelecionado != null) {

							// Preenche LOV
							LOVcodpro.setText(produtoSelecionado.getCodpro() + " - " + produtoSelecionado.getDespro());

							// 👉 PREENCHE O VALOR UNITÁRIO AUTOMATICAMENTE
							EDvlrunt.setText(String.valueOf(produtoSelecionado.getPrrunt()));

							// 👉 Recalcula total automaticamente
							calcularTotal();
						}

						dialog.dispose();
					}
				}
			}
		});

		dialog.add(new JScrollPane(tabela), BorderLayout.CENTER);
		dialog.setVisible(true);
	}

	private void limpaTela() {

		produtoSelecionado = null;

		LOVcodpro.setText("");
		EDqtdpro.setText("");
		EDvlrunt.setText("");
		EDvlrtot.setText("");

		EDdtamov.setText(LocalDate.now().toString());
	}

	// ==========================================================
	// CARREGAR OBJETO
	// ==========================================================
	@Override
	public MovimentoEstoque carrega_no_objeto() {

		MovimentoEstoque mov = new MovimentoEstoque();

		mov.setDatmov(LocalDate.parse(EDdtamov.getText()));
		mov.setSequsu(usuarioLogado);
		mov.setSeqpro(produtoSelecionado);
		mov.setTipmov(TipoMovimento.ENTRADA);
		mov.setQtdmov(Integer.parseInt(EDqtdpro.getText()));
		mov.setVlrunt(Double.parseDouble(EDvlrunt.getText()));
		mov.setVlrtot(Double.parseDouble(EDvlrtot.getText().replace(",", "."))); //PARA NAO BUGAR A VIRGULA NE;


		return mov;
	}

	@Override
	public void carrega_do_objeto(Object o) {
	}
}

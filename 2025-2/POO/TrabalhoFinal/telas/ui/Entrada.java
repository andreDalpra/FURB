package ui;

import static main.furb.banco.Banco.listar;
import static main.furb.banco.Banco.usuarioLogado;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagLayout;
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

		// Valor unitário
		form.add(new JLabel("Valor Unitário:"));
		EDvlrunt = new JTextField();
		form.add(EDvlrunt);

		// Valor total
		form.add(new JLabel("Valor Total:"));
		EDvlrtot = new JTextField();
		EDvlrtot.setEditable(false);
		EDvlrtot.setBackground(Color.WHITE);
		form.add(EDvlrtot);

		// Coloca painel de formulário
		add(form, BorderLayout.NORTH);

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

	private void calcularTotal() {
		try {
			int qtd = Integer.parseInt(EDqtdpro.getText());
			double vlr = Double.parseDouble(EDvlrunt.getText());
			EDvlrtot.setText(String.format("%.2f", qtd * vlr));
		} catch (Exception ex) {
			EDvlrtot.setText("");
		}
	}

	// ==========================================================
	// LOV DE PRODUTOS (FUNCIONAL + CORRIGIDO)
	// ==========================================================
	private void abrirLovProdutos() {

	    JDialog dialog = new JDialog((Frame) null, "Selecionar Produto", true);
	    dialog.setLayout(new BorderLayout());
	    dialog.setSize(600, 350);
	    dialog.setLocationRelativeTo(this);

	    String[] colunas = { "Seq", "Código", "Descrição" };

	    List<Produto> lista = listar(Produto.class);
	    DefaultTableModel model = new DefaultTableModel(colunas, 0);

	    System.out.println("==== PRODUTOS CARREGADOS PARA O LOV ====");

	    for (Produto p : lista) {
	        System.out.println("SEQ=" + p.getSeqpro() + " | COD=" + p.getCodpro() + " | DES=" + p.getDespro());

	        model.addRow(new Object[] {
	            p.getSeqpro(),
	            p.getCodpro(),
	            p.getDespro()
	        });
	    }

	    JTable tabela = new JTable(model) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false; // impede edição
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

	                    int seq = (valor instanceof Number)
	                            ? ((Number) valor).intValue()
	                            : Integer.parseInt(valor.toString());

	                    System.out.println("----");
	                    System.out.println("Linha selecionada no LOV: " + selected);
	                    System.out.println("Valor bruto da coluna 0: " + valor);
	                    System.out.println("SEQ convertido: " + seq);

	                    produtoSelecionado = ProdutoDAO.obtemPelaSequence(seq);

	                    System.out.println("Produto retornado pelo DAO: " + produtoSelecionado);

	                    if (produtoSelecionado != null) {
	                        System.out.println("-> Produto encontrado! Preenchendo LOV...");
	                        LOVcodpro.setText(
	                            produtoSelecionado.getCodpro() + " - " +
	                            produtoSelecionado.getDespro()
	                        );
	                    } else {
	                        System.out.println("-> ERRO: Produto retornou NULL!");
	                    }

	                    dialog.dispose();
	                }
	            }
	        }
	    });

	    dialog.add(new JScrollPane(tabela), BorderLayout.CENTER);
	    dialog.setVisible(true);
	}


	@Override
	public Object carrega_no_objeto() {

		MovimentoEstoque mov = new MovimentoEstoque();

		mov.setDatmov(LocalDate.parse(EDdtamov.getText()));
		mov.setSequsu(usuarioLogado);
		mov.setSeqpro(produtoSelecionado);
		mov.setTipmov(TipoMovimento.ENTRADA);
		mov.setQtdmov(Integer.parseInt(EDqtdpro.getText()));
		mov.setVlrunt(Double.parseDouble(EDvlrunt.getText()));
		mov.setVlrtot(Double.parseDouble(EDvlrtot.getText()));

		return mov;
	}

	@Override
	public void carrega_do_objeto(Object o) {
		// não utilizado
	}
}

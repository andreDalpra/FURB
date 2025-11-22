package ui;

import static main.furb.banco.Banco.usuarioLogado;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import main.furb.app.Tela;
import main.furb.banco.Banco;
import main.furb.controle.ProdutoDAO;
import main.furb.entidades.Produto;
import main.furb.enums.TipoProduto;
import static main.furb.mensagem.Mensagem.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CriarProduto extends JPanel implements Tela {

	private static final long serialVersionUID = 1L;

	private JTable tabela;
	private DefaultTableModel modelo;

	// CAMPOS DO FORMULÁRIO
	private JTextField EDcodpro;
	private JTextField EDdespro;
	private JComboBox<TipoProduto> CBtippro;
	private JTextField EDprrunt;
	private JTextField EDqtdpro;
	private JTextField EDcodusu;

	public CriarProduto() {
		setLayout(new BorderLayout(10, 10));

		// -------------------------
		// PAINEL DO FORMULÁRIO
		// -------------------------
		JPanel form = new JPanel();
		form.setLayout(new GridLayout(0, 2, 10, 10));

		form.add(new JLabel("Código"));
		EDcodpro = new JTextField();
		form.add(EDcodpro);

		form.add(new JLabel("Descrição"));
		EDdespro = new JTextField();
		form.add(EDdespro);

		form.add(new JLabel("Categoria"));
		CBtippro = new JComboBox<>(TipoProduto.values());
		form.add(CBtippro);

		form.add(new JLabel("Preço"));
		EDprrunt = new JTextField();
		form.add(EDprrunt);

		form.add(new JLabel("Quantidade"));
		EDqtdpro = new JTextField();
		form.add(EDqtdpro);

		form.add(new JLabel("Usuário"));
		EDcodusu = new JTextField();
		EDcodusu.setEnabled(false);
		EDcodusu.setText(usuarioLogado.getCodusu()); // MOSTRA NA TELA
		form.add(EDcodusu);

		JButton BTsalvar = new JButton("Salvar");
		BTsalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TELA --> Usuario --> UsuarioDAO --> Banco  --> CSV
				Produto p = carrega_no_objeto();
				ProdutoDAO dao = new ProdutoDAO();

				if (dao.inserir(p)) {
					atualizarTabela();
					limpaTela();
				}

				if (isTemMensagem()) {
					mostrarMensagem();
				}
			}
		});

		JButton BTcadastrar = new JButton("Novo");
		BTcadastrar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        limpaTela();
		    }
		});

		form.add(BTsalvar);
		form.add(BTcadastrar);

		add(form, BorderLayout.NORTH);

		// -------------------------
		// TABELA DE PRODUTOS
		// -------------------------
		String[] colunas = {"SEQ", "SKU", "Nome", "Categoria", "Valor Unit.", "Estoque" };

		modelo = new DefaultTableModel(colunas, 0) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false; // Tabela READ ONLY
		    }
		};

		tabela = new JTable(modelo);
		

		// OCULTA A SEQUENCE : 
		
		tabela.getColumnModel().getColumn(0).setMinWidth(0);
		tabela.getColumnModel().getColumn(0).setMaxWidth(0);
		tabela.getColumnModel().getColumn(0).setPreferredWidth(0);
	
		JScrollPane GDproduto = new JScrollPane(tabela);
		GDproduto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int linha = tabela.getSelectedRow();
		        if (linha >= 0) {

		            // PEGAR SEQ DA LINHA
		            int seq = Integer.parseInt(modelo.getValueAt(linha, 0).toString());

		            // BUSCAR PRODUTO
		            Produto p = ProdutoDAO.obtemPelaSequence(seq);

		            // CARREGAR NOS CAMPOS
		            carrega_do_objeto(p);
			}
			
			}
		});
		add(GDproduto, BorderLayout.CENTER);

		atualizarTabela();
	}


	private void atualizarTabela() {
		modelo.setRowCount(0);

		List<Produto> lista = Banco.listar(Produto.class);

		for (Produto p : lista) {
			modelo.addRow(
					new Object[] {p.getSeqpro(), p.getCodpro(), p.getDespro(), p.getTipro(), p.getPrrunt(), p.getQtdproduto() });
		}
	}

	private void limpaTela() {
		EDcodpro.setText("");
		EDdespro.setText("");
		EDprrunt.setText("");
		EDqtdpro.setText("");
		CBtippro.setSelectedIndex(0);
		EDcodusu.setText(usuarioLogado.getCodusu()); // <--- Farinha, este é o usuário q esta logado no sistema
	}

	@Override
	public Produto carrega_no_objeto() {
		Produto p = new Produto();

		p.setCodpro(EDcodpro.getText());
		p.setDespro(EDdespro.getText());
		p.setTipro((TipoProduto) (CBtippro.getSelectedItem()));
		p.setPrrunt(Double.parseDouble(EDprrunt.getText()));
		p.setQtdproduto(Integer.parseInt(EDqtdpro.getText()));
		p.setSequsu(usuarioLogado);

		return p;
	}

	@Override
	public void carrega_do_objeto(Object p_obj) {
		// TODO Auto-generated method stub

	}
}

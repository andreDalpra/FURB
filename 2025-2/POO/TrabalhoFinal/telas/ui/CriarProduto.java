package ui;

import static main.furb.banco.Banco.usuarioLogado;
import static main.furb.mensagem.Mensagem.isTemMensagem;
import static main.furb.mensagem.Mensagem.mostrarMensagem;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import main.furb.app.Tela;
import main.furb.banco.Banco;
import main.furb.controle.ProdutoDAO;
import main.furb.controle.UsuarioDAO;
import main.furb.entidades.Produto;
import main.furb.entidades.Usuario;
import main.furb.enums.TipoProduto;
import static main.furb.mensagem.Mensagem.*;

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
	private Produto produtoEmEdicao = null;

	public CriarProduto() {

		// ATUALIZANDO A TABELA ANTES DE TUDO

		setLayout(new BorderLayout(10, 10));

		// -------------------------
		// PAINEL DO FORMULÁRIO
		// -------------------------
		JPanel form = new JPanel();
		form.setLayout(new GridLayout(0, 2, 10, 10));
		form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel LBcodigo = new JLabel("Código");
		LBcodigo.setHorizontalAlignment(SwingConstants.LEFT);
		form.add(LBcodigo);
		EDcodpro = new JTextField();
		form.add(EDcodpro);

		JLabel LBdescricao = new JLabel("Descrição");
		LBdescricao.setHorizontalAlignment(SwingConstants.LEFT);
		form.add(LBdescricao);
		EDdespro = new JTextField();
		form.add(EDdespro);

		JLabel LBcategoria = new JLabel("Categoria");
		LBcategoria.setHorizontalAlignment(SwingConstants.LEFT);
		form.add(LBcategoria);
		CBtippro = new JComboBox<>(TipoProduto.values());
		form.add(CBtippro);

		JLabel LBpreco = new JLabel("Preço");
		LBpreco.setHorizontalAlignment(SwingConstants.LEFT);
		form.add(LBpreco);
		EDprrunt = new JTextField();
		form.add(EDprrunt);

		JLabel LBquantidade = new JLabel("Quantidade");
		LBquantidade.setHorizontalAlignment(SwingConstants.LEFT);
		form.add(LBquantidade);
		EDqtdpro = new JTextField();
		form.add(EDqtdpro);

		JLabel LBusuario = new JLabel("Usuário");
		LBusuario.setHorizontalAlignment(SwingConstants.LEFT);
		form.add(LBusuario);
		EDcodusu = new JTextField();
		EDcodusu.setEnabled(false);
		EDcodusu.setText(usuarioLogado.getCodusu());
		form.add(EDcodusu);

		JButton BTsalvar = new JButton("Salvar");
		BTsalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProdutoDAO dao = new ProdutoDAO();

				if (produtoEmEdicao == null) {
					// --- NOVO PRODUTO ---
					Produto p_novo = carrega_no_objeto();
					if (dao.inserir(p_novo)) {
						if (isTemMensagem()) {
							mostrarMensagem();
						}
					}
				} else {
					// --- EDIÇÃO ---
					Produto p_edicao = carrega_no_objeto();

					// usa a mesma sequence do produto original
					p_edicao.setSeqpro(produtoEmEdicao.getSeqpro());

					if (dao.alterar(p_edicao)) {
						if (isTemMensagem()) {
							mostrarMensagem();
						}
					}

					produtoEmEdicao = null; // terminou edição
				}

				List<Produto> produtos = Banco.listar(Produto.class);
				atualizarTabela(produtos);
				limpaTela();
				mostrarMensagem();
			}
		});

		JButton BTexcluir = new JButton("Excluir");
		BTexcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int l_linha = obterLinhaSelecionada();

				if (l_linha == -1) {
					JOptionPane.showMessageDialog(null, "Selecione um usuário!");
					return;
				}

				// CONFIRMAR ISSO?
				int opcao = JOptionPane.showConfirmDialog(null, "Deseja realmente remover este usuário?",
						"Confirmar remoção", JOptionPane.YES_NO_OPTION);

				if (opcao != JOptionPane.YES_OPTION) {
					return;
				}
				int l_seq = (int) getValorDaLinha(l_linha, 0);

				Produto p = ProdutoDAO.obtemPelaSequence(l_seq);
				ProdutoDAO dao = new ProdutoDAO();
				if (dao.excluir(p)) {

					List<Produto> produtos = Banco.listar(Produto.class);
					atualizarTabela(produtos);
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
		form.add(BTexcluir);

		add(form, BorderLayout.NORTH);

		// -------------------------
		// TABELA DE PRODUTOS
		// -------------------------
		String[] colunas = { "SEQ", "SKU", "Nome", "Categoria", "Valor Unit.", "Estoque" };

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
		add(GDproduto, BorderLayout.CENTER);

		tabela.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int linha = tabela.getSelectedRow();
				if (linha >= 0) {

					// PEGAR SEQ DA LINHA (COLUNA 0)
					int l_seq = Integer.parseInt(modelo.getValueAt(linha, 0).toString());

					// BUSCAR PRODUTO
					Produto p = ProdutoDAO.obtemPelaSequence(l_seq);

					// CARREGAR NOS CAMPOS
					carrega_do_objeto(p);
				}
			}
		});

		List<Produto> l_produtos = Banco.listar(Produto.class);
		atualizarTabela(l_produtos);
	}

	public Object getValorDaLinha(int linha, int coluna) {
		return modelo.getValueAt(linha, coluna);
	}

	public int obterLinhaSelecionada() {
		return tabela.getSelectedRow();
	}

	private void atualizarTabela(List<Produto> p_produtos) {
		modelo.setRowCount(0);

		for (Produto p : p_produtos) {
			modelo.addRow(new Object[] { p.getSeqpro(), p.getCodpro(), p.getDespro(), p.getTipro(), p.getPrrunt(),
					p.getQtdproduto() });
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

	public void recarregarTabela() {
		atualizarTabela(Banco.listar(Produto.class));
	}

	@Override
	public void carrega_do_objeto(Object p_obj) {

		if (!(p_obj instanceof Produto)) {
			return;
		}

		Produto p = (Produto) p_obj;
		produtoEmEdicao = p; // <-- Produto q será editado.

		EDcodpro.setText(p.getCodpro());
		EDdespro.setText(p.getDespro());
		CBtippro.setSelectedItem(p.getTipro());
		EDprrunt.setText(String.valueOf(p.getPrrunt()));
		EDqtdpro.setText(String.valueOf(p.getQtdproduto()));

		// Usuário do cadastro
		if (p.getSequsu() != null)
			EDcodusu.setText(p.getSequsu().getCodusu());
	}

}

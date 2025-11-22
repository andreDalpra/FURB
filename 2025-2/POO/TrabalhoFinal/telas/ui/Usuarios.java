package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import main.furb.banco.Banco;
import main.furb.controle.UsuarioDAO;
import main.furb.entidades.Usuario;
import static main.furb.controle.UsuarioDAO.*;
import static main.furb.mensagem.Mensagem.*;

public class Usuarios extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTable tabela;
	private DefaultTableModel modeloTabela;
	private JButton botaoRemover;

	public Usuarios() {
		setLayout(new BorderLayout(10, 10));
		setBorder(BorderFactory.createTitledBorder("Usuários Cadastrados"));

		// Cria o modelo da tabela
		String[] colunas = { "Seq", "Código", "Nome", "E-mail", "Tipo" };
		modeloTabela = new DefaultTableModel(colunas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// Cria a tabela
		tabela = new JTable(modeloTabela);
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// OCULTA A SEQUENCE:

		tabela.getColumnModel().getColumn(0).setMinWidth(0);
		tabela.getColumnModel().getColumn(0).setMaxWidth(0);
		tabela.getColumnModel().getColumn(0).setWidth(0);

		// Adiciona a tabela em um scroll pane
		JScrollPane painelRolagem = new JScrollPane(tabela);

		// Cria o botão de remover
		botaoRemover = new JButton("Remover");
		botaoRemover.addActionListener(new ActionListener() {
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

				Usuario u = UsuarioDAO.obtemPelaSequence(l_seq);
				UsuarioDAO dao = new UsuarioDAO();
				if (dao.excluir(u)) {
					List<Usuario> usuarios = Banco.listar(Usuario.class);
					atualizarTabela(usuarios);
				}

				if (isTemMensagem()) {
					mostrarMensagem();
				}

			}
		});
		JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		painelBotoes.add(botaoRemover);

		// Adiciona os componentes
		add(painelRolagem, BorderLayout.CENTER);
		add(painelBotoes, BorderLayout.SOUTH);

		List<Usuario> usuarios = Banco.listar(Usuario.class);
		atualizarTabela(usuarios);
	}

	public void atualizarTabela(List<Usuario> p_usuarios) {
		// Limpa a tabela
		modeloTabela.setRowCount(0);

		// Adiciona os estudantes
		for (Usuario u : p_usuarios) {
			Object[] l_linha = { u.getSequsu(), u.getCodusu(), u.getNomusu(), u.getEmlusu(), u.getTipusu() };
			modeloTabela.addRow(l_linha);
		}
	}

	public Object getValorDaLinha(int linha, int coluna) {
		return modeloTabela.getValueAt(linha, coluna);
	}

	public int obterLinhaSelecionada() {
		return tabela.getSelectedRow();
	}

}

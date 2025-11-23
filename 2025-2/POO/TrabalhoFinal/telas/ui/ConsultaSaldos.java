package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import main.furb.banco.Banco;
import main.furb.entidades.Produto;

public class ConsultaSaldos extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JTextField EDsaldoGeral;

    public ConsultaSaldos() {

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder("Consulta de Saldo do Estoque"));

        // BOTÃO
        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton BTatualizar = new JButton("Atualizar");
        topo.add(BTatualizar);
        add(topo, BorderLayout.NORTH);

        // GRID AGORA MOSTRA PRODUTOS!!!
        String[] col = {"Seq", "Produto", "Qtd Estoque", "Preço Unitário", "Valor Total"};
        modeloTabela = new DefaultTableModel(col, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // RODAPÉ
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rodape.add(new JLabel("Saldo Total do Estoque (R$):"));

        EDsaldoGeral = new JTextField(15);
        EDsaldoGeral.setEditable(false);
        rodape.add(EDsaldoGeral);

        add(rodape, BorderLayout.SOUTH);

        // EVENTO
        BTatualizar.addActionListener(e -> atualizarTabela());
    }

    private void atualizarTabela() {

        modeloTabela.setRowCount(0);
        double totalGeral = 0;

        List<Produto> produtos = Banco.listar(Produto.class);

        for (Produto p : produtos) {

            double valorTotal = p.getQtdproduto() * p.getPrrunt();
            totalGeral += valorTotal;

            modeloTabela.addRow(new Object[]{
                    p.getSeqpro(),
                    p.getDespro(),
                    p.getQtdproduto(),
                    p.getPrrunt(),
                    valorTotal
            });
        }

        EDsaldoGeral.setText(String.format("%.2f", totalGeral));
    }
}

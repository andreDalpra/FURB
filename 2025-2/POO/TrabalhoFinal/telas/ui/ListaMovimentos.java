package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import main.furb.banco.Banco;
import main.furb.controle.MovimentoDAO;
import main.furb.entidades.MovimentoEstoque;
import main.furb.enums.TipoMovimento;

public class ListaMovimentos extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JComboBox<String> CBfiltro;

    public ListaMovimentos() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder("Movimentos de Estoque"));

        // COLUNAS DA GRID
        String[] colunas = {
            "Seq", "Data", "Produto", "Tipo", "Qtd", "Vlr Unit", "Total", "Saldo Qtd", "Saldo Valor"
        };

        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // OCULTA O SEQ
        tabela.getColumnModel().getColumn(0).setMinWidth(0);
        tabela.getColumnModel().getColumn(0).setMaxWidth(0);

        JScrollPane scroll = new JScrollPane(tabela);

        // COMBO DE FILTRO
        CBfiltro = new JComboBox<>(new String[] {
            "Entradas",
            "Saídas",
            "Todos"
        });

        CBfiltro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarLista();
            }
        });

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTop.add(CBfiltro);

        add(panelTop, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        atualizarLista();  // primeira carga
    }

    // -----------------------------------
    // ATUALIZA A GRID
    // -----------------------------------
    private void atualizarLista() {
        modeloTabela.setRowCount(0);

        MovimentoDAO dao = new MovimentoDAO();
        List<MovimentoEstoque> lista = Banco.listar(MovimentoEstoque.class);// do teu DAO

        // FILTRO DO COMBO
        String opcao = (String) CBfiltro.getSelectedItem();

        if ("Entradas".equals(opcao)) {
            lista.removeIf(m -> m.getTipmov() != TipoMovimento.ENTRADA);
        } else if ("Saídas".equals(opcao)) {
            lista.removeIf(m -> m.getTipmov() != TipoMovimento.SAIDA);
        }

        // ORDENO A DATA AQUI
        Collections.sort(lista, Comparator.comparing(MovimentoEstoque::getDatmov));

        // CALCULA SALDOS (EXTRATO)
        double saldoQtd = 0;
        double saldoVlr = 0;

        for (MovimentoEstoque mov : lista) {

            double total = mov.getQtdmov() * mov.getVlrunt();

            if (mov.getTipmov() == TipoMovimento.ENTRADA) {
                saldoQtd += mov.getQtdmov();
                saldoVlr += total;
            } else {
                saldoQtd -= mov.getQtdmov();
                saldoVlr -= total;
            }

            Object[] l = {
                mov.getSeqmov(),
                mov.getDatmov(),
                mov.getSeqpro().getDespro(),
                mov.getTipmov(),
                mov.getQtdmov(),
                mov.getVlrunt(),
                total,
                saldoQtd,
                saldoVlr
            };

            modeloTabela.addRow(l);
        }
    }
}

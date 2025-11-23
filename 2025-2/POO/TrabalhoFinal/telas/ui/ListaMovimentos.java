package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import main.furb.banco.Banco;
import main.furb.entidades.MovimentoEstoque;
import main.furb.enums.TipoMovimento;

public class ListaMovimentos extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JComboBox<String> CBfiltro;

    private JFormattedTextField txtDataIni;
    private JFormattedTextField txtDataFim;

    // FORMATADORES
    private final DateTimeFormatter fmtEntrada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter fmtTela = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ListaMovimentos() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder("Movimentos de Estoque"));

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

        tabela.getColumnModel().getColumn(0).setMinWidth(0);
        tabela.getColumnModel().getColumn(0).setMaxWidth(0);

        JScrollPane scroll = new JScrollPane(tabela);

        // COMBO
        CBfiltro = new JComboBox<>(new String[] {"Entradas", "Saídas", "Todos"});
        CBfiltro.addActionListener(e -> atualizarLista());

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTop.add(CBfiltro);
        
        // CAMPOS DE DATA COM MÁSCARA
      
        txtDataIni = criarCampoData();
        txtDataFim = criarCampoData();

        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.addActionListener(e -> atualizarLista());

        panelTop.add(new JLabel("Data Inicial:"));
        panelTop.add(txtDataIni);

        panelTop.add(new JLabel("Data Final:"));
        panelTop.add(txtDataFim);

        panelTop.add(btnFiltrar);

        add(panelTop, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        atualizarLista();
    }

    // Cria campo de data dd/MM/yyyy
    private JFormattedTextField criarCampoData() {
        try {
            MaskFormatter mask = new MaskFormatter("##/##/####");
            mask.setPlaceholderCharacter('_');
            return new JFormattedTextField(mask);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar campo de data", e);
        }
    }

    // -----------------------------------
    // ATUALIZA A GRID
    // -----------------------------------
    private void atualizarLista() {
        modeloTabela.setRowCount(0);

        List<MovimentoEstoque> lista = Banco.listar(MovimentoEstoque.class);

        // FILTRO DO COMBO
        String opcao = (String) CBfiltro.getSelectedItem();

        if ("Entradas".equals(opcao)) {
            lista.removeIf(m -> m.getTipmov() != TipoMovimento.ENTRADA);
        } else if ("Saídas".equals(opcao)) {
            lista.removeIf(m -> m.getTipmov() != TipoMovimento.SAIDA);
        }

        // ---------------------------
        // FILTRO POR DATA dd/MM/yyyy
        // ---------------------------
        LocalDate dataIni = parseData(txtDataIni.getText());
        LocalDate dataFim = parseData(txtDataFim.getText());

        if (dataIni != null) {
            lista.removeIf(m -> m.getDatmov().isBefore(dataIni));
        }

        if (dataFim != null) {
            lista.removeIf(m -> m.getDatmov().isAfter(dataFim));
        }

        Collections.sort(lista, Comparator.comparing(MovimentoEstoque::getDatmov));

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
                mov.getDatmov().format(fmtTela),
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

    private LocalDate parseData(String txt) {
        txt = txt.replace("_", "").trim();

        if (txt.length() != 10) return null; // campo vazio ou incompleto

        try {
            return LocalDate.parse(txt, fmtEntrada);
        } catch (Exception e) {
            return null;
        }
    }
}

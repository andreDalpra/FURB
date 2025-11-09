package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import main.furb.enums.TipoMensagem;

public class MensagemUI extends JDialog {

	private static final long serialVersionUID = 7404505540961553813L;

	public MensagemUI(String titulo, String mensagem, TipoMensagem tipo) {
		setTitle(titulo);
		setModal(true);
		setSize(450, 220);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setLayout(new BorderLayout());

		// 🎨 Cores por tipo de mensagem
		Color corPrincipal;
		String iconeEmoji;
		switch (tipo) {
		case OK -> {
			corPrincipal = new Color(46, 204, 113); // Verde
			iconeEmoji = "✅";
		}
		case WARNING -> {
			corPrincipal = new Color(241, 196, 15); // Amarelo
			iconeEmoji = "⚠️";
		}
		case ERROR -> {
			corPrincipal = new Color(231, 76, 60); // Vermelho
			iconeEmoji = "❌";
		}
		default -> {
			corPrincipal = new Color(52, 152, 219); // Azul padrão
			iconeEmoji = "💬";
		}
		}

		// 🟩 Painel superior (barra de título colorida)
		JPanel header = new JPanel();
		header.setBackground(corPrincipal);
		header.setPreferredSize(new Dimension(getWidth(), 50));
		header.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

		JLabel tituloLabel = new JLabel(iconeEmoji + "  " + titulo);
		tituloLabel.setFont(new Font("COMIC SANS", Font.BOLD, 18));
		tituloLabel.setForeground(Color.WHITE);
		header.add(tituloLabel);

		// 📄 Painel central (mensagem)
		JPanel body = new JPanel();
		body.setBackground(new Color(250, 250, 250));
		body.setLayout(new GridBagLayout());

		JLabel msgLabel = new JLabel("<html><div style='text-align:center;'>" + mensagem + "</div></html>");
		msgLabel.setFont(new Font("COMIC SANS", Font.PLAIN, 16));
		msgLabel.setForeground(new Color(60, 60, 60));
		msgLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centraliza o texto dentro do JLabel
		msgLabel.setVerticalAlignment(SwingConstants.CENTER); // Centraliza verticalmente também
		msgLabel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30)); // Margem interna
		body.add(msgLabel);

		// 🔘 Painel inferior (botão)
		JPanel footer = new JPanel();
		footer.setBackground(new Color(245, 245, 245));
		footer.setPreferredSize(new Dimension(getWidth(), 60));

		JButton okButton = new JButton("OK");
		okButton.setFont(new Font("COMIC SANS", Font.BOLD, 14));
		okButton.setFocusPainted(false);
		okButton.setBackground(corPrincipal);
		okButton.setForeground(Color.WHITE);
		okButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		okButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose(); // Fecha a janela
			}
		});

		footer.add(okButton);

		// 🧩 Monta a estrutura
		add(header, BorderLayout.NORTH);
		add(body, BorderLayout.CENTER);
		add(footer, BorderLayout.SOUTH);

		// 🌈 Sombras suaves (efeito popup flutuante)
		getRootPane().setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
						BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		SwingUtilities.invokeLater(() -> {
			// garante que vai rodar depois do setVisible(true)

			setAlwaysOnTop(true);
			toFront();
			requestFocus();
			setAlwaysOnTop(false);

		});

	}

}
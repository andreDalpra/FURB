/*
 * Classe que contem os icones da aplicação 
 * 
 */
package icones;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;

import javax.swing.Icon;

public class IconeFerramenta implements Icon {

	public enum Tipo {
		NOVO, ABRIR, SALVAR, COPIAR, COLAR, RECORTAR, COMPILAR, EQUIPE
	}

	private static final int TAMANHO = 30;
	private final Tipo tipo;

	public IconeFerramenta(Tipo tipo) {
		this.tipo = tipo;
	}

	@Override
	public int getIconWidth() {
		return TAMANHO;
	}

	@Override
	public int getIconHeight() {
		return TAMANHO;
	}

	@Override
	public void paintIcon(Component componente, Graphics graphics, int x, int y) {
		Graphics2D g = (Graphics2D) graphics.create();
		g.translate(x, y);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

		switch (tipo) {
		case NOVO:
			desenharNovo(g);
			break;
		case ABRIR:
			desenharAbrir(g);
			break;
		case SALVAR:
			desenharSalvar(g);
			break;
		case COPIAR:
			desenharCopiar(g);
			break;
		case COLAR:
			desenharColar(g);
			break;
		case RECORTAR:
			desenharRecortar(g);
			break;
		case COMPILAR:
			desenharCompilar(g);
			break;
		case EQUIPE:
			desenharEquipe(g);
			break;
		default:
			break;
		}

		g.dispose();
	}

	private void desenharNovo(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(6, 3, 18, 24);
		g.setColor(new Color(70, 90, 110));
		g.drawRect(6, 3, 18, 24);
		g.setColor(new Color(34, 139, 230));
		g.drawLine(15, 9, 15, 21);
		g.drawLine(9, 15, 21, 15);
	}

	private void desenharAbrir(Graphics2D g) {
		g.setColor(new Color(244, 180, 52));
		g.fillRoundRect(3, 9, 24, 17, 3, 3);
		g.setColor(new Color(176, 112, 20));
		g.drawRoundRect(3, 9, 24, 17, 3, 3);
		g.fillRect(5, 6, 10, 5);
	}

	private void desenharSalvar(Graphics2D g) {
		g.setColor(new Color(69, 111, 145));
		g.fillRoundRect(4, 3, 22, 24, 2, 2);
		g.setColor(Color.WHITE);
		g.fillRect(8, 5, 14, 7);
		g.fillRect(9, 18, 12, 9);
		g.setColor(new Color(38, 62, 80));
		g.drawRoundRect(4, 3, 22, 24, 2, 2);
	}

	private void desenharCopiar(Graphics2D g) {
		g.setColor(new Color(215, 225, 235));
		g.fillRect(9, 4, 16, 20);
		g.setColor(new Color(70, 90, 110));
		g.drawRect(9, 4, 16, 20);
		g.setColor(Color.WHITE);
		g.fillRect(4, 9, 16, 17);
		g.setColor(new Color(70, 90, 110));
		g.drawRect(4, 9, 16, 17);
	}

	private void desenharColar(Graphics2D g) {
		g.setColor(new Color(204, 142, 51));
		g.fillRoundRect(5, 6, 20, 21, 3, 3);
		g.setColor(new Color(120, 78, 24));
		g.drawRoundRect(5, 6, 20, 21, 3, 3);
		g.setColor(Color.WHITE);
		g.fillRect(9, 10, 12, 13);
		g.setColor(new Color(110, 110, 110));
		g.drawRect(9, 10, 12, 13);
		g.setColor(new Color(230, 230, 230));
		g.fillRoundRect(10, 3, 10, 6, 3, 3);
	}

	private void desenharRecortar(Graphics2D g) {
		g.setColor(new Color(185, 42, 42));
		g.drawLine(9, 8, 23, 25);
		g.drawLine(21, 7, 8, 25);
		g.drawOval(3, 19, 8, 8);
		g.drawOval(19, 19, 8, 8);
		g.setColor(new Color(80, 80, 80));
		g.drawLine(11, 14, 22, 3);
		g.drawLine(19, 14, 8, 3);
	}

	private void desenharCompilar(Graphics2D g) {
		g.setColor(new Color(40, 170, 80));
		Polygon play = new Polygon(new int[] { 7, 7, 25 }, new int[] { 4, 26, 15 }, 3);
		g.fillPolygon(play);
		g.setColor(new Color(20, 105, 48));
		g.drawPolygon(play);
	}

	private void desenharEquipe(Graphics2D g) {
		g.setColor(new Color(66, 126, 184));
		g.fillOval(10, 3, 10, 10);
		g.fillRoundRect(7, 15, 16, 12, 8, 8);
		g.setColor(new Color(91, 154, 211));
		g.fillOval(2, 8, 8, 8);
		g.fillOval(20, 8, 8, 8);
		g.fillRoundRect(1, 18, 9, 8, 5, 5);
		g.fillRoundRect(20, 18, 9, 8, 5, 5);
	}
}

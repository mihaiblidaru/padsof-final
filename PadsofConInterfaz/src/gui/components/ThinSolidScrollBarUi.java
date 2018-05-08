package gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * Barra deslizable de color solido, si flechas
 * de dimension variable
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class ThinSolidScrollBarUi extends BasicScrollBarUI {

	/**
	 * Ancho del scrollbar
	 */
	public int width;

	private final Dimension d = new Dimension();

	/**
	 * Inicializa un nuevo scrollbarui
	 * 
	 * @param width
	 *            ancho del scrollbar
	 */
	public ThinSolidScrollBarUi(int width) {
		this.width = width;
	}

	/**
	 * Crea un boton vacio invisible
	 */
	@Override
	protected JButton createDecreaseButton(int orientation) {
		return new JButton() {
			private static final long serialVersionUID = -8507987472364204006L;

			@Override
			public Dimension getPreferredSize() {
				return d;
			}
		};
	}

	/**
	 * Crea un boton vacio invisible
	 */
	@Override
	protected JButton createIncreaseButton(int orientation) {
		return new JButton() {
			private static final long serialVersionUID = 9165356859523155025L;

			@Override
			public Dimension getPreferredSize() {
				return d;
			}
		};
	}

	/**
	 * Pinta el fondo del scrollbar
	 */
	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
		g.setColor(Color.WHITE);
		g.drawRect(r.x, r.y, r.width, r.height);
	}

	/**
	 * Pinta el propio scrollbar
	 */
	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Color color = null;
		JScrollBar sb = (JScrollBar) c;
		if (!sb.isEnabled() || r.width > r.height) {
			return;
		} else if (isDragging) {
			color = Color.LIGHT_GRAY;
		} else if (isThumbRollover()) {
			color = Color.decode("#d7d7d7");
		} else {
			color = Color.decode("#e1e1e1");
		}
		g2.setPaint(color);
		g2.fillRect(r.x, r.y, width, r.height);
		g2.setPaint(Color.WHITE);
		g2.dispose();
	}

	/**
	 * Cambia los limites del scrollbar
	 * 
	 */
	@Override
	protected void setThumbBounds(int x, int y, int width, int height) {
		super.setThumbBounds(x, y, width, height);
		scrollbar.repaint();
	}
}
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

public class ThinSolidScrollBarUi extends BasicScrollBarUI {

	public int width;

	public ThinSolidScrollBarUi(int width) {
		this.width = width;
	}

	private final Dimension d = new Dimension();

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

	@Override
	protected JButton createIncreaseButton(int orientation) {
		return new JButton() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 9165356859523155025L;

			@Override
			public Dimension getPreferredSize() {
				return d;
			}
		};
	}

	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
		g.setColor(Color.white);
		g.drawRect(r.x, r.y, r.width, r.height);
	}

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

	@Override
	protected void setThumbBounds(int x, int y, int width, int height) {
		super.setThumbBounds(x, y, width, height);
		scrollbar.repaint();
	}
}
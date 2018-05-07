package gui.components;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.JTextArea;

public class JMultiLineLabel extends JTextArea {
	private String fullText;
	private boolean fixed;
	private static final long serialVersionUID = -3350083011830553008L;

	public JMultiLineLabel(String string, int width, int height, boolean fixed) {
		super(string.trim());
		this.fullText = string.trim();
		this.fixed = fixed;
		setLineWrap(true);
		setWrapStyleWord(true);
		this.setEditable(false);
		this.setPreferredSize(new Dimension(width, height));
		this.setOpaque(false);
		this.setHighlighter(null);
		this.setFont(new Font("Courier New", Font.PLAIN, this.getFont().getSize()));
		setMaximumSize(getPreferredSize());
		this.setSize(new Dimension(width, height));

		if (fixed) {
			if (getLineCount() > getFullyVisibleLineCount()) {
				this.setText(this.getText() + "...");
				while (getLineCount() > getFullyVisibleLineCount()) {
					String text = this.getText().substring(0, this.getText().length() - 4) + "...";
					this.setText(text);
				}
			}
		} else {
			if (getLineCount() > getFullyVisibleLineCount()) {
				recalculateHeight();
			}
		}
	}

	public void setNewSize(int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
		this.setMaximumSize(getPreferredSize());
		this.setSize(getPreferredSize());
		this.recalculateHeight();
	}

	public void recalculateHeight() {
		Font font = this.getFont();
		FontMetrics fontMetrics = this.getFontMetrics(font);
		//this.repaint();
		int height = fontMetrics.getHeight() * getLineCount();
		Dimension dim = this.getPreferredSize();
		dim.height = height;
		this.setPreferredSize(dim);
		this.setMaximumSize(dim);
		this.setSize(dim);

	}

	@Override
	public int getLineCount() {
		Font font = this.getFont();
		FontMetrics fontMetrics = this.getFontMetrics(font);
		int fontHeight = fontMetrics.getHeight();
		int lineCount;
		try {
			int height = this.modelToView(this.getDocument().getEndPosition().getOffset() - 1).y;
			lineCount = height / fontHeight + 1;
		} catch (Exception e) {
			lineCount = 0;
		}
		return lineCount;
	}

	public int getFullyVisibleLineCount() {
		Font font = this.getFont();
		FontMetrics fontMetrics = this.getFontMetrics(font);
		int fontHeight = fontMetrics.getHeight();

		return this.getMaximumSize().height / fontHeight;
	}

}

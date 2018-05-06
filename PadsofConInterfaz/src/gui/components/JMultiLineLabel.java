package gui.components;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.JTextArea;

public class JMultiLineLabel extends JTextArea {

	public JMultiLineLabel(String string, int width, int height) {
		super(string);
		setLineWrap(true);
		setWrapStyleWord(true);
		this.setEditable(false);
		this.setPreferredSize(new Dimension(width, height));
		this.setOpaque(false);
		this.setHighlighter(null);
		setMaximumSize(getPreferredSize());
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3350083011830553008L;

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

}

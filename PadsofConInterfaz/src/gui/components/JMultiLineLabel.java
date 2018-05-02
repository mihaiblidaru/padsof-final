package gui.components;

import javax.swing.JLabel;

public class JMultiLineLabel extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String html1 = "<html><body style='width: ";
	private static final String html2 = "px'>";
	private static final String html3 = "</body></html>";

	private int width;

	public JMultiLineLabel(String text, int maxWidth) {
		super(html1 + maxWidth + html2 + text + html3);
		this.width = maxWidth;
	}

	/*
	 * @Override public void setText(String text) { super.setText(html1 + width +
	 * html2 + text + html3); }
	 */

}

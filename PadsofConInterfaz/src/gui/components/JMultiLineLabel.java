package gui.components;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.JTextArea;

/**
 * Label Multilinea implementado usando un
 * JTextArea.
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class JMultiLineLabel extends JTextArea {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3350083011830553008L;

	/**
	 * Texto completo del label
	 */
	private String fullText;

	/**
	 * Crea un nuevo Label multilinea. Si se fija la altura y el texto ocupa más
	 * del tamaño visible, este será recortado al numero de lineas completamente
	 * visibles
	 * 
	 * @param string
	 *            cadena contenida en este label
	 * @param width
	 *            ancho del componente
	 * @param height
	 *            alto del componente
	 * @param fixed
	 *            true si el alto es fijo o false si es flexible
	 */
	public JMultiLineLabel(String string, int width, int height, boolean fixed) {
		super(string.trim());
		this.setFullText(string.trim());
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

	/**
	 * Cambia el tamaño del label
	 * 
	 * @param width
	 *            ancho del componente
	 * @param height
	 *            alto del componente
	 */
	public void setNewSize(int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
		this.setMaximumSize(getPreferredSize());
		this.setSize(getPreferredSize());
		this.recalculateHeight();
	}

	/**
	 * Recalcula la altura del componente
	 */
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

	/**
	 * Devuelve el número de lineas reales(teniendo en cuenta el word wrap).
	 * Funciona correctamente solo si la fuente usada es de tipo Monospace ya que
	 * para calcular el numero de lineas se necesita una medida exacta del ancho de
	 * cada linea.
	 * 
	 * @return el numero de lineas del label
	 */
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

	/**
	 * Devuelve el texto completo del label
	 * 
	 * @return el texto completo del label
	 */
	public String getFullText() {
		return fullText;
	}

	/**
	 * Cambia el texto del label
	 * 
	 * @param fullText
	 *            el texto del label
	 */
	public void setFullText(String fullText) {
		this.fullText = fullText;
	}

}

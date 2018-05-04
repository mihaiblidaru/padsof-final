package gui.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Usado para limitar el número de caracteres dentro de un Documento de
 * textarea. Tampoco permite insertar nuevas lineas.
 */
public class LimitedDocument extends PlainDocument {

	private static final long serialVersionUID = -3514808888826488252L;
	private int max;

	public LimitedDocument(int max) {
		this.max = max;
	}

	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		str = str.replaceAll("\n", "");
		if (getLength() + str.length() > max) {
			str = str.substring(0, max - getLength());
		}
		super.insertString(offs, str, a);
	}
}
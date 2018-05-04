package gui.util;

import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class LimitCounter implements DocumentListener {

	private final JLabel label;
	private final int limit;

	public LimitCounter(JLabel label, int limit) {
		super();
		this.label = label;
		this.limit = limit;
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		updateLabel(e);
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		updateLabel(e);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		updateLabel(e);
	}

	public void updateLabel(DocumentEvent e) {
		label.setText(String.format("%d restantes", limit - e.getDocument().getLength()));
	}
}

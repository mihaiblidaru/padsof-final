package gui.util;

import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Esta es nuestra clase que carga nuestro icono
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class LimitCounter implements DocumentListener {

	/**
	 * El label de LimitCounter
	 */
	private final JLabel label;
	
	/**
	 *  El limite
	 */
	private final int limit;

	/**	
	 * Constructor de LimitCounter
	 * @param label el label de LimitCounter
	 * @param limit el limite de LimitCounter
	 */
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

	/**
	 * Esta funcion actualiza la label, cambiando su texto
	 * @param e evento para actualizar la label
	 */
	public void updateLabel(DocumentEvent e) {
		label.setText(String.format("%d restantes", limit - e.getDocument().getLength()));
	}
}

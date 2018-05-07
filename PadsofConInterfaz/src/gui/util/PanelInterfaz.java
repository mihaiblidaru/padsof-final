package gui.util;

import javax.swing.JPanel;

public abstract class PanelInterfaz extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7215146892831106687L;

	protected void initialize() {
		crearComponentes();
		colocarComponentes();
		setDimension();
		registrarEventos();
	}

	protected abstract void setDimension();

	protected void registrarEventos() {
	}

	protected void crearComponentes() {
	}

	protected void colocarComponentes() {
	};
}

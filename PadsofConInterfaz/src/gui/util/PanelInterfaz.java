package gui.util;

import javax.swing.JPanel;

/**
 * Esta es nuestra clase abstracta que utilizaremos para inicializar las interfaces
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public abstract class PanelInterfaz extends JPanel {
	
	private static final long serialVersionUID = -7215146892831106687L;

	/**
	 * Inicializa la interfaz
	 */
	protected void initialize() {
		crearComponentes();
		colocarComponentes();
		setDimension();
		registrarEventos();
	}

	/**
	 * Define la dimension de la interfaz grafica
	 */
	protected abstract void setDimension();

	/**
	 * Registra eventos que ocurran en la interfaz grafica
	 */
	protected void registrarEventos() {
	}

	/**
	 * Crea los componentes de una interfaz grafica
	 */
	protected void crearComponentes() {
	}

	/**
	 * Coloca los componentes de la interfaz grafica
	 */
	protected void colocarComponentes() {
	};
}

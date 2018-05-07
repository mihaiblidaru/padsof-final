package gui.panels.ofertante.inmuebles;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import gui.Gui;
import gui.components.ThinSolidScrollBarUi;
import gui.util.Nombrable;
import gui.util.PanelInterfaz;

/**
 * Esta es nuestra clase que sirve para ver nuestros inmuebles
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class MisInmuebles extends PanelInterfaz implements Nombrable {

	private static final long serialVersionUID = -8320036169616362237L;

	/**
	 * Nombre del panel
	 */
	public static final String NAME = "MIS_INMUEBLES";

	/**
	 * Interfaz grafica
	 */
	private Gui gui;

	/**
	 * Contenedor interno de ofeinmuebles
	 */
	private ContenedorInmuebles coi;

	/**
	 * Constructor de MisInmuebles
	 * @param gui interfaz grafica
	 */
	public MisInmuebles(Gui gui) {
		this.gui = gui;
		this.initialize();
	}

	/**
	 * Funcion que define las dimensiones de la interfaz
	 */
	@Override
	public void setDimension() {
		this.setPreferredSize(new Dimension(995, 600));
	}

	/**
	 * Funcion que crea los componentes de la interfaz, y los añade a la interfaz grafica
	 */
	@Override
	public void crearComponentes() {
		coi = new ContenedorInmuebles(gui);

		JScrollPane scrollPane = new JScrollPane(coi, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUI(new ThinSolidScrollBarUi(7));
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setPreferredSize(new Dimension(1006, 563));
		scrollPane.setBackground(Color.BLUE);

		this.add(scrollPane);

	}

	/**
	 * Esta funcion crea el layout de la interfaz grafica
	 */
	@Override
	public void colocarComponentes() {
		SpringLayout springLayout = new SpringLayout();
		this.setLayout(springLayout);

	}

	/**
	 * Esta funcion cambia la visibilidad, vuelve a cargar los datos
	 * @param flag estado de visibilidad
	 */
	@Override
	public void setVisible(boolean flag) {
		super.setVisible(flag);
		if (flag) {
			cargarInmuebles();
		}
	}

	/**
	 * Esta funcion registra los eventos que ocurren en la interfaz
	 */
	@Override
	public void registrarEventos() {

	}

	/**
	 * Esta funcion carga los inmuebles
	 */
	private void cargarInmuebles() {
		coi.cargarInmuebles();
	}

}

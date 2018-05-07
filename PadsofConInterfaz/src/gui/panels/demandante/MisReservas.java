package gui.panels.demandante;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import gui.Gui;
import gui.components.ThinSolidScrollBarUi;
import gui.controllers.Controller;
import gui.panels.oferta.PanelOferta;
import gui.panels.oferta.PanelOfertaReservadaInterno;
import gui.util.Nombrable;
import gui.util.PanelInterfaz;

/**
 * Esta es nuestra clase que sirve para ver nuestras reservas
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class MisReservas extends PanelInterfaz implements Nombrable {

	private static final long serialVersionUID = -8320036169616362237L;

	/**
	 * Nombre del panel
	 */
	public static final String NAME = "MIS_RESERVAS";

	/**
	 * Interfaz grafica
	 */
	private Gui gui;

	/**
	 * Contenedor interno de reservas
	 */
	private ContenedorReservasInterno cri;

	/**
	 * Constructor de MisReservas
	 * @param gui interfaz grafica
	 */
	public MisReservas(Gui gui) {
		this.gui = gui;
		initialize();
	}

	/**
	 * Cambia la dimension del contenedor
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
		cri = new ContenedorReservasInterno();

		JScrollPane scrollPane = new JScrollPane(cri, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUI(new ThinSolidScrollBarUi(7));
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setPreferredSize(new Dimension(1006, 563));
		scrollPane.setBackground(Color.BLUE);

		this.add(scrollPane);

	}

	/**
	 * Esta funcion  coloca los componentes en la interfaz grafica utilizando un SpringLayout
	 */
	@Override
	public void colocarComponentes() {
		SpringLayout springLayout = new SpringLayout();
		this.setLayout(springLayout);
	}

	/**
	 * Esta funcion carga las ofertas para el panel
	 */
	public void cargarOfertas() {
		Controller c = gui.getController();
		Integer activa = c.demandanteGetOfertaReservada();
		if (activa != null) {
			cri.addReserva(new PanelOfertaReservadaInterno(gui, activa));
		}

		List<Integer> ofertas = c.demandanteGetOfertasContratadas();
		for (Integer id : ofertas) {
			PanelOferta oferta = new PanelOferta(gui, id);
			cri.addContratada(oferta);

		}
		cri.repaint();
	}

	/**
	 * Añade una contratacion a una oferta
	 * @param idOferta id de la oferta
	 */
	public void addContratada(int idOferta) {
		cri.addContratada(new PanelOferta(gui, idOferta));
		cri.repaint();
	}

	/**
	 * Quita la reserva de una oferta
	 * @param idOferta id de la oferta
	 */
	public void removeReserva(int idOferta) {
		cri.removeReserva(idOferta);
		cri.repaint();
	}
}

package gui.panels.admin.ofertas;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import gui.Gui;
import gui.components.ThinSolidScrollBarUi;
import gui.util.Nombrable;

/**
 * Esta es nuestra clase que sirve para trabajar con los oferta pendientes
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class OfertasPendientes extends JLayeredPane implements Nombrable {

	private static final long serialVersionUID = -8320036169616362237L;

	/**
	 * Nombre del panel
	 */
	public static final String NAME = "OFERTAS_PENDIENTES";

	/**
	 * Interfaz grafica
	 */
	private Gui gui;

	/**
	 * COntenedor de Ofertas pendientes
	 */
	private ContenedorOfertasPendientes coi;

	/**
	 * Constructor de OfetasPendientes
	 * @param gui interfaz grafica
	 */
	public OfertasPendientes(Gui gui) {
		this.gui = gui;
		this.setPreferredSize(new Dimension(995, 600));
		this.setName(NAME);
		initialize();
	}

	/**
	 * Crea el layout, el contenedor y el le da un deslizable al contenedor
	 */
	private void initialize() {
		SpringLayout springLayout = new SpringLayout();
		this.setLayout(springLayout);

		coi = new ContenedorOfertasPendientes(gui);

		JScrollPane scrollPane = new JScrollPane(coi, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUI(new ThinSolidScrollBarUi(7));
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setPreferredSize(new Dimension(1006, 563));
		scrollPane.setBackground(Color.BLUE);

		this.add(scrollPane);
		this.setLayer(scrollPane, 1);

	}

	/**
	 * Carga ofertas en el contenedor
	 */
	public void cargarOfertas() {
		coi.cargarOfertas();
	}

	/**
	 * Borra una oferta de la interfaz
	 * @param id id de la oferta 
	 */
	public void removeOferta(int id) {
		coi.removeOferta(id);
		this.revalidate();
		this.repaint();
	}
}

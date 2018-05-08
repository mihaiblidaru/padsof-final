package gui.panels.admin.ofertas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import gui.Gui;
import gui.controllers.Controller;
import gui.panels.oferta.PanelOferta;
import gui.panels.oferta.PanelOfertaAdmin;
import gui.panels.ofertante.inmuebles.PanelInmueble;
import gui.util.PanelInterfaz;

/**
 * Esta es nuestra clase que sirve como contenedor interno de ofertas pendientes
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class ContenedorOfertasPendientes extends PanelInterfaz {

	private static final long serialVersionUID = -213843877174040375L;

	/**
	 * Mapa de ofertas
	 */
	private Map<Integer, PanelOfertaAdmin> ofertas = new HashMap<>();

	/**
	 * Interfaz gafica
	 */
	private Gui gui;

	/**
	 * Layout de la interfaz
	 */
	private SpringLayout layout;

	/**
	 * Label de las ofertas
	 */
	private JLabel labelOfertas;
	
	/**
	 * Separador de las ofertas
	 */
	private JSeparator separatorOfertas;
	
	/**
	 * Label de las ofertas rechazadas
	 */
	private JLabel labelNoOfertas;

	/**
	 * Panel para el grupo de ofertas
	 */
	private JPanel grupoOfertas;

	/**
	 * Separacion entre los inmuebles
	 */
	private static final int SEPARACION_INMUEBLES = 10;
	
	/**
	 * Anch del panel
	 */
	private static final int PANEL_WIDTH = 990;

	/**
	 * Constructor de ContenedorOfetasPendientes
	 * @param gui interfaz grafica
	 */
	public ContenedorOfertasPendientes(Gui gui) {
		this.gui = gui;
		initialize();
	}

	/**
	 * Carga las ofertas y a�ade los paneles
	 */
	public void cargarOfertas() {
		Controller c = gui.getController();
		List<Integer> resultados = null;
		resultados = c.adminGetOfertasPendientes();
		for (Integer id : ofertas.keySet()) {
			if (!resultados.contains(id)) {
				grupoOfertas.remove(ofertas.remove(id));
			}
		}

		for (Integer id : resultados) {
			this.addOferta(new PanelOfertaAdmin(gui, id));
		}
	}

	/**
	 * Cambia la dimension de la interfaz
	 */
	@Override
	public void setDimension() {
		this.setPreferredSize(new Dimension(PANEL_WIDTH,
				90 + SEPARACION_INMUEBLES + ofertas.size() * (PanelOferta.PANEL_HEIGHT + SEPARACION_INMUEBLES)));
	}

	/**
	 * Funcion que crea los componentes de la interfaz, y los a�ade a la interfaz grafica
	 */
	@Override
	public void crearComponentes() {
		Font font = new Font("Times New Roman", Font.PLAIN, 20);
		Font fontinfo = new Font("Verdana", Font.PLAIN, 15);
		labelOfertas = new JLabel("Ofertas pendientes");

		labelNoOfertas = new JLabel("No hay ninguna oferta pendiente en este momento");
		separatorOfertas = new JSeparator(SwingConstants.HORIZONTAL);
		labelOfertas.setFont(font);
		labelNoOfertas.setFont(fontinfo);
		separatorOfertas.setPreferredSize(new Dimension(975, 1));
		separatorOfertas.setForeground(Color.GRAY);
		grupoOfertas = new JPanel();
		grupoOfertas.add(labelNoOfertas);
		grupoOfertas.setPreferredSize(new Dimension(PanelInmueble.PANEL_WIDTH, 100));

		this.add(labelOfertas);
		this.add(separatorOfertas);
		this.add(grupoOfertas);

	}

	/**
	 * Esta funcion  coloca los componentes en la interfaz grafica utilizando un SpringLayout
	 */
	@Override
	public void colocarComponentes() {
		layout = new SpringLayout();
		this.setLayout(layout);
		layout.putConstraint(SpringLayout.NORTH, labelOfertas, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, labelOfertas, 10, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, separatorOfertas, 5, SpringLayout.SOUTH, labelOfertas);
		layout.putConstraint(SpringLayout.WEST, separatorOfertas, -5, SpringLayout.WEST, labelOfertas);

		layout.putConstraint(SpringLayout.NORTH, grupoOfertas, 5, SpringLayout.SOUTH, separatorOfertas);
		layout.putConstraint(SpringLayout.WEST, grupoOfertas, 0, SpringLayout.WEST, labelOfertas);

		FlowLayout l1 = new FlowLayout(FlowLayout.LEFT, 0, SEPARACION_INMUEBLES);
		grupoOfertas.setLayout(l1);

	}

	/**
	 * A�ade el panel de una oferta
	 * @param panelOfertaAdmin panel a a�adir
	 */
	public void addOferta(PanelOfertaAdmin panelOfertaAdmin) {
		if (ofertas.isEmpty())
			labelNoOfertas.setVisible(false);

		int id = panelOfertaAdmin.getIdOferta();

		if (!ofertas.containsKey(id)) {
			grupoOfertas.add(panelOfertaAdmin);
			ofertas.put(id, panelOfertaAdmin);
			setDimension();
			this.grupoOfertas.setPreferredSize(new Dimension(PanelOferta.PANEL_WIDTH,
					ofertas.size() * (PanelOferta.PANEL_HEIGHT + SEPARACION_INMUEBLES)));
		}
	}

	/**
	 * Borra todas las ofertas de la interfaz
	 */
	public void clearOfertas() {
		grupoOfertas.removeAll();
		ofertas.clear();
	}

	/**
	 * Borra una oferta dado un id
	 * @param id id de la oferta
	 */
	public void removeOferta(int id) {
		grupoOfertas.remove(ofertas.remove(id));
		if (ofertas.isEmpty()) {
			labelNoOfertas.setVisible(true);
			this.repaint();
		}
		this.revalidate();
	}
}

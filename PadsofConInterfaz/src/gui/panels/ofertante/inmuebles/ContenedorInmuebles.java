package gui.panels.ofertante.inmuebles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import gui.Gui;
import gui.components.fx.FxButton;
import gui.controllers.Controller;
import gui.panels.Header;
import gui.util.PanelInterfaz;

/**
 * Esta es nuestra clase que sirve como contenedor interno de inmuebles
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class ContenedorInmuebles extends PanelInterfaz {

	private static final long serialVersionUID = -2138438771740403776L;

	/**
	 * El mapa de inmuebles
	 */
	private Map<Integer, JComponent> inmuebles = new HashMap<>();

	/**
	 * La interfaz grafica
	 */
	private Gui gui;

	/**
	 * El layout del contenedor
	 */
	private SpringLayout layout;

	/**
	 * El jlabel de los inmuebles
	 */
	private JLabel labelInmuebles;
	/**
	 * El separador de los inmuebles
	 */
	private JSeparator separatorInmuebles;
	
	/**
	 * El jlabel de los inmuebles, si no hay
	 */
	private JLabel labelNoInmuebles;
	
	/**
	 * Boton para aniadir
	 */
	private FxButton anyadir;

	/**
	 * El panel del grupo de inmuebles
	 */
	private JPanel grupoInmuebles;

	
	/**
	 * La separacion de los inmuebles
	 */
	private static final int SEPARACION_INMUEBLES = 10;
	
	/**
	 * El ancho del panel
	 */
	private static final int PANEL_WIDTH = 990;

	/**
	 * Constructor del ContenedorInmuebles
	 * @param gui interfaz grafica
	 */
	public ContenedorInmuebles(Gui gui) {
		this.gui = gui;
		initialize();
	}

	/**
	 * Funcion que carga los inmuebles en el contenedor
	 */
	public void cargarInmuebles() {
		Controller c = gui.getController();
		List<Integer> resultados = c.ofertanteGetMisInmuebles();
		inmuebles.keySet().stream().filter(k -> !resultados.contains(k))
				.forEach(k -> grupoInmuebles.remove(inmuebles.remove(k)));

		for (Integer id : resultados) {
			this.addInmueble(new PanelInmueble(gui, id));
		}
	}

	/**
	 * Funcion que define las dimensiones de la interfaz
	 */
	@Override
	public void setDimension() {
		this.setPreferredSize(new Dimension(PANEL_WIDTH,
				90 + SEPARACION_INMUEBLES + inmuebles.size() * (PanelInmueble.PANEL_HEIGHT + SEPARACION_INMUEBLES)));
	}

	/**
	 * Funcion que crea los componentes de la interfaz, y los añade a la interfaz grafica
	 */
	@Override
	public void crearComponentes() {
		Font font = new Font("Times New Roman", Font.PLAIN, 20);
		Font fontinfo = new Font("Verdana", Font.PLAIN, 15);
		labelInmuebles = new JLabel("Tus inmuebles");

		labelNoInmuebles = new JLabel("No tienes ninguna inmueble registrado");
		separatorInmuebles = new JSeparator(SwingConstants.HORIZONTAL);
		labelInmuebles.setFont(font);
		labelNoInmuebles.setFont(fontinfo);
		separatorInmuebles.setPreferredSize(new Dimension(975, 1));
		separatorInmuebles.setForeground(Color.GRAY);
		anyadir = new FxButton(150, 30, "Añadir Inmueble");
		grupoInmuebles = new JPanel();
		grupoInmuebles.add(labelNoInmuebles);
		grupoInmuebles.setPreferredSize(new Dimension(PanelInmueble.PANEL_WIDTH, 100));

		this.add(labelInmuebles);
		this.add(separatorInmuebles);
		this.add(grupoInmuebles);

		this.add(anyadir);
	}

	/**
	 * Esta funcion  coloca los componentes en la interfaz grafica utilizando un SpringLayout
	 */
	@Override
	public void colocarComponentes() {
		layout = new SpringLayout();
		this.setLayout(layout);
		layout.putConstraint(SpringLayout.NORTH, labelInmuebles, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, labelInmuebles, 10, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, separatorInmuebles, 5, SpringLayout.SOUTH, labelInmuebles);
		layout.putConstraint(SpringLayout.WEST, separatorInmuebles, -5, SpringLayout.WEST, labelInmuebles);

		layout.putConstraint(SpringLayout.EAST, anyadir, -10, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, anyadir, -5, SpringLayout.NORTH, separatorInmuebles);

		layout.putConstraint(SpringLayout.NORTH, grupoInmuebles, 5, SpringLayout.SOUTH, separatorInmuebles);
		layout.putConstraint(SpringLayout.WEST, grupoInmuebles, 0, SpringLayout.WEST, labelInmuebles);

		FlowLayout l1 = new FlowLayout(FlowLayout.LEFT, 0, SEPARACION_INMUEBLES);
		grupoInmuebles.setLayout(l1);

	}

	/**
	 * Esta funcion registra los eventos que ocurren en la interfaz
	 */
	@Override
	public void registrarEventos() {
		anyadir.setOnAction(event -> {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					gui.showOnly(Header.NAME, AniadirInmueble.NAME);
				}
			});

		});
	}

	/**
	 * Funcion que añade un inmueble al contenedor
	 * @param p panel del inmueble a añadir
	 */
	public void addInmueble(PanelInmueble p) {
		if (inmuebles.isEmpty())
			labelNoInmuebles.setVisible(false);

		int id = p.getIdInmuebles();

		if (!inmuebles.containsKey(id)) {
			grupoInmuebles.add(p);
			inmuebles.put(id, p);
			setDimension();
			this.grupoInmuebles.setPreferredSize(new Dimension(PanelInmueble.PANEL_WIDTH,
					inmuebles.size() * (PanelInmueble.PANEL_HEIGHT + SEPARACION_INMUEBLES)));
		}
	}

	/**
	 * Funcion que borra los inmuebles de la interfaz
	 */
	public void clearInmuebles() {
		grupoInmuebles.removeAll();
		inmuebles.clear();
	}
}

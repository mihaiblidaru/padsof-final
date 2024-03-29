package gui.panels.oferta;

import java.awt.Dimension;
import java.awt.Font;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import gui.Gui;
import gui.components.JMultiLineLabel;
import gui.controllers.Controller;
import gui.util.PanelInterfaz;

/**
 * Esta es nuestra clase que sirve como panel de ofertas
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class PanelOferta extends PanelInterfaz {

	private static final long serialVersionUID = -5513253061378866849L;

	/**
	 * La interfaz grafica
	 */
	protected final Gui gui;

	/**
	 * El id de la oferta
	 */
	private final int idOferta;
	
	/**
	 * El label de la descripcion
	 */
	private JMultiLineLabel descripcion;
	
	/**
	 * El label del precio
	 */
	private JLabel precio;
	
	/**
	 * El label de la fianza
	 */
	private JLabel fianza;
	
	/**
	 * El label de la fecha inicial
	 */
	private JLabel fechaInicio;
	
	/**
	 * El label de hasta
	 */
	private JLabel hasta;
	
	/**
	 * El label de la fecha final
	 */
	private JLabel fechaFin;
	
	/**
	 * El label de la direccion
	 */
	private JMultiLineLabel direccion;

	/**
	 * El layout del panel
	 */
	protected SpringLayout layout;

	/**
	 * La anchura del panel
	 */
	public final static int PANEL_HEIGHT = 160;
	
	/**
	 * El ancho del panel
	 */
	public final static int PANEL_WIDTH = 750;

	/**
	 * El label de desde
	 */
	private JLabel desde;

	/**
	 * Constructor de PanelOferta
	 * @param gui interfaz grafica
	 * @param idOferta id de la oferta
	 */
	public PanelOferta(Gui gui, int idOferta) {
		this.gui = gui;
		this.idOferta = idOferta;
		initialize();
		SwingUtilities.invokeLater(() -> {
			setBorder(BorderFactory.createEtchedBorder());
			cargarDatos(idOferta);
		});
	}

	/**
	 * Funcion que define las dimensiones de la interfaz
	 */
	@Override
	protected void setDimension() {
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
	}

	/**
	 * Funcion que crea los componentes de la interfaz, y los a�ade a la interfaz grafica
	 */
	@Override
	protected void crearComponentes() {
		direccion = new JMultiLineLabel("Calle Alcala, n�7, 1B, 28850, Madrid", 200, 30, true);
		desde = new JLabel("Desde");
		fechaInicio = new JLabel("12-12-2012");
		hasta = new JLabel("Hasta o Num Meses");
		fechaFin = new JLabel("22/12/2012");
		descripcion = new JMultiLineLabel("ERROR. Descripcio", 300, 80, true);
		precio = new JLabel("300 �");
		fianza = new JLabel("+150 �");

		Font precioFont = new Font("Comic Sans", Font.PLAIN, 40);
		Font fianzaFont = new Font("Comic Sans", Font.PLAIN, 20);

		direccion.setAlignmentX(JTextArea.LEFT_ALIGNMENT);

		this.add(direccion);
		this.add(fechaInicio);
		this.add(hasta);
		this.add(fechaFin);
		this.add(desde);
		this.add(descripcion);
		this.add(precio);
		this.add(fianza);

		precio.setFont(precioFont);
		fianza.setFont(fianzaFont);
	}

	/**
	 * Esta funcion  coloca los componentes en la interfaz grafica utilizando un SpringLayout
	 */
	@Override
	protected void colocarComponentes() {
		layout = new SpringLayout();
		setLayout(layout);

		layout.putConstraint(SpringLayout.WEST, direccion, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, direccion, 10, SpringLayout.NORTH, this);

		layout.putConstraint(SpringLayout.NORTH, fechaInicio, 5, SpringLayout.SOUTH, desde);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, fechaInicio, 0, SpringLayout.HORIZONTAL_CENTER, desde);

		layout.putConstraint(SpringLayout.WEST, hasta, 40, SpringLayout.EAST, desde);
		layout.putConstraint(SpringLayout.NORTH, hasta, 0, SpringLayout.NORTH, desde);

		layout.putConstraint(SpringLayout.NORTH, fechaFin, 0, SpringLayout.NORTH, fechaInicio);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, fechaFin, 0, SpringLayout.HORIZONTAL_CENTER, hasta);

		layout.putConstraint(SpringLayout.WEST, desde, 10, SpringLayout.WEST, direccion);
		layout.putConstraint(SpringLayout.NORTH, desde, 40, SpringLayout.SOUTH, direccion);

		layout.putConstraint(SpringLayout.EAST, descripcion, -200, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, descripcion, 10, SpringLayout.NORTH, this);

		layout.putConstraint(SpringLayout.EAST, precio, -20, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, precio, 10, SpringLayout.NORTH, this);

		layout.putConstraint(SpringLayout.EAST, fianza, 0, SpringLayout.EAST, precio);
		layout.putConstraint(SpringLayout.NORTH, fianza, 10, SpringLayout.SOUTH, precio);
	}

	
	/**
	 * Carga los datos para el panel
	 * @param id id de la oferta
	 */
	public void cargarDatos(Integer id) {
		Controller c = this.gui.getController();
		this.precio.setText(String.format("%.2f �", c.ofertaGetPrecio(id)));
		this.fianza.setText(String.format("+ %.2f �", c.ofertaGetFianza(id)));
		LocalDate ini = c.ofertaGetFechaInicio(id);
		this.fechaInicio
				.setText(String.format("%02d/%02d/%04d", ini.getDayOfMonth(), ini.getMonthValue(), ini.getYear()));
		LocalDate fin = c.ofertaGetFechaFin(id);
		if (fin == null) {
			this.hasta.setText("N�mero de meses");
			this.fechaFin.setText(String.valueOf(c.ofertaGetNumMeses(id)));
		} else {
			this.hasta.setText("Hasta");
			this.fechaFin
					.setText(String.format("%02d/%02d/%04d", fin.getDayOfMonth(), fin.getMonthValue(), fin.getYear()));
		}
		this.descripcion.setText(c.ofertaGetDescripcion(id));
		this.direccion.setText(c.ofertaGetDireccion(id));
	}

	/**
	 * Devuelve el id de la oferta
	 * @return idOferta id de la oferta
	 */
	public int getIdOferta() {
		return idOferta;
	}

}

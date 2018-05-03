package gui.panels.oferta;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import gui.Gui;
import gui.components.JMultiLineLabel;
import gui.controllers.Controller;

public class PanelOferta extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5513253061378866849L;

	protected final Gui gui;

	private final int idOferta;
	private JMultiLineLabel descripcion;
	private JLabel precio;
	private JLabel fianza;
	private JLabel fechaInicio;
	private JLabel hasta;
	private JLabel fechaFin;
	private JLabel direccion;

	protected SpringLayout layout;

	private final static int PANEL_HEIGHT = 160;
	private final static int PANEL_WIDTH = 750;

	public PanelOferta(Gui gui, int idOferta) {
		this.gui = gui;
		this.idOferta = idOferta;
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		Font precioFont = new Font("Comic Sans", Font.PLAIN, 40);
		Font fianzaFont = new Font("Comic Sans", Font.PLAIN, 20);
		setBorder(BorderFactory.createEtchedBorder());
		layout = new SpringLayout();
		setLayout(layout);
		this.setBackground(Color.WHITE);
		direccion = new JMultiLineLabel("Calle Alcala, nº7, 1B, 28850, Madrid", 100);
		this.add(direccion);

		layout.putConstraint(SpringLayout.WEST, direccion, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, direccion, 40, SpringLayout.NORTH, this);

		JLabel desde = new JLabel("Desde");
		fechaInicio = new JLabel("12-12-2012");
		this.add(fechaInicio);
		layout.putConstraint(SpringLayout.NORTH, fechaInicio, 5, SpringLayout.SOUTH, desde);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, fechaInicio, 0, SpringLayout.HORIZONTAL_CENTER, desde);

		hasta = new JLabel("Hasta o Num Meses");
		this.add(hasta);
		layout.putConstraint(SpringLayout.WEST, hasta, 40, SpringLayout.EAST, desde);
		layout.putConstraint(SpringLayout.NORTH, hasta, 0, SpringLayout.NORTH, desde);

		fechaFin = new JLabel("22/12/2012");
		this.add(fechaFin);
		layout.putConstraint(SpringLayout.NORTH, fechaFin, 0, SpringLayout.NORTH, fechaInicio);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, fechaFin, 0, SpringLayout.HORIZONTAL_CENTER, hasta);

		this.add(desde);
		layout.putConstraint(SpringLayout.WEST, desde, 10, SpringLayout.WEST, direccion);
		layout.putConstraint(SpringLayout.NORTH, desde, 40, SpringLayout.SOUTH, direccion);

		descripcion = new JMultiLineLabel("ERROR. Descripciofdghjkljhgfds gsg serg erg erg sdgf sdf as fasdw qwn Vacia",
				300);
		// descripcion.setText("dsfasfdsfasf");
		this.add(descripcion);
		layout.putConstraint(SpringLayout.EAST, descripcion, -200, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, descripcion, 10, SpringLayout.NORTH, this);

		precio = new JLabel("300 €");
		precio.setFont(precioFont);
		this.add(precio);
		layout.putConstraint(SpringLayout.EAST, precio, -20, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, precio, 10, SpringLayout.NORTH, this);

		fianza = new JLabel("+150 €");
		fianza.setFont(fianzaFont);
		this.add(fianza);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, fianza, 0, SpringLayout.HORIZONTAL_CENTER, precio);
		layout.putConstraint(SpringLayout.NORTH, fianza, 10, SpringLayout.SOUTH, precio);

		this.addButtons();
		cargarDatos(idOferta);
	}

	protected void addButtons() {
	}

	protected void cargarDatos(Integer id) {
		Controller c = this.gui.getController();
		this.precio.setText(String.format("%.2f €", c.ofertaGetPrecio(id)));
		this.fianza.setText(String.format("+ %.2f €", c.ofertaGetFianza(id)));
		LocalDate ini = c.ofertaGetFechaInicio(id);
		this.fechaInicio
				.setText(String.format("%02d/%02d%04d", ini.getDayOfMonth(), ini.getMonthValue(), ini.getYear()));
		LocalDate fin = c.ofertaGetFechaFin(id);
		if (fin == null) {
			this.hasta.setText("Número de meses");
			this.fechaFin.setText(String.valueOf(c.ofertaGetNumMeses(id)));
		} else {
			this.hasta.setText("Hasta");
			this.fechaFin
					.setText(String.format("%02d/%02d%04d", fin.getDayOfMonth(), fin.getMonthValue(), fin.getYear()));
		}
		this.descripcion.setText(c.ofertaGetDescripcion(id));
		this.direccion.setText(c.ofertaGetDireccion(id));
	}

	public int getIdOferta() {
		return idOferta;
	}

}

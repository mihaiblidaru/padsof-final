package gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import gui.components.JMultiLineLabel;

public class PanelOferta extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5513253061378866849L;

	/**
	 * Create the panel.
	 */
	public PanelOferta() {
		Font precioFont = new Font("Comic Sans", Font.PLAIN, 40);
		Font fianzaFont = new Font("Comic Sans", Font.PLAIN, 20);
		Font pisoFont = new Font("Comic Sans", Font.PLAIN, 30);
		setBorder(BorderFactory.createEtchedBorder());
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		this.setBackground(Color.WHITE);
		JLabel direccion = new JLabel("Calle Alcala, nº7, 1B");
		this.add(direccion);

		springLayout.putConstraint(SpringLayout.WEST, direccion, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, direccion, 40, SpringLayout.NORTH, this);

		JLabel piso = new JLabel("Piso 1");
		this.add(piso);
		piso.setFont(pisoFont);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, piso, 0, SpringLayout.HORIZONTAL_CENTER, direccion);
		springLayout.putConstraint(SpringLayout.SOUTH, piso, 0, SpringLayout.NORTH, direccion);

		JLabel desde = new JLabel("Desde");
		JLabel fechaInicio = new JLabel("12-12-2012");
		this.add(fechaInicio);
		springLayout.putConstraint(SpringLayout.NORTH, fechaInicio, 5, SpringLayout.SOUTH, desde);
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, fechaInicio, 0, SpringLayout.VERTICAL_CENTER, desde);

		JLabel hasta = new JLabel("Hasta");
		this.add(hasta);
		springLayout.putConstraint(SpringLayout.EAST, hasta, 96, SpringLayout.WEST, desde);
		springLayout.putConstraint(SpringLayout.NORTH, hasta, 0, SpringLayout.NORTH, desde);

		JLabel fechaFin = new JLabel("22-12-2012");
		this.add(fechaFin);
		springLayout.putConstraint(SpringLayout.NORTH, fechaFin, 10, SpringLayout.SOUTH, desde);
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, fechaFin, 0, SpringLayout.VERTICAL_CENTER, hasta);

		this.add(desde);
		springLayout.putConstraint(SpringLayout.WEST, desde, 10, SpringLayout.WEST, direccion);
		springLayout.putConstraint(SpringLayout.NORTH, desde, 40, SpringLayout.SOUTH, direccion);

		JMultiLineLabel descripcion = new JMultiLineLabel(
				"hgijkb.ghbgk.s gsgkjsdg sadbgasd ere dfg sdfg dsfg  dfgs dffgsdf df sgf dg erge r ef erf  fee gs sdfksg ffksgjddd haj  gkjasggh",
				300);
		this.add(descripcion);
		springLayout.putConstraint(SpringLayout.EAST, descripcion, -200, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, descripcion, 10, SpringLayout.NORTH, this);

		JLabel descripcion2 = new JLabel(
				"<html><body style='width: 100px'>bla bla y barhgfhdf fghdghfgdh fgd fg hfgh f ffgdfgdf df  dsdfs ato blabla</body><html>");
		this.add(descripcion2);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, descripcion2, 0, SpringLayout.HORIZONTAL_CENTER,
				descripcion);
		springLayout.putConstraint(SpringLayout.NORTH, descripcion2, 10, SpringLayout.SOUTH, descripcion);

		JLabel precio = new JLabel("300 €");
		precio.setFont(precioFont);
		this.add(precio);
		springLayout.putConstraint(SpringLayout.EAST, precio, -20, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, precio, 10, SpringLayout.NORTH, this);

		JLabel fianza = new JLabel("+150 €");
		fianza.setFont(fianzaFont);
		this.add(fianza);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, fianza, 0, SpringLayout.HORIZONTAL_CENTER, precio);
		springLayout.putConstraint(SpringLayout.NORTH, fianza, 10, SpringLayout.SOUTH, precio);

		JButton editar = new JButton("Editar");
		this.add(editar);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, editar, 0, SpringLayout.HORIZONTAL_CENTER, precio);
		springLayout.putConstraint(SpringLayout.NORTH, editar, 10, SpringLayout.SOUTH, fianza);
		editar.setVisible(false);

		JButton contratar = new JButton("CONTRATAR");
		this.add(contratar);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, contratar, 0, SpringLayout.HORIZONTAL_CENTER,
				precio);
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, contratar, 0, SpringLayout.VERTICAL_CENTER, editar);

		JButton reservar = new JButton("RESERVAR");
		this.add(reservar);
		springLayout.putConstraint(SpringLayout.EAST, reservar, -20, SpringLayout.WEST, contratar);
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, reservar, 0, SpringLayout.VERTICAL_CENTER, contratar);

		JButton ver = new JButton("Ver mas detalles");
		this.add(ver);
		springLayout.putConstraint(SpringLayout.EAST, ver, -20, SpringLayout.WEST, reservar);
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, ver, 0, SpringLayout.VERTICAL_CENTER, editar);

		JButton reservado = new JButton("          Reservado          ");
		this.add(reservado);
		springLayout.putConstraint(SpringLayout.WEST, reservado, 10, SpringLayout.EAST, ver);
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, reservado, 0, SpringLayout.VERTICAL_CENTER, ver);
		reservado.setVisible(false);

		this.setPreferredSize(new Dimension(750, 160));

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

}

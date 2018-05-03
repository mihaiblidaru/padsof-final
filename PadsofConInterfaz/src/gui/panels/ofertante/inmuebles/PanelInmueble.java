package gui.panels.ofertante.inmuebles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import gui.Gui;
import gui.controllers.Controller;

public class PanelInmueble extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5513253061378866849L;

	protected final Gui gui;

	private JLabel direccion;

	protected SpringLayout layout;

	private JLabel numOfertas;

	private final static int PANEL_HEIGTH = 140;
	private final static int PANEL_WIDTH = 650;

	public PanelInmueble(Gui gui) {
		this.gui = gui;
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGTH));

		setBorder(BorderFactory.createEtchedBorder());
		layout = new SpringLayout();
		setLayout(layout);
		this.setBackground(Color.WHITE);

		JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
		separator.setPreferredSize(new Dimension(1, PANEL_HEIGTH - 15));
		separator.setForeground(new Color(200, 200, 200));
		this.add(separator);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, separator, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, separator, 0, SpringLayout.VERTICAL_CENTER, this);

		Font dirFont = new Font("Times New Roman", Font.PLAIN, 18);
		direccion = new JLabel("Calle Alcala, n�7, 1B, 28850, Madrid");
		direccion.setFont(dirFont);
		this.add(direccion);

		layout.putConstraint(SpringLayout.WEST, direccion, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, direccion, 10, SpringLayout.NORTH, this);

		numOfertas = new JLabel("5 ofertas activas");
		numOfertas.setFont(dirFont.deriveFont(Font.PLAIN, 18));
		this.add(numOfertas);

		layout.putConstraint(SpringLayout.EAST, numOfertas, -10, SpringLayout.WEST, separator);
		layout.putConstraint(SpringLayout.SOUTH, numOfertas, -15, SpringLayout.SOUTH, this);

		this.addButtons();
	}

	protected void addButtons() {
	}

	public void cargarDatos(Integer id) {
		Controller c = this.gui.getController();
		this.direccion.setText(c.inmuebleGetDireccionCompleta(id));
		this.numOfertas.setText(String.format("%d ofertas activas", c.inmuebleGetNumOfertas(id)));
	}

}

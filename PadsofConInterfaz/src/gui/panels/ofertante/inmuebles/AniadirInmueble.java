package gui.panels.ofertante.inmuebles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import gui.Gui;
import gui.components.ThinSolidScrollBarUi;
import gui.components.fx.FxButton;
import gui.components.fx.FxTextField;
import gui.controllers.Controller;
import gui.panels.Header;
import gui.panels.ofertante.caracteristicas.PanelCaracteristicas;
import gui.util.DialogFactory;
import gui.util.IconLoader;
import gui.util.Nombrable;
import gui.util.PanelInterfaz;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Esta es nuestra clase que sirve para aniadir ofertas
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class AniadirInmueble extends PanelInterfaz implements Nombrable {

	private static final long serialVersionUID = -4230661380101910555L;

	/**
	 * El nombre del panel
	 */
	public static final String NAME = "ANIADIR_INMUEBLE";

	/**
	 * La interfaz grafica
	 */
	private final Gui gui;

	/**
	 * Constructor de AniadirInmueble
	 * @param gui interfaz grafica
	 */
	public AniadirInmueble(Gui gui) {
		this.gui = gui;
		initialize();
	}

	/**
	 * Para a�adir la localidad
	 */
	private FxTextField localidadTextField;
	
	/**
	 * Para a�adir el codigo postal
	 */
	private FxTextField cpTextField;
	
	/**
	 * Para a�adir la direccion
	 */
	private FxTextField direccionTextField;
	
	/**
	 * Para a�adir la direccion, es un extra
	 */
	private FxTextField direccionExtraTextField;
	
	/**
	 * El panel de caracteristicas
	 */
	private PanelCaracteristicas pc;
	
	/**
	 * El jlabel que imprime la etiqueta
	 */
	private JLabel etiqueta;
	
	/**
	 * El jlabel que imprime la localidad
	 */
	private JLabel localidad;
	
	/**
	 * El jlabel que imprime el codigo postal
	 */
	private JLabel codigoPostal;
	
	/**
	 * El jlabel que imprime la direccion
	 */
	private JLabel direccionLabel;
	
	/**
	 * El  segundo jlabel que imprime la direccion
	 */
	private JLabel direccionLabel2;
	
	/**
	 * El jlabel que imprime simbolo de advertencia
	 */
	private JLabel warningIcon;
	
	/**
	 * El separador entre inmuebles
	 */
	private JSeparator separator;
	
	/**
	 * El boton para confirmar
	 */
	private FxButton confirmar;
	
	/**
	 * El jlabel que imprime la advertencia
	 */
	private JLabel alarm;
	
	/**
	 * El jlabel que imprime las caracteristicas
	 */
	private JPanel caracteristicas;

	/**
	 * Funcion que define las dimensiones de la interfaz
	 */
	@Override
	protected void setDimension() {
		this.setPreferredSize(new Dimension(Gui.FRAME_WIDTH, Gui.FRAME_HEIGHT - 20));
	}

	/**
	 * Funcion que crea los componentes de la interfaz, y los a�ade a la interfaz grafica
	 */
	@Override
	protected void crearComponentes() {
		Font font = new Font("Comic Sans", Font.PLAIN, 40);
		etiqueta = new JLabel("A�adir Vivienda");
		localidad = new JLabel("Localidad:");
		codigoPostal = new JLabel("Codigo Postal:");
		this.cpTextField = new FxTextField(187, 25, "Ej: 28850");
		this.localidadTextField = new FxTextField(210, 25, "Ej: Madrid");
		this.direccionTextField = new FxTextField(210, 25, "Ej: Calle Alcala, 30");
		this.direccionExtraTextField = new FxTextField(172, 25, "Ej: Escalera A, Portal 15, 1B");
		direccionLabel = new JLabel("Direccion:");
		direccionLabel2 = new JLabel("Direccion(extra):");
		warningIcon = new JLabel(IconLoader.load("res/img/fa-warning.png", 10, 10));
		separator = new JSeparator(SwingConstants.VERTICAL);
		confirmar = new FxButton(200, 60, "Confirmar");
		alarm = new JLabel("No puedes a�adir el mismo inmueble dos veces");

		etiqueta.setFont(font);

		this.add(etiqueta);
		this.add(localidad);
		this.add(this.localidadTextField);
		this.add(codigoPostal);
		this.add(this.cpTextField);
		this.add(direccionLabel);
		this.add(this.direccionTextField);
		this.add(direccionLabel2);
		this.add(this.direccionExtraTextField);
		this.add(confirmar);
		this.add(alarm);

		this.add(warningIcon);
		this.add(separator);

		cpTextField.setOnlyInteger();

	}

	/**
	 * Esta funcion  coloca los componentes en la interfaz grafica utilizando un SpringLayout
	 */
	@Override
	protected void colocarComponentes() {
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);

		layout.putConstraint(SpringLayout.NORTH, etiqueta, 70, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, etiqueta, 0, SpringLayout.HORIZONTAL_CENTER, this);

		layout.putConstraint(SpringLayout.NORTH, localidad, 25, SpringLayout.SOUTH, etiqueta);
		layout.putConstraint(SpringLayout.WEST, localidad, -160, SpringLayout.WEST, etiqueta);

		layout.putConstraint(SpringLayout.VERTICAL_CENTER, this.localidadTextField, 0, SpringLayout.VERTICAL_CENTER,
				localidad);
		layout.putConstraint(SpringLayout.WEST, this.localidadTextField, 10, SpringLayout.EAST, localidad);

		layout.putConstraint(SpringLayout.NORTH, codigoPostal, 15, SpringLayout.SOUTH, localidad);
		layout.putConstraint(SpringLayout.WEST, codigoPostal, 0, SpringLayout.WEST, localidad);

		layout.putConstraint(SpringLayout.VERTICAL_CENTER, this.cpTextField, 0, SpringLayout.VERTICAL_CENTER,
				codigoPostal);
		layout.putConstraint(SpringLayout.WEST, this.cpTextField, 10, SpringLayout.EAST, codigoPostal);
		layout.putConstraint(SpringLayout.EAST, this.cpTextField, 0, SpringLayout.EAST, this.localidadTextField);

		layout.putConstraint(SpringLayout.NORTH, direccionLabel, 15, SpringLayout.SOUTH, codigoPostal);
		layout.putConstraint(SpringLayout.WEST, direccionLabel, 0, SpringLayout.WEST, localidad);

		layout.putConstraint(SpringLayout.VERTICAL_CENTER, this.direccionTextField, 0, SpringLayout.VERTICAL_CENTER,
				direccionLabel);
		layout.putConstraint(SpringLayout.WEST, this.direccionTextField, 10, SpringLayout.EAST, direccionLabel);

		layout.putConstraint(SpringLayout.NORTH, direccionLabel2, 15, SpringLayout.SOUTH, direccionLabel);
		layout.putConstraint(SpringLayout.WEST, direccionLabel2, 0, SpringLayout.WEST, direccionLabel);

		layout.putConstraint(SpringLayout.VERTICAL_CENTER, this.direccionExtraTextField, 0,
				SpringLayout.VERTICAL_CENTER, direccionLabel2);
		layout.putConstraint(SpringLayout.WEST, this.direccionExtraTextField, 10, SpringLayout.EAST, direccionLabel2);

		layout.putConstraint(SpringLayout.NORTH, confirmar, 30, SpringLayout.SOUTH, direccionExtraTextField);
		layout.putConstraint(SpringLayout.WEST, confirmar, 40, SpringLayout.WEST, direccionLabel2);

		layout.putConstraint(SpringLayout.WEST, alarm, -22, SpringLayout.WEST, confirmar);
		layout.putConstraint(SpringLayout.SOUTH, alarm, -5, SpringLayout.NORTH, confirmar);

		layout.putConstraint(SpringLayout.VERTICAL_CENTER, warningIcon, 0, SpringLayout.VERTICAL_CENTER, alarm);
		layout.putConstraint(SpringLayout.EAST, warningIcon, -5, SpringLayout.WEST, alarm);

		layout.putConstraint(SpringLayout.WEST, separator, 15, SpringLayout.EAST, this.localidadTextField);
		layout.putConstraint(SpringLayout.NORTH, separator, -5, SpringLayout.NORTH, this.localidadTextField);
		separator.setPreferredSize(new Dimension(2, 220));
		separator.setForeground(new Color(200, 200, 200));
		caracteristicas = buildPanelCaracteristicas();
		this.add(caracteristicas);
		layout.putConstraint(SpringLayout.WEST, caracteristicas, 0, SpringLayout.EAST, separator);
		layout.putConstraint(SpringLayout.NORTH, caracteristicas, 5, SpringLayout.NORTH, separator);
	}

	/**
	 * Funcion que consigue los datos rellenados, avisa de posibles errores
	 */
	private EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					Controller c = gui.getController();
					String localidad = localidadTextField.getText();
					String cp = cpTextField.getText();
					String direccion = direccionTextField.getText();
					String direccionExtra = direccionExtraTextField.getText();
					if (localidad.isEmpty()) {
						JOptionPane.showMessageDialog(new JPanel(), "El campo localidad no puede estar vacio", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					if (cp.isEmpty()) {
						JOptionPane.showMessageDialog(new JPanel(), "El campo codigo postal no puede estar vacio",
								"Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					if (direccion.isEmpty()) {
						DialogFactory.emptyFieldError("direccion");
						return;
					}
					direccion = direccion.trim() + " " + direccionExtra.trim();

					List<String> claves = pc.getClaves();
					List<String> valores = pc.getValores();
					try {
						c.addInmueble(localidad, Integer.parseInt(cp), direccion, claves, valores);
						gui.showOnly(Header.NAME, MisInmuebles.NAME);
					} catch (NumberFormatException e) {

					}

				}
			});

		}
	};

	/**
	 * Esta funcion construye el panel de caracteristicas
	 * @return panel creado
	 */
	private JPanel buildPanelCaracteristicas() {
		SpringLayout layout = new SpringLayout();
		JPanel panel = new JPanel(layout);
		panel.setPreferredSize(new Dimension(320, 300));
		panel.setBackground(Color.WHITE);
		Font font = new Font("Open Sans", Font.PLAIN, 15);
		JLabel caracteristicas = new JLabel("Caracteristicas");
		caracteristicas.setFont(font);
		panel.add(caracteristicas);
		layout.putConstraint(SpringLayout.WEST, caracteristicas, 10, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, caracteristicas, 3, SpringLayout.NORTH, panel);

		FxButton addCaracteristica = new FxButton(25, 25, "");
		addCaracteristica.setGraphics("res/img/fa-plus-circle.png", 15, 15);
		panel.add(addCaracteristica);

		layout.putConstraint(SpringLayout.EAST, addCaracteristica, -20, SpringLayout.EAST, panel);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, addCaracteristica, 0, SpringLayout.VERTICAL_CENTER,
				caracteristicas);
		JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);

		panel.add(separator);
		separator.setPreferredSize(new Dimension(290, 1));
		separator.setForeground(new Color(200, 200, 200));
		layout.putConstraint(SpringLayout.NORTH, separator, 3, SpringLayout.SOUTH, caracteristicas);
		layout.putConstraint(SpringLayout.WEST, separator, 0, SpringLayout.WEST, caracteristicas);

		pc = new PanelCaracteristicas();
		JScrollPane scrollPanel = new JScrollPane(pc, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		scrollPanel.setPreferredSize(new Dimension(320, 200));
		scrollPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel.add(scrollPanel);
		scrollPanel.getVerticalScrollBar().setUI(new ThinSolidScrollBarUi(7));
		scrollPanel.getVerticalScrollBar().setBackground(Color.WHITE);

		layout.putConstraint(SpringLayout.NORTH, scrollPanel, 2, SpringLayout.SOUTH, separator);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, scrollPanel, 0, SpringLayout.HORIZONTAL_CENTER, panel);
		pc.addCaracteristica();
		pc.addCaracteristica();
		pc.addCaracteristica();
		pc.addCaracteristica();
		pc.addCaracteristica();

		addCaracteristica.setOnAction((event) -> pc.addCaracteristica());
		return panel;
	}

	/**
	 * Esta funcion registra los eventos que ocurren en la interfaz, pulsar el boton
	 */
	@Override
	protected void registrarEventos() {
		confirmar.setOnAction(handler);
	}

}

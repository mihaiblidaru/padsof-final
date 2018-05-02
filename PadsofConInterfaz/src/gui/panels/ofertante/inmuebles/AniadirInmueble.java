package gui.panels.ofertante.inmuebles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import app.clases.inmueble.InmuebleDuplicadoException;
import app.clases.users.UsuarioNoPermisoException;
import gui.Gui;
import gui.components.ThinSolidScrollBarUi;
import gui.components.fx.FxButton;
import gui.components.fx.FxTextField;
import gui.controllers.Controller;
import gui.panels.Header;
import gui.panels.ofertante.caracteristicas.PanelCaracteristicas;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class AniadirInmueble extends JPanel {

	private static final long serialVersionUID = -4230661380101910555L;

	public static final String NAME = "ANIADIR_INMUEBLE";

	private final Gui gui;

	private static AniadirInmueble instance = null;

	public static AniadirInmueble getInstance(Gui gui) {
		if (instance == null) {
			return (instance = new AniadirInmueble(gui));
		} else {
			return instance;
		}
	}

	private AniadirInmueble(Gui gui) {
		this.gui = gui;
		this.setName(NAME);
		initialize();
	}

	private FxTextField localidadTextField;
	private FxTextField cpTextField;
	private FxTextField direccionTextField;
	private FxTextField direccionExtraTextField;

	private void initialize() {
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		this.setPreferredSize(new Dimension(Gui.FRAME_WIDTH, Gui.FRAME_HEIGHT - 20));
		this.setBackground(Color.WHITE);

		Font font = new Font("Comic Sans", Font.PLAIN, 40);

		JLabel etiqueta = new JLabel("Añadir Vivienda");
		layout.putConstraint(SpringLayout.NORTH, etiqueta, 70, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, etiqueta, 0, SpringLayout.HORIZONTAL_CENTER, this);
		etiqueta.setFont(font);
		this.add(etiqueta);

		JLabel localidad = new JLabel("Localidad:");
		layout.putConstraint(SpringLayout.NORTH, localidad, 25, SpringLayout.SOUTH, etiqueta);
		layout.putConstraint(SpringLayout.WEST, localidad, -160, SpringLayout.WEST, etiqueta);
		this.add(localidad);

		this.localidadTextField = new FxTextField(210, 25, "Ej: Madrid");
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, this.localidadTextField, 0, SpringLayout.VERTICAL_CENTER,
				localidad);
		layout.putConstraint(SpringLayout.WEST, this.localidadTextField, 10, SpringLayout.EAST, localidad);
		this.add(this.localidadTextField);

		JLabel codigoPostal = new JLabel("Codigo Postal:");
		layout.putConstraint(SpringLayout.NORTH, codigoPostal, 15, SpringLayout.SOUTH, localidad);
		layout.putConstraint(SpringLayout.WEST, codigoPostal, 0, SpringLayout.WEST, localidad);
		this.add(codigoPostal);

		this.cpTextField = new FxTextField(187, 25, "Ej: 28850");
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, this.cpTextField, 0, SpringLayout.VERTICAL_CENTER,
				codigoPostal);
		layout.putConstraint(SpringLayout.WEST, this.cpTextField, 10, SpringLayout.EAST, codigoPostal);
		layout.putConstraint(SpringLayout.EAST, this.cpTextField, 0, SpringLayout.EAST, this.localidadTextField);
		this.add(this.cpTextField);

		JLabel direccionLabel = new JLabel("Direccion:");
		layout.putConstraint(SpringLayout.NORTH, direccionLabel, 15, SpringLayout.SOUTH, codigoPostal);
		layout.putConstraint(SpringLayout.WEST, direccionLabel, 0, SpringLayout.WEST, localidad);
		this.add(direccionLabel);

		this.direccionTextField = new FxTextField(210, 25, "Ej: Calle Alcala, 30");
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, this.direccionTextField, 0, SpringLayout.VERTICAL_CENTER,
				direccionLabel);
		layout.putConstraint(SpringLayout.WEST, this.direccionTextField, 10, SpringLayout.EAST, direccionLabel);
		this.add(this.direccionTextField);

		JLabel direccionLabel2 = new JLabel("Direccion(extra):");
		layout.putConstraint(SpringLayout.NORTH, direccionLabel2, 15, SpringLayout.SOUTH, direccionLabel);
		layout.putConstraint(SpringLayout.WEST, direccionLabel2, 0, SpringLayout.WEST, direccionLabel);
		this.add(direccionLabel2);

		this.direccionExtraTextField = new FxTextField(172, 25, "Ej: Escalera A, Portal 15, 1B");
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, this.direccionExtraTextField, 0,
				SpringLayout.VERTICAL_CENTER, direccionLabel2);
		layout.putConstraint(SpringLayout.WEST, this.direccionExtraTextField, 10, SpringLayout.EAST, direccionLabel2);
		this.add(this.direccionExtraTextField);

		FxButton confirmar = new FxButton(200, 60, "Confirmar");
		layout.putConstraint(SpringLayout.NORTH, confirmar, 30, SpringLayout.SOUTH, direccionExtraTextField);
		layout.putConstraint(SpringLayout.WEST, confirmar, 40, SpringLayout.WEST, direccionLabel2);
		this.add(confirmar);

		JLabel alarm = new JLabel("No puedes añadir el mismo inmueble dos veces");
		layout.putConstraint(SpringLayout.WEST, alarm, -22, SpringLayout.WEST, confirmar);
		layout.putConstraint(SpringLayout.SOUTH, alarm, -5, SpringLayout.NORTH, confirmar);
		this.add(alarm);

		ImageIcon image = null;
		try {
			image = new ImageIcon(
					ImageIO.read(new File("res/img/fa-warning.png")).getScaledInstance(10, 10, Image.SCALE_SMOOTH));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		JLabel warningIcon = new JLabel(image);

		this.add(warningIcon);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, warningIcon, 0, SpringLayout.VERTICAL_CENTER, alarm);
		layout.putConstraint(SpringLayout.EAST, warningIcon, -5, SpringLayout.WEST, alarm);

		JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
		this.add(separator);
		layout.putConstraint(SpringLayout.WEST, separator, 15, SpringLayout.EAST, this.localidadTextField);
		layout.putConstraint(SpringLayout.NORTH, separator, -5, SpringLayout.NORTH, this.localidadTextField);
		separator.setPreferredSize(new Dimension(2, 220));
		separator.setForeground(new Color(200, 200, 200));

		JPanel caracteristicas = buildPanelCaracteristicas();

		this.add(caracteristicas);

		layout.putConstraint(SpringLayout.WEST, caracteristicas, 0, SpringLayout.EAST, separator);
		layout.putConstraint(SpringLayout.NORTH, caracteristicas, 5, SpringLayout.NORTH, separator);
		cpTextField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("^\\d*\\d") && !newValue.isEmpty()) {
					cpTextField.setText(oldValue);
				}
			}
		});

		confirmar.setOnAction(handler);
	}

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
						JOptionPane.showMessageDialog(new JPanel(), "El campo direccion no puede estar vacio", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					direccion = direccion.trim() + " " + direccionExtra.trim();

					List<String> claves = pc.getClaves();
					List<String> valores = pc.getValores();
					try {
						c.addInmueble(localidad, Integer.parseInt(cp), direccion, claves, valores);
						gui.showOnly(Header.NAME, MisInmuebles.NAME);
					} catch (NumberFormatException | UsuarioNoPermisoException e) {

					} catch (InmuebleDuplicadoException e) {
						JOptionPane.showMessageDialog(new JPanel(), "Ya tienes un inmueble con estos datos", "Error",
								JOptionPane.ERROR_MESSAGE);
					}

				}
			});

		}
	};

	private PanelCaracteristicas pc;;

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

}

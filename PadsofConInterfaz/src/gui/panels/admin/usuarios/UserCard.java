package gui.panels.admin.usuarios;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.Gui;
import gui.controllers.Controller;
import gui.dialogs.EditUserDialog;
import gui.panels.admin.AdminView;
import gui.util.IconLoader;
import gui.util.PanelInterfaz;

public class UserCard extends PanelInterfaz {

	private static final long serialVersionUID = -222109993973543359L;
	public static final int PANEL_WIDTH = 200;
	public static final int PANEL_HEIGHT = 160;

	private int userID;
	private String tipo;
	private JPanel filaTarjeta;
	private JPanel filaTelefono;
	private JButton editar;
	private Gui gui;
	private JLabel labelTipo;
	private JLabel userName;
	private JLabel labelTarjeta;
	private JLabel labelTelefono;

	public UserCard(Gui gui, Integer userID, String tipo) {
		this.userID = userID;
		this.tipo = tipo;
		this.gui = gui;
		initialize();
		this.setBorder(BorderFactory.createEtchedBorder());
		cargarDatos();
	}

	public Integer getUserId() {
		return userID;
	}

	/**
	 * Define la dimension de la interfaz grafica
	 */
	protected void setDimension() {
		this.setPreferredSize(new Dimension(200, 160));
	}

	/**
	 * Crea los componentes de una interfaz grafica
	 */
	protected void crearComponentes() {
		userName = new JLabel("12345670X");
		labelTipo = new JLabel("Ofertante");
		filaTelefono = new JPanel();
		filaTarjeta = new JPanel();
		editar = new JButton("Editar");
		editar.setPreferredSize(new Dimension(80, editar.getPreferredSize().height));

		userName.setAlignmentX(Component.LEFT_ALIGNMENT);
		labelTipo.setAlignmentX(Component.LEFT_ALIGNMENT);
		filaTelefono.setAlignmentX(Component.LEFT_ALIGNMENT);
		filaTarjeta.setAlignmentX(Component.LEFT_ALIGNMENT);

		this.add(Box.createRigidArea(new Dimension(1, 15)));
		this.add(userName);
		this.add(Box.createRigidArea(new Dimension(10, 3)));
		this.add(labelTipo);
		this.add(Box.createRigidArea(new Dimension(10, 10)));
		this.add(filaTelefono);
		this.add(Box.createRigidArea(new Dimension(10, 10)));
		this.add(filaTarjeta);
		this.add(Box.createRigidArea(new Dimension(10, 10)));
		this.add(editar);

		JLabel imgTel = new JLabel(IconLoader.load("res/img/fa-phone-square.png", 20, 20));
		labelTelefono = new JLabel("603 296 012");

		JLabel imgTarjeta = new JLabel(IconLoader.load("res/img/fa-cc-visa.png", 20, 20));
		labelTarjeta = new JLabel("1234132412341324");

		filaTelefono.add(imgTel);
		filaTelefono.add(Box.createRigidArea(new Dimension(5, 1)));
		filaTelefono.add(labelTelefono);

		filaTarjeta.add(imgTarjeta);
		filaTarjeta.add(Box.createRigidArea(new Dimension(5, 1)));
		filaTarjeta.add(labelTarjeta);

	}

	private void cargarDatos() {
		Controller c = gui.getController();
		if (tipo.equalsIgnoreCase("o")) {
			labelTipo.setText("Ofertante");
		} else if (tipo.equalsIgnoreCase("d")) {
			labelTipo.setText("Demandante");
		} else if (tipo.equalsIgnoreCase("d")) {
			labelTipo.setText("Ofertante-Demandante");
		}

		/*userName.setText(c.usuarioGetNombre());
		labelTelefono.setText(c.usuarioGetTelefono());
		labelTarjeta.setText(c.usuarioGetTarjeta());*/
	}

	public String getTarjeta() {
		return labelTarjeta.getText();
	}

	public String getTelefono() {
		return labelTelefono.getText();
	}

	public String getNombre() {
		return userName.getText();
	}

	/**
	 * Coloca los componentes de la interfaz grafica
	 */
	protected void colocarComponentes() {
		BoxLayout l = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(l);
		filaTelefono.setLayout(new BoxLayout(filaTelefono, BoxLayout.X_AXIS));
		filaTarjeta.setLayout(new BoxLayout(filaTarjeta, BoxLayout.X_AXIS));
	};

	/**
	 * Registra eventos que ocurran en la interfaz grafica
	 */
	protected void registrarEventos() {
		this.editar.addActionListener(e -> {
			EditUserDialog dialog = new EditUserDialog(gui, userID, this);
			dialog.setVisible(true);
			if (dialog.getValue()) {
				AdminView av = (AdminView) gui.getComponent(AdminView.NAME);
				av.getUsuariosBloqueadosTab().cargarUsuarios();
			}
		});
	}

}

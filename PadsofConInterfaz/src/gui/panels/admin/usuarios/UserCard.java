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

/**
 * Esta es nuestra clase que sirve como tarjeta de usuario
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class UserCard extends PanelInterfaz {

	private static final long serialVersionUID = -222109993973543359L;
	
	/**
	 * Ancho del panel
	 */
	public static final int PANEL_WIDTH = 200;
	
	/**
	 * Altura del panel
	 */
	public static final int PANEL_HEIGHT = 160;

	/**
	 * Id del usuario
	 */
	private int userID;
	
	/**
	 * Tipo del usuario
	 */
	private String tipo;
	
	/**
	 * Panel de la tarjeta
	 */
	private JPanel filaTarjeta;
	
	/**
	 * Boton para editar
	 */
	private JButton editar;
	
	/**
	 * Interfaz grafica
	 */
	private Gui gui;
	
	/**
	 * Label que imprime el tipo
	 */
	private JLabel labelTipo;
	
	/**
	 * Label que imprime el nombre del usuario
	 */
	private JLabel userName;
	
	/**
	 * Label que imprime la tarjeta
	 */
	private JLabel labelTarjeta;

	/**
	 * Constructor de UserCard
	 * @param gui interfaz grafica
	 * @param userID id de usuario
	 * @param tipo tipo del usuario
	 */
	public UserCard(Gui gui, Integer userID, String tipo) {
		this.userID = userID;
		this.tipo = tipo;
		this.gui = gui;
		initialize();
		this.setBorder(BorderFactory.createEtchedBorder());
		cargarDatos();
	}

	/**
	 * Devuelve el id de usuario
	 * @return userID id de usuario
	 */
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
		filaTarjeta = new JPanel();
		editar = new JButton("Editar");
		editar.setPreferredSize(new Dimension(80, editar.getPreferredSize().height));

		userName.setAlignmentX(Component.LEFT_ALIGNMENT);
		labelTipo.setAlignmentX(Component.LEFT_ALIGNMENT);
		filaTarjeta.setAlignmentX(Component.LEFT_ALIGNMENT);

		this.add(Box.createRigidArea(new Dimension(1, 15)));
		this.add(userName);
		this.add(Box.createRigidArea(new Dimension(10, 3)));
		this.add(labelTipo);
		this.add(Box.createRigidArea(new Dimension(10, 10)));
		this.add(filaTarjeta);
		this.add(Box.createRigidArea(new Dimension(10, 10)));
		this.add(editar);

		JLabel imgTarjeta = new JLabel(IconLoader.load("res/img/fa-cc-visa.png", 20, 20));
		labelTarjeta = new JLabel("1234132412341324");

		filaTarjeta.add(imgTarjeta);
		filaTarjeta.add(Box.createRigidArea(new Dimension(5, 1)));
		filaTarjeta.add(labelTarjeta);

	}

	/**
	 * Carga los datos de la tarjeta de usuario
	 */
	private void cargarDatos() {
		Controller c = gui.getController();
		if (tipo.equalsIgnoreCase("o")) {
			labelTipo.setText("Ofertante");
		} else if (tipo.equalsIgnoreCase("d")) {
			labelTipo.setText("Demandante");
		} else if (tipo.equalsIgnoreCase("od")) {
			labelTipo.setText("Ofertante-Demandante");
		}

		userName.setText(c.usuarioGetNombre(userID));
		labelTarjeta.setText(c.usuarioGetTarjeta(userID));
	}

	/**
	 * Devuelve la tarjeta del usuario
	 * @return tarjeta del usuario
	 */
	public String getTarjeta() {
		return labelTarjeta.getText();
	}

	/**
	 * Devuelve el nombre del usuario
	 * @return nombre del usuario
	 */
	public String getNombre() {
		return userName.getText();
	}

	/**
	 * Devuelve el tipo del usuario
	 * @return tipo del usuario
	 */
	public String getTipo() {
		return labelTipo.getText();
	}

	/**
	 * Coloca los componentes de la interfaz grafica
	 */
	protected void colocarComponentes() {
		BoxLayout l = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(l);
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

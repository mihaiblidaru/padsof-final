package gui.panels.admin.usuarios;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import gui.Gui;
import gui.controllers.Controller;
import gui.util.DialogFactory;

/**
 * Esta es nuestra clase que sirve como contenedor interno de usuarios
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class ContenedorUsuarios extends JPanel {

	private static final long serialVersionUID = -2138438771740403776L;

	/**
	 * El layout
	 */
	private FlowLayout layout;

	/**
	 * La separacion entre usuarios
	 */
	private static final int SEPARACION = 20;
	/**
	 * Interfaz grafica
	 */
	private Gui gui;

	/**
	 * Mapa de usuarios
	 */
	Map<Integer, UserCard> usuarios = new HashMap<>();

	/**
	 * Constructor de ContenedorUsuario
	 * @param gui interfaz grafica
	 */
	public ContenedorUsuarios(Gui gui) {
		this.gui = gui;
		layout = new FlowLayout(FlowLayout.LEFT);
		this.setLayout(layout);
		this.setPreferredSize(new Dimension(700, 500));
		layout.setVgap(SEPARACION);
		layout.setHgap(SEPARACION);
	}

	/**
	 * Añade una tarjeta usuario
	 * @param p tarjeta de usuario a añadir
	 * @return tarjeta de usuario añadida
	 */
	public Component addUsuario(UserCard p) {
		this.add(p);
		usuarios.put(p.getUserId(), p);

		recalculateSize();
		return p;
	}

	/**
	 * Quita la tarjeta de usuario de un usuario
	 * @param id id del usuario a quitar
	 */
	public void removeUsuario(Integer id) {
		UserCard c = usuarios.remove(id);

		this.remove(c);
	}

	/**
	 * Recalcula el tamaño de la interfaz
	 */
	private void recalculateSize() {
		int rowCount = (int) Math.ceil(this.getComponents().length / 3.0);
		int height = (rowCount * (UserCard.PANEL_HEIGHT + this.layout.getVgap())) + this.layout.getVgap();
		Dimension original = this.getPreferredSize();
		original.height = height;
		this.setPreferredSize(original);
	}

	/**
	 * Carga los usuarios al contenedor
	 */
	public void cargarUsuarios() {
		Controller c = gui.getController();
		Map<Integer, String> users = c.adminGetUsuariosProblemaPagos();
		this.removeAll();
		this.usuarios.clear();
		for (Integer i : users.keySet()) {
			UserCard card = new UserCard(gui, i, users.get(i));
			this.addUsuario(card);
		}

		this.revalidate();
		this.repaint();
	}

	/**
	 * Carga un usuario dado el nombre
	 * @param nombre el nombre del usuario
	 */
	public void cargarUsuario(String nombre) {
		Controller c = gui.getController();
		Map<Integer, String> users = c.adminGetUsuarioProblemaPagos(nombre);
		if (users.isEmpty()) {
			DialogFactory.simpleErrorMessage("Ningun usuario encontrado");
		} else {
			this.removeAll();
			this.usuarios.clear();
			for (Integer i : users.keySet()) {
				UserCard card = new UserCard(gui, i, users.get(i));
				this.addUsuario(card);
			}
			this.revalidate();
			this.repaint();
		}
	}
}

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

public class ContenedorUsuarios extends JPanel {

	private static final long serialVersionUID = -2138438771740403776L;

	private FlowLayout layout;

	private static final int SEPARACION = 20;
	private Gui gui;

	Map<Integer, UserCard> usuarios = new HashMap<>();

	public ContenedorUsuarios(Gui gui) {
		this.gui = gui;
		layout = new FlowLayout(FlowLayout.LEFT);
		this.setLayout(layout);
		this.setPreferredSize(new Dimension(700, 500));
		layout.setVgap(SEPARACION);
		layout.setHgap(SEPARACION);
	}

	public Component addUsuario(UserCard p) {
		this.add(p);
		usuarios.put(p.getUserId(), p);

		recalculateSize();
		return p;
	}

	public void removeUsuario(Integer id) {
		UserCard c = usuarios.remove(id);

		this.remove(c);
	}

	private void recalculateSize() {
		int rowCount = (int) Math.ceil(this.getComponents().length / 3.0);
		int height = (rowCount * (UserCard.PANEL_HEIGHT + this.layout.getVgap())) + this.layout.getVgap();
		Dimension original = this.getPreferredSize();
		original.height = height;
		this.setPreferredSize(original);
	}

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

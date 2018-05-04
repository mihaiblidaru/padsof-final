package gui.panels.admin.usuarios;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import gui.Gui;
import gui.panels.admin.UserCard;

public class ContenedorUsuarios extends JPanel {

	private static final long serialVersionUID = -2138438771740403776L;

	private FlowLayout layout;

	private static final int SEPARACION = 20;

	Map<Integer, UserCard> usuarios = new HashMap<>();

	public ContenedorUsuarios(Gui gui) {
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

	public void clearOfertas() {
		this.removeAll();
	}
}

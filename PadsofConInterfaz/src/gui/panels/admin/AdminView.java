package gui.panels.admin;

import java.awt.CardLayout;

import javax.swing.JPanel;

import gui.Gui;
import gui.panels.admin.ofertas.OfertasPendientes;
import gui.panels.admin.usuarios.UsuariosBloqueados;
import gui.util.Nombrable;

public class AdminView extends JPanel implements Nombrable {

	private static final long serialVersionUID = 6946331622568374988L;
	public static String NAME = "ADMIN_PANEL";
	private final Gui gui;
	private CardLayout layout;
	private OfertasPendientes ofertasPendientes;

	public AdminView(Gui gui) {
		layout = new CardLayout();
		this.setLayout(layout);

		this.gui = gui;
		ofertasPendientes = new OfertasPendientes(gui);
		this.add(new ControlPanel(), ControlPanel.NAME);
		this.add(ofertasPendientes, OfertasPendientes.NAME);
		this.add(new UsuariosBloqueados(gui), UsuariosBloqueados.NAME);
		layout.first(this);

	}

	public OfertasPendientes getOfertasPendientesTab() {
		return ofertasPendientes;
	}

	public void show(String name) {
		if (name == OfertasPendientes.NAME) {
			ofertasPendientes.cargarOfertas();
		}

		layout.show(this, name);
	}

}

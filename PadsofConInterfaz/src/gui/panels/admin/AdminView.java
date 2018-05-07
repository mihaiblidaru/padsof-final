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
	private CardLayout layout;
	private OfertasPendientes ofertasPendientes;
	private UsuariosBloqueados usuariosBloqueados;

	public AdminView(Gui gui) {
		layout = new CardLayout();
		this.setLayout(layout);

		ofertasPendientes = new OfertasPendientes(gui);
		usuariosBloqueados = new UsuariosBloqueados(gui);
		this.add(ofertasPendientes, OfertasPendientes.NAME);
		this.add(usuariosBloqueados, UsuariosBloqueados.NAME);
		layout.first(this);

	}

	public void show(String name) {
		if (name == OfertasPendientes.NAME) {
			ofertasPendientes.cargarOfertas();
		} else if (name == UsuariosBloqueados.NAME) {
			usuariosBloqueados.cargarUsuarios();
		}

		layout.show(this, name);
	}

	public UsuariosBloqueados getUsuariosBloqueadosTab() {
		return usuariosBloqueados;
	}

	public OfertasPendientes getOfertasPendientesTab() {
		return ofertasPendientes;
	}

}

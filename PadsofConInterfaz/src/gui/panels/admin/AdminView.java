package gui.panels.admin;

import java.awt.CardLayout;

import javax.swing.JPanel;

import gui.Gui;
import gui.panels.admin.ofertas.OfertasPendientes;
import gui.panels.admin.usuarios.UsuariosBloqueados;
import gui.util.Nombrable;

/**
 * Esta es nuestra clase que sirve para trabajar con el panel del admin
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class AdminView extends JPanel implements Nombrable {

	private static final long serialVersionUID = 6946331622568374988L;
	
	/**
	 * Nombre del panel
	 */
	public static String NAME = "ADMIN_PANEL";
	
	/**
	 * El layout
	 */
	private CardLayout layout;
	
	/**
	 * Las ofertas pendientes
	 */
	private OfertasPendientes ofertasPendientes;
	
	/**
	 * Los usuarios bloqueados
	 */
	private UsuariosBloqueados usuariosBloqueados;

	/**
	 * Constructor de AdminView
	 * @param gui interfaz grafica
	 */
	public AdminView(Gui gui) {
		layout = new CardLayout();
		this.setLayout(layout);

		ofertasPendientes = new OfertasPendientes(gui);
		usuariosBloqueados = new UsuariosBloqueados(gui);
		this.add(ofertasPendientes, OfertasPendientes.NAME);
		this.add(usuariosBloqueados, UsuariosBloqueados.NAME);
		layout.first(this);

	}

	/**
	 * Muestra los usuarios o las ofertas
	 * @param name nombre a mostrar
	 */
	public void show(String name) {
		if (name == OfertasPendientes.NAME) {
			ofertasPendientes.cargarOfertas();
		} else if (name == UsuariosBloqueados.NAME) {
			usuariosBloqueados.cargarUsuarios();
		}

		layout.show(this, name);
	}

	/**
	 * Devuelve los usuarios bloqueados
	 * @return usuariosBloqueados los usuarios bloqueados
	 */
	public UsuariosBloqueados getUsuariosBloqueadosTab() {
		return usuariosBloqueados;
	}

	/**
	 * Devuelve las ofertas pendientes
	 * @return ofertasPendientes los ofertasPendientes
	 */
	public OfertasPendientes getOfertasPendientesTab() {
		return ofertasPendientes;
	}

}

package gui.panels.admin;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import javax.swing.JTabbedPane;

import javax.swing.border.EmptyBorder;


import gui.Gui;


public class AdminView extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6946331622568374988L;
	public static String NAME = "ADMIN_PANEL";
	private final Gui gui;
	public AdminView(Gui gui) {
		super(JTabbedPane.TOP);
		this.setName(NAME);
		this.gui = gui;
		Icon icon = new ImageIcon();
		this.insertTab("Panel de control", icon, new ControlPanel(), "Panel de control", 0);
		this.insertTab("Ofertas", icon, new OfertasTab(), "Panel de control", 1);
		this.insertTab("Usuarios", icon, new UsuariosTab(), "Panel de control", 2);
		this.setBorder(new EmptyBorder(0, 0, 0, 0));
		
	}
	
	
	
}

package gui.panels.admin.ofertas;

import java.awt.Color;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import gui.Gui;
import gui.components.ThinSolidScrollBarUi;
import gui.controllers.Controller;
import gui.panels.oferta.PanelOfertaAdmin;
import gui.util.DialogFactory;
import gui.util.Nombrable;

public class OfertasPendientes extends JLayeredPane implements Nombrable {

	private static final long serialVersionUID = -8320036169616362237L;

	public static final String NAME = "OFERTAS_PENDIENTES";

	private Gui gui;

	private ContenedorOfertasPendientes coi;

	private static OfertasPendientes instance = null;

	public static OfertasPendientes getInstance(Gui gui) {
		if (instance == null) {
			return (instance = new OfertasPendientes(gui));
		} else {
			return instance;
		}
	}

	private OfertasPendientes(Gui gui) {
		this.gui = gui;
		this.setPreferredSize(new Dimension(995, 600));
		// this.setBackground(Color.GREEN);
		this.setName(NAME);
		initialize();
	}

	private void initialize() {
		SpringLayout springLayout = new SpringLayout();
		this.setLayout(springLayout);

		coi = new ContenedorOfertasPendientes(gui);

		JScrollPane scrollPane = new JScrollPane(coi, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUI(new ThinSolidScrollBarUi(7));
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setPreferredSize(new Dimension(1006, 563));
		scrollPane.setBackground(Color.BLUE);

		this.add(scrollPane);
		this.setLayer(scrollPane, 1);

	}

	public void cargarOfertas() {
		coi.clearOfertas();
		Controller c = gui.getController();
		List<Integer> ofertas;
		try {
			ofertas = c.adminGetOfertasPendientes();
			for (Integer id : ofertas) {
				PanelOfertaAdmin oferta = new PanelOfertaAdmin(gui, id);
				coi.addOferta(oferta);
			}
			coi.repaint();
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
		}
	}

	public void clearOfertas() {
		coi.clearOfertas();
	}

	@Override
	public void setGlobalName() {
		this.setName(NAME);
	}

}

package gui.panels.admin.ofertas;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import gui.Gui;
import gui.components.ThinSolidScrollBarUi;
import gui.util.Nombrable;

public class OfertasPendientes extends JLayeredPane implements Nombrable {

	private static final long serialVersionUID = -8320036169616362237L;

	public static final String NAME = "OFERTAS_PENDIENTES";

	private Gui gui;

	private ContenedorOfertasPendientes coi;

	public OfertasPendientes(Gui gui) {
		this.gui = gui;
		this.setPreferredSize(new Dimension(995, 600));
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
		coi.cargarOfertas();
	}

	public void removeOferta(int id) {
		coi.removeOferta(id);
		this.revalidate();
	}
}

package gui.panels.ofertante.inmuebles;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import gui.Gui;
import gui.components.ThinSolidScrollBarUi;
import gui.util.Nombrable;
import gui.util.PanelInterfaz;

public class MisInmuebles extends PanelInterfaz implements Nombrable {

	private static final long serialVersionUID = -8320036169616362237L;

	public static final String NAME = "MIS_INMUEBLES";

	private Gui gui;

	private ContenedorInmuebles coi;

	public MisInmuebles(Gui gui) {
		this.gui = gui;
		this.initialize();
	}

	@Override
	public void setDimension() {
		this.setPreferredSize(new Dimension(995, 600));
	}

	@Override
	public void crearComponentes() {
		coi = new ContenedorInmuebles(gui);

		JScrollPane scrollPane = new JScrollPane(coi, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUI(new ThinSolidScrollBarUi(7));
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setPreferredSize(new Dimension(1006, 563));
		scrollPane.setBackground(Color.BLUE);

		this.add(scrollPane);

	}

	@Override
	public void colocarComponentes() {
		SpringLayout springLayout = new SpringLayout();
		this.setLayout(springLayout);

	}

	@Override
	public void setVisible(boolean flag) {
		super.setVisible(flag);
		if (flag) {
			cargarInmuebles();
		}
	}

	@Override
	public void registrarEventos() {

	}

	private void cargarInmuebles() {
		coi.cargarInmuebles();
	}

}

package gui.panels.ofertante.inmuebles;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import gui.Gui;
import gui.components.ThinSolidScrollBarUi;
import gui.util.Nombrable;
import gui.util.PanelInterfazPrincipal;

public class MisInmuebles extends JLayeredPane implements Nombrable, PanelInterfazPrincipal {

	private static final long serialVersionUID = -8320036169616362237L;

	public static final String NAME = "MIS_INMUEBLES";

	private Gui gui;

	private ContenedorInmuebles coi;

	public MisInmuebles(Gui gui) {
		this.gui = gui;
		SwingUtilities.invokeLater(() -> initialize());
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
		this.setLayer(scrollPane, 1);

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

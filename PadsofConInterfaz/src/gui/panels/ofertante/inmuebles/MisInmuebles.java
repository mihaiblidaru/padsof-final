package gui.panels.ofertante.inmuebles;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import gui.Gui;
import gui.components.ThinSolidScrollBarUi;
import gui.controllers.Controller;
import gui.util.Nombrable;

public class MisInmuebles extends JLayeredPane implements Nombrable {

	private static final long serialVersionUID = -8320036169616362237L;

	public static final String NAME = "MIS_INMUEBLES";

	private Gui gui;

	private ContenedorInmuebles coi;

	private static MisInmuebles instance = null;

	public static MisInmuebles getInstance(Gui gui) {
		if (instance == null) {
			return (instance = new MisInmuebles(gui));
		} else {
			return instance;
		}
	}

	private MisInmuebles(Gui gui) {
		this.gui = gui;
		this.setPreferredSize(new Dimension(995, 600));
		SwingUtilities.invokeLater(() -> initialize());
	}

	private void initialize() {
		SpringLayout springLayout = new SpringLayout();
		this.setLayout(springLayout);

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
	public void setVisible(boolean flag) {
		super.setVisible(flag);
		if (flag) {
			cargarInmuebles();
		}
	}

	private void cargarInmuebles() {
		coi.clearInmuebles();
		Controller c = gui.getController();
		List<Integer> inmuebles = c.ofertanteGetMisInmuebles();
		for (Integer id : inmuebles) {
			PanelInmueble p = new PanelInmueble(gui);
			p.cargarDatos(id);
			coi.addInmueble(p);
		}
		coi.repaint();
	}

}

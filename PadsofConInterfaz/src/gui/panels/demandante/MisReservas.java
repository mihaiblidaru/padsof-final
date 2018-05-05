package gui.panels.demandante;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import gui.Gui;
import gui.components.ThinSolidScrollBarUi;
import gui.controllers.Controller;
import gui.panels.oferta.PanelOferta;
import gui.panels.oferta.PanelOfertaReservadaInterno;
import gui.util.Nombrable;

public class MisReservas extends JLayeredPane implements Nombrable {

	private static final long serialVersionUID = -8320036169616362237L;

	public static final String NAME = "MIS_RESERVAS";

	private Gui gui;

	private ContenedorReservasInterno cri;

	public MisReservas(Gui gui) {
		this.gui = gui;
		this.setPreferredSize(new Dimension(995, 600));
		// this.setBackground(Color.GREEN);
		this.setName(NAME);
		initialize();
	}

	private void initialize() {
		SpringLayout springLayout = new SpringLayout();
		this.setLayout(springLayout);

		cri = new ContenedorReservasInterno(gui);

		JScrollPane scrollPane = new JScrollPane(cri, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUI(new ThinSolidScrollBarUi(7));
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setPreferredSize(new Dimension(1006, 563));
		scrollPane.setBackground(Color.BLUE);

		this.add(scrollPane);
		this.setLayer(scrollPane, 1);

	}

	public void cargarOfertas() {
		cri.clearOfertas();
		Controller c = gui.getController();
		Integer activa = c.demandanteGetOfertaReservada();
		if (activa != null) {
			cri.addReserva(new PanelOfertaReservadaInterno(gui, activa));
		}

		List<Integer> ofertas = c.demandanteGetOfertasContratadas();
		for (Integer id : ofertas) {
			PanelOferta oferta = new PanelOferta(gui, id);
			cri.addContratada(oferta);

		}
		cri.repaint();
	}

	public void clearOfertas() {
		cri.clearOfertas();
	}

}

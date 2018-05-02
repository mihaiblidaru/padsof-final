package gui.panels.ofertante.ofertas;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import app.clases.ofertas.Estado;
import gui.Gui;
import gui.components.ThinSolidScrollBarUi;
import gui.controllers.Controller;
import gui.panels.oferta.PanelOferta;
import gui.panels.oferta.PanelOfertaEditable;

public class MisOfertas extends JLayeredPane {

	private static final long serialVersionUID = -8320036169616362237L;

	public static final String NAME = "MIS_OFERTAS";

	private Gui gui;

	private ContenedorOfertasInterno coi;

	private static MisOfertas instance = null;

	public static MisOfertas getInstance(Gui gui) {
		if (instance == null) {
			return (instance = new MisOfertas(gui));
		} else {
			return instance;
		}
	}

	private MisOfertas(Gui gui) {
		this.gui = gui;
		this.setPreferredSize(new Dimension(995, 600));
		// this.setBackground(Color.GREEN);
		this.setName(NAME);
		initialize();
	}

	private void initialize() {
		SpringLayout springLayout = new SpringLayout();
		this.setLayout(springLayout);

		coi = new ContenedorOfertasInterno(gui);

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
		Controller c = gui.getController();
		List<Integer> ofertas = c.ofertanteGetMisOfertas();
		for (Integer id : ofertas) {
			Estado estado = c.ofertaGetEstado(id);
			if (estado == Estado.ACEPTADA || estado == Estado.CONTRATADA || estado == Estado.RESERVADA) {
				PanelOferta oferta = new PanelOferta(gui);
				oferta.cargarDatos(id);
				coi.addActiva(oferta);
			} else if (estado == Estado.PENDIENTE || estado == Estado.PENDIENTE_DE_CAMBIOS) {
				PanelOferta oferta = new PanelOfertaEditable(gui);
				oferta.cargarDatos(id);
				coi.addPendiente(oferta);
			} else if (estado == Estado.RETIRADA) {
				PanelOferta oferta = new PanelOferta(gui);
				oferta.cargarDatos(id);
				coi.addRechazada(oferta);
			}
		}
		coi.repaint();
	}

	public void clearOfertas() {
		coi.clearOfertas();
	}

}

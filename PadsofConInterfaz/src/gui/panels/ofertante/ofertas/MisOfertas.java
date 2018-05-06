package gui.panels.ofertante.ofertas;

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
import gui.panels.oferta.PanelOferta;
import gui.panels.oferta.PanelOfertaEditable;
import gui.util.GuiConstants;
import gui.util.Nombrable;

public class MisOfertas extends JLayeredPane implements Nombrable {

	private static final long serialVersionUID = -8320036169616362237L;

	public static final String NAME = "MIS_OFERTAS";

	private Gui gui;

	private ContenedorOfertasInterno coi;

	private JScrollPane scrollPane;

	public MisOfertas(Gui gui) {
		this.gui = gui;
		this.setPreferredSize(new Dimension(995, 600));
		initialize();
	}

	private void initialize() {
		SpringLayout springLayout = new SpringLayout();
		this.setLayout(springLayout);

		coi = new ContenedorOfertasInterno(gui);

		scrollPane = new JScrollPane(coi, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
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
		List<Integer> ofertas = c.ofertanteGetMisOfertas();
		for (Integer id : ofertas) {
			String estado = c.ofertaGetEstado(id);
			if (estado.equals(GuiConstants.ESTADO_ACEPTADA) || estado.equals(GuiConstants.ESTADO_CONTRATADA)
					|| estado.equals(GuiConstants.ESTADO_RESERVADA)) {
				PanelOferta oferta = new PanelOferta(gui, id);
				coi.addActiva(oferta);
			} else if (estado.equals(GuiConstants.ESTADO_PENDIENTE) || estado.equals(GuiConstants.ESTADO_PENDIENTE)) {
				PanelOferta oferta = new PanelOfertaEditable(gui, id);
				coi.addPendiente(oferta);
			} else if (estado.equals(GuiConstants.ESTADO_RETIRADA)) {
				PanelOferta oferta = new PanelOferta(gui, id);
				coi.addRechazada(oferta);
			}
		}

		coi.repaint();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				scrollPane.getHorizontalScrollBar().setValue(0);
				scrollPane.getVerticalScrollBar().setValue(0);
			}
		});
	}

	public void clearOfertas() {
		coi.clearOfertas();
	}

}

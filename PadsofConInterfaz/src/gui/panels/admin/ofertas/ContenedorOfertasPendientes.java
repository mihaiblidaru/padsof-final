package gui.panels.admin.ofertas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import gui.Gui;
import gui.controllers.Controller;
import gui.panels.oferta.PanelOferta;
import gui.panels.oferta.PanelOfertaAdmin;
import gui.panels.ofertante.inmuebles.PanelInmueble;
import gui.util.PanelInterfaz;

public class ContenedorOfertasPendientes extends PanelInterfaz {

	private static final long serialVersionUID = -213843877174040375L;

	private Map<Integer, PanelOfertaAdmin> ofertas = new HashMap<>();

	private Gui gui;

	private SpringLayout layout;

	private JLabel labelOfertas;
	private JSeparator separatorOfertas;
	private JLabel labelNoOfertas;

	private JPanel grupoOfertas;

	private static final int SEPARACION_INMUEBLES = 10;
	private static final int PANEL_WIDTH = 990;

	public ContenedorOfertasPendientes(Gui gui) {
		this.gui = gui;
		initialize();
	}

	public void cargarOfertas() {
		Controller c = gui.getController();
		List<Integer> resultados = null;
		resultados = c.adminGetOfertasPendientes();
		for (Integer id : ofertas.keySet()) {
			if (!resultados.contains(id)) {
				grupoOfertas.remove(ofertas.remove(id));
			}
		}

		for (Integer id : resultados) {
			this.addOferta(new PanelOfertaAdmin(gui, id));
		}
	}

	@Override
	public void setDimension() {
		this.setPreferredSize(new Dimension(PANEL_WIDTH,
				90 + SEPARACION_INMUEBLES + ofertas.size() * (PanelOferta.PANEL_HEIGHT + SEPARACION_INMUEBLES)));
	}

	@Override
	public void crearComponentes() {
		Font font = new Font("Times New Roman", Font.PLAIN, 20);
		Font fontinfo = new Font("Verdana", Font.PLAIN, 15);
		labelOfertas = new JLabel("Ofertas pendientes");

		labelNoOfertas = new JLabel("No hay ninguna oferta pendiente en este momento");
		separatorOfertas = new JSeparator(SwingConstants.HORIZONTAL);
		labelOfertas.setFont(font);
		labelNoOfertas.setFont(fontinfo);
		separatorOfertas.setPreferredSize(new Dimension(975, 1));
		separatorOfertas.setForeground(Color.GRAY);
		grupoOfertas = new JPanel();
		grupoOfertas.add(labelNoOfertas);
		grupoOfertas.setPreferredSize(new Dimension(PanelInmueble.PANEL_WIDTH, 100));

		this.add(labelOfertas);
		this.add(separatorOfertas);
		this.add(grupoOfertas);

	}

	@Override
	public void colocarComponentes() {
		layout = new SpringLayout();
		this.setLayout(layout);
		layout.putConstraint(SpringLayout.NORTH, labelOfertas, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, labelOfertas, 10, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, separatorOfertas, 5, SpringLayout.SOUTH, labelOfertas);
		layout.putConstraint(SpringLayout.WEST, separatorOfertas, -5, SpringLayout.WEST, labelOfertas);

		layout.putConstraint(SpringLayout.NORTH, grupoOfertas, 5, SpringLayout.SOUTH, separatorOfertas);
		layout.putConstraint(SpringLayout.WEST, grupoOfertas, 0, SpringLayout.WEST, labelOfertas);

		FlowLayout l1 = new FlowLayout(FlowLayout.LEFT, 0, SEPARACION_INMUEBLES);
		grupoOfertas.setLayout(l1);

	}

	public void addOferta(PanelOfertaAdmin panelOfertaAdmin) {
		if (ofertas.isEmpty())
			labelNoOfertas.setVisible(false);

		int id = panelOfertaAdmin.getIdOferta();

		if (!ofertas.containsKey(id)) {
			grupoOfertas.add(panelOfertaAdmin);
			ofertas.put(id, panelOfertaAdmin);
			setDimension();
			this.grupoOfertas.setPreferredSize(new Dimension(PanelOferta.PANEL_WIDTH,
					ofertas.size() * (PanelOferta.PANEL_HEIGHT + SEPARACION_INMUEBLES)));
		}
	}

	public void clearOfertas() {
		grupoOfertas.removeAll();
		ofertas.clear();
	}

	public void removeOferta(int id) {
		grupoOfertas.remove(ofertas.remove(id));
		if (ofertas.isEmpty()) {
			labelNoOfertas.setVisible(true);
			this.repaint();
		}
		this.revalidate();
	}
}

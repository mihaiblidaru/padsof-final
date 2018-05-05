package gui.panels.ofertante.inmuebles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import gui.Gui;
import gui.components.fx.FxButton;
import gui.controllers.Controller;
import gui.panels.Header;
import gui.util.PanelInterfazPrincipal;

public class ContenedorInmuebles extends JPanel implements PanelInterfazPrincipal {

	private static final long serialVersionUID = -2138438771740403776L;

	private Map<Integer, JComponent> inmuebles = new HashMap<>();

	private Gui gui;

	private SpringLayout layout;

	private JLabel labelInmuebles;
	private JSeparator separatorInmuebles;
	private JLabel labelNoInmuebles;
	private FxButton anyadir;

	private JPanel grupoInmuebles;

	private static final int SEPARACION_INMUEBLES = 10;
	private static final int PANEL_WIDTH = 990;

	public ContenedorInmuebles(Gui gui) {
		this.gui = gui;
		initialize();
	}

	public void cargarInmuebles() {
		Controller c = gui.getController();
		List<Integer> resultados = c.ofertanteGetMisInmuebles();
		inmuebles.keySet().stream().filter(k -> !resultados.contains(k))
				.forEach(k -> grupoInmuebles.remove(inmuebles.remove(k)));

		for (Integer id : resultados) {
			this.addInmueble(new PanelInmueble(gui, id));
		}
	}

	@Override
	public void setDimension() {
		this.setPreferredSize(new Dimension(PANEL_WIDTH,
				90 + SEPARACION_INMUEBLES + inmuebles.size() * (PanelInmueble.PANEL_HEIGHT + SEPARACION_INMUEBLES)));
	}

	@Override
	public void crearComponentes() {
		Font font = new Font("Times New Roman", Font.PLAIN, 20);
		Font fontinfo = new Font("Verdana", Font.PLAIN, 15);
		labelInmuebles = new JLabel("Tus inmuebles");

		labelNoInmuebles = new JLabel("No tienes ninguna inmueble registrado");
		separatorInmuebles = new JSeparator(SwingConstants.HORIZONTAL);
		labelInmuebles.setFont(font);
		labelNoInmuebles.setFont(fontinfo);
		separatorInmuebles.setPreferredSize(new Dimension(975, 1));
		separatorInmuebles.setForeground(Color.GRAY);
		anyadir = new FxButton(150, 30, "Añadir Inmueble");
		grupoInmuebles = new JPanel();
		grupoInmuebles.add(labelNoInmuebles);
		grupoInmuebles.setPreferredSize(new Dimension(PanelInmueble.PANEL_WIDTH, 100));

		this.add(labelInmuebles);
		this.add(separatorInmuebles);
		this.add(grupoInmuebles);

		this.add(anyadir);
	}

	@Override
	public void colocarComponentes() {
		layout = new SpringLayout();
		this.setLayout(layout);
		layout.putConstraint(SpringLayout.NORTH, labelInmuebles, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, labelInmuebles, 10, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, separatorInmuebles, 5, SpringLayout.SOUTH, labelInmuebles);
		layout.putConstraint(SpringLayout.WEST, separatorInmuebles, -5, SpringLayout.WEST, labelInmuebles);

		layout.putConstraint(SpringLayout.EAST, anyadir, -10, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, anyadir, -5, SpringLayout.NORTH, separatorInmuebles);

		layout.putConstraint(SpringLayout.NORTH, grupoInmuebles, 5, SpringLayout.SOUTH, separatorInmuebles);
		layout.putConstraint(SpringLayout.WEST, grupoInmuebles, 0, SpringLayout.WEST, labelInmuebles);

		FlowLayout l1 = new FlowLayout(FlowLayout.LEFT, 0, SEPARACION_INMUEBLES);
		grupoInmuebles.setLayout(l1);

	}

	@Override
	public void registrarEventos() {
		anyadir.setOnAction(event -> {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					gui.showOnly(Header.NAME, AniadirInmueble.NAME);
				}
			});

		});
	}

	public void addInmueble(PanelInmueble p) {
		if (inmuebles.isEmpty())
			labelNoInmuebles.setVisible(false);

		int id = p.getIdInmuebles();

		if (!inmuebles.containsKey(id)) {
			grupoInmuebles.add(p);
			inmuebles.put(id, p);
			setDimension();
			this.grupoInmuebles.setPreferredSize(new Dimension(PanelInmueble.PANEL_WIDTH,
					inmuebles.size() * (PanelInmueble.PANEL_HEIGHT + SEPARACION_INMUEBLES)));
		}
	}

	public void clearInmuebles() {
		grupoInmuebles.removeAll();
		inmuebles.clear();
	}
}

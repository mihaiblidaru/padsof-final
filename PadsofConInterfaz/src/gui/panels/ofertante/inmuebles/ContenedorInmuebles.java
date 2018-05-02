package gui.panels.ofertante.inmuebles;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import gui.Gui;
import gui.components.fx.FxButton;
import gui.panels.Header;

public class ContenedorInmuebles extends JPanel {

	private static final long serialVersionUID = -2138438771740403776L;

	private List<JComponent> inmuebles = new ArrayList<>();

	private SpringLayout layout;

	private JLabel labelInmuebles;
	private JSeparator separatorInmuebles;
	private JLabel labelNoInmuebles;

	private static final int SEPARACION_INMUEBLES = 10;

	public ContenedorInmuebles(Gui gui) {
		this.setBackground(Color.GREEN);
		layout = new SpringLayout();
		this.setLayout(layout);
		Font font = new Font("Times New Roman", Font.PLAIN, 20);
		Font fontinfo = new Font("Verdana", Font.PLAIN, 15);
		labelInmuebles = new JLabel("Tus inmuebles");
		labelInmuebles.setFont(font);

		labelNoInmuebles = new JLabel("No tienes ninguna inmueble registrado");
		labelNoInmuebles.setFont(fontinfo);

		separatorInmuebles = new JSeparator(SwingConstants.HORIZONTAL);
		separatorInmuebles.setPreferredSize(new Dimension(975, 1));
		separatorInmuebles.setForeground(Color.GRAY);

		inmuebles.addAll(Arrays.asList(labelInmuebles, separatorInmuebles, labelNoInmuebles));

		this.add(labelInmuebles);
		this.add(labelNoInmuebles);
		this.add(separatorInmuebles);

		layout.putConstraint(SpringLayout.NORTH, labelInmuebles, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, labelInmuebles, 10, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, separatorInmuebles, 5, SpringLayout.SOUTH, labelInmuebles);
		layout.putConstraint(SpringLayout.WEST, separatorInmuebles, -5, SpringLayout.WEST, labelInmuebles);

		layout.putConstraint(SpringLayout.NORTH, labelNoInmuebles, 5, SpringLayout.SOUTH, separatorInmuebles);
		layout.putConstraint(SpringLayout.WEST, labelNoInmuebles, 0, SpringLayout.WEST, labelInmuebles);

		FxButton anyadir = new FxButton(150, 30, "Añadir Inmueble");

		layout.putConstraint(SpringLayout.EAST, anyadir, -10, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, anyadir, -5, SpringLayout.NORTH, separatorInmuebles);
		this.add(anyadir);

		anyadir.setOnAction(event -> {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					gui.showOnly(Header.NAME, AniadirInmueble.NAME);
				}
			});

		});

	}

	public Component addInmueble(JComponent p) {
		if (inmuebles.get(inmuebles.size() - 1) == labelNoInmuebles) {
			this.remove(inmuebles.remove(inmuebles.size() - 1));
		}

		this.add(p);
		layout.putConstraint(SpringLayout.NORTH, p, SEPARACION_INMUEBLES, SpringLayout.SOUTH,
				inmuebles.get(inmuebles.size() - 1));
		layout.putConstraint(SpringLayout.WEST, p, 10, SpringLayout.WEST, this);
		inmuebles.add(p);

		this.setPreferredSize(new Dimension(WIDTH, 30 + inmuebles.size() * p.getPreferredSize().height));
		return p;
	}

	public void clearInmuebles() {
		inmuebles.stream().filter(c -> c instanceof PanelInmueble).forEach(c -> this.remove(c));
		for (Iterator<JComponent> iterator = inmuebles.iterator(); iterator.hasNext();) {
			JComponent jComponent = iterator.next();
			if (jComponent instanceof PanelInmueble) {
				iterator.remove();
			}

		}

	}

}

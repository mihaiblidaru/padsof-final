package gui.panels.ofertante;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import gui.Gui;
import gui.components.fx.FxButton;
import gui.panels.Header;
import gui.panels.oferta.PanelOferta;

public class ContenedorOfertasInterno extends JPanel {

	private static final long serialVersionUID = -2138438771740403776L;

	private List<JComponent> activas = new ArrayList<>();
	private List<JComponent> pendientes = new ArrayList<>();
	private List<JComponent> rechazadas = new ArrayList<>();

	private SpringLayout layout;

	private final Gui gui;
	private JLabel labelActivas;
	private JLabel labelPendientes;
	private JLabel labelRechazadas;
	private JSeparator separatorActivas;
	private JSeparator separatorPendientes;
	private JSeparator separatorRechazadas;

	private JLabel labelNoActivas;

	private JLabel labelNoPendientes;

	private JLabel labelNoRechazadas;

	private static final int SEPARACION_OFERTAS = 10;
	private static final int SEPARACION_SECCIONES = 70;

	public ContenedorOfertasInterno(Gui gui) {
		this.gui = gui;
		layout = new SpringLayout();
		this.setLayout(layout);
		Font font = new Font("Times New Roman", Font.PLAIN, 20);
		Font fontinfo = new Font("Verdana", Font.PLAIN, 15);
		labelActivas = new JLabel("Activas");
		labelPendientes = new JLabel("Pendientes");
		labelRechazadas = new JLabel("Rechazadas");

		labelActivas.setFont(font);
		labelPendientes.setFont(font);
		labelRechazadas.setFont(font);

		labelNoActivas = new JLabel("No tienes ninguna oferta activa");
		labelNoPendientes = new JLabel("No tienes ninguna oferta pendiente");
		labelNoRechazadas = new JLabel("No tienes ninguna oferta rechazada");

		labelNoActivas.setFont(fontinfo);
		labelNoPendientes.setFont(fontinfo);
		labelNoRechazadas.setFont(fontinfo);

		separatorActivas = new JSeparator(SwingConstants.HORIZONTAL);
		separatorPendientes = new JSeparator(SwingConstants.HORIZONTAL);
		separatorRechazadas = new JSeparator(SwingConstants.HORIZONTAL);

		separatorActivas.setPreferredSize(new Dimension(975, 1));
		separatorPendientes.setPreferredSize(new Dimension(975, 1));
		separatorRechazadas.setPreferredSize(new Dimension(975, 1));

		separatorActivas.setForeground(Color.GRAY);
		separatorPendientes.setForeground(Color.GRAY);
		separatorRechazadas.setForeground(Color.GRAY);

		activas.addAll(Arrays.asList(labelActivas, separatorActivas, labelNoActivas));
		pendientes.addAll(Arrays.asList(labelPendientes, separatorPendientes, labelNoPendientes));
		rechazadas.addAll(Arrays.asList(labelRechazadas, separatorRechazadas, labelNoRechazadas));

		this.add(labelActivas);
		this.add(labelPendientes);
		this.add(labelRechazadas);
		this.add(labelNoActivas);
		this.add(labelNoPendientes);
		this.add(labelNoRechazadas);
		this.add(separatorActivas);
		this.add(separatorPendientes);
		this.add(separatorRechazadas);

		layout.putConstraint(SpringLayout.NORTH, labelActivas, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, labelActivas, 10, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, separatorActivas, 5, SpringLayout.SOUTH, labelActivas);
		layout.putConstraint(SpringLayout.WEST, separatorActivas, -5, SpringLayout.WEST, labelActivas);

		layout.putConstraint(SpringLayout.NORTH, labelNoActivas, 5, SpringLayout.SOUTH, separatorActivas);
		layout.putConstraint(SpringLayout.WEST, labelNoActivas, 0, SpringLayout.WEST, labelActivas);

		layout.putConstraint(SpringLayout.NORTH, labelPendientes, SEPARACION_SECCIONES, SpringLayout.NORTH,
				labelNoActivas);
		layout.putConstraint(SpringLayout.WEST, labelPendientes, 10, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, separatorPendientes, 5, SpringLayout.SOUTH, labelPendientes);
		layout.putConstraint(SpringLayout.WEST, separatorPendientes, -5, SpringLayout.WEST, labelPendientes);

		layout.putConstraint(SpringLayout.NORTH, labelNoPendientes, 5, SpringLayout.SOUTH, separatorPendientes);
		layout.putConstraint(SpringLayout.WEST, labelNoPendientes, 0, SpringLayout.WEST, labelPendientes);

		layout.putConstraint(SpringLayout.NORTH, labelRechazadas, SEPARACION_SECCIONES, SpringLayout.NORTH,
				labelNoPendientes);
		layout.putConstraint(SpringLayout.WEST, labelRechazadas, 10, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, separatorRechazadas, 5, SpringLayout.SOUTH, labelRechazadas);
		layout.putConstraint(SpringLayout.WEST, separatorRechazadas, -5, SpringLayout.WEST, labelRechazadas);

		layout.putConstraint(SpringLayout.NORTH, labelNoRechazadas, 5, SpringLayout.SOUTH, separatorRechazadas);
		layout.putConstraint(SpringLayout.WEST, labelNoRechazadas, 0, SpringLayout.WEST, labelRechazadas);

		FxButton anyadir = new FxButton(120, 30, "Añadir Oferta");

		layout.putConstraint(SpringLayout.EAST, anyadir, -10, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, anyadir, -5, SpringLayout.NORTH, separatorActivas);
		this.add(anyadir);

		anyadir.setOnAction(event -> {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					if (gui.getController().getNumInmuebles() == 0) {

						JOptionPane.showMessageDialog(new JPanel(),
								"No tienes ningun inmueble registrado. Registra un inmueble e intentalo de nuevo",
								"Error", JOptionPane.ERROR_MESSAGE);
					} else {
						gui.showOnly(Header.NAME, AniadirOferta.NAME);
					}
				}
			});

		});

	}

	public Component addActiva(JComponent p) {
		if (activas.get(activas.size() - 1) == labelNoActivas) {
			this.remove(activas.remove(activas.size() - 1));
		}

		this.add(p);
		layout.putConstraint(SpringLayout.NORTH, p, SEPARACION_OFERTAS, SpringLayout.SOUTH,
				activas.get(activas.size() - 1));
		layout.putConstraint(SpringLayout.WEST, p, 10, SpringLayout.WEST, this);
		activas.add(p);
		JComponent next = pendientes.get(0);

		layout.putConstraint(SpringLayout.NORTH, next, SEPARACION_SECCIONES, SpringLayout.SOUTH, p);
		layout.putConstraint(SpringLayout.WEST, next, 10, SpringLayout.WEST, this);
		recalculateSize();
		return p;
	}

	public Component addPendiente(JComponent p) {
		if (pendientes.get(pendientes.size() - 1) == labelNoPendientes) {
			this.remove(pendientes.remove(pendientes.size() - 1));
		}

		this.add(p);
		layout.putConstraint(SpringLayout.NORTH, p, SEPARACION_OFERTAS, SpringLayout.SOUTH,
				pendientes.get(pendientes.size() - 1));
		layout.putConstraint(SpringLayout.WEST, p, 10, SpringLayout.WEST, this);
		pendientes.add(p);
		JComponent next = rechazadas.get(0);

		layout.putConstraint(SpringLayout.NORTH, next, SEPARACION_SECCIONES, SpringLayout.SOUTH, p);
		layout.putConstraint(SpringLayout.WEST, next, 10, SpringLayout.WEST, this);
		recalculateSize();
		return p;
	}

	public Component addRechazada(JComponent p) {
		if (rechazadas.get(rechazadas.size() - 1) == labelNoRechazadas) {
			this.remove(rechazadas.remove(rechazadas.size() - 1));
		}

		this.add(p);
		layout.putConstraint(SpringLayout.NORTH, p, SEPARACION_OFERTAS, SpringLayout.SOUTH,
				rechazadas.get(rechazadas.size() - 1));
		layout.putConstraint(SpringLayout.WEST, p, 10, SpringLayout.WEST, this);
		rechazadas.add(p);
		recalculateSize();
		return p;
	}

	private void recalculateSize() {
		layout.putConstraint(SpringLayout.SOUTH, this, 30, SpringLayout.SOUTH, rechazadas.get(rechazadas.size() - 1));
	}

	public void clearOfertas() {
		for (Iterator<JComponent> iterator = activas.iterator(); iterator.hasNext();) {
			JComponent component = iterator.next();
			if (component instanceof PanelOferta) {
				this.remove(component);
				iterator.remove();
			}

		}

		for (Iterator<JComponent> iterator = pendientes.iterator(); iterator.hasNext();) {
			JComponent component = iterator.next();
			if (component instanceof PanelOferta) {
				this.remove(component);
				iterator.remove();
			}

		}

		for (Iterator<JComponent> iterator = rechazadas.iterator(); iterator.hasNext();) {
			JComponent component = iterator.next();
			if (component instanceof PanelOferta) {
				this.remove(component);
				iterator.remove();
			}
		}

		addActiva(this.labelNoActivas);
		addPendiente(this.labelNoPendientes);
		addRechazada(this.labelNoRechazadas);

	}
}

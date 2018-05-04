package gui.panels.demandante;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import gui.Gui;

public class ContenedorReservasInterno extends JPanel {

	private static final long serialVersionUID = -2138438771740403776L;

	private List<JComponent> reservadas = new ArrayList<>();
	private List<JComponent> contratadas = new ArrayList<>();

	private SpringLayout layout;

	private JLabel labelReservadas;
	private JLabel labelContratadas;
	private JSeparator separatorReservadas;
	private JSeparator separatorContratadas;

	private JLabel labelNoReservadas;

	private JLabel labelNoPendientes;

	private static final int SEPARACION_OFERTAS = 10;
	private static final int SEPARACION_SECCIONES = 70;

	public ContenedorReservasInterno(Gui gui) {
		layout = new SpringLayout();
		this.setLayout(layout);
		Font font = new Font("Times New Roman", Font.PLAIN, 20);
		Font fontinfo = new Font("Verdana", Font.PLAIN, 15);
		labelReservadas = new JLabel("Reservada");
		labelContratadas = new JLabel("Contratadas");

		labelReservadas.setFont(font);
		labelContratadas.setFont(font);

		labelNoReservadas = new JLabel("No tienes ninguna oferta reservada");
		labelNoPendientes = new JLabel("No tienes ninguna oferta contratada");

		labelNoReservadas.setFont(fontinfo);
		labelNoPendientes.setFont(fontinfo);

		separatorReservadas = new JSeparator(SwingConstants.HORIZONTAL);
		separatorContratadas = new JSeparator(SwingConstants.HORIZONTAL);

		separatorReservadas.setPreferredSize(new Dimension(975, 1));
		separatorContratadas.setPreferredSize(new Dimension(975, 1));

		separatorReservadas.setForeground(Color.GRAY);
		separatorContratadas.setForeground(Color.GRAY);

		reservadas.addAll(Arrays.asList(labelReservadas, separatorReservadas, labelNoReservadas));
		contratadas.addAll(Arrays.asList(labelContratadas, separatorContratadas, labelNoPendientes));

		this.add(labelReservadas);
		this.add(labelContratadas);
		this.add(labelNoReservadas);
		this.add(labelNoPendientes);
		this.add(separatorReservadas);
		this.add(separatorContratadas);

		setContraits();

	}

	private void setContraits() {
		layout.putConstraint(SpringLayout.NORTH, labelReservadas, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, labelReservadas, 10, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, separatorReservadas, 5, SpringLayout.SOUTH, labelReservadas);
		layout.putConstraint(SpringLayout.WEST, separatorReservadas, -5, SpringLayout.WEST, labelReservadas);

		layout.putConstraint(SpringLayout.NORTH, labelNoReservadas, 5, SpringLayout.SOUTH, separatorReservadas);
		layout.putConstraint(SpringLayout.WEST, labelNoReservadas, 0, SpringLayout.WEST, labelReservadas);

		layout.putConstraint(SpringLayout.NORTH, labelContratadas, SEPARACION_SECCIONES, SpringLayout.NORTH,
				labelNoReservadas);
		layout.putConstraint(SpringLayout.WEST, labelContratadas, 10, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, separatorContratadas, 5, SpringLayout.SOUTH, labelContratadas);
		layout.putConstraint(SpringLayout.WEST, separatorContratadas, -5, SpringLayout.WEST, labelContratadas);

		layout.putConstraint(SpringLayout.NORTH, labelNoPendientes, 5, SpringLayout.SOUTH, separatorContratadas);
		layout.putConstraint(SpringLayout.WEST, labelNoPendientes, 0, SpringLayout.WEST, labelContratadas);

	}

	public Component addReserva(JComponent p) {
		if (reservadas.get(reservadas.size() - 1) == labelNoReservadas) {
			this.remove(reservadas.remove(reservadas.size() - 1));
		}

		this.add(p);
		layout.putConstraint(SpringLayout.NORTH, p, SEPARACION_OFERTAS, SpringLayout.SOUTH,
				reservadas.get(reservadas.size() - 1));
		layout.putConstraint(SpringLayout.WEST, p, 10, SpringLayout.WEST, this);
		reservadas.add(p);
		JComponent next = contratadas.get(0);

		layout.putConstraint(SpringLayout.NORTH, next, SEPARACION_SECCIONES, SpringLayout.SOUTH, p);
		layout.putConstraint(SpringLayout.WEST, next, 10, SpringLayout.WEST, this);
		recalculateSize();
		return p;
	}

	public Component addContratada(JComponent p) {
		if (contratadas.get(contratadas.size() - 1) == labelNoPendientes) {
			this.remove(contratadas.remove(contratadas.size() - 1));
		}

		this.add(p);
		layout.putConstraint(SpringLayout.NORTH, p, SEPARACION_OFERTAS, SpringLayout.SOUTH,
				contratadas.get(contratadas.size() - 1));
		layout.putConstraint(SpringLayout.WEST, p, 10, SpringLayout.WEST, this);
		contratadas.add(p);
		recalculateSize();
		return p;
	}

	private void recalculateSize() {
		layout.putConstraint(SpringLayout.SOUTH, this, 30, SpringLayout.SOUTH, contratadas.get(contratadas.size() - 1));
	}

	public void clearOfertas() {
		this.removeAll();
		this.add(labelReservadas);
		this.add(labelContratadas);
		this.add(labelNoReservadas);
		this.add(labelNoPendientes);
		this.add(separatorReservadas);
		this.add(separatorContratadas);

		reservadas.clear();
		contratadas.clear();
		reservadas.addAll(Arrays.asList(labelReservadas, separatorReservadas, labelNoReservadas));
		contratadas.addAll(Arrays.asList(labelContratadas, separatorContratadas, labelNoPendientes));

		this.setContraits();
	}
}

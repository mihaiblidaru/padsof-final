package gui.panels.demandante;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import gui.panels.oferta.PanelOferta;
import gui.panels.oferta.PanelOfertaReservadaInterno;
import gui.util.LimitedFlowLayout;
import gui.util.PanelInterfaz;

public class ContenedorReservasInterno extends PanelInterfaz {

	private static final long serialVersionUID = -2138438771740403776L;

	private Map<Integer, PanelOfertaReservadaInterno> reservadas = new HashMap<>();
	private Map<Integer, PanelOferta> contratadas = new HashMap<>();

	private SpringLayout layout;

	private JLabel labelReservadas;
	private JLabel labelContratadas;
	private JSeparator separatorReservadas;
	private JSeparator separatorContratadas;

	private JLabel labelNoReservadas;

	private JLabel labelNoContratadas;

	private JPanel grupoReservadas;

	private JPanel grupoContratadas;

	private static final int SEPARACION_OFERTAS = 10;

	private static final int PANEL_WIDTH = 990;

	public ContenedorReservasInterno() {
		layout = new SpringLayout();
		this.setLayout(layout);

	}

	@Override
	public void setDimension() {
		double height = 100 + grupoReservadas.getPreferredSize().getHeight()
				+ grupoContratadas.getPreferredSize().getHeight();

		this.setPreferredSize(new Dimension(PANEL_WIDTH, (int) height));

	}

	@Override
	public void crearComponentes() {
		Font font = new Font("Times New Roman", Font.PLAIN, 20);
		Font fontinfo = new Font("Verdana", Font.PLAIN, 15);
		labelReservadas = new JLabel("Reservada");
		labelContratadas = new JLabel("Contratadas");
		separatorReservadas = new JSeparator(SwingConstants.HORIZONTAL);
		separatorContratadas = new JSeparator(SwingConstants.HORIZONTAL);

		labelNoReservadas = new JLabel("No tienes ninguna oferta reservada");
		labelNoContratadas = new JLabel("No tienes ninguna oferta contratada");
		grupoContratadas = new JPanel(new LimitedFlowLayout(FlowLayout.LEFT, 0, 10, 800));
		grupoReservadas = new JPanel(new LimitedFlowLayout(FlowLayout.LEFT, 0, 10, 800));

		labelReservadas.setFont(font);
		labelContratadas.setFont(font);

		labelNoReservadas.setFont(fontinfo);
		labelNoContratadas.setFont(fontinfo);

		grupoReservadas.setPreferredSize(new Dimension(800, 170));
		grupoContratadas.setPreferredSize(new Dimension(800, 170));

		separatorReservadas.setPreferredSize(new Dimension(975, 1));
		separatorContratadas.setPreferredSize(new Dimension(975, 1));
		separatorReservadas.setForeground(Color.GRAY);
		separatorContratadas.setForeground(Color.GRAY);

		this.add(grupoReservadas);
		this.add(grupoContratadas);

		grupoReservadas.add(labelNoReservadas);
		grupoContratadas.add(labelNoContratadas);

		this.add(labelReservadas);
		this.add(labelContratadas);
		this.add(separatorReservadas);
		this.add(separatorContratadas);

	}

	@Override
	public void colocarComponentes() {
		layout.putConstraint(SpringLayout.NORTH, labelReservadas, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, labelReservadas, 10, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, separatorReservadas, 5, SpringLayout.SOUTH, labelReservadas);
		layout.putConstraint(SpringLayout.WEST, separatorReservadas, -5, SpringLayout.WEST, labelReservadas);

		layout.putConstraint(SpringLayout.NORTH, grupoReservadas, 5, SpringLayout.SOUTH, separatorReservadas);
		layout.putConstraint(SpringLayout.WEST, grupoReservadas, 0, SpringLayout.WEST, labelReservadas);

		layout.putConstraint(SpringLayout.NORTH, labelContratadas, 10, SpringLayout.SOUTH, grupoReservadas);
		layout.putConstraint(SpringLayout.WEST, labelContratadas, 0, SpringLayout.WEST, labelReservadas);

		layout.putConstraint(SpringLayout.NORTH, separatorContratadas, 5, SpringLayout.SOUTH, labelContratadas);
		layout.putConstraint(SpringLayout.WEST, separatorContratadas, -5, SpringLayout.WEST, labelContratadas);

		layout.putConstraint(SpringLayout.NORTH, grupoContratadas, 5, SpringLayout.SOUTH, separatorContratadas);
		layout.putConstraint(SpringLayout.WEST, grupoContratadas, 0, SpringLayout.WEST, labelContratadas);

	}

	public void addReserva(PanelOfertaReservadaInterno p) {
		if (reservadas.isEmpty())
			labelNoReservadas.setVisible(false);

		if (!reservadas.containsKey(p.getIdOferta())) {
			reservadas.put(p.getIdOferta(), p);
			grupoReservadas.add(p);
		}

		grupoReservadas.setPreferredSize(new Dimension(800,
				reservadas.size() * (PanelOferta.PANEL_HEIGHT + SEPARACION_OFERTAS) + SEPARACION_OFERTAS));
		setDimension();
	}

	public void addContratada(PanelOferta p) {
		if (contratadas.isEmpty())
			labelNoContratadas.setVisible(false);

		if (!contratadas.containsKey(p.getIdOferta())) {
			contratadas.put(p.getIdOferta(), p);
			grupoContratadas.add(p);
		}

		grupoContratadas.setPreferredSize(new Dimension(800,
				contratadas.size() * (PanelOferta.PANEL_HEIGHT + SEPARACION_OFERTAS) + SEPARACION_OFERTAS));
		setDimension();
	}

	@Override
	public void registrarEventos() {
		// TODO Auto-generated method stub

	}

	public void removeReserva(int idOferta) {
		if (reservadas.containsKey(idOferta)) {
			grupoReservadas.remove(reservadas.remove(idOferta));
			if (reservadas.isEmpty())
				labelNoReservadas.setVisible(true);
		}

	}
}

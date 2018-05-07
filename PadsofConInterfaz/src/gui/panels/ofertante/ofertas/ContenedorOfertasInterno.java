package gui.panels.ofertante.ofertas;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
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

/**
 * Esta es nuestra clase que sirve como contenedor interno de ofertas
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class ContenedorOfertasInterno extends JPanel {

	private static final long serialVersionUID = -2138438771740403776L;

	/**
	 * Lista de ofertas activas
	 */
	private List<JComponent> activas = new ArrayList<>();
	
	/**
	 * Lista de ofertas pendientes
	 */
	private List<JComponent> pendientes = new ArrayList<>();
	
	/**
	 * Lista de ofertas rechazadas
	 */
	private List<JComponent> rechazadas = new ArrayList<>();

	/**
	 * El layout del contenedor
	 */
	private SpringLayout layout;

	/**
	 * El jlabel de las ofertas activas
	 */
	private JLabel labelActivas;
	
	/**
	 * El jlabel de las ofertas pendientes
	 */
	private JLabel labelPendientes;
	
	/**
	 * El jlabel de las ofertas rechazadas
	 */
	private JLabel labelRechazadas;
	
	/**
	 * El separador de las ofertas activas
	 */
	private JSeparator separatorActivas;
	
	/**
	 * El separador de las ofertas pendientes
	 */
	private JSeparator separatorPendientes;
	/**
	 * El separador de las ofertas rechazadas
	 */
	private JSeparator separatorRechazadas;

	/**
	 * El jlabel de las ofertas no activas
	 */
	private JLabel labelNoActivas;

	/**
	 * El jlabel de las ofertas no pendientes
	 */
	private JLabel labelNoPendientes;

	/**
	 * El jlabel de las ofertas no rechazadas
	 */
	private JLabel labelNoRechazadas;

	/**
	 * Boton para aniadir
	 */
	private FxButton anyadir;

	/**
	 * El numero de pixeles de separacion entre ofertas
	 */
	private static final int SEPARACION_OFERTAS = 10;
	
	/**
	 * El numero de pixeles de separacion entre secciones
	 */
	private static final int SEPARACION_SECCIONES = 70;

	/**
	 * Constructor del contenedor, crea los componentes de la clase y los añade
	 * @param gui la interfaz grafica
	 */
	public ContenedorOfertasInterno(Gui gui) {
		layout = new SpringLayout();
		this.setLayout(layout);
		Font font = new Font("Times New Roman", Font.PLAIN, 20);
		Font fontinfo = new Font("Verdana", Font.PLAIN, 15);
		labelActivas = new JLabel("Activas");
		labelPendientes = new JLabel("Pendientes");
		labelRechazadas = new JLabel("Rechazadas");
		anyadir = new FxButton(120, 30, "Añadir Oferta");

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

		setContraits();

	}

	/**
	 * Funcion que coloca los elementos del contenedor
	 */
	private void setContraits() {
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

		layout.putConstraint(SpringLayout.EAST, anyadir, -10, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, anyadir, -5, SpringLayout.NORTH, separatorActivas);
	}

	/**
	 * Funcion que añade una activa
	 * @param p componente a aniadir
	 * @return p componente a aniadir
	 */
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

	/**
	 * Funcion que añade una pendiente
	 * @param p componente a aniadir
	 * @return p componente a aniadir
	 */
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

	/**
	 * Funcion que añade una rechazada
	 * @param p componente a aniadir
	 * @return p componente a aniadir
	 */
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

	/**
	 * Funcion que recalcula el tamaño de la interfaz grafica
	 */
	private void recalculateSize() {
		layout.putConstraint(SpringLayout.SOUTH, this, 30, SpringLayout.SOUTH, rechazadas.get(rechazadas.size() - 1));
	}

	/**
	 * Funcion que borra todo, añade los componentes y luego coloca componentes
	 */
	public void clearOfertas() {
		this.removeAll();
		this.add(labelActivas);
		this.add(labelPendientes);
		this.add(labelRechazadas);
		this.add(labelNoActivas);
		this.add(labelNoPendientes);
		this.add(labelNoRechazadas);
		this.add(separatorActivas);
		this.add(separatorPendientes);
		this.add(separatorRechazadas);
		this.add(anyadir);
		activas.clear();
		pendientes.clear();
		rechazadas.clear();
		activas.addAll(Arrays.asList(labelActivas, separatorActivas, labelNoActivas));
		pendientes.addAll(Arrays.asList(labelPendientes, separatorPendientes, labelNoPendientes));
		rechazadas.addAll(Arrays.asList(labelRechazadas, separatorRechazadas, labelNoRechazadas));

		this.setContraits();
	}
}

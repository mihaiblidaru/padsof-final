package gui.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import gui.Gui;
import gui.components.ThinSolidScrollBarUi;
import gui.controllers.Controller;
import gui.panels.oferta.PanelOfertaBusqueda;
import gui.util.Nombrable;
import gui.util.PanelInterfaz;

/**
 * Esta clase nos sirve para trabajar con los resultados de la busqueda
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 */
public class ResultadosBusqueda extends PanelInterfaz implements Nombrable {

	/**
	 * Altura del panel
	 */
	private static final int PANEL_HEIGHT = 562;
	
	/**
	 * Altura del panel
	 */
	private static final int PANEL_WIDTH = 815;
	private static final long serialVersionUID = 8638372520699078390L;
	
	/**
	 * Nombre del panel
	 */
	public final static String NAME = "RESULTADOS_BUSQUEDA";
	/**
	 * Interfaz grafica
	 */
	private final Gui gui;
	
	/**
	 * Contenedor de las ofertas
	 */
	private ContenedorOfertas contenedor;

	/**
	 * Constructor de ResultadosBusquedas
	 * @param gui interfaz grafica
	 */
	public ResultadosBusqueda(Gui gui) {
		this.gui = gui;
		initialize();
		cargarResultados(gui.getController().getUltimasOfertas(5));
	}

	/**
	 * Carga los resultados de una busqueda
	 * @param resultados lista de id al hacer una busqueda
	 */
	public void cargarResultados(List<Integer> resultados) {
		contenedor.cargarResultados(resultados);
		this.revalidate();
	}

	/**
	 * Actualza la ofertas del contenedor
	 */
	public void actualizarOfertas() {
		contenedor.actualizarOfertas();
	}

	/**
	 * Crea los componentes del resultado de busqueda
	 */
	@Override
	public void crearComponentes() {
		contenedor = new ContenedorOfertas(gui);
		JScrollPane scrollPanel = new JScrollPane(contenedor, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanel.setPreferredSize(new Dimension(826, 564));
		scrollPanel.getVerticalScrollBar().setUI(new ThinSolidScrollBarUi(7));
		scrollPanel.getVerticalScrollBar().setBackground(Color.WHITE);
		scrollPanel.getVerticalScrollBar().setUnitIncrement(16);
		scrollPanel.setBorder(BorderFactory.createEmptyBorder());
		this.add(scrollPanel);

	}

	/**
	 * Coloca los componentes del LoginPanel utilizando un springLayout
	 */
	@Override
	public void colocarComponentes() {
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
	}

	/**
	 * Registra los eventos que ocurren en el loginPanel
	 */
	@Override
	public void registrarEventos() {
	}

	/**
	 * Define la dimension de la interfaz grafica
	 */
	@Override
	public void setDimension() {
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
	}

	/**
	 * Esta clase es un contenedor de las ofertas
	 * @author Mihai Blidaru
	 * @author Sergio Dominguez
	 */
	class ContenedorOfertas extends JPanel {
		private static final long serialVersionUID = -2181119752625080665L;
		
		/**
		 * El layout
		 */
		private final FlowLayout layout;
		
		/**
		 * Mapa de resultados de la busqueda
		 */
		private Map<Integer, PanelOfertaBusqueda> resultados = new HashMap<>();
		
		/**
		 * Interfaz grafica
		 */
		private Gui gui;
		
		/**
		 * Label al no haber resutados
		 */
		private JLabel noResultados;

		/**
		 * Constructor de ContenedorOfertas
		 * @param gui interfaz grafica
		 */
		public ContenedorOfertas(Gui gui) {
			this.gui = gui;
			layout = new FlowLayout(FlowLayout.CENTER, 0, 10);
			noResultados = new JLabel("No se han encontrado ofertas");
			noResultados.setBorder(new EmptyBorder(100, 0, 0, 0));
			noResultados.setForeground(Color.LIGHT_GRAY);
			Font font = new Font("Arial", Font.PLAIN, 30);
			noResultados.setFont(font);
			this.setLayout(layout);
			this.add(noResultados);
		}

		/**
		 * Añade el panel de una oferta tras una busqueda
		 * @param c panel a añadir
		 * @return c pañel añadido
		 */
		public Component addOferta(PanelOfertaBusqueda c) {
			if (resultados.isEmpty()) {
				noResultados.setVisible(false);
			}
			this.add(c);
			resultados.put(c.getIdOferta(), c);

			this.setPreferredSize(
					new Dimension(750, 10 + this.getComponentCount() * (PanelOfertaBusqueda.PANEL_HEIGHT + 10)));

			return c;
		}

		/**
		 * Carga los resultados tras una busqueda
		 * @param r lista de resultados tras una busqueda
		 */
		public void cargarResultados(List<Integer> r) {
			resultados.values().stream().forEach(v -> this.remove(v));
			resultados.clear();
			noResultados.setVisible(true);
			this.setPreferredSize(new Dimension(750, 300));
			for (Integer id : r) {
				if (!resultados.containsKey(id)) {
					PanelOfertaBusqueda panelOferta = new PanelOfertaBusqueda(this.gui, id);
					this.addOferta(panelOferta);
				}
			}
			this.revalidate();
			this.repaint();
		}

		/**
		 * Actualiza las ofertas actualizando sus botones
		 */
		public void actualizarOfertas() {
			Controller c = gui.getController();
			List<Integer> noVisibles = c.ofertanteGetMisOfertas();
			noVisibles.forEach(id -> removeOferta(id));
			resultados.values().stream().forEach(r -> r.actualizarBotones());
		}

		/**
		 * Borrra una oferta de la interfaz
		 * @param idOferta id de la oferta a borrar
		 */
		public void removeOferta(int idOferta) {
			this.remove(resultados.remove(idOferta));
			if (resultados.isEmpty()) {
				noResultados.setVisible(true);
			}

			this.revalidate();
			this.repaint();

		}
	}

	/**
	 * Borra una oferta del contenedor
	 * @param idOferta id de la oferta a borrar
	 */
	public void removeOferta(int idOferta) {
		contenedor.removeOferta(idOferta);
		this.revalidate();
		this.repaint();
	}

}

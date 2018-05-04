package gui.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import gui.Gui;
import gui.components.ThinSolidScrollBarUi;
import gui.panels.oferta.PanelOfertaBusqueda;
import gui.util.Nombrable;
import gui.util.PanelInterfazPrincipal;

public class ResultadosBusqueda extends JPanel implements Nombrable, PanelInterfazPrincipal {

	private static final int PANEL_HEIGHT = 562;
	private static final int PANEL_WIDTH = 815;
	private static final long serialVersionUID = 8638372520699078390L;
	private static ResultadosBusqueda instance = null;
	public final static String NAME = "RESULTADOS_BUSQUEDA";
	private final Gui gui;
	private ContenedorOfertas contenedor;

	public static ResultadosBusqueda getInstance(Gui gui) {
		if (instance == null) {
			return (instance = new ResultadosBusqueda(gui));
		} else {
			return instance;
		}
	}

	private ResultadosBusqueda(Gui gui) {
		this.gui = gui;
		initialize();
	}

	public void cargarResultados(List<Integer> resultados) {
		contenedor.cargarResultados(resultados);
		this.revalidate();
	}

	public void actualizarBotones() {
		contenedor.actualizarBotones();
	}

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

	@Override
	public void colocarComponentes() {
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
	}

	@Override
	public void registrarEventos() {
	}

	@Override
	public void setDimension() {
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
	}

	class ContenedorOfertas extends JPanel {
		private static final long serialVersionUID = -2181119752625080665L;
		private final FlowLayout layout;
		Map<Integer, PanelOfertaBusqueda> resultados = new HashMap<>();
		private Gui gui;

		public ContenedorOfertas(Gui gui) {
			this.gui = gui;
			layout = new FlowLayout(FlowLayout.CENTER, 0, 10);
			this.setLayout(layout);
		}

		public Component addOferta(PanelOfertaBusqueda c) {
			this.add(c);
			resultados.put(c.getIdOferta(), c);

			this.setPreferredSize(
					new Dimension(750, 10 + this.getComponentCount() * (c.getPreferredSize().height + 10)));

			return c;
		}

		public void cargarResultados(List<Integer> r) {
			for (Integer id : r) {
				if (!resultados.containsKey(id)) {
					PanelOfertaBusqueda panelOferta = new PanelOfertaBusqueda(this.gui, id);
					contenedor.addOferta(panelOferta);
				}
			}

			this.revalidate();
		}

		public void actualizarBotones() {
			resultados.values().stream().forEach(c -> c.actualizarBotones());
		}
	}

}

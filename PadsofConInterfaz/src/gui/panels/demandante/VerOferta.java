package gui.panels.demandante;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import gui.Gui;
import gui.components.JMultiLineLabel;
import gui.components.fx.FxButton;
import gui.controllers.Controller;
import gui.dialogs.ComentarDialog;
import gui.panels.Header;
import gui.panels.ResultadosBusqueda;
import gui.panels.SearchMenu;
import gui.panels.oferta.comentario.PanelComentario;
import gui.util.IconLoader;
import gui.util.LimitedFlowLayout;
import gui.util.Nombrable;
import gui.util.PanelInterfaz;

public class VerOferta extends PanelInterfaz implements Nombrable {

	public static final String NAME = "VISTA_OFERTA";
	private static final long serialVersionUID = 8592039965953760934L;
	private static final int PANEL_HEIGHT = 560;
	private static final int PANEL_WIDTH = 995;

	private Gui gui;
	private SpringLayout layout;
	private JMultiLineLabel direccion;
	private JLabel fechaInicio;
	private JLabel hasta;
	private JLabel fechaFin;
	private JLabel desde;
	private JMultiLineLabel descripcion;
	private JLabel precio;
	private JLabel fianza;
	private JLabel total;
	private JLabel precioTotal;
	private JPanel comentarios;
	private JScrollPane scrollPane;
	private FxButton aniadirComentario;

	protected Integer idOferta;
	private JPanel grupoValoracion;
	private ImageIcon empty;
	private ImageIcon full;
	protected boolean[] valoracionInicial = new boolean[5];
	boolean yaValorado;
	private JLabel star1;
	private JLabel star2;
	private JLabel star3;
	private JLabel star4;
	private JLabel star5;
	private JLabel labelValoracion;
	private FxButton contratarBtn;
	private FxButton reservarBtn;

	public VerOferta(Gui gui) {
		this.gui = gui;
		initialize();
	}

	@Override
	public void setDimension() {
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void crearComponentes() {
		Font precioFont = new Font("Comic Sans", Font.PLAIN, 37);
		Font fianzaFont = new Font("Comic Sans", Font.PLAIN, 15);

		direccion = new JMultiLineLabel("Calle Alcala, nº7, 1B, 28850, Madrid", 200, 30, true);
		descripcion = new JMultiLineLabel(
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus purus elit, mattis id mauris eu, varius venenatis eros. Vestibulum quis pulvinar odio, vitae ultrices arcu. Nulla et cursus ante, ac rutrum purus. Suspendisse sed ex purus. Donec condimentum leo non interdum consectetur. Curabitur fermentum dolor eu porta sagittis. Nam ut justo quis diam efficitur fringilla. Vivamus a ornare est, eget porttitor mauris. Maecenas faucibus pretium ligula ut tempus. Donec eleifend erat ligula, ut cursus quam lacinia vitae.",
				400, 100, false);
		descripcion.setBackground(Color.RED);

		desde = new JLabel("Desde");
		fechaInicio = new JLabel("12-12-2012");
		hasta = new JLabel("Hasta o Num Meses");
		fechaFin = new JLabel("22/12/2012");
		precio = new JLabel("300 €");
		fianza = new JLabel("Fianza +150 €");
		total = new JLabel("Total");
		precioTotal = new JLabel("450€");
		aniadirComentario = new FxButton(130, 30, "Añadir Comentario");
		contratarBtn = new FxButton(100, 25, "Contratar");
		reservarBtn = new FxButton(100, 25, "Reservar");

		direccion.setAlignmentX(JTextArea.LEFT_ALIGNMENT);
		precio.setFont(precioFont);
		fianza.setFont(fianzaFont);
		total.setFont(fianzaFont.deriveFont(Font.PLAIN, 30));
		Map attributes = fianzaFont.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		precioTotal.setFont(fianzaFont.deriveFont(attributes).deriveFont(Font.PLAIN, 40));

		comentarios = new JPanel(new LimitedFlowLayout(FlowLayout.RIGHT, 50));

		scrollPane = new JScrollPane(comentarios, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(830, 320));
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);

		grupoValoracion = new JPanel();
		grupoValoracion.setPreferredSize(new Dimension(200, 40));
		labelValoracion = new JLabel("Valora esta oferta");

		labelValoracion.setFont(precioFont.deriveFont(Font.PLAIN, 17));

		empty = IconLoader.load("res/img/fa-star-empty.png", 30, 30);
		full = IconLoader.load("res/img/fa-star-full.png", 30, 30);
		star1 = new JLabel(empty);
		star2 = new JLabel(empty);
		star3 = new JLabel(empty);
		star4 = new JLabel(empty);
		star5 = new JLabel(empty);

		grupoValoracion.add(star1);
		grupoValoracion.add(star2);
		grupoValoracion.add(star3);
		grupoValoracion.add(star4);
		grupoValoracion.add(star5);

		this.add(direccion);
		this.add(fechaInicio);
		this.add(hasta);
		this.add(fechaFin);
		this.add(desde);
		this.add(descripcion);
		this.add(precio);
		this.add(fianza);
		this.add(total);
		this.add(precioTotal);
		this.add(scrollPane);
		this.add(aniadirComentario);
		this.add(grupoValoracion);
		this.add(labelValoracion);
		this.add(contratarBtn);
		this.add(reservarBtn);

	}

	@Override
	public void colocarComponentes() {
		layout = new SpringLayout();
		setLayout(layout);
		layout.putConstraint(SpringLayout.WEST, direccion, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, direccion, 10, SpringLayout.NORTH, this);

		layout.putConstraint(SpringLayout.NORTH, fechaInicio, 5, SpringLayout.SOUTH, desde);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, fechaInicio, 0, SpringLayout.HORIZONTAL_CENTER, desde);

		layout.putConstraint(SpringLayout.WEST, hasta, 40, SpringLayout.EAST, desde);
		layout.putConstraint(SpringLayout.NORTH, hasta, 0, SpringLayout.NORTH, desde);

		layout.putConstraint(SpringLayout.NORTH, fechaFin, 0, SpringLayout.NORTH, fechaInicio);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, fechaFin, 0, SpringLayout.HORIZONTAL_CENTER, hasta);

		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, fianza, 0, SpringLayout.HORIZONTAL_CENTER, precio);
		layout.putConstraint(SpringLayout.NORTH, fianza, 5, SpringLayout.SOUTH, precio);

		layout.putConstraint(SpringLayout.WEST, precio, 5, SpringLayout.EAST, descripcion);
		layout.putConstraint(SpringLayout.NORTH, precio, 30, SpringLayout.NORTH, this);

		layout.putConstraint(SpringLayout.WEST, descripcion, 30, SpringLayout.EAST, direccion);
		layout.putConstraint(SpringLayout.NORTH, descripcion, 10, SpringLayout.NORTH, this);

		layout.putConstraint(SpringLayout.WEST, desde, 10, SpringLayout.WEST, direccion);
		layout.putConstraint(SpringLayout.NORTH, desde, 40, SpringLayout.SOUTH, direccion);

		layout.putConstraint(SpringLayout.NORTH, total, 5, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, total, 70, SpringLayout.EAST, precio);

		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, precioTotal, 0, SpringLayout.HORIZONTAL_CENTER, total);
		layout.putConstraint(SpringLayout.NORTH, precioTotal, 5, SpringLayout.SOUTH, total);

		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, contratarBtn, 0, SpringLayout.HORIZONTAL_CENTER,
				precioTotal);
		layout.putConstraint(SpringLayout.NORTH, contratarBtn, 20, SpringLayout.SOUTH, precioTotal);

		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, reservarBtn, 0, SpringLayout.HORIZONTAL_CENTER, precio);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, reservarBtn, 0, SpringLayout.VERTICAL_CENTER, contratarBtn);

		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, scrollPane, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.SOUTH, scrollPane, -10, SpringLayout.SOUTH, this);

		layout.putConstraint(SpringLayout.EAST, aniadirComentario, 0, SpringLayout.EAST, scrollPane);
		layout.putConstraint(SpringLayout.SOUTH, aniadirComentario, -10, SpringLayout.NORTH, scrollPane);

		layout.putConstraint(SpringLayout.NORTH, grupoValoracion, 10, SpringLayout.SOUTH, fechaInicio);
		layout.putConstraint(SpringLayout.WEST, grupoValoracion, 20, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, labelValoracion, 10, SpringLayout.SOUTH, grupoValoracion);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, labelValoracion, 0, SpringLayout.HORIZONTAL_CENTER,
				grupoValoracion);

		grupoValoracion.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 0));

	}

	public void cargarComentarios(Integer id) {
		Controller c = gui.getController();
		comentarios.removeAll();
		List<PanelComentario> resultados = c.ofertaGetComentarios(id);
		for (PanelComentario panelComentario : resultados) {
			colocarComentario(panelComentario);
		}

		comentarios.revalidate();
		comentarios.repaint();
		scrollPane.revalidate();
		scrollPane.repaint();
		this.revalidate();
		this.repaint();
	}

	private void colocarComentario(PanelComentario p) {
		if (p.getPadre() == null) {
			comentarios.add(p);
		} else {
			Integer padre = p.getPadre();
			Component[] coms = comentarios.getComponents();
			for (int i = 0; i < coms.length; i++) {
				PanelComentario panelPadre = (PanelComentario) coms[i];
				if (((PanelComentario) coms[i]).getIdComentario().equals(padre)) {
					comentarios.add(p, i + 1);
					p.setNivel(panelPadre.getNivel() + 1);
					break;
				}
			}
		}
		p.revalidate();
		p.repaint();

		int altura = Stream.of(comentarios.getComponents()).map(c -> c.getPreferredSize().height + 5)
				.mapToInt(Integer::intValue).sum() + 5;
		comentarios.setPreferredSize(new Dimension(800, altura));

	}

	public void cargarDatos(Integer id) {
		this.idOferta = id;
		SwingUtilities.invokeLater(() -> {
			Controller c = this.gui.getController();
			float precio = c.ofertaGetPrecio(id);
			float fianza = c.ofertaGetFianza(id);
			this.precio.setText(String.format("%.2f €", precio));
			this.fianza.setText(String.format("Fianza +%.2f €", fianza));
			this.precioTotal.setText(String.format("%.2f €", precio + fianza));
			LocalDate ini = c.ofertaGetFechaInicio(id);
			this.fechaInicio
					.setText(String.format("%02d/%02d/%04d", ini.getDayOfMonth(), ini.getMonthValue(), ini.getYear()));
			LocalDate fin = c.ofertaGetFechaFin(id);
			if (fin == null) {
				this.hasta.setText("Número de meses");
				this.fechaFin.setText(String.valueOf(c.ofertaGetNumMeses(id)));
			} else {
				this.hasta.setText("Hasta");
				this.fechaFin.setText(
						String.format("%02d/%02d%04d", fin.getDayOfMonth(), fin.getMonthValue(), fin.getYear()));
			}
			this.descripcion.setText(c.ofertaGetDescripcion(id));
			this.direccion.setText(c.ofertaGetDireccion(id));
			cargarValoracion(id);
			cargarComentarios(id);
			this.revalidate();
			this.repaint();
		});
	}

	protected void cargarValoracion(Integer id) {
		Controller c = gui.getController();
		Integer val = c.ofertaGetValoracion(id);

		for (int i = 0; i < val; i++) {
			((JLabel) grupoValoracion.getComponent(i)).setIcon(full);
			valoracionInicial[i] = true;
		}

		for (int i = val; i < 5; i++) {
			((JLabel) grupoValoracion.getComponent(i)).setIcon(empty);
			valoracionInicial[i] = false;
		}

		yaValorado = c.ofertaYaTieneValoracion(idOferta);
		if (yaValorado) {
			labelValoracion.setText("Ya has valorado esta oferta");
		} else {
			labelValoracion.setText("Valora esta oferta");
		}

	}

	@Override
	public void registrarEventos() {
		this.aniadirComentario.setOnAction((event) -> {
			SwingUtilities.invokeLater(() -> {
				ComentarDialog dialog = new ComentarDialog(gui, this.idOferta, null);
				dialog.setVisible(true);
				if (dialog.getValue()) {
					VerOferta vo = (VerOferta) gui.getComponent(VerOferta.NAME);
					vo.cargarDatos(idOferta);
				}
			});
		});

		star1.addMouseListener(new StarListener(grupoValoracion, 0));
		star2.addMouseListener(new StarListener(grupoValoracion, 1));
		star3.addMouseListener(new StarListener(grupoValoracion, 2));
		star4.addMouseListener(new StarListener(grupoValoracion, 3));
		star5.addMouseListener(new StarListener(grupoValoracion, 4));

		grupoValoracion.addMouseListener(new StarGroupListener());

		this.contratarBtn.setOnAction((event) -> {
			SwingUtilities.invokeLater(() -> {
				if (this.gui.getController().contratarOferta(idOferta)) {
					ResultadosBusqueda rb = (ResultadosBusqueda) this.gui.getComponent(ResultadosBusqueda.NAME);
					rb.removeOferta(idOferta);
					this.gui.showOnly(Header.NAME, SearchMenu.NAME, ResultadosBusqueda.NAME);
				}
			});
		});

		this.reservarBtn.setOnAction((event) -> {
			SwingUtilities.invokeLater(() -> {
				if (this.gui.getController().reservarOferta(idOferta)) {
					ResultadosBusqueda rb = (ResultadosBusqueda) this.gui.getComponent(ResultadosBusqueda.NAME);
					rb.removeOferta(idOferta);
					this.gui.showOnly(Header.NAME, SearchMenu.NAME, ResultadosBusqueda.NAME);
				}
			});
		});
	}

	private class StarListener implements MouseListener {
		private JPanel group;
		private int index;
		private boolean initialStatus[];

		public StarListener(JPanel group, int index) {
			this.group = group;
			this.index = index;
			initialStatus = new boolean[5];
			for (int i = 0; i < 5; i++) {
				JLabel star = ((JLabel) group.getComponent(i));
				initialStatus[i] = star.getIcon().equals(full);
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (!yaValorado) {
				Controller c = gui.getController();
				c.ofertaValorar(idOferta, index + 1);
				cargarValoracion(idOferta);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (!yaValorado) {
				for (int i = 0; i <= index; i++) {
					((JLabel) group.getComponent(i)).setIcon(full);
				}

				for (int i = index + 1; i < 5; i++) {
					((JLabel) group.getComponent(i)).setIcon(empty);
				}
			}

		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}

	private class StarGroupListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (!yaValorado) {
				JPanel group = (JPanel) e.getSource();
				for (int i = 0; i < 5; i++) {
					JLabel star = ((JLabel) group.getComponent(i));
					if (valoracionInicial[i]) {
						star.setIcon(full);
					} else {
						star.setIcon(empty);
					}
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

	}

}

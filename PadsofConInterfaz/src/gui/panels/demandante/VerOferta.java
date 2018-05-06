package gui.panels.demandante;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
import gui.panels.oferta.comentario.PanelComentario;
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

	private Integer idOferta;

	public VerOferta(Gui gui) {
		this.gui = gui;
		initialize();
		//this.setBackground(Color.RED);

	}

	@Override
	public void setDimension() {
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void crearComponentes() {
		Font precioFont = new Font("Comic Sans", Font.PLAIN, 50);
		Font fianzaFont = new Font("Comic Sans", Font.PLAIN, 20);

		direccion = new JMultiLineLabel("Calle Alcala, nº7, 1B, 28850, Madrid", 200, 30);
		descripcion = new JMultiLineLabel(
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus purus elit, mattis id mauris eu, varius venenatis eros. Vestibulum quis pulvinar odio, vitae ultrices arcu. Nulla et cursus ante, ac rutrum purus. Suspendisse sed ex purus. Donec condimentum leo non interdum consectetur. Curabitur fermentum dolor eu porta sagittis. Nam ut justo quis diam efficitur fringilla. Vivamus a ornare est, eget porttitor mauris. Maecenas faucibus pretium ligula ut tempus. Donec eleifend erat ligula, ut cursus quam lacinia vitae.",
				400, 65);

		desde = new JLabel("Desde");
		fechaInicio = new JLabel("12-12-2012");
		hasta = new JLabel("Hasta o Num Meses");
		fechaFin = new JLabel("22/12/2012");
		precio = new JLabel("300 €");
		fianza = new JLabel("Fianza +150 €");
		total = new JLabel("Total");
		precioTotal = new JLabel("450€");
		aniadirComentario = new FxButton(130, 25, "Añadir Comentario");

		direccion.setAlignmentX(JTextArea.LEFT_ALIGNMENT);
		precio.setFont(precioFont);
		fianza.setFont(fianzaFont);
		total.setFont(fianzaFont.deriveFont(Font.PLAIN, 30));
		Map attributes = fianzaFont.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

		precioTotal.setFont(fianzaFont.deriveFont(attributes).deriveFont(Font.PLAIN, 40));

		comentarios = new JPanel(new LimitedFlowLayout(FlowLayout.RIGHT, 300));

		scrollPane = new JScrollPane(comentarios, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(830, 320));

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

		layout.putConstraint(SpringLayout.WEST, precio, 20, SpringLayout.EAST, descripcion);
		layout.putConstraint(SpringLayout.NORTH, precio, 20, SpringLayout.NORTH, this);

		layout.putConstraint(SpringLayout.WEST, descripcion, 30, SpringLayout.EAST, direccion);
		layout.putConstraint(SpringLayout.NORTH, descripcion, 10, SpringLayout.NORTH, this);

		layout.putConstraint(SpringLayout.WEST, desde, 10, SpringLayout.WEST, direccion);
		layout.putConstraint(SpringLayout.NORTH, desde, 40, SpringLayout.SOUTH, direccion);

		layout.putConstraint(SpringLayout.NORTH, total, 5, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, total, 70, SpringLayout.EAST, precio);

		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, precioTotal, 0, SpringLayout.HORIZONTAL_CENTER, total);
		layout.putConstraint(SpringLayout.NORTH, precioTotal, 5, SpringLayout.SOUTH, total);

		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, scrollPane, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.SOUTH, scrollPane, -10, SpringLayout.SOUTH, this);

		layout.putConstraint(SpringLayout.EAST, aniadirComentario, 0, SpringLayout.EAST, scrollPane);
		layout.putConstraint(SpringLayout.SOUTH, aniadirComentario, -10, SpringLayout.NORTH, scrollPane);

	}

	public void cargarComentarios(Integer id) {
		SwingUtilities.invokeLater(() -> {
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
		});
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
					p.setPreferredSize(new Dimension(panelPadre.getPreferredSize().width - 40,
							panelPadre.getPreferredSize().height));
					break;
				}
			}
		}
		p.revalidate();
		p.repaint();
		comentarios.setPreferredSize(new Dimension(800, comentarios.getComponentCount() * 36));

	}

	public void cargarDatos(Integer id) {
		this.idOferta = id;
		SwingUtilities.invokeLater(() -> {
			Controller c = this.gui.getController();
			this.precio.setText(String.format("%.2f €", c.ofertaGetPrecio(id)));
			this.fianza.setText(String.format("Fianza +%.2f €", c.ofertaGetFianza(id)));
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
			cargarComentarios(id);
		});
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

	}

}

package gui.panels.oferta.comentario;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import gui.Gui;
import gui.components.JMultiLineLabel;
import gui.components.fx.FxButton;
import gui.dialogs.ComentarDialog;
import gui.panels.demandante.VerOferta;
import gui.util.PanelInterfaz;

public class PanelComentario extends PanelInterfaz {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7878894793986097142L;
	private final String texto;
	private final Integer idComentario;
	private int nivel = 0;
	private SpringLayout layout;
	private JMultiLineLabel labelTexto;
	private Integer padre;
	private Integer idOferta;
	private FxButton responer;
	private Gui gui;

	public final static int PANEL_WIDTH = 700;
	public final static int PANEL_HEIGHT = 30;
	private final static int DECREMENTO_POR_NIVEL = 40;

	public PanelComentario(Gui gui, String texto, Integer idComentario, Integer padre, Integer idOferta) {
		this.texto = texto;
		this.idComentario = idComentario;
		this.padre = padre;
		this.gui = gui;
		this.idOferta = idOferta;
		this.setBorder(BorderFactory.createEtchedBorder());
	}

	@Override
	public void setDimension() {
		this.setPreferredSize(new Dimension(PANEL_WIDTH - nivel * DECREMENTO_POR_NIVEL, PANEL_HEIGHT));
	}

	@Override
	public void crearComponentes() {
		labelTexto = new JMultiLineLabel(texto, 500, 20, false);
		responer = new FxButton(80, 25, "Responder");

		this.add(responer);
		this.add(labelTexto);
	}

	@Override
	public void colocarComponentes() {
		layout = new SpringLayout();
		this.setLayout(layout);

		layout.putConstraint(SpringLayout.EAST, responer, -2, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, responer, 0, SpringLayout.VERTICAL_CENTER, this);

		layout.putConstraint(SpringLayout.WEST, labelTexto, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, labelTexto, 0, SpringLayout.VERTICAL_CENTER, this);
	}

	@Override
	public void registrarEventos() {
		responer.setOnAction((event) -> {
			SwingUtilities.invokeLater(() -> {
				ComentarDialog dialog = new ComentarDialog(gui, idOferta, idComentario);
				dialog.setVisible(true);
				if (dialog.getValue()) {
					VerOferta vo = (VerOferta) gui.getComponent(VerOferta.NAME);
					System.out.println(vo);
					vo.cargarComentarios(idOferta);
				}
			});
		});
	}

	public Integer getIdComentario() {
		return idComentario;
	}

	public Integer getPadre() {
		return padre;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
		this.labelTexto.setNewSize(500 - nivel * DECREMENTO_POR_NIVEL, 20);
		setDimension();
	}

}

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

/**
 * Esta es nuestra clase que sirve como panel de comentarios
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class PanelComentario extends PanelInterfaz {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7878894793986097142L;
	
	/**
	 * Texto del comentario
	 */
	private final String texto;
	
	/**
	 * Id del comentario
	 */
	private final Integer idComentario;
	
	/**
	 * Nivel del panel
	 */
	private int nivel = 0;
	
	/**
	 * Layout del panel
	 */
	private SpringLayout layout;
	
	/**
	 * Label que imprime el texto
	 */
	private JMultiLineLabel labelTexto;
	
	/**
	 * Id del comentario padre
	 */
	private Integer padre;
	
	/**
	 * Id de la oferta
	 */
	private Integer idOferta;
	
	/**
	 * Boton para responder
	 */
	private FxButton responer;
	
	/**
	 * La interfaz grafica
	 */
	private Gui gui;

	/**
	 * El ancho del panel
	 */
	public final static int PANEL_WIDTH = 700;
	
	/**
	 * La altura del panel
	 */
	public final static int PANEL_HEIGHT = 30;
	
	/**
	 * El decremento de anchura por nivel
	 */
	private final static int DECREMENTO_POR_NIVEL = 40;

	
	/**
	 * Constructor del PanelComentario
	 * @param gui interfaz grafica
	 * @param texto texto del comentario
	 * @param idComentario id del comentario
	 * @param padre id del comentario padre
	 * @param idOferta id de la oferta
	 */
	public PanelComentario(Gui gui, String texto, Integer idComentario, Integer padre, Integer idOferta) {
		this.texto = texto;
		this.idComentario = idComentario;
		this.padre = padre;
		this.gui = gui;
		this.idOferta = idOferta;
		this.setBorder(BorderFactory.createEtchedBorder());
		initialize();
	}

	/**
	 * Funcion que define las dimensiones de la interfaz
	 */
	@Override
	public void setDimension() {
		int height = this.labelTexto.getPreferredSize().height;
		this.setPreferredSize(new Dimension(PANEL_WIDTH - nivel * DECREMENTO_POR_NIVEL, height + 15));
	}

	/**
	 * Funcion que crea los componentes de la interfaz, y los añade a la interfaz grafica
	 */
	@Override
	public void crearComponentes() {
		labelTexto = new JMultiLineLabel(texto, 500, 20, false);
		responer = new FxButton(80, 25, "Responder");

		this.add(responer);
		this.add(labelTexto);
	}

	/**
	 * Esta funcion  coloca los componentes en la interfaz grafica utilizando un SpringLayout
	 */
	@Override
	public void colocarComponentes() {
		layout = new SpringLayout();
		this.setLayout(layout);

		layout.putConstraint(SpringLayout.EAST, responer, -2, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, responer, 0, SpringLayout.VERTICAL_CENTER, this);

		layout.putConstraint(SpringLayout.WEST, labelTexto, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, labelTexto, 0, SpringLayout.VERTICAL_CENTER, this);
	}

	/**
	 * Esta funcion registra los eventos que ocurren en la interfaz
	 */
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

	/**
	 * Devuelve el id del comentario
	 * @return idComentario id del comentario
	 */
	public Integer getIdComentario() {
		return idComentario;
	}

	/**
	 * Devuelve el id del comentario padre
	 * @return padre id del comentario padre
	 */
	public Integer getPadre() {
		return padre;
	}

	/**
	 * Devuelve el nivel del comentario
	 * @return nivel nivel del comentario
	 */
	public int getNivel() {
		return nivel;
	}

	/**
	 * Cambia el nivel del comentario y redimensiona
	 * @param nivel nivel del comentario
	 */
	public void setNivel(int nivel) {
		this.nivel = nivel;
		this.labelTexto.setNewSize(500 - nivel * DECREMENTO_POR_NIVEL, 20);

		setDimension();
	}

}

package gui.panels.oferta;

import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import gui.Gui;
import gui.components.fx.FxButton;
import gui.panels.Header;
import gui.panels.ofertante.ofertas.EditarOferta;

/**
 * Esta es nuestra clase que sirve como panel de ofertas editables
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class PanelOfertaEditable extends PanelOferta {

	
	private static final long serialVersionUID = -5513253061378866849L;
	
	/**
	 * El boton para editar
	 */
	private FxButton editar;

	/**
	 * Constructor de PanelOfertaEditable
	 * @param gui interfaz grafica
	 * @param idOferta id de la oferta
	 */
	public PanelOfertaEditable(Gui gui, int idOferta) {
		super(gui, idOferta);
	}

	/**
	 * Funcion que crea los componentes de la interfaz, y los añade a la interfaz grafica
	 */
	@Override
	protected void crearComponentes() {
		super.crearComponentes();
		editar = new FxButton(110, 30, "Editar");
		this.add(editar);
	}

	/**
	 * Esta funcion  coloca los componentes en la interfaz grafica utilizando un SpringLayout
	 */
	@Override
	protected void colocarComponentes() {
		super.colocarComponentes();
		layout.putConstraint(SpringLayout.SOUTH, editar, -10, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.EAST, editar, -15, SpringLayout.EAST, this);
	}

	/**
	 * Esta funcion registra los eventos que ocurren en la interfaz
	 */
	@Override
	protected void registrarEventos() {
		editar.setOnAction(e -> {
			SwingUtilities.invokeLater(() -> {
				EditarOferta eo = (EditarOferta) gui.getComponent(EditarOferta.NAME);
				eo.cargarDatos(this);
				gui.showOnly(Header.NAME, EditarOferta.NAME);
			});
		});
	}

}

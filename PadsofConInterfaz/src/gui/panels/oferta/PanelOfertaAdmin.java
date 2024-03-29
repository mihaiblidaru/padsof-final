package gui.panels.oferta;

import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import gui.Gui;
import gui.components.fx.FxButton;
import gui.controllers.Controller;
import gui.panels.admin.AdminView;

/**
 * Esta es nuestra clase que sirve como panel de ofertas para el admin
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class PanelOfertaAdmin extends PanelOferta {

	private static final long serialVersionUID = -5513253061378866849L;

	/**
	 * Boton para rechazar
	 */
	private FxButton rechazar;

	/**
	 * Boton para aceptar
	 */
	private FxButton aceptar;

	/**
	 * Constructor de PanelOfertaAdmin
	 * @param gui interfaz grafica
	 * @param idOferta id de la oferta
	 */
	public PanelOfertaAdmin(Gui gui, int idOferta) {
		super(gui, idOferta);
	}

	/**
	 * Funcion que crea los componentes de la interfaz, y los a�ade a la interfaz grafica
	 */
	@Override
	protected void crearComponentes() {
		super.crearComponentes();
		rechazar = new FxButton(100, 25, "Rechazar");
		aceptar = new FxButton(100, 25, "Aceptar");

		this.add(rechazar);
		this.add(aceptar);
	}

	/**
	 * Esta funcion  coloca los componentes en la interfaz grafica utilizando un SpringLayout
	 */
	@Override
	protected void colocarComponentes() {
		super.colocarComponentes();
		layout.putConstraint(SpringLayout.SOUTH, rechazar, -10, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.EAST, rechazar, -7, SpringLayout.EAST, this);

		layout.putConstraint(SpringLayout.SOUTH, aceptar, 0, SpringLayout.SOUTH, rechazar);
		layout.putConstraint(SpringLayout.EAST, aceptar, -7, SpringLayout.WEST, rechazar);
	}

	/**
	 * Esta funcion registra los eventos que ocurren en la interfaz
	 */
	@Override
	protected void registrarEventos() {
		super.registrarEventos();
		aceptar.setOnAction(e -> {
			SwingUtilities.invokeLater(() -> {
				Controller c = gui.getController();
				c.aceptarOferta(this.getIdOferta());
				AdminView av = (AdminView) gui.getComponent(AdminView.NAME);
				av.getOfertasPendientesTab().removeOferta(this.getIdOferta());

			});
		});

		rechazar.setOnAction(e -> {
			SwingUtilities.invokeLater(() -> {
				Controller c = gui.getController();
				c.rechazarOferta(this.getIdOferta());
				AdminView av = (AdminView) gui.getComponent(AdminView.NAME);
				av.getOfertasPendientesTab().removeOferta(this.getIdOferta());
			});
		});
	}
}

package gui.panels.oferta;

import javax.swing.SpringLayout;

import gui.Gui;
import gui.components.fx.FxButton;

public class PanelOfertaAdmin extends PanelOferta {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5513253061378866849L;
	private FxButton rechazar;
	private FxButton aceptar;
	private FxButton pedirCambios;

	public PanelOfertaAdmin(Gui gui, int idOferta) {
		super(gui, idOferta);
		setListeners();
	}

	@Override
	protected void addButtons() {
		rechazar = new FxButton(100, 25, "Rechazar");
		pedirCambios = new FxButton(120, 25, "Pedir Cambios");
		aceptar = new FxButton(100, 25, "Aceptar");

		this.add(rechazar);
		this.add(aceptar);
		this.add(pedirCambios);
		layout.putConstraint(SpringLayout.SOUTH, rechazar, -10, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.EAST, rechazar, -7, SpringLayout.EAST, this);

		layout.putConstraint(SpringLayout.SOUTH, pedirCambios, 0, SpringLayout.SOUTH, rechazar);
		layout.putConstraint(SpringLayout.EAST, pedirCambios, -7, SpringLayout.WEST, rechazar);

		layout.putConstraint(SpringLayout.SOUTH, aceptar, 0, SpringLayout.SOUTH, pedirCambios);
		layout.putConstraint(SpringLayout.EAST, aceptar, -7, SpringLayout.WEST, pedirCambios);

	}

	private void setListeners() {
		/*
		aceptar.setOnAction(e -> {
			SwingUtilities.invokeLater(() -> {
				EditarOferta eo = (EditarOferta) gui.getComponent(EditarOferta.NAME);
				eo.cargarDatos(this.getIdOferta());
				gui.showOnly(Header.NAME, EditarOferta.NAME);
			});
		});*/

	}

}

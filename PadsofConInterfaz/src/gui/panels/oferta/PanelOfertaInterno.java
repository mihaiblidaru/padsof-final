package gui.panels.oferta;

import javax.swing.SpringLayout;

import gui.Gui;
import gui.components.fx.FxButton;

public class PanelOfertaInterno extends PanelOferta {

	private static final long serialVersionUID = 1832028488484561007L;

	private FxButton editar;

	public PanelOfertaInterno(Gui gui) {
		super(gui);
	}

	@Override
	protected void addButtons() {
		this.editar = new FxButton(70, 30, "EDITAR");
		this.add(editar);
		layout.putConstraint(SpringLayout.SOUTH, editar, -10, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.EAST, editar, -15, SpringLayout.EAST, this);
	}

	@Override
	public void cargarDatos(Integer id) {
		super.cargarDatos(id);
		this.editar.setVisible(this.gui.getController().ofertaGetEditable(id));
	}
}

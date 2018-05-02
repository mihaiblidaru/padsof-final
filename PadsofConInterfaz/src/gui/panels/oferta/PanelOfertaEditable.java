package gui.panels.oferta;

import javax.swing.SpringLayout;

import gui.Gui;
import gui.components.fx.FxButton;

public class PanelOfertaEditable extends PanelOferta {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5513253061378866849L;

	public PanelOfertaEditable(Gui gui) {
		super(gui);
	}

	@Override
	protected void addButtons() {
		FxButton contratar = new FxButton(110, 30, "EDITAR");
		this.add(contratar);
		layout.putConstraint(SpringLayout.SOUTH, contratar, -10, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.EAST, contratar, -15, SpringLayout.EAST, this);
	}

}

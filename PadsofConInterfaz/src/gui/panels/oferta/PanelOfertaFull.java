package gui.panels.oferta;

import javax.swing.SpringLayout;

import gui.Gui;
import gui.components.fx.FxButton;

public class PanelOfertaFull extends PanelOferta {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5513253061378866849L;

	public PanelOfertaFull(Gui gui) {
		super(gui);
	}

	@Override
	protected void addButtons() {
		FxButton contratar = new FxButton(110, 30, "CONTRATAR");
		this.add(contratar);
		layout.putConstraint(SpringLayout.SOUTH, contratar, -10, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.EAST, contratar, -15, SpringLayout.EAST, this);

		FxButton reservar = new FxButton(110, 30, "RESERVAR");
		this.add(reservar);
		layout.putConstraint(SpringLayout.EAST, reservar, -15, SpringLayout.WEST, contratar);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, reservar, 0, SpringLayout.VERTICAL_CENTER, contratar);

		FxButton ver = new FxButton(150, 30, "Ver mas detalles");
		this.add(ver);
		layout.putConstraint(SpringLayout.EAST, ver, -15, SpringLayout.WEST, reservar);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, ver, 0, SpringLayout.VERTICAL_CENTER, contratar);
	}

}

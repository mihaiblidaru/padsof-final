package gui.panels.oferta;

import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import gui.Gui;
import gui.components.fx.FxButton;
import gui.panels.Header;
import gui.panels.ofertante.ofertas.EditarOferta;

public class PanelOfertaEditable extends PanelOferta {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5513253061378866849L;
	private FxButton editar;

	public PanelOfertaEditable(Gui gui, int idOferta) {
		super(gui, idOferta);
		setListeners();
	}

	@Override
	protected void addButtons() {
		editar = new FxButton(110, 30, "EDITAR");
		this.add(editar);
		layout.putConstraint(SpringLayout.SOUTH, editar, -10, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.EAST, editar, -15, SpringLayout.EAST, this);
	}

	private void setListeners() {
		editar.setOnAction(e -> {
			SwingUtilities.invokeLater(() -> {
				EditarOferta eo = (EditarOferta) gui.getComponent(EditarOferta.NAME);
				eo.cargarDatos(this.getIdOferta());
				gui.showOnly(Header.NAME, EditarOferta.NAME);
			});
		});

	}

}

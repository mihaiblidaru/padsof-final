package gui.panels.oferta;

import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import gui.Gui;
import gui.components.fx.FxButton;
import gui.controllers.Controller;
import gui.panels.demandante.MisReservas;

public class PanelOfertaReservadaInterno extends PanelOferta {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5513253061378866849L;
	private FxButton contratar;
	private FxButton cancelar;

	public PanelOfertaReservadaInterno(Gui gui, int idOferta) {
		super(gui, idOferta);
		setListeners();
	}

	@Override
	protected void addButtons() {
		JPanel contenedorBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));

		contratar = new FxButton(110, 30, "Contratar");
		cancelar = new FxButton(110, 30, "Cancelar");
		contenedorBotones.add(cancelar);
		contenedorBotones.add(contratar);

		this.add(contenedorBotones);

		layout.putConstraint(SpringLayout.EAST, contenedorBotones, -10, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, contenedorBotones, -10, SpringLayout.SOUTH, this);
	}

	private void setListeners() {
		contratar.setOnAction(e -> {
			SwingUtilities.invokeLater(() -> {
				Controller c = gui.getController();
				if (c.contratarOferta(this.getIdOferta())) {
					MisReservas mr = (MisReservas) gui.getComponent(MisReservas.NAME);
					mr.removeReserva(this.getIdOferta());
					mr.addContratada(this.getIdOferta());
				}
			});
		});

		cancelar.setOnAction(e -> {
			SwingUtilities.invokeLater(() -> {
				Controller c = gui.getController();
				if (c.demandanteCancelarReserva()) {
					MisReservas mr = (MisReservas) gui.getComponent(MisReservas.NAME);
					mr.removeReserva(this.getIdOferta());
				}
			});
		});
	}

}

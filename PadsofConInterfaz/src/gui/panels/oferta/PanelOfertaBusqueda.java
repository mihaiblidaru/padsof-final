package gui.panels.oferta;

import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import app.clases.ofertas.Estado;
import gui.Gui;
import gui.components.fx.FxButton;
import gui.controllers.Controller;
import gui.panels.ResultadosBusqueda;

public class PanelOfertaBusqueda extends PanelOferta {

	private static final long serialVersionUID = -5513253061378866849L;
	private FxButton contratar;
	private FxButton reservar;
	private FxButton ver;

	public PanelOfertaBusqueda(Gui gui, int idOferta) {
		super(gui, idOferta);
		setListeners();
	}

	private void setListeners() {
		contratar.setOnAction(e -> {
			SwingUtilities.invokeLater(() -> {
				if (gui.getController().contratarOferta(this.getIdOferta())) {
					ResultadosBusqueda resBusqueda = (ResultadosBusqueda) gui.getComponent(ResultadosBusqueda.NAME);
					resBusqueda.removeOferta(this.getIdOferta());
				}
			});
		});
	}

	@Override
	protected void addButtons() {
		JPanel contenedorBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));

		contratar = new FxButton(110, 30, "CONTRATAR");
		reservar = new FxButton(110, 30, "RESERVAR");
		ver = new FxButton(150, 30, "Ver mas detalles");
		contenedorBotones.add(ver);
		contenedorBotones.add(reservar);
		contenedorBotones.add(contratar);
		contratar.setVisible(false);
		reservar.setVisible(false);
		ver.setVisible(false);
		this.add(contenedorBotones);
		layout.putConstraint(SpringLayout.EAST, contenedorBotones, -10, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, contenedorBotones, -10, SpringLayout.SOUTH, this);

	}

	@Override
	protected void cargarDatos(Integer id) {
		super.cargarDatos(id);
		actualizarBotones();
	}

	public void actualizarBotones() {
		Controller c = this.gui.getController();
		if (c.tienePermisosDemandante()) {
			Estado estado = c.ofertaGetEstado(this.getIdOferta());
			reservar.setVisible(true);
			ver.setVisible(true);
			if (estado == Estado.RESERVADA) {
				reservar.setText("Reservada");
				reservar.setDisable(true);
			} else {
				contratar.setVisible(true);
			}
		}
	}

}

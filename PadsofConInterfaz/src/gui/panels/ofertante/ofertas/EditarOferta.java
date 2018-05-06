package gui.panels.ofertante.ofertas;

import java.time.LocalDate;

import javax.swing.SwingUtilities;

import gui.Gui;
import gui.controllers.Controller;
import gui.listeners.EditarOfertaListener;
import gui.panels.oferta.PanelOferta;
import gui.util.LimitCounter;
import gui.util.Nombrable;
import gui.util.ParameterReference;

public class EditarOferta extends AniadirOferta implements Nombrable {

	private static final long serialVersionUID = 2220134063340646027L;

	public final static String NAME = "PANEL_EDITAR_OFERTA";

	private ParameterReference<PanelOferta> panelOferta = new ParameterReference<>();

	public EditarOferta(Gui gui) {
		super(gui);
	}

	@Override
	public void setVisible(boolean state) {
		super.setVisible(state);
	}

	@Override
	public void crearComponentes() {
		super.crearComponentes();
		this.titulo.setText("Editar Oferta");
		this.warningIcon.setVisible(false);
		this.alarm.setVisible(false);
	}

	public void cargarDatos(PanelOferta panel) {
		SwingUtilities.invokeLater(() -> {
			int id = panel.getIdOferta();
			Controller c = gui.getController();
			precioTextBox.setText(String.valueOf(c.ofertaGetPrecio(id)));
			fianzaTextBox.setText(String.valueOf(c.ofertaGetFianza(id)));
			descripcionTextBox.setText(c.ofertaGetDescripcion(id));
			desdeDatePicker.setValue(c.ofertaGetFechaInicio(id));
			LocalDate fechaFin = c.ofertaGetFechaFin(id);
			comboBoxInmueble.removeAllItems();
			comboBoxInmueble.addItem(c.ofertaGetDireccion(id));
			comboBoxInmueble.addItem("dummy");
			comboBoxInmueble.setSelectedIndex(0);

			if (fechaFin == null) {
				checkBoxVivienda.setSelected(true);
				checkBoxVacacional.setSelected(false);
				hasta.setText("Meses");
				hastaDatePicker.setVisible(false);
				mesesTextField.setVisible(true);
				mesesTextField.setText(String.valueOf(c.ofertaGetNumMeses(id)));
			} else {
				hasta.setText("Hasta");
				checkBoxVacacional.setSelected(true);
				checkBoxVivienda.setSelected(false);
				hastaDatePicker.setVisible(true);
				mesesTextField.setVisible(false);
				hastaDatePicker.setValue(fechaFin);
			}
			checkBoxVacacional.setDisable(true);
			checkBoxVivienda.setDisable(true);
			comboBoxInmueble.setEnabled(false);
			this.panelOferta.setValue(panel);

		});
	}

	@Override
	public void registrarEventos() {
		confirmar.setOnAction(new EditarOfertaListener(gui, panelOferta, precioTextBox, fianzaTextBox, desdeDatePicker,
				hastaDatePicker, descripcionTextBox, mesesTextField, checkBoxVacacional));

		descripcionTextBox.getDocument().addDocumentListener(new LimitCounter(limitLabel, limit));
	}

}
package gui.listeners;

import java.time.LocalDate;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import gui.Gui;
import gui.components.fx.FxCheckBox;
import gui.components.fx.FxDatePicker;
import gui.components.fx.FxTextField;
import gui.controllers.Controller;
import gui.panels.Header;
import gui.panels.oferta.PanelOferta;
import gui.panels.ofertante.ofertas.MisOfertas;
import gui.util.DialogFactory;
import gui.util.ParameterReference;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class EditarOfertaListener implements EventHandler<ActionEvent> {
	private final Gui gui;
	private final FxTextField precioTextBox;
	private final FxTextField fianzaTextBox;
	private final FxDatePicker desdeDatePicker;
	private final FxDatePicker hastaDatePicker;
	private final JTextArea descripcionTextBox;
	private final FxTextField mesesTextField;
	private final FxCheckBox checkBoxVacacional;
	private final ParameterReference<PanelOferta> panelOferta;

	public EditarOfertaListener(Gui gui, ParameterReference<PanelOferta> panelOferta, FxTextField precioTextBox,
			FxTextField fianzaTextBox, FxDatePicker desdeDatePicker, FxDatePicker hastaDatePicker,
			JTextArea descripcionTextBox, FxTextField mesesTextField, FxCheckBox checkBoxVacacional) {
		super();
		this.gui = gui;
		this.precioTextBox = precioTextBox;
		this.fianzaTextBox = fianzaTextBox;
		this.desdeDatePicker = desdeDatePicker;
		this.hastaDatePicker = hastaDatePicker;
		this.descripcionTextBox = descripcionTextBox;
		this.mesesTextField = mesesTextField;
		this.checkBoxVacacional = checkBoxVacacional;
		this.panelOferta = panelOferta;
	}

	@Override
	public void handle(ActionEvent event) {
		SwingUtilities.invokeLater(() -> {
			Controller c = gui.getController();
			String precio = precioTextBox.getText();
			String fianza = fianzaTextBox.getText();
			String descripcion = descripcionTextBox.getText();
			LocalDate desde = desdeDatePicker.getValue();

			Float precioAsNum = Float.parseFloat(precio);
			Float fianzaAsNum = Float.parseFloat(fianza);

			int idOferta = panelOferta.getValue().getIdOferta();

			if (precio.isEmpty()) {
				DialogFactory.emptyFieldError("precio");
				return;
			} else if (precioAsNum.isInfinite() || precioAsNum.isNaN() || precioAsNum.compareTo(0.00001f) < 0) {
				DialogFactory.invalidValueError("precio");
				precioTextBox.setText("");
				return;
			} else if (fianza.isEmpty()) {
				DialogFactory.emptyFieldError("fianza");
				return;
			} else if (fianzaAsNum.isInfinite() || fianzaAsNum.isNaN() || fianzaAsNum.compareTo(0.00001f) < 0) {
				DialogFactory.invalidValueError("fianza");
				fianzaTextBox.setText("");
				return;
			} else if (descripcion.isEmpty()) {
				DialogFactory.emptyFieldError("descripcion");
				return;
			} else if (desde == null) {
				DialogFactory.emptyFieldError("desde");
				return;
			}

			if (checkBoxVacacional.isSelected()) {
				LocalDate hasta = hastaDatePicker.getValue();
				if (hasta == null) {
					DialogFactory.emptyFieldError("hasta");
					return;
				} else if (desde.compareTo(hasta) > 0) {
					DialogFactory.simpleErrorMessage("La fecha final no puede ser anterior a la fecha de inicio");
					hastaDatePicker.setValue(null);
					return;
				}

				c.ofertaSetDescripcion(idOferta, descripcion);
				c.ofertaSetFianza(idOferta, fianzaAsNum);
				c.ofertaSetPrecio(idOferta, precioAsNum);
				c.ofertaSetFechaInicio(idOferta, desde);
				c.ofertaSetFechaFin(idOferta, hasta);
				this.panelOferta.getValue().cargarDatos(idOferta);
				gui.showOnly(Header.NAME, MisOfertas.NAME);

			} else {
				String numMeses = mesesTextField.getText();

				if (numMeses.isEmpty()) {
					DialogFactory.emptyFieldError("Meses");
					return;
				}
				Integer mesesAsNum = null;
				try {
					mesesAsNum = Integer.parseInt(numMeses);
				} catch (NumberFormatException e) {
					DialogFactory.invalidValueError("meses");
					mesesTextField.setText("");
					return;
				}

				if (mesesAsNum < 1) {
					DialogFactory.invalidValueError("meses");
					mesesTextField.setText("");
					return;
				}

				c.ofertaSetDescripcion(idOferta, descripcion);
				c.ofertaSetFianza(idOferta, fianzaAsNum);
				c.ofertaSetPrecio(idOferta, precioAsNum);
				c.ofertaSetFechaInicio(idOferta, desde);
				c.ofertaSetNumMeses(idOferta, mesesAsNum);
				this.panelOferta.getValue().cargarDatos(idOferta);
				gui.showOnly(Header.NAME, MisOfertas.NAME);
			}
		});
	}
}

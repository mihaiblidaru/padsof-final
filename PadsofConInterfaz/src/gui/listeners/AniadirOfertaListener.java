package gui.listeners;

import java.time.LocalDate;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import app.clases.users.UsuarioNoPermisoException;
import gui.Gui;
import gui.components.fx.FxCheckBox;
import gui.components.fx.FxDatePicker;
import gui.components.fx.FxTextField;
import gui.controllers.Controller;
import gui.panels.Header;
import gui.panels.ofertante.ofertas.MisOfertas;
import gui.util.DialogFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class AniadirOfertaListener implements EventHandler<ActionEvent> {
	private final Gui gui;
	private final FxTextField precioTextBox;
	private final FxTextField fianzaTextBox;
	private final FxDatePicker desdeDatePicker;
	private final FxDatePicker hastaDatePicker;
	private final JTextArea descripcionTextBox;
	private final FxTextField mesesTextField;
	private final FxCheckBox checkBoxVacacional;
	private final JComboBox<String> comboBoxInmuebles;
	private final List<Integer> inmuebles;

	public AniadirOfertaListener(Gui gui, FxTextField precioTextBox, FxTextField fianzaTextBox,
			FxDatePicker desdeDatePicker, FxDatePicker hastaDatePicker, JTextArea descripcionTextBox,
			FxTextField mesesTextField, FxCheckBox checkBoxVacacional, JComboBox<String> comboBoxInmuebles,
			List<Integer> inmuebles) {
		super();
		this.gui = gui;
		this.precioTextBox = precioTextBox;
		this.fianzaTextBox = fianzaTextBox;
		this.desdeDatePicker = desdeDatePicker;
		this.hastaDatePicker = hastaDatePicker;
		this.descripcionTextBox = descripcionTextBox;
		this.mesesTextField = mesesTextField;
		this.checkBoxVacacional = checkBoxVacacional;
		this.comboBoxInmuebles = comboBoxInmuebles;
		this.inmuebles = inmuebles;
	}

	@Override
	public void handle(ActionEvent event) {
		SwingUtilities.invokeLater(() -> {
			Controller c = gui.getController();
			String precio = precioTextBox.getText();
			String fianza = fianzaTextBox.getText();
			String descripcion = descripcionTextBox.getText();
			LocalDate desde = desdeDatePicker.getValue();
			int index = comboBoxInmuebles.getSelectedIndex();
			int idInmueble = inmuebles.get(index);

			if (precio.isEmpty()) {
				DialogFactory.emptyFieldError("precio");
				return;
			} else if (fianza.isEmpty()) {
				DialogFactory.emptyFieldError("fianza");
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
				}
				try {
					c.addOfertaVacacional(desde, hasta, Float.parseFloat(precio), Float.parseFloat(fianza), descripcion,
							idInmueble);
					gui.showOnly(Header.NAME, MisOfertas.NAME);
				} catch (NumberFormatException | UsuarioNoPermisoException e) {
					e.printStackTrace();
				}
			} else {
				String numMeses = mesesTextField.getText();
				if (numMeses.isEmpty()) {
					DialogFactory.emptyFieldError("Meses");
					return;
				}
				try {
					c.addOfertaVivienda(desde, Integer.parseInt(numMeses), Float.parseFloat(precio),
							Float.parseFloat(fianza), descripcion, idInmueble);
					gui.showOnly(Header.NAME, MisOfertas.NAME);
				} catch (NumberFormatException | UsuarioNoPermisoException e) {
					e.printStackTrace();
				}
			}

		});
	}

}

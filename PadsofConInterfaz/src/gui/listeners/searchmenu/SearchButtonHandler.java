package gui.listeners.searchmenu;

import java.time.LocalDate;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gui.Gui;
import gui.components.fx.FxCheckBox;
import gui.components.fx.FxDatePicker;
import gui.components.fx.FxTextField;
import gui.controllers.Controller;
import gui.panels.ResultadosBusqueda;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SearchButtonHandler implements EventHandler<ActionEvent> {
	private final Gui gui;
	private final FxTextField localidadOCPField;
	private final FxDatePicker desdeDatePicker;
	private final FxDatePicker hastaDatePicker;
	private final FxCheckBox checkBoxVacacional;
	private static final String regexOnlyDigits = "^\\d*\\d";

	public SearchButtonHandler(Gui gui, FxTextField localidadOCPField, FxDatePicker desdeDatePicker,
			FxDatePicker hastaDatePicker, FxCheckBox checkBoxVacacional) {
		super();
		this.gui = gui;
		this.localidadOCPField = localidadOCPField;
		this.desdeDatePicker = desdeDatePicker;
		this.hastaDatePicker = hastaDatePicker;
		this.checkBoxVacacional = checkBoxVacacional;
	}

	@Override
	public void handle(ActionEvent event) {
		Controller controller = gui.getController();
		String localidad = localidadOCPField.getText();
		List<Integer> resultados;
		if (localidad.isEmpty()) {
			JOptionPane.showMessageDialog(new JPanel(), "El campo localidad no puede estar vacio", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		LocalDate desde = desdeDatePicker.getValue();

		if (desde == null) {
			JOptionPane.showMessageDialog(new JPanel(), "La fecha de inicio no puede estar vacia", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		String arg1 = null;
		Integer arg2 = null;
		if (localidad.matches(regexOnlyDigits)) {
			arg2 = Integer.parseInt(localidad);
		} else {
			arg1 = localidad;
		}

		if (checkBoxVacacional.isSelected()) {
			LocalDate hasta = hastaDatePicker.getValue();
			if (hasta == null) {
				JOptionPane.showMessageDialog(new JPanel(), "La fecha final no puede estar vacia", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			resultados = controller.buscarVacacional(arg1, arg2, desde, hasta);
		} else {

			resultados = controller.buscarViviendas(arg1, arg2, desde);
		}

		ResultadosBusqueda rb = (ResultadosBusqueda) gui.getComponent(ResultadosBusqueda.NAME);
		rb.cargarResultados(resultados);
	}
}

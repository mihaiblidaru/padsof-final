package gui.listeners;

import java.time.LocalDate;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import gui.Gui;
import gui.components.fx.FxCheckBox;
import gui.components.fx.FxDatePicker;
import gui.components.fx.FxTextField;
import gui.controllers.Controller;
import gui.panels.ResultadosBusqueda;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Esta clase nos sirve para trabajar con el listener de login
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 */
public class SearchButtonHandler implements EventHandler<ActionEvent> {
	/**
	 * Interfaz graficca
	 */
	private final Gui gui;
	
	/**
	 * Aqui ponemos la localidad o el codigo postal
	 */
	private final FxTextField localidadOCPField;
	
	/**
	 * Para seleccionar la fecha inicial
	 */
	private final FxDatePicker desdeDatePicker;
	
	/**
	 * Para seleccionar la fecha final
	 */
	private final FxDatePicker hastaDatePicker;
	
	/**
	 * Para marcar si es vacacional
	 */
	private final FxCheckBox checkBoxVacacional;
	
	/**
	 * Expresion regular para filtrar solo numeros
	 */
	private static final String regexOnlyDigits = "^\\d*\\d";

	/**
	 * Constructor de SearchButtonHandler
	 * @param gui interfaz grafica
	 * @param localidadOCPField Aqui ponemos la localidad o el codigo postal
	 * @param desdeDatePicker Para seleccionar la fecha inicial
	 * @param hastaDatePicker Para seleccionar la fecha final
	 * @param checkBoxVacacional Para marcar si es vacacional
	 */
	public SearchButtonHandler(Gui gui, FxTextField localidadOCPField, FxDatePicker desdeDatePicker,
			FxDatePicker hastaDatePicker, FxCheckBox checkBoxVacacional) {
		super();
		this.gui = gui;
		this.localidadOCPField = localidadOCPField;
		this.desdeDatePicker = desdeDatePicker;
		this.hastaDatePicker = hastaDatePicker;
		this.checkBoxVacacional = checkBoxVacacional;
	}

	/**
	 * Esta funcion haceuna busqueda con los datos obtenidos con la clase
	 * @param event evento realizado en la interfaz
	 */
	@Override
	public void handle(ActionEvent event) {
		SwingUtilities.invokeLater(() -> {
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
		});
	}
}

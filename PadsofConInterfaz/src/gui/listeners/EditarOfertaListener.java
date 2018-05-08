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
/**
 * Esta clase nos sirve para trabajar con el listener de aniadir oferta
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 */
public class EditarOfertaListener implements EventHandler<ActionEvent> {
	
	/**
	 * Interfaz grafica
	 */
	private final Gui gui;
	
	/**
	 * Para aniadir el precio
	 */
	private final FxTextField precioTextBox;
	
	/**
	 * Para aniadir la fianza
	 */
	private final FxTextField fianzaTextBox;
	
	/**
	 * Para seleccionar la fecha inicial
	 */
	private final FxDatePicker desdeDatePicker;
	
	/**
	 * Para seleccionar la fecha final
	 */
	private final FxDatePicker hastaDatePicker;
	
	/**
	 * Para aniadir la descripcion
	 */
	private final JTextArea descripcionTextBox;
	
	/**
	 * Para aniadir los meses
	 */
	private final FxTextField mesesTextField;
	
	/**
	 * Para marcar si es vacacional
	 */
	private final FxCheckBox checkBoxVacacional;
	
	/**
	 * El panel de la oferta
	 */
	private final ParameterReference<PanelOferta> panelOferta;

	/**
	 * Constructor de EdirtarOfertaListener
	 * @param gui interfaz grafica
	 * @param panelOferta
	 * @param fianzaTextBox para aniadur fianza
	 * @param desdeDatePicker para aniadur desde
	 * @param hastaDatePicker para aniadur hasta 
	 * @param descripcionTextBox para aniadur descripcion
	 * @param mesesTextField para aniadur meses
	 * @param checkBoxVacacional para aniadur si es vacacional
	 */
	
	
	 * @param precioTextBox para aniadur precio
	 
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

package gui.components.fx;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

/**
 * Clase que permite usa DatePicker's
 * de javafx en swing
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class FxDatePicker extends FxWrapper {

	private static final long serialVersionUID = -6084390837327862411L;
	/**
	 * Texto indicativo del datepicker
	 */
	private final String placeholder;

	/**
	 * El datepicker de este panel
	 */
	private DatePicker datePicker = null;

	/**
	 * Crea un nuevo DatePicker
	 * 
	 * @param width
	 *            ancho del datepicker
	 * @param height
	 *            alto del datepicker
	 * @param placeholder
	 *            texto indicativo del datepicker
	 */
	public FxDatePicker(int width, int height, String placeholder) {
		super(width, height);
		this.placeholder = placeholder;
		JFXPanel panel = this;

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Scene scene = createScene();
				panel.setScene(scene);
			}
		});

	}

	/**
	 * Inicializa el componente de javafx
	 * 
	 * @return el componente creado
	 */
	private Scene createScene() {
		Group root = new Group();
		Scene scene = new Scene(root, this.getWidth(), this.getHeight());
		this.datePicker = new DatePicker();
		datePicker.setConverter(new StringConverter<LocalDate>() {
			private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			@Override
			public String toString(LocalDate localDate) {
				if (localDate == null)
					return "";
				return dateTimeFormatter.format(localDate);
			}

			@Override
			public LocalDate fromString(String dateString) {
				if (dateString == null || dateString.trim().isEmpty()) {
					return null;
				}
				return LocalDate.parse(dateString, dateTimeFormatter);
			}
		});

		datePicker.setPromptText(this.placeholder);
		datePicker.setPrefWidth(this.getWidth());
		datePicker.setPrefHeight(this.getHeight());
		datePicker.setMinSize(DatePicker.USE_PREF_SIZE, DatePicker.USE_PREF_SIZE);
		this.node = datePicker;
		this.fixNavigation(datePicker);
		root.getChildren().add(datePicker);
		return scene;
	}

	/**
	 * Devuelve el valor del datepicker
	 * 
	 * @return la fecha del datepicker
	 */
	public LocalDate getValue() {
		return this.datePicker.getValue();
	}

	/**
	 * Cambia la fecha del datepicker
	 * 
	 * @param value
	 *            nueva fecha del datepicker
	 */
	public final void setValue(LocalDate value) {
		datePicker.setValue(value);
	}

}

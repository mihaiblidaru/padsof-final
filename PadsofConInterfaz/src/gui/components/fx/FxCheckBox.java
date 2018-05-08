package gui.components.fx;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

/**
 * Clase que permite usa CheckBox'es
 * de javafx en swing
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class FxCheckBox extends FxWrapper {
	private static final long serialVersionUID = -6084390837327862411L;
	/**
	 * Texto que acompaña al checkbox
	 */
	private final String placeholder;

	/**
	 * El checbox en si
	 */
	private CheckBox checkBox = null;

	/**
	 * Crea un nuevo checkbox
	 * 
	 * @param width
	 *            ancho del checkbox + texto
	 * @param height
	 *            alto del checkbox + texto
	 * @param placeholder
	 *            texto que acompaña al checkbox
	 */
	public FxCheckBox(int width, int height, String placeholder) {
		super(width, height);
		this.placeholder = placeholder;
		JFXPanel panel = this;

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Scene scene = createScene();
				panel.setScene(scene);
				panel.setVisible(true);
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
		this.checkBox = new CheckBox();
		checkBox.setText(this.placeholder);
		checkBox.setPrefWidth(this.getWidth());
		checkBox.setPrefHeight(this.getHeight());
		checkBox.setMinSize(TextField.USE_PREF_SIZE, TextField.USE_PREF_SIZE);
		this.fixNavigation(checkBox);
		this.node = checkBox;
		root.getChildren().add(checkBox);
		return scene;
	}

	/**
	 * Devuelve si el checkbox está selecionado o no
	 * 
	 * @return si el checkbox está selecionado o no
	 */
	public final boolean isSelected() {
		return checkBox.isSelected();
	}

	/**
	 * Cambia el estado de seleccion del checkbox
	 * 
	 * @param value
	 *            nuevo valor del checkbox
	 */
	public final void setSelected(boolean value) {
		Platform.runLater(() -> checkBox.setSelected(value));
	}

	/**
	 * Cambia el manejador del evento que se dispara cuando el usuario
	 * hace click en el checkbox
	 * 
	 * @param arg0
	 *            manejador del evento
	 */
	public final void setOnAction(EventHandler<ActionEvent> arg0) {
		Platform.runLater(() -> checkBox.setOnAction(arg0));
	}
}

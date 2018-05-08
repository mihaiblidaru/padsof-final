package gui.components.fx;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Clase que permite usar campos de contraseña
 * de javafx en swing
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class FxPasswordField extends FxWrapper {
	private static final long serialVersionUID = -6084390837327862411L;
	/**
	 * Texto indicativo del campo
	 */
	private final String placeholder;

	/**
	 * El componente del panel
	 */
	private PasswordField textfield = null;

	/**
	 * Crea un nuevo campo para contraseñas
	 * 
	 * @param width
	 *            ancho del campo
	 * @param height
	 *            alto del campo
	 * @param placeholder
	 *            texto indicativo del campo
	 */
	public FxPasswordField(int width, int height, String placeholder) {
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
		this.textfield = new PasswordField();
		textfield.setPromptText(this.placeholder);
		textfield.setPrefWidth(this.getWidth());
		textfield.setPrefHeight(this.getHeight());
		textfield.setMinSize(TextField.USE_PREF_SIZE, TextField.USE_PREF_SIZE);
		this.node = textfield;
		this.fixNavigation(textfield);
		root.getChildren().add(textfield);
		return scene;
	}

	/**
	 * Devuelve el texto del campo de texto
	 * 
	 * @return el texto del campo de texto
	 */
	public String getText() {
		return this.textfield.getText();
	}

	/**
	 * Cambia el texto del campo de texto
	 * 
	 * @param value
	 *            nuevo valor del texto
	 */
	public void setText(String value) {
		this.textfield.setText(value);
	}

}

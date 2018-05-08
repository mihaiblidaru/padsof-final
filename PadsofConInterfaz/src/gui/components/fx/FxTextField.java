package gui.components.fx;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

/**
 * Clase que permite usar campos de texto
 * de javafx en swing
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class FxTextField extends FxWrapper {
	private static final long serialVersionUID = -6084390837327862411L;
	/**
	 * Texto indicativo del campo
	 */
	private final String placeholder;

	/**
	 * El componente del panel
	 */
	private TextField textfield = null;
	/**
	 * Expresion regular para filtar solo numertos con decimales
	 */
	private final static String onlyFloatRegex = "^[0-9]*(?:\\.[0-9]*)?";

	public FxTextField(int width, int height, String placeholder) {
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
		this.textfield = new TextField();
		textfield.setPromptText(this.placeholder);
		textfield.setPrefWidth(this.getWidth());
		textfield.setPrefHeight(this.getHeight());
		textfield.setMinSize(TextField.USE_PREF_SIZE, TextField.USE_PREF_SIZE);
		this.fixNavigation(textfield);
		this.node = textfield;
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
		Platform.runLater(() -> this.textfield.setText(value));

	}

	/**
	 * limita este campo de text a solo numeros enteros positivos
	 */
	public void setOnlyInteger() {
		Platform.runLater(() -> {
			textfield.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (!newValue.matches("^\\d*\\d") && !newValue.isEmpty()) {
						textfield.setText(oldValue);
					}
				}
			});
		});
	}

	/**
	 * Limita este campo de texto a solo numeros positivos con decimales
	 */
	public void setOnlyFloat() {
		Platform.runLater(() -> {
			textfield.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (!newValue.matches(onlyFloatRegex)) {
						textfield.setText(oldValue);
					}
				}
			});
		});
	}

	/**
	 * Impone un limite máximo de caracteres en este campo
	 * 
	 * @param limit
	 *            limite de caracteres
	 */
	public void setCharLimit(int limit) {
		Platform.runLater(() -> {
			textfield.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (newValue.length() > limit) {
						textfield.setText(oldValue);
					}
				}
			});
		});
	}

}

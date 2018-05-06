package gui.components.fx;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

public class FxTextField extends FxWrapper {
	private static final long serialVersionUID = -6084390837327862411L;
	private final String placeholder;
	private TextField textfield = null;
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

	public String getText() {
		return this.textfield.getText();

	}

	public void setText(String value) {
		Platform.runLater(() -> this.textfield.setText(value));

	}

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

package gui.components.fx;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class FxCheckBox extends FxWrapper {
	private static final long serialVersionUID = -6084390837327862411L;
	private final String placeholder;
	private CheckBox checkBox = null;

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

	public String getText() {
		return this.checkBox.getText();

	}

	public void setText(String value) {
		Platform.runLater(() -> this.checkBox.setText(value));

	}

	public final boolean isSelected() {
		return checkBox.isSelected();
	}

	public final void setSelected(boolean value) {
		Platform.runLater(() -> checkBox.setSelected(value));
	}

	public final void setOnAction(EventHandler<ActionEvent> arg0) {
		Platform.runLater(() -> checkBox.setOnAction(arg0));
	}

	public void fire() {
		checkBox.fire();
	}

}

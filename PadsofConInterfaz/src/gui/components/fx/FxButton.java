package gui.components.fx;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FxButton extends FxWrapper {
	private static final long serialVersionUID = -6084390837327862411L;
	private final String text;
	private Button button = null;

	public FxButton(int width, int height, String text) {
		super(width, height);
		this.text = text;
		JFXPanel panel = this;

		Scene scene = createScene();
		panel.setScene(scene);

	}

	private Scene createScene() {
		Group root = new Group();
		Scene scene = new Scene(root, this.getWidth(), this.getHeight());
		this.button = new Button(text);
		button.setPrefWidth(this.getWidth());
		button.setPrefHeight(this.getHeight());
		button.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
		button.setEllipsisString("");
		button.setStyle(String.format("-fx-font-size: %dpx;", (int) (0.5 * this.getHeight())));
		this.node = button;
		this.fixNavigation(button);
		root.getChildren().add(button);
		return scene;
	}

	public void setFontScale(double scale) {
		button.setStyle(String.format("-fx-font-size: %dpx;", (int) (scale * this.getHeight())));
	}

	public void setGraphics(String path, int w, int h) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Image image = null;
				try {
					image = new Image(new FileInputStream(path));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ImageView imageView = new ImageView(image);
				imageView.setFitWidth(w);
				imageView.setFitHeight(h);
				button.setGraphic(imageView);
				imageView.setVisible(true);
			}
		});

	}

	public final void setOnAction(EventHandler<ActionEvent> arg0) {
		button.setOnAction(arg0);
	}

	public final void setContentDisplay(ContentDisplay arg0) {
		button.setContentDisplay(arg0);
	}

	public void fire() {
		button.fire();
	}

}

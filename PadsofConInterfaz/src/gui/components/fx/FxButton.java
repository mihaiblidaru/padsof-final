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

/**
 * Clase que permite usar Botones
 * de javafx en swing
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class FxButton extends FxWrapper {
	private static final long serialVersionUID = -6084390837327862411L;
	/**
	 * Texto del boton
	 */
	private final String text;

	/**
	 * Componente
	 */
	private Button button = null;

	/**
	 * Crea un nuevo Boton
	 * 
	 * @param width
	 *            ancho del boton
	 * @param height
	 *            alto del boton
	 * @param text
	 *            texto del boton
	 */
	public FxButton(int width, int height, String text) {
		super(width, height);
		this.text = text;
		JFXPanel panel = this;

		Scene scene = createScene();
		panel.setScene(scene);

	}

	/**
	 * Inicializa el componente de javafx
	 * 
	 * @return el componente creado
	 */
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

	/**
	 * Cambia la escala de la fuente usada en este boton
	 * 
	 * @param scale
	 *            escala de la fuente
	 */
	public void setFontScale(double scale) {
		button.setStyle(String.format("-fx-font-size: %dpx;", (int) (scale * this.getHeight())));
	}

	/**
	 * Añade una imagen al boton
	 * 
	 * @param path
	 *            ruta al archivo
	 * @param w
	 *            ancho de la imagen
	 * @param h
	 *            alto de la imagen
	 */
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

	/**
	 * Añade un manejador de evento para cuando se hace click en el boton
	 * 
	 * @param arg0
	 *            manejador de enentos
	 */
	public final void setOnAction(EventHandler<ActionEvent> arg0) {
		button.setOnAction(arg0);
	}

	/**
	 * Cambia el lado donde se muestran los graficos del boton
	 * 
	 * @param arg0
	 *            ajustes para el grafico del boton
	 */
	public final void setContentDisplay(ContentDisplay arg0) {
		button.setContentDisplay(arg0);
	}

	/**
	 * Simula el hacer click en el boton
	 */
	public void fire() {
		button.fire();
	}

	/**
	 * Cambia el texto del boton
	 * 
	 * @param string
	 *            nuevo texto
	 */
	public void setText(String string) {
		Platform.runLater(() -> button.setText(string));
	}

}

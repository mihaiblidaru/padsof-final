package gui.components.fx;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Clase abstracta usada para poder traer de forma facil
 * componentes de javaFx a Swing usando la clase JFXPanel
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public abstract class FxWrapper extends JFXPanel {

	private static final long serialVersionUID = -5416489256571893215L;

	/**
	 * Ancho fijo del componente
	 */
	private final int width;

	/**
	 * Alto fijo del componente
	 */
	private final int height;

	/**
	 * El componente interno de javafx
	 */
	protected Node node;

	/**
	 * Estado de la navegacion entre componentes
	 */
	private boolean navigationFixed = false;

	/**
	 * Crea un nuevo JFXPanel que va a envolver a un componente de javafx
	 * 
	 * @param width
	 *            ancho del componente
	 * @param height
	 *            alto del componente
	 */
	protected FxWrapper(int width, int height) {
		this.width = width;
		this.height = height;
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.GREEN);
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	/**
	 * Añade un manejador de eventos al comonente
	 * 
	 * @param <T> tipo de evento
	 * @param eventType
	 *            tipo de evento a escuchar
	 * @param eventHandler
	 *            manejador del evento
	 */
	public <T extends Event> void addEventHandler(EventType<T> eventType, EventHandler<? super T> eventHandler) {
		node.addEventHandler(eventType, eventHandler);
	}

	/**
	 * Desencadena un envento para este componente
	 * 
	 * @param event
	 *            evento disparado
	 */
	public void fireEvent(Event event) {
		node.fireEvent(event);
	}

	/**
	 * Arregla la navegacion entre componentes registrando un nuevo
	 * event listener para la tecla TAB
	 * 
	 * @param control
	 *            control para el cual se va a arreglar la navegacion
	 */
	protected void fixNavigation(Control control) {
		if (!navigationFixed) {
			control.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					if (event.getCode() == KeyCode.TAB) {
						KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
						manager.focusNextComponent();
					}
				}
			});
			navigationFixed = true;
		}
	}

	/**
	 * Cambia el estado de deshabilitacion del componente
	 * 
	 * @param value
	 *            valor nuebo
	 */
	public final void setDisable(boolean value) {
		Platform.runLater(() -> node.setDisable(value));
	}

}

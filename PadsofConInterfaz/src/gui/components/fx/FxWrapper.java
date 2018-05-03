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

public abstract class FxWrapper extends JFXPanel {

	private static final long serialVersionUID = -5416489256571893215L;
	private final int width;
	private final int height;
	protected Node node;
	private boolean navigationFixed = false;

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

	public <T extends Event> void addEventHandler(EventType<T> eventType, EventHandler<? super T> eventHandler) {
		node.addEventHandler(eventType, eventHandler);
	}

	public void fireEvent(Event event) {
		node.fireEvent(event);
	}

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

	public final void setDisable(boolean value) {
		Platform.runLater(() -> node.setDisable(value));
	}

}

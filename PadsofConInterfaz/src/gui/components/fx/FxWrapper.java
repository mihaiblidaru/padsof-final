package gui.components.fx;

import java.awt.Dimension;
import java.awt.KeyboardFocusManager;

import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public abstract class FxWrapper extends JFXPanel {

	private static final long serialVersionUID = -5416489256571893215L;
	private final int width;
	private final int height;
	private boolean navigationFixed = false;
	
	protected FxWrapper(int width, int height) {
		this.width = width;
		this.height = height;
		this.setPreferredSize(new Dimension(width, height));
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	protected void fixNavigation(Control control) {
		if(!navigationFixed) {
	        control.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					if(event.getCode() == KeyCode.TAB) {
					    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
					    manager.focusNextComponent();
					}
				}
			});
	        navigationFixed = true;
        }
	}
}

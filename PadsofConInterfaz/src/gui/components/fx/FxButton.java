package gui.components.fx;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

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
        Group root  =  new  Group();
        Scene scene  =  new  Scene(root, this.getWidth(), this.getHeight());
        this.button = new Button(text);
        button.setPrefWidth(this.getWidth());
        button.setPrefHeight(this.getHeight());
        this.fixNavigation(button);
        root.getChildren().add(button);
        return scene;
    }
    
    public void addActionListener(ActionListener listener) {
    	button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
    		    @Override public void handle(MouseEvent e) {
    		    	ActionEvent e1 = new ActionEvent(button, 1, "none");
    		    	listener.actionPerformed(e1);
    		    }
    	});
    }
}

package gui.components.fx;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;


public class FxPasswordField extends FxWrapper {
	private static final long serialVersionUID = -6084390837327862411L;

	private final String placeholder;
	private PasswordField textfield = null;
	
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
		
    private Scene createScene() {
        Group root  =  new  Group();
        Scene scene  =  new  Scene(root, this.getWidth(), this.getHeight());
        this.textfield = new PasswordField();
        textfield.setPromptText(this.placeholder);
        textfield.setPrefWidth(this.getWidth());
        textfield.setPrefHeight(this.getHeight());
        this.node = textfield;
        this.fixNavigation(textfield);
        root.getChildren().add(textfield);
        return scene;
    }
    
    public String getText() {
    	return this.textfield.getText();
    }

}

package gui.components.fx;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.imageio.ImageIO;

import javafx.embed.swing.JFXPanel;
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
        Group root  =  new  Group();
        Scene scene  =  new  Scene(root, this.getWidth(), this.getHeight());
        this.button = new Button(text);
        button.setPrefWidth(this.getWidth());
        button.setPrefHeight(this.getHeight());
        button.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        button.setEllipsisString("");
        button.setStyle(String.format("-fx-font-size: %dpx;", (int)(0.5 * this.getHeight())));
        this.node = button;
        this.fixNavigation(button);
        root.getChildren().add(button);
        return scene;
    }
    
    public void setFontScale(double scale) {
    	button.setStyle(String.format("-fx-font-size: %dpx;", (int)(scale * this.getHeight())));
    }
    
    public void setGraphics(String path) throws FileNotFoundException {
    	Image image = new Image(new FileInputStream(path));
    	ImageView imageView = new ImageView(image);
    	imageView.setFitWidth(20);
    	imageView.setFitHeight(20);
    	this.button.setGraphic(imageView);
    	imageView.setVisible(true);
    	this.button.setContentDisplay(ContentDisplay.RIGHT);
    	    	
    }
    
    
}

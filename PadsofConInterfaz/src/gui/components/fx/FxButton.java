package gui.components.fx;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Screen;


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
        Screen mainScreen = Screen.getPrimary();
        System.out.println(mainScreen.getDpi());
        button.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        button.setEllipsisString("");
        button.setStyle(String.format("-fx-font-size: %dpx;", (int)(0.5 * this.getHeight())));
        this.node = button;
        this.fixNavigation(button);
        root.getChildren().add(button);
        return scene;
    }
}

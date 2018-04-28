package gui.components.fx;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

public class FxRadioGroup extends FxWrapper{

	private static final long serialVersionUID = -824396371534082875L;
	
	public FxRadioGroup(int width, int height, String...options) {
		super(width, height);
		Scene scene = createScene();
        this.setScene(scene);
	}
	
    private Scene createScene(String...options) {
        Group root  =  new  Group();
        Scene scene  =  new  Scene(root, this.getWidth(), this.getHeight(), Color.ALICEBLUE);
        ToggleGroup group = new ToggleGroup();
       
        for (int i = 0; i < options.length; i++) {
        	RadioButton rb = new RadioButton(options[i]);
            rb.setToggleGroup(group);
            rb.setPrefWidth(this.getWidth());
            rb.setPrefHeight(this.getHeight());
            root.getChildren().add(rb);
            
            if(i==0)
            	rb.setSelected(true);
            
		}
        
        
       
        return scene;
    }
    
}

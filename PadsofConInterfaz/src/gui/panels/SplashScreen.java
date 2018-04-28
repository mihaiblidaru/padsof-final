package gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class SplashScreen extends JPanel{


	private static final long serialVersionUID = 64660570488751734L;
	private BufferedImage image;
	
	public SplashScreen() throws IOException {
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(300, 180));
		SpringLayout layout = new SpringLayout();
		ResourceBundle rb =  ResourceBundle.getBundle("locale.Lang");
		this.setLayout(layout);
		//this.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		JLabel labelTitulo = new JLabel(rb.getString("app.name"));
		
		Font titleFont = new Font("Comic Sans", Font.PLAIN, 35);
		labelTitulo.setFont(titleFont);
	
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, labelTitulo, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, labelTitulo, 0, SpringLayout.NORTH, this);
		add(labelTitulo);
	         
	    image = ImageIO.read(new File("logo.png"));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters            
	}
	
	
	
	

}

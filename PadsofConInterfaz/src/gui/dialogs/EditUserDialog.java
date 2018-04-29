package gui.dialogs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import gui.components.fx.FxTextField;

public class EditUserDialog extends JDialog {
	private final static int FRAME_WIDTH = 350;
	private final static int FRAME_HEIGHT = 200;
	public EditUserDialog() {
		this.setTitle("Editar Usuario");
		this.setModal(true);
		
		this.setContentPane(buildPanel());
		this.setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setResizable(false);
		this.setBounds((int) (screenSize.getWidth()/2 - FRAME_WIDTH/2) , (int) (screenSize.getHeight()/2 - FRAME_HEIGHT/2), FRAME_WIDTH, FRAME_HEIGHT);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.pack();
	}
	
	
	private JPanel buildPanel() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.decode("#ffffff"));
		panel.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		SpringLayout layout = new SpringLayout();
		panel.setLayout(layout);
				
		JLabel dni = new JLabel("1234567X");
		panel.add(dni);
		
		layout.putConstraint(SpringLayout.WEST, dni, 20, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, dni, 20, SpringLayout.NORTH, panel);
		BufferedImage phoneIcon = null;
		try {
			phoneIcon = ImageIO.read(new File("res/img/fa-phone-square.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		JLabel phoneIconLabel = new JLabel(new ImageIcon(phoneIcon.getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		
		panel.add(phoneIconLabel);
		layout.putConstraint(SpringLayout.WEST, phoneIconLabel, 20, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, phoneIconLabel, 20, SpringLayout.NORTH, dni);
		
		
		BufferedImage ccIcon = null;
		try {
			ccIcon = ImageIO.read(new File("res/img/fa-cc-visa.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JLabel ccIconLabel = new JLabel(new ImageIcon(ccIcon.getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		
		panel.add(ccIconLabel);
		layout.putConstraint(SpringLayout.WEST, ccIconLabel, 20, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, ccIconLabel, 10, SpringLayout.SOUTH, phoneIconLabel);
		
		
		FxTextField ccField = new FxTextField(100, 25, "credido");
		
		panel.add(ccField);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, ccField, 0, SpringLayout.HORIZONTAL_CENTER, ccIconLabel);
		layout.putConstraint(SpringLayout.WEST, ccField, 20, SpringLayout.EAST, ccIconLabel);
		
		
		
		return panel;
	}

}

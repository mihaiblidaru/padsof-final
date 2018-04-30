package gui.dialogs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import gui.Gui;
import gui.components.fx.FxButton;
import gui.components.fx.FxTextField;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class EditUserDialog extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private final static int FRAME_WIDTH = 350;
	private final static int FRAME_HEIGHT = 200;
	private final Gui gui;
	private final JFrame frame;
	public EditUserDialog(Gui gui) {
		frame=this;
		this.gui = gui;
		this.setName("Editar Usuario");
		this.setContentPane(buildPanel());
		this.setAlwaysOnTop(true);
		this.setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setResizable(false);
		this.setBounds((int) (screenSize.getWidth()/2 - FRAME_WIDTH/2) , (int) (screenSize.getHeight()/2 - FRAME_HEIGHT/2), FRAME_WIDTH, FRAME_HEIGHT);
		this.pack();
		gui.setEnabled(false);
		registerWindowListeners();
	}
	
	
	private void registerWindowListeners() {
		
	    WindowAdapter adapter = new WindowAdapter() {
	            @Override
	            public void windowClosing(WindowEvent we) {//overrode to show message
	                super.windowClosing(we);
	                gui.setEnabled(true);
	            }

	            @Override
	            public void windowIconified(WindowEvent we) {
	            	((JFrame)we.getSource()).setState(JFrame.NORMAL);
	            }
	        };
	    
	        this.addWindowListener(adapter);	
	}


	private JPanel buildPanel() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.decode("#ffffff"));
		panel.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		SpringLayout layout = new SpringLayout();
		panel.setLayout(layout);
				
		JLabel dni = new JLabel("<html><h1>1234567X</h1></html>");
		panel.add(dni);
		
		layout.putConstraint(SpringLayout.WEST, dni, 20, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, dni, 20, SpringLayout.NORTH, panel);
		BufferedImage phoneIcon = null;
		try {
			phoneIcon = ImageIO.read(new File("res/img/fa-phone-square.png"));
		} catch (IOException e) {
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
		
		
		FxTextField ccField = new FxTextField(250, 25, "Tarjeta de credito");
		
		panel.add(ccField);
		layout.putConstraint(SpringLayout.NORTH, ccField, 0, SpringLayout.NORTH, ccIconLabel);
		layout.putConstraint(SpringLayout.WEST, ccField, 20, SpringLayout.EAST, ccIconLabel);
		
		FxButton okButton = new FxButton(40, 25, "OK");
		
		panel.add(okButton);
		layout.putConstraint(SpringLayout.NORTH, okButton, 25, SpringLayout.SOUTH, ccField);
		layout.putConstraint(SpringLayout.WEST, okButton, 50, SpringLayout.WEST, ccField);
		
		FxButton cancelButton = new FxButton(80, 25, "Cancelar");
		
		panel.add(cancelButton);
		layout.putConstraint(SpringLayout.NORTH, cancelButton, 0, SpringLayout.NORTH, okButton);
		layout.putConstraint(SpringLayout.WEST, cancelButton, 20, SpringLayout.EAST, okButton);
		
		cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
			
		});
		
		
		return panel;
	}

}
package gui.panels;

import java.awt.Container;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.panels.PanelOferta;

import javax.swing.SpringLayout;
import java.awt.Color;


public class InterfazVerViviendas {
	
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazVerViviendas window = new InterfazVerViviendas();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InterfazVerViviendas() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.WHITE);
		try {
			File file = new File("512.png");
			frame.setIconImage(ImageIO.read(file));
		} catch (IOException e) {
			
		}
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		Container con = frame.getContentPane();
		 
		PanelOferta oferta = new PanelOferta();
		springLayout.putConstraint(SpringLayout.NORTH, oferta, 50, SpringLayout.NORTH, con);
		springLayout.putConstraint(SpringLayout.WEST, oferta, 10, SpringLayout.WEST, con);
		springLayout.putConstraint(SpringLayout.EAST, oferta, 10, SpringLayout.EAST, con);
		
		JButton anadir = new JButton ("Añadir vivienda");
		springLayout.putConstraint(SpringLayout.SOUTH, anadir, 10, springLayout.SOUTH, con);
		springLayout.putConstraint(SpringLayout.EAST, anadir, -10, springLayout.EAST, con);
		con.add(anadir);
	}
}

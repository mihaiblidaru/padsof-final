package gui.panels;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.SpringLayout;

import gui.panels.oferta.PanelOferta;

import java.awt.Color;

public class InterfazDemandante {

		private JFrame frame;

		/**
		 * Launch the application.
		 */
		public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						InterfazDemandante window = new InterfazDemandante();
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
		public InterfazDemandante() {
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
			
			JPanel panel = new PanelOferta();
			springLayout.putConstraint(SpringLayout.NORTH, panel, 100, SpringLayout.NORTH, con);
			springLayout.putConstraint(SpringLayout.WEST, panel, 200, SpringLayout.WEST, con);
			//springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, panel, 0, SpringLayout.VERTICAL_CENTER, con);
			con.add(panel);
			
			JPanel panel2 = new PanelOferta();
			springLayout.putConstraint(SpringLayout.NORTH, panel2, 20, SpringLayout.SOUTH, panel);
			springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, panel2, 0, SpringLayout.HORIZONTAL_CENTER, panel);
			con.add(panel2);	
			
			JPanel panel3 = new PanelOferta();
			springLayout.putConstraint(SpringLayout.NORTH, panel3, 10, SpringLayout.SOUTH, panel2);
			springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, panel3, 0, SpringLayout.HORIZONTAL_CENTER, panel);
			con.add(panel3);
			
		}
	
}

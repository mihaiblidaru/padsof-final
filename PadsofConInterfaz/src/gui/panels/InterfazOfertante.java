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

public class InterfazOfertante {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazOfertante window = new InterfazOfertante();
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
	public InterfazOfertante() {
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
		JButton anyadir = new JButton("Añadir Oferta");
			
		springLayout.putConstraint(SpringLayout.EAST, anyadir, -40, SpringLayout.EAST, con);
		springLayout.putConstraint(SpringLayout.NORTH, anyadir, 70, SpringLayout.NORTH, con);
		con.add(anyadir);
		
		JButton viviendas = new JButton("Mis viviendas");
		
		springLayout.putConstraint(SpringLayout.EAST, viviendas, -300, SpringLayout.EAST, con);
		springLayout.putConstraint(SpringLayout.NORTH, viviendas, 5, SpringLayout.NORTH, con);
		con.add(viviendas);
		
		JButton ofertas = new JButton("Mis ofertas");
		
		springLayout.putConstraint(SpringLayout.WEST, ofertas, 20, SpringLayout.EAST, viviendas);
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, ofertas, 0, SpringLayout.VERTICAL_CENTER, viviendas);
		con.add(ofertas);
		
		JButton logout = new JButton("Logout");
		
		springLayout.putConstraint(SpringLayout.EAST, logout, -20, SpringLayout.EAST, con);
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, logout, 0, SpringLayout.VERTICAL_CENTER, viviendas);
		con.add(logout);
		
		JLabel TuVacaPiso = new JLabel("TuVacaPiso");
		springLayout.putConstraint(SpringLayout.NORTH, TuVacaPiso, 5, SpringLayout.NORTH, con);
		springLayout.putConstraint(SpringLayout.WEST, TuVacaPiso, 20, SpringLayout.WEST, con);
		con.add(TuVacaPiso);
		
		JPanel panel = new PanelOferta();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 100, SpringLayout.NORTH, con);
		springLayout.putConstraint(SpringLayout.WEST, panel, 20, SpringLayout.WEST, con);
		//springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, panel, 0, SpringLayout.VERTICAL_CENTER, con);
		con.add(panel);
		
		JPanel panel2 = new PanelOferta();
		springLayout.putConstraint(SpringLayout.NORTH, panel2, 20, SpringLayout.SOUTH, panel);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, panel2, 0, SpringLayout.HORIZONTAL_CENTER, panel);
		con.add(panel2);
		
		JLabel activas = new JLabel("Activas");
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, activas, 0, SpringLayout.VERTICAL_CENTER, anyadir);
		springLayout.putConstraint(SpringLayout.WEST, activas, 20, SpringLayout.WEST, con);
		con.add(activas);
		
		JLabel pendientes = new JLabel("Pendientes: el administrador debe aceptar está oferta antes de ser publicada");
		springLayout.putConstraint(SpringLayout.NORTH, pendientes, 5, SpringLayout.SOUTH, panel);
		springLayout.putConstraint(SpringLayout.WEST, pendientes, 20, SpringLayout.WEST, con);
		con.add(pendientes);
		
		JLabel rechazadas = new JLabel("Rechazadas");
		springLayout.putConstraint(SpringLayout.NORTH, rechazadas, 15, SpringLayout.SOUTH, panel2);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, rechazadas, 20, SpringLayout.HORIZONTAL_CENTER, activas);
		con.add(rechazadas);
		
		JPanel panel3 = new PanelOferta();
		springLayout.putConstraint(SpringLayout.NORTH, panel3, 10, SpringLayout.SOUTH, rechazadas);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, panel3, 0, SpringLayout.HORIZONTAL_CENTER, panel);
		con.add(panel3);
		
		
		
	}
}

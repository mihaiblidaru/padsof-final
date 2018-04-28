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
import java.awt.Color;

public class InterfazAñadirOferta {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazAñadirOferta window = new InterfazAñadirOferta();
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
	public InterfazAñadirOferta() {
		initialize();
	}
	
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1104, 621);
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
		Font font = new Font("Comic Sans", Font.PLAIN, 40);
		Font descfont = new Font("Comic Sans", Font.PLAIN, 25);
		Font conffont = new Font("Comic Sans", Font.PLAIN, 25);
		
		JLabel AñadirOfertas = new JLabel("Añadir Oferta");
		AñadirOfertas.setFont(font);
		springLayout.putConstraint(SpringLayout.NORTH, AñadirOfertas, 50, SpringLayout.NORTH, con);
		springLayout.putConstraint(SpringLayout.WEST, AñadirOfertas, 20, SpringLayout.WEST, con);
		con.add(AñadirOfertas);
		
		JLabel name = new JLabel("Selecciona al vivienda que quieres oferta");
		springLayout.putConstraint(SpringLayout.NORTH, name, 20, SpringLayout.SOUTH, AñadirOfertas);
		springLayout.putConstraint(SpringLayout.WEST, name, 30, SpringLayout.WEST, con);
		con.add(name);
		
		JTextField viviendaname = new JTextField(50);
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, viviendaname, 0, SpringLayout.VERTICAL_CENTER, name);
		springLayout.putConstraint(SpringLayout.WEST, viviendaname, 10, SpringLayout.EAST, name);
		con.add(viviendaname);
		
		JLabel precio = new JLabel("Precio");
		springLayout.putConstraint(SpringLayout.NORTH, precio, 20, SpringLayout.SOUTH, name);
		springLayout.putConstraint(SpringLayout.WEST, precio, 30, SpringLayout.WEST, con);
		con.add(precio);
		
		JTextField precioname = new JTextField(10);
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, precioname, 0, SpringLayout.VERTICAL_CENTER, precio);
		springLayout.putConstraint(SpringLayout.WEST, precioname, 10, SpringLayout.EAST, precio);
		con.add(precioname);
		
		JLabel fianza = new JLabel("Fianza");
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, fianza, 0, SpringLayout.VERTICAL_CENTER, precio);
		springLayout.putConstraint(SpringLayout.WEST, fianza, 10, SpringLayout.EAST, precioname);
		con.add(fianza);
		
		JTextField fianzaname = new JTextField(10);
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, fianzaname, 0, SpringLayout.VERTICAL_CENTER, fianza);
		springLayout.putConstraint(SpringLayout.WEST, fianzaname, 10, SpringLayout.EAST, fianza);
		con.add(fianzaname);
		
		
		JLabel desde = new JLabel("Desde");
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, desde, 0, SpringLayout.VERTICAL_CENTER, precio);
		springLayout.putConstraint(SpringLayout.WEST, desde, 100, SpringLayout.EAST, fianzaname);
		con.add(desde);
		
		JTextField desdename = new JTextField(10);
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, desdename, 0, SpringLayout.VERTICAL_CENTER, desde);
		springLayout.putConstraint(SpringLayout.WEST, desdename, 10, SpringLayout.EAST, desde);
		con.add(desdename);
		
		JLabel hasta = new JLabel("Hasta");
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, hasta, 0, SpringLayout.VERTICAL_CENTER, desde);
		springLayout.putConstraint(SpringLayout.WEST, hasta, 20, SpringLayout.EAST, desdename);
		con.add(hasta);
		
		JTextField hastaname = new JTextField(10);
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, hastaname, 0, SpringLayout.VERTICAL_CENTER, hasta);
		springLayout.putConstraint(SpringLayout.WEST, hastaname, 10, SpringLayout.EAST, hasta);
		con.add(hastaname);
		
		JLabel descripcion = new JLabel("Descripcion");
		descripcion.setFont(descfont);
		springLayout.putConstraint(SpringLayout.WEST, descripcion, 0, SpringLayout.WEST, precio);
		springLayout.putConstraint(SpringLayout.NORTH, descripcion, 20, SpringLayout.SOUTH, precio);
		con.add(descripcion);
		
		JTextField descripcionname = new JTextField(60);
		springLayout.putConstraint(SpringLayout.WEST, descripcionname, 0, SpringLayout.WEST, descripcion);
		springLayout.putConstraint(SpringLayout.NORTH, descripcionname, 10, SpringLayout.SOUTH, descripcion);
		springLayout.putConstraint(SpringLayout.SOUTH, descripcionname, 180, SpringLayout.SOUTH, descripcion);
		con.add(descripcionname);
		
		JButton confirmar = new JButton("Confirmar");
		confirmar.setFont(conffont);
		springLayout.putConstraint(SpringLayout.NORTH, confirmar, 40, springLayout.SOUTH, descripcionname);
		springLayout.putConstraint(SpringLayout.WEST, confirmar, 70, springLayout.WEST, con);
		confirmar.setPreferredSize(new Dimension(200, 100));
		con.add(confirmar);
		
		JLabel alarm = new JLabel("No puedes añadir la misma oferta dos veces");
		springLayout.putConstraint(SpringLayout.WEST, alarm, 0, SpringLayout.WEST, confirmar);
		springLayout.putConstraint(SpringLayout.SOUTH, alarm, -20, SpringLayout.NORTH, confirmar);
		con.add(alarm);
		
		
	}
}

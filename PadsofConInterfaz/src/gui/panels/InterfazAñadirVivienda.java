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
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.SpringLayout;
import java.awt.Color;


public class InterfazAñadirVivienda {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazAñadirVivienda window = new InterfazAñadirVivienda();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public InterfazAñadirVivienda() {
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
		Font font = new Font("Comic Sans", Font.PLAIN, 35);
		
		JLabel etiqueta = new JLabel("Añadir Viviendas");
		springLayout.putConstraint(SpringLayout.NORTH, etiqueta, 100, SpringLayout.NORTH, con);
		springLayout.putConstraint(SpringLayout.WEST, etiqueta, 320, SpringLayout.WEST, con);
		etiqueta.setFont(font);
		con.add(etiqueta);
		
		JLabel localidad = new JLabel("Localidad");
		springLayout.putConstraint(SpringLayout.NORTH, localidad, 20, SpringLayout.SOUTH, etiqueta);
		springLayout.putConstraint(SpringLayout.WEST, localidad, -10, SpringLayout.WEST, etiqueta);
		con.add(localidad);
		
		JTextField locname = new JTextField(20);
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, locname, 0, SpringLayout.VERTICAL_CENTER, localidad);
		springLayout.putConstraint(SpringLayout.WEST, locname, 10, SpringLayout.EAST, localidad);
		con.add(locname);
		
		JLabel codigoPostal = new JLabel("Codigo Postal");
		springLayout.putConstraint(SpringLayout.NORTH, codigoPostal, 10, SpringLayout.SOUTH, localidad);
		springLayout.putConstraint(SpringLayout.WEST, codigoPostal, 0, SpringLayout.WEST, localidad);
		con.add(codigoPostal);
		
		JTextField cpname = new JTextField(20);
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, cpname, 0, SpringLayout.VERTICAL_CENTER, codigoPostal);
		springLayout.putConstraint(SpringLayout.WEST, cpname, 10, SpringLayout.EAST, codigoPostal);
		springLayout.putConstraint(SpringLayout.EAST, cpname, 0, SpringLayout.EAST, locname);
		con.add(cpname);
		
		JLabel calle = new JLabel("Calle ");
		springLayout.putConstraint(SpringLayout.NORTH, calle, 10, SpringLayout.SOUTH, codigoPostal);
		springLayout.putConstraint(SpringLayout.WEST, calle, 0, SpringLayout.WEST, localidad);
		con.add(calle);
		
		JTextField cname = new JTextField(10);
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, cname, 0, SpringLayout.VERTICAL_CENTER, calle);
		springLayout.putConstraint(SpringLayout.WEST, cname, 10, SpringLayout.EAST, calle);
		con.add(cname);
		
		JLabel numero = new JLabel("Numero ");
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, numero, 0, SpringLayout.VERTICAL_CENTER, calle);
		springLayout.putConstraint(SpringLayout.WEST, numero, 5, SpringLayout.EAST, cname);
		con.add(numero);
		
		JTextField num = new JTextField(20);
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, num, 0, SpringLayout.VERTICAL_CENTER, numero);
		springLayout.putConstraint(SpringLayout.WEST, num, 5, SpringLayout.EAST, numero);
		springLayout.putConstraint(SpringLayout.EAST, num, 0, SpringLayout.EAST, locname);
		con.add(num);
		
		JLabel piso = new JLabel("Piso ");
		springLayout.putConstraint(SpringLayout.NORTH, piso, 10, SpringLayout.SOUTH, calle);
		springLayout.putConstraint(SpringLayout.WEST, piso, 0, SpringLayout.WEST, calle);
		con.add(piso);
		
		JTextField pname = new JTextField(5);
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, pname, 0, SpringLayout.VERTICAL_CENTER, piso);
		springLayout.putConstraint(SpringLayout.WEST, pname, 5, SpringLayout.EAST, piso);
		con.add(pname);
		
		JLabel escalera = new JLabel("Escalera ");
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, escalera, 0, SpringLayout.VERTICAL_CENTER, piso);
		springLayout.putConstraint(SpringLayout.WEST, escalera, 3, SpringLayout.EAST, pname);
		con.add(escalera);
		
		JTextField ename = new JTextField(3);
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, ename, 0, SpringLayout.VERTICAL_CENTER, piso);
		springLayout.putConstraint(SpringLayout.WEST, ename, 5, SpringLayout.EAST, escalera);
		con.add(ename);
		
		JLabel letra = new JLabel("Letra ");
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, letra, 0, SpringLayout.VERTICAL_CENTER, piso);
		springLayout.putConstraint(SpringLayout.WEST, letra, 5, SpringLayout.EAST, ename);
		con.add(letra);
		
		JTextField lname = new JTextField(2);
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, lname, 0, SpringLayout.VERTICAL_CENTER, letra);
		springLayout.putConstraint(SpringLayout.WEST, lname, 3, SpringLayout.EAST, letra);
		springLayout.putConstraint(SpringLayout.EAST, lname, 0, SpringLayout.EAST, locname);
		con.add(lname);
		
		JButton confirmar = new JButton("Confirmar");
		Font conffont = new Font("Comic Sans", Font.PLAIN, 25);
		confirmar.setFont(conffont);
		springLayout.putConstraint(SpringLayout.NORTH, confirmar, 80, springLayout.SOUTH, escalera);
		springLayout.putConstraint(SpringLayout.WEST, confirmar, 340, springLayout.WEST, con);
		confirmar.setPreferredSize(new Dimension(200, 100));
		con.add(confirmar);
		
		JLabel alarm = new JLabel("No puedes añadir la misma oferta dos veces");
		springLayout.putConstraint(SpringLayout.WEST, alarm, 0, SpringLayout.WEST, confirmar);
		springLayout.putConstraint(SpringLayout.SOUTH, alarm, -20, SpringLayout.NORTH, confirmar);
		con.add(alarm);
		
		
		
		
	}
}

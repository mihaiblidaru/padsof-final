package gui.panels.ofertante;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.SpringLayout;

import gui.Gui;
import gui.components.fx.FxTextField;



public class InterfazAniadirVivienda extends JPanel{

	private static final long serialVersionUID = -4230661380101910555L;

	private final Gui gui;
	
	private static InterfazAniadirVivienda instance = null; 
	
	public static InterfazAniadirVivienda getInstance(Gui gui) {
		if(instance == null) {
			return (instance = new InterfazAniadirVivienda(gui));
		}else {
			return instance;
		}
	}

	
	private InterfazAniadirVivienda(Gui gui) {
		this.gui = gui;
		initialize();
	}
	
	
	private void initialize() {
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		this.setPreferredSize(new Dimension(Gui.FRAME_WIDTH, Gui.FRAME_HEIGHT-20));
		this.setBackground(Color.WHITE);
		
		Font font = new Font("Comic Sans", Font.PLAIN, 40);
		
		JLabel etiqueta = new JLabel("Añadir Vivienda");
		layout.putConstraint(SpringLayout.NORTH, etiqueta, 50, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, etiqueta, 320, SpringLayout.WEST, this);
		etiqueta.setFont(font);
		this.add(etiqueta);
		
		JLabel localidad = new JLabel("Localidad:");
		layout.putConstraint(SpringLayout.NORTH, localidad, 20, SpringLayout.SOUTH, etiqueta);
		layout.putConstraint(SpringLayout.WEST, localidad, -10, SpringLayout.WEST, etiqueta);
		this.add(localidad);
		
		FxTextField locname = new FxTextField(210, 25, "Ej: Madrid");
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, locname, 0, SpringLayout.VERTICAL_CENTER, localidad);
		layout.putConstraint(SpringLayout.WEST, locname, 10, SpringLayout.EAST, localidad);
		this.add(locname);
		
		JLabel codigoPostal = new JLabel("Codigo Postal:");
		layout.putConstraint(SpringLayout.NORTH, codigoPostal, 15, SpringLayout.SOUTH, localidad);
		layout.putConstraint(SpringLayout.WEST, codigoPostal, 0, SpringLayout.WEST, localidad);
		this.add(codigoPostal);
		
		FxTextField cpname = new FxTextField(187,25, "Ej: 28850");
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, cpname, 0, SpringLayout.VERTICAL_CENTER, codigoPostal);
		layout.putConstraint(SpringLayout.WEST, cpname, 10, SpringLayout.EAST, codigoPostal);
		layout.putConstraint(SpringLayout.EAST, cpname, 0, SpringLayout.EAST, locname);
		this.add(cpname);
		
		JLabel direccionLabel = new JLabel("Direccion:");
		layout.putConstraint(SpringLayout.NORTH, direccionLabel, 15, SpringLayout.SOUTH, codigoPostal);
		layout.putConstraint(SpringLayout.WEST, direccionLabel, 0, SpringLayout.WEST, localidad);
		this.add(direccionLabel);
		

		FxTextField dirreccionTextBox = new FxTextField(210, 25, "Ej: Calle Alcala, 30");
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, dirreccionTextBox, 0, SpringLayout.VERTICAL_CENTER, direccionLabel);
		layout.putConstraint(SpringLayout.WEST, dirreccionTextBox, 10, SpringLayout.EAST, direccionLabel);
		this.add(dirreccionTextBox);
		
		
		JLabel direccionLabel2 = new JLabel("Direccion(extra):");
		layout.putConstraint(SpringLayout.NORTH, direccionLabel2, 15, SpringLayout.SOUTH, direccionLabel);
		layout.putConstraint(SpringLayout.WEST, direccionLabel2, 0, SpringLayout.WEST, direccionLabel);
		this.add(direccionLabel2);
		

		FxTextField dirreccionTextBox2 = new FxTextField(172, 25, "Ej: Escalera A, Portal 15, 1B");
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, dirreccionTextBox2, 0, SpringLayout.VERTICAL_CENTER, direccionLabel2);
		layout.putConstraint(SpringLayout.WEST, dirreccionTextBox2, 10, SpringLayout.EAST, direccionLabel2);
		this.add(dirreccionTextBox2);
		

		JButton confirmar = new JButton("Confirmar");
		Font conffont = new Font("Comic Sans", Font.PLAIN, 25);
		confirmar.setFont(conffont);
		layout.putConstraint(SpringLayout.NORTH, confirmar, 80, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST, confirmar, 340, SpringLayout.WEST, this);
		confirmar.setPreferredSize(new Dimension(250, 150));
		this.add(confirmar);
		
		JLabel alarm = new JLabel("No puedes añadir la misma oferta dos veces");
		layout.putConstraint(SpringLayout.WEST, alarm, 0, SpringLayout.WEST, confirmar);
		layout.putConstraint(SpringLayout.SOUTH, alarm, -20, SpringLayout.NORTH, confirmar);
		this.add(alarm);
		
	}
}

package gui.panels.ofertante;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import gui.Gui;
import gui.components.fx.FxButton;
import gui.components.fx.FxTextField;
import gui.panels.Header;
import gui.panels.LoginPanel;
import gui.panels.SearchMenu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;



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
		JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
		this.add(separator);
		layout.putConstraint(SpringLayout.WEST, separator, 5, SpringLayout.EAST, locname);
		layout.putConstraint(SpringLayout.NORTH, separator, -5, SpringLayout.NORTH, locname);
		separator.setPreferredSize(new Dimension(2, 130));
		separator.setForeground(new Color(200, 200, 200));
		
		JPanel caracteristicas = buildPanelCaracteristicas();
		
		this.add(caracteristicas);
		
		layout.putConstraint(SpringLayout.WEST, caracteristicas, 5, SpringLayout.EAST, separator);
		layout.putConstraint(SpringLayout.NORTH, caracteristicas, 5, SpringLayout.NORTH, separator);
		
		
	}
	
	
	private JPanel buildPanelCaracteristicas() {
		SpringLayout layout = new SpringLayout();
		JPanel panel = new JPanel(layout);
		panel.setPreferredSize(new Dimension(300, 300));
		Font font = new Font("Open Sans", Font.PLAIN, 15);
		JLabel caracteristicas  = new JLabel("Caracteristicas");
		caracteristicas.setFont(font);
		panel.add(caracteristicas);
		layout.putConstraint(SpringLayout.WEST, caracteristicas, 10, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, caracteristicas, 5, SpringLayout.NORTH, panel);
						
		FxButton addCaracteristica = new FxButton(25, 25, "");
		addCaracteristica.setGraphics("res/img/fa-plus-circle.png", 15, 15);
		panel.add(addCaracteristica);
		
		layout.putConstraint(SpringLayout.EAST, addCaracteristica, -10, SpringLayout.EAST, panel);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, addCaracteristica, 0, SpringLayout.VERTICAL_CENTER, caracteristicas);
		JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
		
		panel.add(separator);
		separator.setPreferredSize(new Dimension(280, 1));
		separator.setForeground(new Color(200, 200, 200));
		layout.putConstraint(SpringLayout.NORTH, separator, 3, SpringLayout.SOUTH, caracteristicas);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, separator, 0, SpringLayout.HORIZONTAL_CENTER, panel);
		
		PanelCaracteristicas pc = new PanelCaracteristicas();
	
		panel.add(pc);
		
		layout.putConstraint(SpringLayout.NORTH, pc, 2, SpringLayout.SOUTH, separator);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, pc, 0, SpringLayout.HORIZONTAL_CENTER, panel);
		pc.addCaracteristica();
		pc.addCaracteristica();
		pc.addCaracteristica();
		
		addCaracteristica.setOnAction(new EventHandler<ActionEvent>() { 
	        @Override 
	        public void handle(ActionEvent e) { 
	        	pc.addCaracteristica();
	        } 
	     });  
		
		return panel;
	}
	
	private static class PanelCaracteristicas extends JPanel{
		SpringLayout layout;
		public PanelCaracteristicas() {
			layout = new SpringLayout();
			this.setPreferredSize(new Dimension(300, 300));
		}

		private static final long serialVersionUID = -1075184306259033731L;
		
		
		@Override
		public Component add(Component c) {
			super.add(c);
			if(this.getComponentCount() == 0) {
				layout.putConstraint(SpringLayout.NORTH, c, 0, SpringLayout.NORTH, this);
				layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, c, 0, SpringLayout.HORIZONTAL_CENTER, this);
			}else {
				Component last = this.getComponent(this.getComponentCount()-1);
				layout.putConstraint(SpringLayout.NORTH, c, 5, SpringLayout.SOUTH, last);
			}
			return c;
		}
		
		public void addCaracteristica() {
			this.add(makeCaracteristica());
		}
		
		private JPanel makeCaracteristica() {
			SpringLayout layout = new SpringLayout();
			JPanel panel = new JPanel(layout);
			panel.setPreferredSize(new Dimension(300, 30));
			FxTextField clave = new FxTextField(80, 25, "Ej: Piscina"); 
			FxTextField valor = new FxTextField(120, 25, "Ej: No");
			FxButton delete = new FxButton(25, 25, "");
			panel.add(clave);
			panel.add(valor);
			panel.add(delete);
			layout.putConstraint(SpringLayout.WEST, clave, 5, SpringLayout.WEST, panel);
			layout.putConstraint(SpringLayout.NORTH, clave, 5, SpringLayout.NORTH, panel);
			layout.putConstraint(SpringLayout.WEST, valor, 5, SpringLayout.EAST, clave);
			layout.putConstraint(SpringLayout.VERTICAL_CENTER, valor, 0, SpringLayout.VERTICAL_CENTER, clave);
			layout.putConstraint(SpringLayout.WEST, delete, 5, SpringLayout.EAST, valor);
			return panel;
		}
	};

	
}

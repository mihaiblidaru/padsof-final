package gui.panels.ofertante.caracteristicas;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Arrays;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

import gui.components.fx.FxButton;
import gui.components.fx.FxTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class PanelCaracteristicas extends JPanel{
	SpringLayout layout;
	public PanelCaracteristicas() {
		layout = new SpringLayout();
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
		this.setPreferredSize(new Dimension(320, this.getComponentCount() * 35));
		return c;
	}
	
	public void addCaracteristica() {
		this.add(makeCaracteristica());
	}
	
	
	private void removeCaracteristica(JPanel panel) {
		this.remove(panel);
		Component[] components = this.getComponents();
		this.removeAll();
		Arrays.asList(components).stream().forEach(c->add(c));
		this.repaint();
	}
	
	private JPanel makeCaracteristica() {
		SpringLayout layout = new SpringLayout();
		JPanel panel = new JPanel(layout);
		panel.setPreferredSize(new Dimension(300, 30));
		FxTextField clave = new FxTextField(110, 25, "Ej: Piscina"); 
		FxTextField valor = new FxTextField(140, 25, "Ej: No");
		FxButton delete = new FxButton(25, 25, "");
		panel.add(clave);
		panel.add(valor);
		panel.add(delete);
		delete.setGraphics("res/img/fa-delete.png", 15, 15);
		layout.putConstraint(SpringLayout.WEST, clave, 5, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, clave, 0, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, valor, 5, SpringLayout.EAST, clave);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, valor, 0, SpringLayout.VERTICAL_CENTER, clave);
		layout.putConstraint(SpringLayout.WEST, delete, 5, SpringLayout.EAST, valor);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, delete, 0, SpringLayout.VERTICAL_CENTER, clave);
		
		delete.setOnAction(new EventHandler<ActionEvent>() { 
	        @Override 
	        public void handle(ActionEvent e) {
	        	removeCaracteristica(panel);
	        } 
	     });
		
		return panel;
	}
};
package gui.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import gui.Gui;
import gui.components.ThinSolidScrollBarUi;

public class ResultadosBusqueda extends JPanel {

	private static final long serialVersionUID = 8638372520699078390L;
	private static ResultadosBusqueda instance = null; 
	
	public static ResultadosBusqueda getInstance(Gui gui) {
		if(instance == null) {
			return (instance = new ResultadosBusqueda(gui));
		}else {
			return instance;
		}
	}
	
	
	private ResultadosBusqueda(Gui gui) {
		this.setPreferredSize(new Dimension(815, 562));
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		
		ContenedorOfertas co = new ContenedorOfertas();
		JScrollPane scrollPanel  = new JScrollPane(co,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanel.setPreferredSize(new Dimension(826,564));
		scrollPanel.getVerticalScrollBar().setUI(new ThinSolidScrollBarUi(7));
		scrollPanel.getVerticalScrollBar().setBackground(Color.WHITE);
		scrollPanel.getVerticalScrollBar().setUnitIncrement(16);
		scrollPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		co.addOtro(new PanelOferta());
		co.addOtro(new PanelOferta());
		co.addOtro(new PanelOferta());
		co.addOtro(new PanelOferta());
		co.addOtro(new PanelOferta());
		co.addOtro(new PanelOferta());
		co.addOtro(new PanelOferta());
		co.addOtro(new PanelOferta());
		co.addOtro(new PanelOferta());
		co.addOtro(new PanelOferta());
		co.addOtro(new PanelOferta());
		this.add(scrollPanel);
	}


	class ContenedorOfertas extends JPanel{
		
		private static final long serialVersionUID = -2181119752625080665L;
		private final SpringLayout layout;
		public ContenedorOfertas() {
			layout = new SpringLayout();
			this.setLayout(layout);
		}
				
		
		public Component addOtro(JPanel c) {
			if(this.getComponentCount() == 0) {
				layout.putConstraint(SpringLayout.NORTH, c, 10, SpringLayout.NORTH, this);
				layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, c, 0, SpringLayout.HORIZONTAL_CENTER, this);
			}else {
				Component last = this.getComponent(this.getComponentCount() - 1);
				layout.putConstraint(SpringLayout.NORTH, c, 10, SpringLayout.SOUTH, last);
				layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, c, 0, SpringLayout.HORIZONTAL_CENTER, this);
			}
			this.add(c);
			this.setPreferredSize(new Dimension(750, 10 + this.getComponentCount() * (c.getPreferredSize().height+10)));
			return c;
		}
	}
	
	
	
	
	
	
	
}

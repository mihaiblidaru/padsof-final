package gui.panels.admin;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import gui.Gui;

public class ControlPanel extends JPanel {

	private static final long serialVersionUID = 7615793439529902683L;
	
	
	public ControlPanel() {
		this.setBackground(Color.RED);
		this.setPreferredSize(new Dimension(Gui.FRAME_WIDTH, 562));
	}

}

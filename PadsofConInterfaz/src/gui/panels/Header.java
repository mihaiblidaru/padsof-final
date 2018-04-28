package gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;


import gui.Gui;
import gui.components.fx.FxButton;

public class Header extends JPanel {

	private static final long serialVersionUID = -5230943621476766861L;
	public final static String NAME = "HEADER";
	private Gui gui;
	
	public final static int LOGIN = 0;
	public final static int LOGOUT = 1;
	public final static int MIS_RESERVAS = 2;
	public final static int MIS_OFERTAS = 3;
	public final static int MIS_INMUEBLES = 4;
	
	private List<JComponent> buttons;
	
	
	public void setButtonVisibility(int button, boolean state) {
		JComponent btn = buttons.get(button); 
		btn.setVisible(state);
	}
	
	public Header(Gui gui) {
		this.gui = gui;
		this.setName(NAME);
		this.buttons = new ArrayList<JComponent>();
		this.setPreferredSize(new Dimension(Gui.FRAME_WIDTH, 30));
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		this.setBackground(Color.decode("#FFFFFF"));
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));

		JLabel label = new JLabel("<html><body><font face=\"Courier New\" size=\"3\" color='#00'>TuVacaPiso</font></body></html>");
		
		this.add(label);
		layout.putConstraint(SpringLayout.WEST, label, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, label, 0, SpringLayout.VERTICAL_CENTER, this);
		
		FxButton loginButton = new FxButton(50, 25, "Login");
		FxButton logoutButton = new FxButton(50, 25, "Logout");
		FxButton misReservasButton = new FxButton(100, 25, "Mis reservas");
		FxButton misOfertasButton = new FxButton(100, 25, "Mis ofertas");
		FxButton misInmueblesButton = new FxButton(100, 25, "Mis viviendas");
		
		this.add(loginButton);
		this.add(misOfertasButton);
		this.add(misInmueblesButton);
		this.add(misReservasButton);
		loginButton.addActionListener(loginButtonListener);
		logoutButton.addActionListener(logoutButtonListener);
		
		this.buttons.add(loginButton);
		this.buttons.add(logoutButton);
		this.buttons.add(misReservasButton);
		this.buttons.add(misOfertasButton);
		this.buttons.add(misInmueblesButton);
		logoutButton.setVisible(false);
		misOfertasButton.setVisible(false);
		misInmueblesButton.setVisible(false);
		misReservasButton.setVisible(false);
		placeButtons();
	}
	
	public void placeButtons() {
		SpringLayout layout = (SpringLayout) this.getLayout();
		
		
		List<JComponent> visible = new ArrayList<JComponent>();
		
		for (JComponent button : this.buttons) {
			if(button.isVisible()) {
				visible.add(button);
			}
		}
		
		JComponent last = visible.remove(0);
		layout.removeLayoutComponent(last);		
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, last, 0, SpringLayout.VERTICAL_CENTER, this);
		layout.putConstraint(SpringLayout.EAST, last, -10, SpringLayout.EAST, this);
		
		while(!visible.isEmpty()) {
			JComponent actual = visible.remove(0);
			layout.removeLayoutComponent(actual);
			layout.putConstraint(SpringLayout.EAST, actual, -10, SpringLayout.WEST, last);
			layout.putConstraint(SpringLayout.VERTICAL_CENTER, actual, 0, SpringLayout.VERTICAL_CENTER, this);
			last = actual;
		}
	}
	
	private ActionListener loginButtonListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			gui.setVisiblePane(Header.NAME, false);
			gui.setVisiblePane(SearchMenu.NAME, false);
			gui.setVisiblePane(LoginPanel.NAME, true);
		}
	};
	
	private ActionListener logoutButtonListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			/*gui.setVisiblePane(Header.NAME, false);
			gui.setVisiblePane(SearchMenu.NAME, false);
			gui.setVisiblePane(LoginPanel.NAME, true);*/
		}
	};
	

}

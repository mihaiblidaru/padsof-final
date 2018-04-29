package gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;


import gui.Gui;
import gui.components.fx.FxButton;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Header extends JPanel {

	private static final long serialVersionUID = -5230943621476766861L;
	public final static String NAME = "HEADER";
	private Gui gui;
	
	public final static int LOGIN = 0;
	public final static int LOGOUT = 1;
	public final static int MIS_RESERVAS = 2;
	public final static int MIS_OFERTAS = 3;
	public final static int MIS_INMUEBLES = 4;
	
	private List<FxButton> buttons;
	
	
	public void setButtonVisibility(int button, boolean state) {
		JComponent btn = buttons.get(button); 
		btn.setVisible(state);
	}
	
	public Header(Gui gui) {
		this.gui = gui;
		this.setName(NAME);
		this.buttons = new ArrayList<FxButton>();
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
		FxButton logoutButton = new FxButton(60, 25, "Logout");
		FxButton misReservasButton = new FxButton(100, 25, "Mis reservas");
		FxButton misOfertasButton = new FxButton(100, 25, "Mis ofertas");
		FxButton misInmueblesButton = new FxButton(100, 25, "Mis viviendas");
		
		logoutButton.setVisible(true);
		
		this.add(loginButton);
		this.add(logoutButton);
		this.add(misOfertasButton);
		this.add(misInmueblesButton);
		this.add(misReservasButton);
		logoutButton.addEventHandler(MouseEvent.MOUSE_CLICKED, logoutButtonHandler);
		loginButton.addEventHandler(MouseEvent.MOUSE_CLICKED, loginButtonHandler);
		
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
				
		List<FxButton> visible = new ArrayList<FxButton>();
		
		for (FxButton button : this.buttons) {
			if(button.isVisible()) {
				visible.add(button);
			}
		}
		
		FxButton last = visible.remove(0);
			
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, last, 0, SpringLayout.VERTICAL_CENTER, this);
		layout.putConstraint(SpringLayout.EAST, last, -10, SpringLayout.EAST, this);
		
		while(!visible.isEmpty()) {
			FxButton actual = visible.remove(0);
			
			layout.putConstraint(SpringLayout.EAST, actual, -10, SpringLayout.WEST, last);
			layout.putConstraint(SpringLayout.VERTICAL_CENTER, actual, 0, SpringLayout.VERTICAL_CENTER, this);
			last = actual;
		}
	}
	
	
    EventHandler<MouseEvent> loginButtonHandler = new EventHandler<MouseEvent>() { 
        @Override 
        public void handle(MouseEvent e) { 
        	gui.setVisiblePane(Header.NAME, false);
			gui.setVisiblePane(SearchMenu.NAME, false);
			gui.setVisiblePane(LoginPanel.NAME, true);
        } 
     };  
     
     EventHandler<MouseEvent> logoutButtonHandler = new EventHandler<MouseEvent>() { 
         @Override 
         public void handle(MouseEvent e) { 
        	gui.getController().logout();
 			setButtonVisibility(MIS_INMUEBLES, false);
 			setButtonVisibility(MIS_OFERTAS, false);
 			setButtonVisibility(MIS_RESERVAS, false);
 			setButtonVisibility(LOGOUT, false);
 			setButtonVisibility(LOGIN, true);
 			placeButtons();
 			
 			gui.showOnly(Header.NAME, SearchMenu.NAME);
         } 
      };  

}

package gui.listeners.loginpanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import app.clases.users.Rol;
import gui.Gui;
import gui.components.fx.FxPasswordField;
import gui.components.fx.FxTextField;
import gui.controllers.Controller;
import gui.panels.Header;
import gui.panels.LoginPanel;
import gui.panels.SearchMenu;

public class LoginButtonListener implements ActionListener {
	private Gui gui;
	private FxTextField userTextFied;
	private FxPasswordField passwordTextField;
	public LoginButtonListener(Gui gui, FxTextField userTextFied, FxPasswordField passwordTextField) {
		this.gui = gui;
		this.userTextFied = userTextFied;
		this.passwordTextField = passwordTextField;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Controller controller = gui.getController();
		String user = userTextFied.getText();
		String password = passwordTextField.getText();
		
		if(user.isEmpty() || password.isEmpty()) {
			//directamente no llama al controllador
		}else {
			Rol rol = null;
			try {
				rol = controller.login(user, password);
			} catch (SQLException e) {
				//popup;
			}
			
			if(rol==null) {
				//usuario o contraseña incorectos
			} else {
				gui.setVisiblePane(LoginPanel.NAME, false);
				Header header = (Header) gui.getComponent(Header.NAME);
				header.setButtonVisibility(Header.LOGIN, false);
				header.setButtonVisibility(Header.LOGOUT, true);
				gui.setVisiblePane(Header.NAME, true);
				if(rol==Rol.D) {
					gui.setVisiblePane(SearchMenu.NAME, true);
					header.setButtonVisibility(Header.MIS_RESERVAS, true);
				}
				
				if(rol==Rol.OD) {
					gui.setVisiblePane(SearchMenu.NAME, true);
					header.setButtonVisibility(Header.MIS_RESERVAS, true);
					header.setButtonVisibility(Header.MIS_INMUEBLES, true);
					header.setButtonVisibility(Header.MIS_OFERTAS, true);
				}
				
				if(rol==Rol.O) {
					header.setButtonVisibility(Header.MIS_INMUEBLES, true);
					header.setButtonVisibility(Header.MIS_OFERTAS, true);
				}
				header.placeButtons();
			}
		}

	}

}

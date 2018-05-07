package gui.listeners;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import gui.Gui;
import gui.components.fx.FxPasswordField;
import gui.components.fx.FxTextField;
import gui.controllers.Controller;
import gui.dialogs.EditUserDialog;
import gui.panels.Header;
import gui.panels.LoginPanel;
import gui.panels.ResultadosBusqueda;
import gui.panels.SearchMenu;
import gui.panels.admin.AdminView;
import gui.panels.admin.ControlPanel;
import gui.panels.ofertante.ofertas.MisOfertas;
import gui.util.DialogFactory;
import gui.util.GuiConstants;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class LoginButtonHandler implements EventHandler<ActionEvent> {
	private Gui gui;
	private FxTextField userTextFied;
	private FxPasswordField passwordTextField;

	public LoginButtonHandler(Gui gui, FxTextField userTextFied, FxPasswordField passwordTextField) {
		this.gui = gui;
		this.userTextFied = userTextFied;
		this.passwordTextField = passwordTextField;
	}

	@Override
	public void handle(ActionEvent event) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Controller controller = gui.getController();
				String user = userTextFied.getText();
				String password = passwordTextField.getText();

				if (user.isEmpty() || password.isEmpty()) {
					// directamente no llama al controllador
				} else {
					String rol = controller.login(user, password);

					if (rol.equals(GuiConstants.ROL_NO_REGISTRADO)) {

						final JFrame dialog = new EditUserDialog(gui);

						dialog.setVisible(true);
					} else {
						gui.setVisiblePane(LoginPanel.NAME, false);
						Header header = (Header) gui.getComponent(Header.NAME);
						gui.setVisiblePane(Header.NAME, true);
						ResultadosBusqueda rb = (ResultadosBusqueda) gui.getComponent(ResultadosBusqueda.NAME);
						if (rol.equals(GuiConstants.ROL_DEMANTANDTE)) {
							gui.showOnly(Header.NAME, SearchMenu.NAME, ResultadosBusqueda.NAME);
							header.verBotones(Header.BOTONES_DEMANDANTE);
							rb.actualizarOfertas();
						} else if (rol.equals(GuiConstants.ROL_OFERTANTE_DEMANDANTE)) {
							gui.showOnly(Header.NAME, SearchMenu.NAME, ResultadosBusqueda.NAME);
							header.verBotones(Header.BOTONES_OFERTANTE_DEMANDANTE);
							MisOfertas panelOfertas = (MisOfertas) gui.getComponent(MisOfertas.NAME);
							panelOfertas.cargarOfertas();
							rb.actualizarOfertas();
						} else if (rol.equals(GuiConstants.ROL_OFERTANTE)) {
							header.verBotones(Header.BOTONES_OFERTANTE);
							gui.setVisiblePane(MisOfertas.NAME, true);
							MisOfertas panelOfertas = (MisOfertas) gui.getComponent(MisOfertas.NAME);
							panelOfertas.cargarOfertas();
						} else if (rol.equals(GuiConstants.ROL_ADMIN)) {
							gui.setVisiblePane(AdminView.NAME, true);
							header.verBotones(Header.BOTONES_ADMIN);
							AdminView av = (AdminView) gui.getComponent(AdminView.NAME);
							av.show(ControlPanel.NAME);
							header.appNameSetVisible(false);

						} else if (rol.equals(GuiConstants.ROL_DESCONOCIDO)) {
							DialogFactory.internalError("Rol del usuario desconocido");
						}
					}
				}
			}
		});

	}
}

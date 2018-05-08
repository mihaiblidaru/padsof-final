package gui.listeners;

import javax.swing.SwingUtilities;

import gui.Gui;
import gui.components.fx.FxPasswordField;
import gui.components.fx.FxTextField;
import gui.controllers.Controller;
import gui.panels.Header;
import gui.panels.LoginPanel;
import gui.panels.ResultadosBusqueda;
import gui.panels.SearchMenu;
import gui.panels.admin.AdminView;
import gui.panels.admin.ofertas.OfertasPendientes;
import gui.panels.ofertante.ofertas.MisOfertas;
import gui.util.DialogFactory;
import gui.util.GuiConstants;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Esta clase nos sirve para trabajar con el listener de login
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 */
public class LoginButtonHandler implements EventHandler<ActionEvent> {
	
	/**
	 * La interfaz grafica
	 */
	private Gui gui;
	
	/**
	 * Para poner el usuario
	 */
	private FxTextField userTextFied;
	
	/**
	 * Para poner la contraseña
	 */
	private FxPasswordField passwordTextField;

	/**
	 * Constructor de LoginButtonHandler
	 * @param gui interfaz grafica
	 * @param userTextFied para poner el usuario
	 * @param passwordTextField para poner la contraseña
	 */
	public LoginButtonHandler(Gui gui, FxTextField userTextFied, FxPasswordField passwordTextField) {
		this.gui = gui;
		this.userTextFied = userTextFied;
		this.passwordTextField = passwordTextField;
	}

	/**
	 * Esta funcion hace el login con los datos cogido en la clae
	 * @param event evento realizado en la interfaz
	 */
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
						DialogFactory.simpleErrorMessage("Nombre de usuario o contraseña incorrectos");

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
							av.show(OfertasPendientes.NAME);
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

package gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import gui.Gui;
import gui.components.fx.FxButton;
import gui.panels.admin.AdminView;
import gui.panels.admin.ControlPanel;
import gui.panels.admin.ofertas.OfertasPendientes;
import gui.panels.admin.usuarios.UsuariosBloqueados;
import gui.panels.demandante.MisReservas;
import gui.panels.ofertante.inmuebles.MisInmuebles;
import gui.panels.ofertante.ofertas.MisOfertas;
import gui.util.Nombrable;
import gui.util.PanelInterfazPrincipal;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Header extends JPanel implements Nombrable, PanelInterfazPrincipal {

	private static final long serialVersionUID = -5230943621476766861L;
	public final static String NAME = "HEADER";
	private Gui gui;

	public final static int BOTONES_ADMIN = 1;
	public final static int BOTONES_OFERTANTE = 2;
	public final static int BOTONES_DEMANDANTE = 3;
	public final static int BOTONES_OFERTANTE_DEMANDANTE = 4;
	public final static int BOTONES_NO_REGISTRADO = 5;

	private static final int PANEL_HEIGHT = 30;
	private static final int PANEL_WIDTH = Gui.FRAME_WIDTH;

	private JLabel appName;
	private FxButton panelDeControl;
	private FxButton adminOfertas;
	private FxButton adminUsuarios;
	private FxButton misReservasButton;
	private FxButton logoutButton;
	private FxButton loginButton;
	private FxButton misInmueblesButton;
	private FxButton misOfertasButton;
	private SpringLayout layout;
	private JPanel grupoBotonesUsuario;
	private JPanel grupoBotonesAdmin;

	public Header(Gui gui) {
		this.gui = gui;
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));
		this.initialize();
	}

	@Override
	public void setDimension() {
		this.setPreferredSize(new Dimension(PANEL_WIDTH - 6, PANEL_HEIGHT));
	}

	@Override
	public void crearComponentes() {
		this.loginButton = new FxButton(50, 25, "Login");
		this.logoutButton = new FxButton(60, 25, "Logout");
		this.misReservasButton = new FxButton(100, 25, "Mis reservas");
		this.misOfertasButton = new FxButton(100, 25, "Mis ofertas");
		this.misInmueblesButton = new FxButton(100, 25, "Mis viviendas");
		this.panelDeControl = new FxButton(140, 25, "Panel de Control");
		this.adminOfertas = new FxButton(80, 25, "Ofertas");
		this.adminUsuarios = new FxButton(90, 25, "Usuarios");
		this.appName = new JLabel("TuVacaPiso");
		this.grupoBotonesUsuario = new JPanel();
		this.grupoBotonesAdmin = new JPanel();
		Font font = new Font("Courier New", Font.BOLD, 15);
		this.appName.setFont(font);

		this.add(appName);
		this.add(grupoBotonesAdmin);
		this.add(grupoBotonesUsuario);
		grupoBotonesAdmin.add(panelDeControl);
		grupoBotonesAdmin.add(adminOfertas);
		grupoBotonesAdmin.add(adminUsuarios);

		grupoBotonesUsuario.add(misOfertasButton);
		grupoBotonesUsuario.add(misInmueblesButton);
		grupoBotonesUsuario.add(misReservasButton);
		grupoBotonesUsuario.add(logoutButton);
		grupoBotonesUsuario.add(loginButton);

		grupoBotonesAdmin.setVisible(false);
		this.logoutButton.setVisible(false);
		this.misOfertasButton.setVisible(false);
		this.misInmueblesButton.setVisible(false);
		this.misReservasButton.setVisible(false);
	}

	@Override
	public void colocarComponentes() {
		this.layout = new SpringLayout();
		this.setLayout(layout);

		layout.putConstraint(SpringLayout.WEST, appName, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, appName, 0, SpringLayout.VERTICAL_CENTER, this);

		FlowLayout l1 = new FlowLayout(FlowLayout.LEFT, 5, 0);
		this.grupoBotonesAdmin.setLayout(l1);

		FlowLayout l2 = new FlowLayout(FlowLayout.RIGHT, 5, 0);
		this.grupoBotonesUsuario.setLayout(l2);

		layout.putConstraint(SpringLayout.WEST, grupoBotonesAdmin, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, grupoBotonesAdmin, 0, SpringLayout.VERTICAL_CENTER, this);

		layout.putConstraint(SpringLayout.EAST, grupoBotonesUsuario, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, grupoBotonesUsuario, 0, SpringLayout.VERTICAL_CENTER, this);

	}

	@Override
	public void registrarEventos() {
		logoutButton.setOnAction(logoutButtonHandler);
		loginButton.setOnAction(event -> {
			gui.pushVisible();
			gui.showOnly(LoginPanel.NAME);
		});

		misOfertasButton.setOnAction(event -> gui.showOnly(Header.NAME, MisOfertas.NAME));
		misInmueblesButton.setOnAction(event -> {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					gui.showOnly(Header.NAME, MisInmuebles.NAME);
				}
			});
		});

		misReservasButton.setOnAction(event -> {
			SwingUtilities.invokeLater(() -> gui.showOnly(MisReservas.NAME, Header.NAME));
		});

		panelDeControl.setOnAction(event -> {
			SwingUtilities.invokeLater(() -> {
				AdminView av = (AdminView) gui.getComponent(AdminView.NAME);
				av.show(ControlPanel.NAME);
			});
		});

		adminOfertas.setOnAction(event -> {
			SwingUtilities.invokeLater(() -> {
				AdminView av = (AdminView) gui.getComponent(AdminView.NAME);
				av.show(OfertasPendientes.NAME);
			});
		});

		adminUsuarios.setOnAction(event -> {
			SwingUtilities.invokeLater(() -> {
				AdminView av = (AdminView) gui.getComponent(AdminView.NAME);
				av.show(UsuariosBloqueados.NAME);
			});
		});
	}

	EventHandler<ActionEvent> logoutButtonHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			SwingUtilities.invokeLater(() -> {

				gui.getController().logout();
				verBotones(BOTONES_NO_REGISTRADO);
				appNameSetVisible(true);

				MisOfertas mo = (MisOfertas) gui.getComponent(MisOfertas.NAME);
				mo.clearOfertas();
				gui.showOnly(Header.NAME, SearchMenu.NAME, ResultadosBusqueda.NAME);

			});

		}
	};

	public void appNameSetVisible(boolean state) {
		appName.setVisible(state);
	}

	public void verBotones(int grupoBotones) {
		grupoBotonesAdmin.setVisible(false);
		this.loginButton.setVisible(false);
		this.logoutButton.setVisible(false);
		this.misOfertasButton.setVisible(false);
		this.misInmueblesButton.setVisible(false);
		this.misReservasButton.setVisible(false);

		switch (grupoBotones) {
		case BOTONES_OFERTANTE_DEMANDANTE:
			misReservasButton.setVisible(true);
			misOfertasButton.setVisible(true);
			misInmueblesButton.setVisible(true);
			break;
		case BOTONES_OFERTANTE:
			misOfertasButton.setVisible(true);
			misInmueblesButton.setVisible(true);
			break;
		case BOTONES_DEMANDANTE:
			misReservasButton.setVisible(true);
			break;
		case BOTONES_NO_REGISTRADO:
			loginButton.setVisible(true);
			break;
		case BOTONES_ADMIN:
			grupoBotonesAdmin.setVisible(true);
			break;
		}
		if (grupoBotones != BOTONES_NO_REGISTRADO) {
			logoutButton.setVisible(true);
		}
	}

}

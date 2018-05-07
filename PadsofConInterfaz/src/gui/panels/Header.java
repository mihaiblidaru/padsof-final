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
import gui.panels.admin.ofertas.OfertasPendientes;
import gui.panels.admin.usuarios.UsuariosBloqueados;
import gui.panels.demandante.MisReservas;
import gui.panels.ofertante.inmuebles.MisInmuebles;
import gui.panels.ofertante.ofertas.MisOfertas;
import gui.util.Nombrable;
import gui.util.PanelInterfaz;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Header extends PanelInterfaz implements Nombrable {

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
	private FxButton botonBusqueda;

	public Header(Gui gui) {
		this.gui = gui;
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));
		initialize();
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
		this.adminOfertas = new FxButton(80, 25, "Ofertas");
		this.adminUsuarios = new FxButton(90, 25, "Usuarios");
		this.appName = new JLabel("TuVacaPiso");
		this.botonBusqueda = new FxButton(90, 25, "Busqueda");

		this.grupoBotonesUsuario = new JPanel();
		this.grupoBotonesAdmin = new JPanel();
		Font font = new Font("Courier New", Font.BOLD, 15);
		this.appName.setFont(font);

		this.add(appName);
		this.add(grupoBotonesAdmin);
		this.add(grupoBotonesUsuario);
		this.add(botonBusqueda);
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
		this.botonBusqueda.setVisible(false);

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

		layout.putConstraint(SpringLayout.WEST, botonBusqueda, 30, SpringLayout.EAST, appName);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, botonBusqueda, 0, SpringLayout.VERTICAL_CENTER, this);

		layout.putConstraint(SpringLayout.WEST, grupoBotonesAdmin, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, grupoBotonesAdmin, 0, SpringLayout.VERTICAL_CENTER, this);

		layout.putConstraint(SpringLayout.EAST, grupoBotonesUsuario, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, grupoBotonesUsuario, 0, SpringLayout.VERTICAL_CENTER, this);
	}

	@Override
	public void registrarEventos() {
		botonBusqueda.setOnAction(e -> {
			SwingUtilities.invokeLater(() -> {
				gui.showOnly(Header.NAME, SearchMenu.NAME, ResultadosBusqueda.NAME);
			});
		});

		logoutButton.setOnAction(logoutButtonHandler);
		loginButton.setOnAction(event -> {
			gui.showOnly(LoginPanel.NAME);
		});

		misOfertasButton.setOnAction(event -> {
			gui.showOnly(Header.NAME, MisOfertas.NAME);
		});

		misInmueblesButton.setOnAction(event -> {
			SwingUtilities.invokeLater(() -> {
				gui.showOnly(Header.NAME, MisInmuebles.NAME);
			});
		});

		misReservasButton.setOnAction(event -> {
			SwingUtilities.invokeLater(() -> {
				MisReservas misReservas = (MisReservas) gui.getComponent(MisReservas.NAME);
				misReservas.cargarOfertas();
				gui.showOnly(MisReservas.NAME, Header.NAME);
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

				ResultadosBusqueda rb = (ResultadosBusqueda) gui.getComponent(ResultadosBusqueda.NAME);
				rb.actualizarOfertas();
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
			botonBusqueda.setVisible(true);
			break;
		case BOTONES_OFERTANTE:
			botonBusqueda.setVisible(false);
			misOfertasButton.setVisible(true);
			misInmueblesButton.setVisible(true);
			break;
		case BOTONES_DEMANDANTE:
			misReservasButton.setVisible(true);
			botonBusqueda.setVisible(true);
			break;
		case BOTONES_NO_REGISTRADO:
			loginButton.setVisible(true);
			botonBusqueda.setVisible(false);
			break;
		case BOTONES_ADMIN:
			grupoBotonesAdmin.setVisible(true);
			botonBusqueda.setVisible(false);
			break;
		}
		if (grupoBotones != BOTONES_NO_REGISTRADO) {
			logoutButton.setVisible(true);
		}
	}

}

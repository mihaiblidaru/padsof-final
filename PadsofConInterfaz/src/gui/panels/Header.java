package gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import gui.Gui;
import gui.components.fx.FxButton;
import gui.panels.admin.AdminView;
import gui.panels.admin.ControlPanel;
import gui.panels.admin.UsuariosTab;
import gui.panels.admin.ofertas.OfertasPendientes;
import gui.panels.demandante.MisReservas;
import gui.panels.ofertante.inmuebles.MisInmuebles;
import gui.panels.ofertante.ofertas.MisOfertas;
import gui.util.Nombrable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Header extends JPanel implements Nombrable {

	private static final long serialVersionUID = -5230943621476766861L;
	public final static String NAME = "HEADER";
	private Gui gui;

	public final static int LOGIN = 0;
	public final static int LOGOUT = 1;
	public final static int MIS_RESERVAS = 2;
	public final static int MIS_OFERTAS = 3;
	public final static int MIS_INMUEBLES = 4;
	public final static int PANEL_DE_CONTROL = 5;
	public final static int ADMIN_OFERTAS = 6;
	public final static int ADMIN_USUARIOS = 7;

	private List<FxButton> buttons;
	private JLabel appName;
	private FxButton panelDeControl;
	private FxButton adminOfertas;
	private FxButton adminUsuarios;

	private static Header instance = null;

	public static Header getInstance(Gui gui) {
		if (instance == null) {
			return (instance = new Header(gui));
		} else {
			return instance;
		}
	}

	public void setButtonVisibility(int button, boolean state) {
		buttons.get(button).setVisible(state);
	}

	private Header(Gui gui) {
		this.gui = gui;
		this.buttons = new ArrayList<FxButton>();
		this.setPreferredSize(new Dimension(Gui.FRAME_WIDTH, 30));
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		this.setBackground(Color.decode("#fefffe"));
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));

		Font font = new Font("Courier New", Font.BOLD, 15);
		appName = new JLabel("TuVacaPiso");
		appName.setFont(font);

		this.add(appName);
		layout.putConstraint(SpringLayout.WEST, appName, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, appName, 0, SpringLayout.VERTICAL_CENTER, this);

		FxButton loginButton = new FxButton(50, 25, "Login");
		FxButton logoutButton = new FxButton(60, 25, "Logout");
		FxButton misReservasButton = new FxButton(100, 25, "Mis reservas");
		FxButton misOfertasButton = new FxButton(100, 25, "Mis ofertas");
		FxButton misInmueblesButton = new FxButton(100, 25, "Mis viviendas");
		panelDeControl = new FxButton(140, 25, "Panel de Control");
		adminOfertas = new FxButton(80, 25, "Ofertas");
		adminUsuarios = new FxButton(90, 25, "Usuarios");

		logoutButton.setVisible(true);

		this.add(loginButton);
		this.add(logoutButton);
		this.add(misOfertasButton);
		this.add(misInmueblesButton);
		this.add(misReservasButton);
		this.add(panelDeControl);
		this.add(adminOfertas);
		this.add(adminUsuarios);

		layout.putConstraint(SpringLayout.WEST, panelDeControl, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, adminOfertas, 10, SpringLayout.EAST, panelDeControl);
		layout.putConstraint(SpringLayout.WEST, adminUsuarios, 10, SpringLayout.EAST, adminOfertas);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, panelDeControl, 0, SpringLayout.VERTICAL_CENTER, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, adminOfertas, 0, SpringLayout.VERTICAL_CENTER, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, adminUsuarios, 0, SpringLayout.VERTICAL_CENTER, this);

		logoutButton.setOnAction(logoutButtonHandler);
		loginButton.setOnAction(event -> {
			gui.pushVisible();
			gui.showOnly(LoginPanel.NAME);
		});

		this.buttons.add(loginButton);
		this.buttons.add(logoutButton);
		this.buttons.add(misReservasButton);
		this.buttons.add(misOfertasButton);
		this.buttons.add(misInmueblesButton);
		this.buttons.add(panelDeControl);
		this.buttons.add(adminOfertas);
		this.buttons.add(adminUsuarios);
		panelDeControl.setVisible(false);
		adminOfertas.setVisible(false);
		adminUsuarios.setVisible(false);
		logoutButton.setVisible(false);
		misOfertasButton.setVisible(false);
		misInmueblesButton.setVisible(false);
		misReservasButton.setVisible(false);
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

		placeUserButtons();
		setAdminButtonListeners();
	}

	public void placeUserButtons() {
		SpringLayout layout = (SpringLayout) this.getLayout();

		List<FxButton> visible = new ArrayList<FxButton>();

		for (int i = 0; i < 4; i++) {
			if (buttons.get(i).isVisible()) {
				visible.add(buttons.get(i));
			}
		}

		FxButton last = visible.remove(0);

		layout.putConstraint(SpringLayout.VERTICAL_CENTER, last, 0, SpringLayout.VERTICAL_CENTER, this);
		layout.putConstraint(SpringLayout.EAST, last, -10, SpringLayout.EAST, this);

		while (!visible.isEmpty()) {
			FxButton actual = visible.remove(0);

			layout.putConstraint(SpringLayout.EAST, actual, -10, SpringLayout.WEST, last);
			layout.putConstraint(SpringLayout.VERTICAL_CENTER, actual, 0, SpringLayout.VERTICAL_CENTER, this);
			last = actual;
		}
	}

	EventHandler<ActionEvent> logoutButtonHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			SwingUtilities.invokeLater(() -> {

				gui.getController().logout();
				setButtonVisibility(MIS_INMUEBLES, false);
				setButtonVisibility(MIS_OFERTAS, false);
				setButtonVisibility(MIS_RESERVAS, false);
				setButtonVisibility(LOGOUT, false);
				setButtonVisibility(LOGIN, true);
				setButtonVisibility(Header.PANEL_DE_CONTROL, false);
				setButtonVisibility(Header.ADMIN_OFERTAS, false);
				setButtonVisibility(Header.ADMIN_USUARIOS, false);
				appNameSetVisible(true);

				placeUserButtons();
				MisOfertas mo = (MisOfertas) gui.getComponent(MisOfertas.NAME);
				mo.clearOfertas();
				gui.showOnly(Header.NAME, SearchMenu.NAME, ResultadosBusqueda.NAME);

			});

		}
	};

	private void setAdminButtonListeners() {
		AdminView av = AdminView.getInstance(gui);
		panelDeControl.setOnAction(event -> {
			SwingUtilities.invokeLater(() -> {
				av.show(ControlPanel.NAME);
			});
		});

		adminOfertas.setOnAction(event -> {
			SwingUtilities.invokeLater(() -> {
				av.show(OfertasPendientes.NAME);
			});
		});

		adminUsuarios.setOnAction(event -> {
			SwingUtilities.invokeLater(() -> {
				av.show(UsuariosTab.NAME);
			});
		});

	}

	public void appNameSetVisible(boolean state) {
		appName.setVisible(state);
	}

}

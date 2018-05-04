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
import javax.swing.SwingUtilities;

import gui.Gui;
import gui.components.fx.FxButton;
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

	private List<FxButton> buttons;

	private static Header instance = null;

	public static Header getInstance(Gui gui) {
		if (instance == null) {
			return (instance = new Header(gui));
		} else {
			return instance;
		}
	}

	public void setButtonVisibility(int button, boolean state) {
		JComponent btn = buttons.get(button);
		btn.setVisible(state);
	}

	private Header(Gui gui) {
		this.gui = gui;
		this.setName(NAME);
		this.buttons = new ArrayList<FxButton>();
		this.setPreferredSize(new Dimension(Gui.FRAME_WIDTH, 30));
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		this.setBackground(Color.decode("#fefffe"));
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));

		JLabel label = new JLabel(
				"<html><body><font face=\"Courier New\" size=\"3\" color='#00'>TuVacaPiso</font></body></html>");

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

		placeButtons();

	}

	public void placeButtons() {
		SpringLayout layout = (SpringLayout) this.getLayout();

		List<FxButton> visible = new ArrayList<FxButton>();

		for (FxButton button : this.buttons) {
			if (button.isVisible()) {
				visible.add(button);
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
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					gui.getController().logout();
					setButtonVisibility(MIS_INMUEBLES, false);
					setButtonVisibility(MIS_OFERTAS, false);
					setButtonVisibility(MIS_RESERVAS, false);
					setButtonVisibility(LOGOUT, false);
					setButtonVisibility(LOGIN, true);
					placeButtons();
					MisOfertas mo = (MisOfertas) gui.getComponent(MisOfertas.NAME);
					mo.clearOfertas();
					gui.showOnly(Header.NAME, SearchMenu.NAME, ResultadosBusqueda.NAME);
				}
			});

		}
	};

}

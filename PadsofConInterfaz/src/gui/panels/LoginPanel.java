package gui.panels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import gui.Gui;
import gui.components.fx.FxButton;
import gui.components.fx.FxPasswordField;
import gui.components.fx.FxTextField;
import gui.listeners.LoginButtonHandler;
import gui.util.Nombrable;
import gui.util.PanelInterfaz;
import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginPanel extends PanelInterfaz implements Nombrable {

	private static final int PANEL_HEIGHT = 180;
	private static final int PANEL_WIDTH = 300;
	private static final long serialVersionUID = -3260581588702723617L;
	public final static String NAME = "LOGIN_PANEL";
	private Gui gui;
	private SpringLayout layout;

	private FxTextField textUsuario;
	private FxPasswordField textPassword;
	private Font titleFont;
	private JLabel labelTitulo;
	private JLabel labelUsuario;
	private JLabel labelPassword;
	private FxButton loginBtn;
	private FxButton volverBtn;
	private JPanel grupoBotones;

	public LoginPanel(Gui gui) {
		this.gui = gui;
		this.setBorder(BorderFactory.createEtchedBorder());
		initialize();
	}

	@Override
	public void setVisible(boolean state) {
		super.setVisible(state);
		if (state == true) {
			this.textUsuario.setText("");
			this.textPassword.setText("");
		}
	}

	@Override
	public void crearComponentes() {
		ResourceBundle rb = ResourceBundle.getBundle("locale.Lang");
		this.titleFont = new Font("Comic Sans", Font.PLAIN, 35);
		this.labelTitulo = new JLabel(rb.getString("app.name"));
		this.labelUsuario = new JLabel(rb.getString("panels.login.user"));
		this.textUsuario = new FxTextField(170, 25, rb.getString("panels.login.userInstruction"));
		this.labelPassword = new JLabel(rb.getString("panels.login.password"));
		this.textPassword = new FxPasswordField(170, 25, rb.getString("panels.login.password"));
		this.loginBtn = new FxButton(80, 30, rb.getString("panels.login.loginBtn"));
		this.volverBtn = new FxButton(80, 30, "Volver");
		this.grupoBotones = new JPanel();

		volverBtn.setGraphics("res/img/fa-triangle-left.png", 15, 15);
		volverBtn.setContentDisplay(ContentDisplay.LEFT);
		volverBtn.setFontScale(0.4);

		labelTitulo.setFont(titleFont);
		add(labelTitulo);
		add(labelUsuario);
		add(textUsuario);
		add(labelPassword);
		grupoBotones.add(volverBtn);
		grupoBotones.add(loginBtn);
		add(grupoBotones);
	}

	@Override
	public void colocarComponentes() {
		this.layout = new SpringLayout();
		this.setLayout(this.layout);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, labelTitulo, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, labelTitulo, 0, SpringLayout.NORTH, this);

		layout.putConstraint(SpringLayout.NORTH, labelUsuario, 15, SpringLayout.SOUTH, labelTitulo);
		layout.putConstraint(SpringLayout.WEST, labelUsuario, -30, SpringLayout.WEST, labelTitulo);

		layout.putConstraint(SpringLayout.VERTICAL_CENTER, textUsuario, 0, SpringLayout.VERTICAL_CENTER, labelUsuario);
		layout.putConstraint(SpringLayout.WEST, textUsuario, 25, SpringLayout.EAST, labelUsuario);

		layout.putConstraint(SpringLayout.NORTH, labelPassword, 20, SpringLayout.SOUTH, labelUsuario);
		layout.putConstraint(SpringLayout.WEST, labelPassword, 0, SpringLayout.WEST, labelUsuario);

		layout.putConstraint(SpringLayout.VERTICAL_CENTER, textPassword, 0, SpringLayout.VERTICAL_CENTER,
				labelPassword);
		layout.putConstraint(SpringLayout.WEST, textPassword, 0, SpringLayout.WEST, textUsuario);
		add(textPassword);

		FlowLayout l1 = new FlowLayout(FlowLayout.CENTER, 10, 0);
		this.grupoBotones.setLayout(l1);

		layout.putConstraint(SpringLayout.NORTH, grupoBotones, 20, SpringLayout.SOUTH, textPassword);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, grupoBotones, 0, SpringLayout.HORIZONTAL_CENTER, this);
	}

	@Override
	public void registrarEventos() {
		loginBtn.setOnAction(new LoginButtonHandler(gui, textUsuario, textPassword));

		volverBtn.setOnAction(event -> {
			SwingUtilities.invokeLater(() -> {
				gui.showOnly(Header.NAME, SearchMenu.NAME, ResultadosBusqueda.NAME);
			});
		});

		EventHandler<KeyEvent> handler = (event) -> {
			if (event.getCode() == KeyCode.ENTER) {
				loginBtn.fire();
			}
		};

		textPassword.addEventHandler(KeyEvent.KEY_PRESSED, handler);
		textUsuario.addEventHandler(KeyEvent.KEY_PRESSED, handler);

	}

	@Override
	public void setDimension() {
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
	}
}

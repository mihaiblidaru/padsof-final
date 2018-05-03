package gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import gui.Gui;
import gui.components.fx.FxButton;
import gui.components.fx.FxPasswordField;
import gui.components.fx.FxTextField;
import gui.listeners.LoginButtonHandler;
import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginPanel extends JPanel {

	private static final long serialVersionUID = -3260581588702723617L;
	public final static String NAME = "LOGIN_PANEL";
	private Gui gui;
	private final SpringLayout layout;

	private FxTextField textUsuario;
	private FxPasswordField textPassword;

	private static LoginPanel instance = null;

	public static LoginPanel getInstance(Gui gui) {
		if (instance == null) {
			return (instance = new LoginPanel(gui));
		} else {
			return instance;
		}
	}

	private LoginPanel(Gui gui) {
		this.gui = gui;
		this.setName(NAME);

		this.setBackground(Color.WHITE);
		this.layout = new SpringLayout();
		this.setLayout(this.layout);
		this.setBorder(BorderFactory.createEtchedBorder());
		this.setPreferredSize(new Dimension(300, 180));
		createComponents();
	}

	private void createComponents() {
		ResourceBundle rb = ResourceBundle.getBundle("locale.Lang");
		JLabel labelTitulo = new JLabel(rb.getString("app.name"));

		Font titleFont = new Font("Comic Sans", Font.PLAIN, 35);
		labelTitulo.setFont(titleFont);

		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, labelTitulo, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, labelTitulo, 0, SpringLayout.NORTH, this);
		add(labelTitulo);

		JLabel labelUsuario = new JLabel(rb.getString("panels.login.user"));
		layout.putConstraint(SpringLayout.NORTH, labelUsuario, 15, SpringLayout.SOUTH, labelTitulo);
		layout.putConstraint(SpringLayout.WEST, labelUsuario, -30, SpringLayout.WEST, labelTitulo);
		add(labelUsuario);

		this.textUsuario = new FxTextField(170, 25, rb.getString("panels.login.userInstruction"));
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, textUsuario, 0, SpringLayout.VERTICAL_CENTER, labelUsuario);
		layout.putConstraint(SpringLayout.WEST, textUsuario, 25, SpringLayout.EAST, labelUsuario);
		add(textUsuario);

		JLabel labelPassword = new JLabel(rb.getString("panels.login.password"));
		layout.putConstraint(SpringLayout.NORTH, labelPassword, 20, SpringLayout.SOUTH, labelUsuario);
		layout.putConstraint(SpringLayout.WEST, labelPassword, 0, SpringLayout.WEST, labelUsuario);
		add(labelPassword);

		this.textPassword = new FxPasswordField(170, 25, rb.getString("panels.login.password"));
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, textPassword, 0, SpringLayout.VERTICAL_CENTER,
				labelPassword);
		layout.putConstraint(SpringLayout.WEST, textPassword, 0, SpringLayout.WEST, textUsuario);
		add(textPassword);

		FxButton loginBtn = new FxButton(80, 30, rb.getString("panels.login.loginBtn"));
		layout.putConstraint(SpringLayout.EAST, loginBtn, -60, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, loginBtn, 20, SpringLayout.SOUTH, textPassword);
		add(loginBtn);
		loginBtn.setOnAction(new LoginButtonHandler(gui, textUsuario, textPassword));

		FxButton volverBtn = new FxButton(80, 30, "Volver");
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, volverBtn, 0, SpringLayout.VERTICAL_CENTER, loginBtn);
		layout.putConstraint(SpringLayout.EAST, volverBtn, -15, SpringLayout.WEST, loginBtn);
		add(volverBtn);
		volverBtn.setGraphics("res/img/fa-triangle-left.png", 15, 15);
		volverBtn.setContentDisplay(ContentDisplay.LEFT);
		volverBtn.setFontScale(0.4);

		volverBtn.setOnAction(event -> gui.popVisible());

		EventHandler<KeyEvent> handler = (event) -> {
			if (event.getCode() == KeyCode.ENTER) {
				loginBtn.fire();
			}
		};

		textPassword.addEventHandler(KeyEvent.KEY_PRESSED, handler);
		textUsuario.addEventHandler(KeyEvent.KEY_PRESSED, handler);

	}

	@Override
	public void setVisible(boolean state) {
		super.setVisible(state);
		if (state == true) {
			clearField();
		}
	}

	private void clearField() {
		this.textUsuario.setText("");
		this.textPassword.setText("");
	}
}

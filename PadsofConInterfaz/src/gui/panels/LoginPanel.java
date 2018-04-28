package gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ResourceBundle;

import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.BevelBorder;

import gui.Gui;
import gui.components.fx.FxButton;
import gui.components.fx.FxPasswordField;
import gui.components.fx.FxTextField;
import gui.listeners.loginpanel.LoginButtonListener;

import javax.swing.JLabel;

public class LoginPanel extends JPanel {

	private static final long serialVersionUID = -3260581588702723617L;
	public final static String NAME = "LOGIN_PANEL";
	private Gui gui;
	
	public LoginPanel(Gui gui) {
		this.gui = gui;
		this.setName(NAME);
		ResourceBundle rb =  ResourceBundle.getBundle("locale.Lang");
		this.setBackground(Color.WHITE);
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		this.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		
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
		
		FxTextField textUsuario = new FxTextField(170,25, rb.getString("panels.login.userInstruction"));
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, textUsuario, 0, SpringLayout.VERTICAL_CENTER, labelUsuario);
		layout.putConstraint(SpringLayout.WEST, textUsuario, 25, SpringLayout.EAST, labelUsuario);
		add(textUsuario);
		
		JLabel labelPassword = new JLabel(rb.getString("panels.login.password"));
		layout.putConstraint(SpringLayout.NORTH, labelPassword, 20, SpringLayout.SOUTH, labelUsuario);
		layout.putConstraint(SpringLayout.WEST, labelPassword, 0, SpringLayout.WEST, labelUsuario);
		add(labelPassword);
		
		
		FxPasswordField textPassword = new FxPasswordField(170, 25, rb.getString("panels.login.password"));
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, textPassword, 0, SpringLayout.VERTICAL_CENTER, labelPassword);
		layout.putConstraint(SpringLayout.WEST, textPassword, 0, SpringLayout.WEST, textUsuario);
		add(textPassword);
		
		FxButton loginBtn = new FxButton(80,30, rb.getString("panels.login.loginBtn"));
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, loginBtn, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, loginBtn, 20, SpringLayout.SOUTH, textPassword);
		
		add(loginBtn);
		
		loginBtn.addActionListener(new LoginButtonListener(gui, textUsuario, textPassword));
		this.setPreferredSize(new Dimension(300, 180));

		
	}
}

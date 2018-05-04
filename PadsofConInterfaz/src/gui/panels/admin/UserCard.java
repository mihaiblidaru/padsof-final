package gui.panels.admin;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class UserCard extends JPanel {

	private static final long serialVersionUID = -222109993973543359L;
	public static final int PANEL_WIDTH = 200;
	public static final int PANEL_HEIGHT = 160;

	private int userID;

	public UserCard(Integer userID) {
		this.userID = userID;
		this.setPreferredSize(new Dimension(200, 160));
		this.setBackground(Color.CYAN);
	}

	public Integer getUserId() {
		return userID;
	}

}

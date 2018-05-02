package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.SpringLayout;
import javax.swing.UIManager;

import gui.controllers.Controller;
import gui.panels.Header;
import gui.panels.LoginPanel;
import gui.panels.ResultadosBusqueda;
import gui.panels.SearchMenu;
import gui.panels.SplashScreen;
import gui.panels.admin.AdminView;
import gui.panels.ofertante.AniadirOferta;
import gui.panels.ofertante.AniadirVivienda;
import gui.panels.ofertante.MisOfertas;

public class Gui extends JFrame {

	private static final long serialVersionUID = -8417053675962769827L;
	public static int FRAME_WIDTH = 1000;
	public static int FRAME_HEIGHT = 621;

	private Controller controller;
	private JLayeredPane contentPane;
	private SpringLayout layout;

	public void SetController(Controller controller) {
		this.controller = controller;
	}

	Stack<List<Component>> stack = new Stack<>();

	private void initialize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds((int) (screenSize.getWidth() / 2 - FRAME_WIDTH / 2),
				(int) (screenSize.getHeight() / 2 - FRAME_HEIGHT / 2), FRAME_WIDTH, FRAME_HEIGHT);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.contentPane = new JLayeredPane();
		this.setContentPane(this.contentPane);
		this.contentPane.setOpaque(true);
		contentPane.setBackground(Color.decode("#ffffff"));

		this.layout = new SpringLayout();
		getContentPane().setLayout(this.layout);
	}

	public static void main(String[] args) {
		Gui gui = new Gui();
		UIManager.put("Panel.background", Color.WHITE);
		UIManager.put("OptionPane.background", Color.WHITE);
		SplashScreen splashScreen = new SplashScreen();
		Controller controller = null;
		try {
			controller = new Controller();
		} catch (SQLException e) {
			JOptionPane.showInputDialog(gui, "SQL ERROR", null);
		}

		gui.initialize();
		gui.createPanels();
		gui.setVisible(true);
		splashScreen.setVisible(false);
		splashScreen.dispose();
		splashScreen = null;
		gui.SetController(controller);
	}

	private void createPanels() {
		Header header = Header.getInstance(this);
		contentPane.add(header);
		contentPane.setBackground(Color.WHITE);
		LoginPanel loginPanel = LoginPanel.getInstance(this);
		contentPane.add(loginPanel);
		loginPanel.setVisible(false);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, loginPanel, 0, SpringLayout.HORIZONTAL_CENTER,
				contentPane);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, loginPanel, 0, SpringLayout.VERTICAL_CENTER, contentPane);

		SearchMenu searchMenu = new SearchMenu();
		contentPane.add(searchMenu);
		layout.putConstraint(SpringLayout.NORTH, searchMenu, 0, SpringLayout.SOUTH, header);

		AdminView adminView = new AdminView(this);
		contentPane.add(adminView);
		layout.putConstraint(SpringLayout.NORTH, adminView, -25, SpringLayout.SOUTH, header);
		layout.putConstraint(SpringLayout.WEST, adminView, -2, SpringLayout.WEST, header);
		contentPane.moveToFront(adminView);
		adminView.setVisible(false);

		AniadirOferta aniadirOferta = AniadirOferta.getInstance(this);
		contentPane.add(aniadirOferta);
		layout.putConstraint(SpringLayout.NORTH, aniadirOferta, 0, SpringLayout.SOUTH, header);
		layout.putConstraint(SpringLayout.WEST, aniadirOferta, 0, SpringLayout.WEST, contentPane);
		aniadirOferta.setVisible(false);

		AniadirVivienda aniadirVivienda = AniadirVivienda.getInstance(this);

		contentPane.add(aniadirVivienda);
		layout.putConstraint(SpringLayout.NORTH, aniadirVivienda, 0, SpringLayout.SOUTH, header);
		layout.putConstraint(SpringLayout.WEST, aniadirVivienda, 0, SpringLayout.WEST, contentPane);
		aniadirVivienda.setVisible(false);
		searchMenu.setVisible(true);

		ResultadosBusqueda resBusqueda = ResultadosBusqueda.getInstance(this);
		contentPane.add(resBusqueda);
		layout.putConstraint(SpringLayout.NORTH, resBusqueda, 0, SpringLayout.SOUTH, header);
		layout.putConstraint(SpringLayout.WEST, resBusqueda, 0, SpringLayout.EAST, searchMenu);

		MisOfertas interfazOfertante = MisOfertas.getInstance(this);
		contentPane.add(interfazOfertante);
		layout.putConstraint(SpringLayout.NORTH, interfazOfertante, 0, SpringLayout.SOUTH, header);
		layout.putConstraint(SpringLayout.WEST, interfazOfertante, 0, SpringLayout.WEST, contentPane);
		interfazOfertante.setVisible(false);

		// resBusqueda.setVisible(false);
		// searchMenu.setVisible(false);

	}

	public Controller getController() {
		return this.controller;
	}

	public void setVisiblePane(String panelName, boolean state) {
		Component c = getComponent(panelName);
		if (c != null) {
			c.setVisible(state);
		}
	}

	public void showOnly(String... panelsName) {
		Component[] components = this.contentPane.getComponents();
		for (int i = 0; i < components.length; i++) {
			components[i].setVisible(false);
		}

		for (int i = 0; i < components.length; i++) {
			for (int j = 0; j < panelsName.length; j++) {
				if (panelsName[j].equals(components[i].getName())) {
					components[i].setVisible(true);
				}
			}
		}
	}

	public Component getComponent(String panelName) {
		Component[] components = this.contentPane.getComponents();
		Component res = null;
		for (int i = 0; i < components.length; i++) {
			if (panelName.equals(components[i].getName())) {
				res = components[i];
				break;
			}
		}
		return res;
	}

	public void pushVisible() {
		stack.push(Arrays.asList(this.contentPane.getComponents()).stream().filter(c -> c.isVisible())
				.collect(Collectors.toList()));
	}

	public void popVisible() {
		List<Component> last = stack.pop();
		Arrays.asList(this.contentPane.getComponents()).stream().forEach(c -> c.setVisible(false));
		last.stream().forEach(c -> c.setVisible(true));
	}

}

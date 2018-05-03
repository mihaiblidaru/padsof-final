package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import gui.controllers.Controller;
import gui.panels.Header;
import gui.panels.LoginPanel;
import gui.panels.ResultadosBusqueda;
import gui.panels.SearchMenu;
import gui.panels.SplashScreen;
import gui.panels.admin.AdminView;
import gui.panels.ofertante.inmuebles.AniadirInmueble;
import gui.panels.ofertante.inmuebles.MisInmuebles;
import gui.panels.ofertante.ofertas.AniadirOferta;
import gui.panels.ofertante.ofertas.EditarOferta;
import gui.panels.ofertante.ofertas.MisOfertas;

public class Gui extends JFrame {

	private static final long serialVersionUID = -8417053675962769827L;
	public static int FRAME_WIDTH = 1000;
	public static int FRAME_HEIGHT = 621;

	private Controller controller;
	private JPanel contentPane;
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
		this.contentPane = new JPanel();
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
		gui.SetController(controller);

		SwingUtilities.invokeLater(() -> {
			gui.initialize();
			gui.createPanels();
			gui.setVisible(true);
			splashScreen.setVisible(false);
			splashScreen.dispose();
		});

	}

	private void createPanels() {
		Header header = Header.getInstance(this);
		contentPane.add(header);
		contentPane.setBackground(Color.WHITE);
		LoginPanel loginPanel = LoginPanel.getInstance(this);
		contentPane.add(loginPanel);

		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, loginPanel, 0, SpringLayout.HORIZONTAL_CENTER,
				contentPane);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, loginPanel, 0, SpringLayout.VERTICAL_CENTER, contentPane);

		SearchMenu searchMenu = SearchMenu.getInstance(this);
		contentPane.add(searchMenu);
		layout.putConstraint(SpringLayout.NORTH, searchMenu, 0, SpringLayout.SOUTH, header);

		AdminView adminView = new AdminView(this);
		contentPane.add(adminView);
		layout.putConstraint(SpringLayout.NORTH, adminView, -25, SpringLayout.SOUTH, header);
		layout.putConstraint(SpringLayout.WEST, adminView, -2, SpringLayout.WEST, header);

		AniadirOferta aniadirOferta = AniadirOferta.getInstance(this);
		contentPane.add(aniadirOferta);
		layout.putConstraint(SpringLayout.NORTH, aniadirOferta, 0, SpringLayout.SOUTH, header);
		layout.putConstraint(SpringLayout.WEST, aniadirOferta, 0, SpringLayout.WEST, contentPane);

		EditarOferta editarOferta = EditarOferta.getInstance(this);
		contentPane.add(editarOferta);
		layout.putConstraint(SpringLayout.NORTH, editarOferta, 0, SpringLayout.SOUTH, header);
		layout.putConstraint(SpringLayout.WEST, editarOferta, 0, SpringLayout.WEST, contentPane);

		AniadirInmueble aniadirVivienda = AniadirInmueble.getInstance(this);

		contentPane.add(aniadirVivienda);
		layout.putConstraint(SpringLayout.NORTH, aniadirVivienda, 0, SpringLayout.SOUTH, header);
		layout.putConstraint(SpringLayout.WEST, aniadirVivienda, 0, SpringLayout.WEST, contentPane);

		ResultadosBusqueda resBusqueda = ResultadosBusqueda.getInstance(this);
		contentPane.add(resBusqueda);
		layout.putConstraint(SpringLayout.NORTH, resBusqueda, 0, SpringLayout.SOUTH, header);
		layout.putConstraint(SpringLayout.WEST, resBusqueda, 0, SpringLayout.EAST, searchMenu);

		MisOfertas misOfertas = MisOfertas.getInstance(this);
		contentPane.add(misOfertas);
		layout.putConstraint(SpringLayout.NORTH, misOfertas, 0, SpringLayout.SOUTH, header);
		layout.putConstraint(SpringLayout.WEST, misOfertas, 0, SpringLayout.WEST, contentPane);

		MisInmuebles misInmuebles = MisInmuebles.getInstance(this);
		contentPane.add(misInmuebles);
		layout.putConstraint(SpringLayout.NORTH, misInmuebles, 0, SpringLayout.SOUTH, header);
		layout.putConstraint(SpringLayout.WEST, misInmuebles, 0, SpringLayout.WEST, contentPane);

		header.setVisible(true);
		searchMenu.setVisible(true);
		loginPanel.setVisible(false);
		aniadirOferta.setVisible(false);
		adminView.setVisible(false);
		aniadirVivienda.setVisible(false);
		misOfertas.setVisible(false);
		resBusqueda.setVisible(false);
		misInmuebles.setVisible(false);
		editarOferta.setVisible(false);

		// contentPane.moveToFront(misInmuebles);

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
		Stream.of(this.contentPane.getComponents()).forEach(c -> c.setVisible(false));

		Predicate<Component> contains = (t) -> Stream.of(panelsName).filter(s -> s.equals(t.getName())).count() == 1;

		Stream.of(this.contentPane.getComponents()).filter(contains).forEach(c -> c.setVisible(true));
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

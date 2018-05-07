package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.swing.JFrame;
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
import gui.panels.demandante.MisReservas;
import gui.panels.demandante.VerOferta;
import gui.panels.ofertante.inmuebles.AniadirInmueble;
import gui.panels.ofertante.inmuebles.MisInmuebles;
import gui.panels.ofertante.ofertas.AniadirOferta;
import gui.panels.ofertante.ofertas.EditarOferta;
import gui.panels.ofertante.ofertas.MisOfertas;
import gui.util.DialogFactory;
import gui.util.Nombrable;

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

	private FileLock lock;
	private FileChannel channel;
	private File file;

	public static void main(String[] args) {
		Gui gui = new Gui();
		UIManager.put("Panel.background", Color.WHITE);
		UIManager.put("OptionPane.background", Color.WHITE);

		if (gui.isAppActive()) {
			DialogFactory.simpleErrorMessage("Ya existe una instancia de la aplicacion");
			System.exit(1);
		}

		SplashScreen splashScreen = new SplashScreen();
		Controller controller = null;
		controller = new Controller(gui);
		gui.SetController(controller);

		SwingUtilities.invokeLater(() -> {
			gui.initialize();
			gui.createPanels();
			gui.setVisible(true);
			splashScreen.setVisible(false);
			splashScreen.dispose();
		});

	}

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

	private void createPanels() {
		Header header = new Header(this);
		AdminView adminView = new AdminView(this);
		LoginPanel loginPanel = new LoginPanel(this);
		SearchMenu searchMenu = new SearchMenu(this);
		AniadirOferta aniadirOferta = new AniadirOferta(this);
		EditarOferta editarOferta = new EditarOferta(this);
		AniadirInmueble aniadirVivienda = new AniadirInmueble(this);
		ResultadosBusqueda resBusqueda = new ResultadosBusqueda(this);
		MisOfertas misOfertas = new MisOfertas(this);
		MisInmuebles misInmuebles = new MisInmuebles(this);
		MisReservas misReservas = new MisReservas(this);
		VerOferta verOferta = new VerOferta(this);

		contentPane.add(header);
		contentPane.add(loginPanel);
		header.setGlobalName();
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, loginPanel, 0, SpringLayout.HORIZONTAL_CENTER,
				contentPane);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, loginPanel, 0, SpringLayout.VERTICAL_CENTER, contentPane);

		contentPane.add(searchMenu);
		layout.putConstraint(SpringLayout.NORTH, searchMenu, 0, SpringLayout.SOUTH, header);

		contentPane.add(adminView);
		layout.putConstraint(SpringLayout.NORTH, adminView, 0, SpringLayout.SOUTH, header);
		layout.putConstraint(SpringLayout.WEST, adminView, 0, SpringLayout.WEST, header);

		contentPane.add(aniadirOferta);
		layout.putConstraint(SpringLayout.NORTH, aniadirOferta, 0, SpringLayout.SOUTH, header);
		layout.putConstraint(SpringLayout.WEST, aniadirOferta, 0, SpringLayout.WEST, contentPane);

		contentPane.add(editarOferta);
		layout.putConstraint(SpringLayout.NORTH, editarOferta, 0, SpringLayout.SOUTH, header);
		layout.putConstraint(SpringLayout.WEST, editarOferta, 0, SpringLayout.WEST, contentPane);

		contentPane.add(aniadirVivienda);
		layout.putConstraint(SpringLayout.NORTH, aniadirVivienda, 0, SpringLayout.SOUTH, header);
		layout.putConstraint(SpringLayout.WEST, aniadirVivienda, 0, SpringLayout.WEST, contentPane);

		contentPane.add(resBusqueda);
		layout.putConstraint(SpringLayout.NORTH, resBusqueda, 0, SpringLayout.SOUTH, header);
		layout.putConstraint(SpringLayout.WEST, resBusqueda, 0, SpringLayout.EAST, searchMenu);

		contentPane.add(misOfertas);
		layout.putConstraint(SpringLayout.NORTH, misOfertas, 0, SpringLayout.SOUTH, header);
		layout.putConstraint(SpringLayout.WEST, misOfertas, 0, SpringLayout.WEST, contentPane);

		contentPane.add(misInmuebles);
		layout.putConstraint(SpringLayout.NORTH, misInmuebles, 0, SpringLayout.SOUTH, header);
		layout.putConstraint(SpringLayout.WEST, misInmuebles, 0, SpringLayout.WEST, contentPane);

		contentPane.add(misReservas);
		layout.putConstraint(SpringLayout.NORTH, misReservas, 0, SpringLayout.SOUTH, header);
		layout.putConstraint(SpringLayout.WEST, misReservas, 0, SpringLayout.WEST, contentPane);

		contentPane.add(verOferta);
		layout.putConstraint(SpringLayout.NORTH, verOferta, 0, SpringLayout.SOUTH, header);
		layout.putConstraint(SpringLayout.WEST, verOferta, 0, SpringLayout.WEST, contentPane);

		header.setVisible(true);
		searchMenu.setVisible(true);
		loginPanel.setVisible(false);
		aniadirOferta.setVisible(false);
		adminView.setVisible(false);
		aniadirVivienda.setVisible(false);
		misOfertas.setVisible(false);
		resBusqueda.setVisible(true);
		misInmuebles.setVisible(false);
		editarOferta.setVisible(false);
		misReservas.setVisible(false);
		verOferta.setVisible(false);
		Stream.of(contentPane.getComponents()).forEach(c -> ((Nombrable) c).setGlobalName());

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

	@SuppressWarnings("resource")
	public boolean isAppActive() {
		try {
			file = new File("filelock.tmp");
			channel = new RandomAccessFile(file, "rw").getChannel();

			try {
				lock = channel.tryLock();
			} catch (OverlappingFileLockException e) {
				closeLock();
				return true;
			}

			if (lock == null) {
				closeLock();
				return true;
			}

			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					closeLock();
					deleteFile();
				}
			});
			return false;
		} catch (Exception e) {
			closeLock();
			return true;
		}
	}

	private void closeLock() {
		try {
			lock.release();
		} catch (Exception e) {
		}
		try {
			channel.close();
		} catch (Exception e) {
		}

	}

	private void deleteFile() {
		try {
			file.delete();
		} catch (Exception e) {
		}
	}

}

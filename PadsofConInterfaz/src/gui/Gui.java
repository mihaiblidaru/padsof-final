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

/**
 * Clase principal de la interfaz.Crea y contiene todos los demas elementos de
 * la interfaz
 * 
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class Gui extends JFrame {

	private static final long serialVersionUID = -8417053675962769827L;
	/**
	 * Ancho de la ventana
	 */
	public static int FRAME_WIDTH = 1000;

	/**
	 * Alto de la ventana
	 */
	public static int FRAME_HEIGHT = 621;

	/**
	 * Controlador de la aplicacion
	 */
	private Controller controller;

	/**
	 * Panel principal del frame
	 */
	private JPanel contentPane;

	/**
	 * LayoutManager aplicado al panel principal
	 */
	private SpringLayout layout;

	/**
	 * Añade un controlador a la interfaz
	 * 
	 * @param controller
	 *            controlador de la aplicacion
	 */
	public void SetController(Controller controller) {
		this.controller = controller;
	}

	/**
	 * Bloqueo para el fichero usado para comprobar si la aplicacion ya se esta
	 * ejecutando
	 */
	private FileLock lock;

	/**
	 * Canal al fichero usado para comprobar si la aplicacion ya se esta
	 * ejecutando
	 */
	private FileChannel channel;

	/**
	 * Fichero usado para comprobar si la aplicacion ya se está ejecutando
	 */
	private File file;

	/**
	 * Punto de entrada en la aplicacion
	 * 
	 * @param args
	 *            argumentos del programa
	 */
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

	/**
	 * Realiza algunos ajustes basicos de la interfaz y ajustes globales
	 */
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

	/**
	 * Crea todos los paneles principales de la interfaz
	 */
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

	/**
	 * Devuelve el controler asociado a este frame
	 * 
	 * @return controler asociado a este frame
	 */
	public Controller getController() {
		return this.controller;
	}

	/**
	 * Dado el nombre de un panel cambia su visibilidad
	 * 
	 * @param panelName
	 *            Nombre del panel
	 * @param state
	 *            nuevo estado del panel
	 */
	public void setVisiblePane(String panelName, boolean state) {
		Component c = getComponent(panelName);
		if (c != null) {
			c.setVisible(state);
		}
	}

	/**
	 * Oculta todos los paneles de la interfaz dejando visibles solo
	 * los que se indican como parametros
	 * 
	 * @param panelsName
	 *            nombre de los paneles que se van a marcar como visivles
	 */
	public void showOnly(String... panelsName) {
		Stream.of(this.contentPane.getComponents()).forEach(c -> c.setVisible(false));

		Predicate<Component> contains = (t) -> Stream.of(panelsName).filter(s -> s.equals(t.getName())).count() == 1;

		Stream.of(this.contentPane.getComponents()).filter(contains).forEach(c -> c.setVisible(true));
	}

	/**
	 * Devuelve un subcomponente de este frame dado su nombre
	 * 
	 * @param panelName
	 *            nombre del panel buscado
	 * @return el subcomponente buscado
	 */
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

	/**
	 * Comprueba si la aplicacion ya se encuentra ejecutando
	 * usando un filelock
	 * 
	 * @return true si la aplicacion ya se esta ejecutando o false en caso contrario
	 */
	@SuppressWarnings("resource")
	public boolean isAppActive() {
		try {
			file = new File("filelock.tmp");
			channel = new RandomAccessFile(file, "rw").getChannel();

			try {
				lock = channel.tryLock();
			} catch (OverlappingFileLockException e) {
				try {
					lock.release();
				} catch (Exception e1) {
				}
				try {
					channel.close();
				} catch (Exception e1) {
				}
				return true;
			}

			if (lock == null) {
				try {
					lock.release();
				} catch (Exception e1) {
				}
				try {
					channel.close();
				} catch (Exception e1) {
				}
				return true;
			}

			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					try {
						lock.release();
					} catch (Exception e) {
					}
					try {
						channel.close();
					} catch (Exception e) {
					}
					try {
						file.delete();
					} catch (Exception e) {
					}
				}
			});
			return false;
		} catch (Exception e) {
			try {
				lock.release();
			} catch (Exception e1) {
			}
			try {
				channel.close();
			} catch (Exception e1) {
			}
			return true;
		}
	}

}

package gui.panels.admin;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.TitledBorder;

import gui.Gui;
import gui.controllers.Controller;
import gui.util.PanelInterfaz;

public class ControlPanel extends PanelInterfaz {

	private Gui gui;
	JPanel ofertaPendiente;
	JPanel problemaPagos;
	JPanel problemaCobros;
	JLabel oferta;
	private SpringLayout layout;
	JButton ver;
	private static final long serialVersionUID = 7615793439529902683L;
	public static final String NAME = "PANEL_DE_CONTROL";
	private List<Integer> pagos;
	private List<Integer> cobros;

	public ControlPanel(Gui gui) {
		this.gui = gui;
		pagos = new ArrayList<>();
		cobros = new ArrayList<>();
		initialize();
	}

	public void colocarComponentes() {
		layout = new SpringLayout();
		this.setLayout(layout);

		layout.putConstraint(SpringLayout.NORTH, ofertaPendiente, 20, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, ofertaPendiente, 90, SpringLayout.NORTH, ofertaPendiente);
		layout.putConstraint(SpringLayout.WEST, ofertaPendiente, 20, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, ofertaPendiente, 20, SpringLayout.EAST, this);

		layout.putConstraint(SpringLayout.NORTH, oferta, 10, SpringLayout.NORTH, ofertaPendiente);
		layout.putConstraint(SpringLayout.WEST, oferta, 5, SpringLayout.WEST, ofertaPendiente);

		layout.putConstraint(SpringLayout.WEST, ver, -10, SpringLayout.EAST, oferta);

		layout.putConstraint(SpringLayout.NORTH, problemaPagos, 20, SpringLayout.SOUTH, ofertaPendiente);
		layout.putConstraint(SpringLayout.SOUTH, problemaPagos, 90, SpringLayout.NORTH, problemaPagos);
		layout.putConstraint(SpringLayout.WEST, problemaPagos, 0, SpringLayout.WEST, ofertaPendiente);
		layout.putConstraint(SpringLayout.EAST, problemaPagos, 0, SpringLayout.EAST, ofertaPendiente);

		layout.putConstraint(SpringLayout.NORTH, problemaCobros, 20, SpringLayout.SOUTH, problemaPagos);
		layout.putConstraint(SpringLayout.SOUTH, problemaCobros, 90, SpringLayout.NORTH, problemaCobros);
		layout.putConstraint(SpringLayout.WEST, problemaCobros, 0, SpringLayout.WEST, ofertaPendiente);
		layout.putConstraint(SpringLayout.EAST, problemaCobros, 0, SpringLayout.EAST, ofertaPendiente);

	}

	public void crearComponentes() {

		Controller c = gui.getController();

		problemaPagos = new JPanel();
		problemaCobros = new JPanel();
		ofertaPendiente = new JPanel();
		ver = new JButton("Ver");
		oferta = new JLabel("Piso cutre, una cantidad muy grande de cutre, uye");

		TitledBorder title;
		title = BorderFactory.createTitledBorder(" Ofertas nuevas");
		TitledBorder titlep;
		titlep = BorderFactory.createTitledBorder(pagos.size() + " Usuarios con problemas de pago ");
		TitledBorder titlec;
		titlec = BorderFactory.createTitledBorder(cobros.size() + " Usuarios con problemas de cobro");
		this.ofertaPendiente.setBorder(title);
		this.problemaPagos.setBorder(titlep);
		this.problemaCobros.setBorder(titlec);

		this.ofertaPendiente.add(oferta);
		this.ofertaPendiente.add(ver);
		this.add(ofertaPendiente);

		this.add(problemaPagos);
		this.add(problemaCobros);
	}

	@Override
	public void registrarEventos() {

	}

	public void setDimension() {
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(Gui.FRAME_WIDTH, 562));
	}
	public void cargarUsuarios() {

		Controller c = gui.getController();
		pagos = c.adminGetOfertantesProblemaCobros();
		cobros = c.adminGetDemandantesProblemaPagos();
		
		TitledBorder titlep;
		titlep = BorderFactory.createTitledBorder( pagos.size() + " Usuarios con problemas de pago ");
		TitledBorder titlec;
		titlec = BorderFactory.createTitledBorder( cobros.size() + " Usuarios con problemas de cobro");
		
		this.problemaPagos.setBorder(titlep);
		this.problemaCobros.setBorder(titlec);
		
	}
	
	public void cargarOfertas() {
		
		Controller c = gui.getController();
		List<Integer> ofertas = null;
		
		ofertas = c.adminGetOfertasPendientes();
		
		TitledBorder title;
		title = BorderFactory.createTitledBorder( ofertas.size() +" Ofertas nuevas");
		this.ofertaPendiente.setBorder(title);
		
		while
		

	}
}

package gui.panels.ofertante;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import gui.Gui;
import gui.components.fx.FxButton;
import gui.panels.Header;
import gui.panels.PanelOferta;

public class MisOfertas extends JPanel {

	private static final long serialVersionUID = -8320036169616362237L;

	public static final String NAME = "MIS_OFERTAS";

	private Gui gui;

	private static MisOfertas instance = null;

	public static MisOfertas getInstance(Gui gui) {
		if (instance == null) {
			return (instance = new MisOfertas(gui));
		} else {
			return instance;
		}
	}

	private MisOfertas(Gui gui) {
		this.gui = gui;
		this.setPreferredSize(new Dimension(995, 600));
		this.setBackground(Color.GREEN);
		this.setName(NAME);
		initialize();
	}

	private void initialize() {
		SpringLayout springLayout = new SpringLayout();
		this.setLayout(springLayout);
		FxButton anyadir = new FxButton(160, 40, "Añadir Oferta");

		springLayout.putConstraint(SpringLayout.EAST, anyadir, -40, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, anyadir, 20, SpringLayout.NORTH, this);
		this.add(anyadir);

		anyadir.setOnAction(event -> {
			if (gui.getController().getNumInmuebles() == 0) {

				JOptionPane.showMessageDialog(new JPanel(),
						"No tienes ningun inmueble registrado. Registra un inmueble e intentalo de nuevo", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				gui.showOnly(Header.NAME, AniadirOferta.NAME);
			}

		});

		JPanel panel = new PanelOferta();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 100, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, panel, 20, SpringLayout.WEST, this);
		this.add(panel);

		JPanel panel2 = new PanelOferta();
		springLayout.putConstraint(SpringLayout.NORTH, panel2, 20, SpringLayout.SOUTH, panel);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, panel2, 0, SpringLayout.HORIZONTAL_CENTER, panel);
		this.add(panel2);

		JLabel activas = new JLabel("Activas");
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, activas, 0, SpringLayout.VERTICAL_CENTER, anyadir);
		springLayout.putConstraint(SpringLayout.WEST, activas, 20, SpringLayout.WEST, this);
		this.add(activas);

		JLabel pendientes = new JLabel("Pendientes: el administrador debe aceptar está oferta antes de ser publicada");
		springLayout.putConstraint(SpringLayout.NORTH, pendientes, 5, SpringLayout.SOUTH, panel);
		springLayout.putConstraint(SpringLayout.WEST, pendientes, 20, SpringLayout.WEST, this);
		this.add(pendientes);

		JLabel rechazadas = new JLabel("Rechazadas");
		springLayout.putConstraint(SpringLayout.NORTH, rechazadas, 15, SpringLayout.SOUTH, panel2);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, rechazadas, 20, SpringLayout.HORIZONTAL_CENTER,
				activas);
		this.add(rechazadas);

		JPanel panel3 = new PanelOferta();
		springLayout.putConstraint(SpringLayout.NORTH, panel3, 10, SpringLayout.SOUTH, rechazadas);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, panel3, 0, SpringLayout.HORIZONTAL_CENTER, panel);
		this.add(panel3);

	}
}

package gui.panels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import gui.Gui;
import gui.components.fx.FxButton;
import gui.components.fx.FxCheckBox;
import gui.components.fx.FxDatePicker;
import gui.components.fx.FxTextField;
import gui.listeners.SearchButtonHandler;
import gui.util.Nombrable;
import gui.util.PanelInterfazPrincipal;

public class SearchMenu extends JPanel implements Nombrable, PanelInterfazPrincipal {

	private static final long serialVersionUID = 3588062913427566780L;
	public final static String NAME = "SEARCH_MENU";

	private FxCheckBox checkBoxVacacional;
	private FxCheckBox checkBoxVivienda;
	private FxTextField localidad;
	private FxDatePicker desde;
	private FxDatePicker hasta;
	private FxButton buscar;
	private SpringLayout layout;
	private Gui gui;

	public SearchMenu(Gui gui) {
		this.gui = gui;
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.LIGHT_GRAY));
		initialize();
	}

	@Override
	public void crearComponentes() {
		checkBoxVacacional = new FxCheckBox(100, 20, "Vacacional");
		checkBoxVivienda = new FxCheckBox(100, 20, "Vivienda");
		localidad = new FxTextField(150, 25, "Localidad o CP");
		desde = new FxDatePicker(150, 25, "Desde");
		hasta = new FxDatePicker(150, 25, "Hasta");
		buscar = new FxButton(150, 25, "Buscar");

		this.add(checkBoxVacacional);
		this.add(checkBoxVivienda);
		this.add(localidad);
		this.add(desde);
		this.add(hasta);
		this.add(buscar);

		checkBoxVacacional.setSelected(true);
	}

	@Override
	public void colocarComponentes() {
		layout = new SpringLayout();
		this.setLayout(layout);
		layout.putConstraint(SpringLayout.NORTH, localidad, 70, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, localidad, 0, SpringLayout.HORIZONTAL_CENTER, this);

		layout.putConstraint(SpringLayout.NORTH, desde, 10, SpringLayout.SOUTH, localidad);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, desde, 0, SpringLayout.HORIZONTAL_CENTER, this);

		layout.putConstraint(SpringLayout.NORTH, hasta, 10, SpringLayout.SOUTH, desde);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, hasta, 0, SpringLayout.HORIZONTAL_CENTER, this);

		layout.putConstraint(SpringLayout.NORTH, buscar, 10, SpringLayout.SOUTH, hasta);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, buscar, 0, SpringLayout.HORIZONTAL_CENTER, this);

		layout.putConstraint(SpringLayout.WEST, checkBoxVivienda, 0, SpringLayout.WEST, localidad);
		layout.putConstraint(SpringLayout.SOUTH, checkBoxVivienda, -10, SpringLayout.NORTH, localidad);

		layout.putConstraint(SpringLayout.WEST, checkBoxVacacional, 0, SpringLayout.WEST, checkBoxVivienda);
		layout.putConstraint(SpringLayout.SOUTH, checkBoxVacacional, -10, SpringLayout.NORTH, checkBoxVivienda);

	}

	@Override
	public void registrarEventos() {
		checkBoxVacacional.setOnAction(event -> {
			if (checkBoxVacacional.isSelected()) {
				checkBoxVivienda.setSelected(false);
				hasta.setVisible(true);
				layout.putConstraint(SpringLayout.NORTH, hasta, 10, SpringLayout.SOUTH, desde);
				layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, hasta, 0, SpringLayout.HORIZONTAL_CENTER, this);
				layout.putConstraint(SpringLayout.NORTH, buscar, 10, SpringLayout.SOUTH, hasta);
			} else {
				checkBoxVacacional.setSelected(true);
			}
		});

		checkBoxVivienda.setOnAction(event -> {
			if (checkBoxVivienda.isSelected()) {
				checkBoxVacacional.setSelected(false);
				hasta.setVisible(false);
				layout.removeLayoutComponent(hasta);
				layout.putConstraint(SpringLayout.NORTH, buscar, 10, SpringLayout.SOUTH, desde);

			} else {
				checkBoxVivienda.setSelected(true);
			}
		});

		buscar.setOnAction(new SearchButtonHandler(gui, localidad, desde, hasta, checkBoxVacacional));
	}

	@Override
	public void setDimension() {
		this.setPreferredSize(new Dimension(180, Gui.FRAME_HEIGHT));
	}
}

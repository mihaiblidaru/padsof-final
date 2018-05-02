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
import gui.listeners.searchmenu.SearchButtonHandler;

public class SearchMenu extends JPanel {
	public final static String NAME = "SEARCH_MENU";
	private static final long serialVersionUID = 3588062913427566780L;

	private static SearchMenu instance = null;

	public static SearchMenu getInstance(Gui gui) {
		if (instance == null) {
			return (instance = new SearchMenu(gui));
		} else {
			return instance;
		}
	}

	private SearchMenu(Gui gui) {
		this.setName(NAME);
		this.setPreferredSize(new Dimension(180, Gui.FRAME_HEIGHT));
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.LIGHT_GRAY));

		FxCheckBox checkBoxVacacional = new FxCheckBox(100, 20, "Vacacional");
		FxCheckBox checkBoxVivienda = new FxCheckBox(100, 20, "Vivienda");
		FxTextField localidad = new FxTextField(150, 25, "Localidad o CP");
		FxDatePicker desde = new FxDatePicker(150, 25, "Desde");
		FxDatePicker hasta = new FxDatePicker(150, 25, "Hasta");
		FxButton buscar = new FxButton(150, 25, "Buscar");

		this.add(checkBoxVacacional);
		this.add(checkBoxVivienda);
		this.add(localidad);
		this.add(desde);
		this.add(hasta);
		this.add(buscar);

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

		checkBoxVacacional.setSelected(true);
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
}

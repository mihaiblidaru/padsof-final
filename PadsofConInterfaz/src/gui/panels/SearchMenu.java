package gui.panels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import gui.Gui;
import gui.components.fx.FxButton;
import gui.components.fx.FxDatePicker;
import gui.components.fx.FxTextField;

public class SearchMenu extends JPanel {
	public final static String NAME = "SEARCH_MENU";
	private static final long serialVersionUID = 3588062913427566780L;

	public SearchMenu() {
		this.setName(NAME);
		this.setPreferredSize(new Dimension(180, Gui.FRAME_HEIGHT));
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.LIGHT_GRAY));

		FxTextField localidad = new FxTextField(150, 25, "Localidad o CP");
		this.add(localidad);
		layout.putConstraint(SpringLayout.NORTH, localidad, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, localidad, 0, SpringLayout.HORIZONTAL_CENTER, this);
		FxDatePicker desde = new FxDatePicker(150, 25, "Desde");
		this.add(desde);

		layout.putConstraint(SpringLayout.NORTH, desde, 10, SpringLayout.SOUTH, localidad);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, desde, 0, SpringLayout.HORIZONTAL_CENTER, this);

		FxDatePicker hasta = new FxDatePicker(150, 25, "Hasta");
		this.add(hasta);
		layout.putConstraint(SpringLayout.NORTH, hasta, 10, SpringLayout.SOUTH, desde);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, hasta, 0, SpringLayout.HORIZONTAL_CENTER, this);

		FxButton buscar = new FxButton(150, 25, "Buscar");
		this.add(buscar);
		layout.putConstraint(SpringLayout.NORTH, buscar, 10, SpringLayout.SOUTH, hasta);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, buscar, 0, SpringLayout.HORIZONTAL_CENTER, this);

		/*
		 * TODO hacer que esto funcione. Por que los componentes de swing son
		 * horiblemente feos FxRadioGroup rg = new FxRadioGroup(180, 80, "Vivienda",
		 * "Vacacional");
		 * 
		 * layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, rg, 0,
		 * SpringLayout.HORIZONTAL_CENTER, this);
		 * layout.putConstraint(SpringLayout.NORTH, rg, 10, SpringLayout.SOUTH, buscar);
		 */

	}
}

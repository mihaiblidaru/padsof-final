package gui.panels.ofertante.ofertas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.border.MatteBorder;

import gui.Gui;
import gui.components.fx.FxButton;
import gui.components.fx.FxCheckBox;
import gui.components.fx.FxDatePicker;
import gui.components.fx.FxTextField;
import gui.controllers.Controller;
import gui.listeners.AniadirOfertaListener;
import gui.util.IconLoader;
import gui.util.LimitCounter;
import gui.util.LimitedDocument;
import gui.util.Nombrable;
import gui.util.PanelInterfaz;


/**
 * Esta es nuestra clase que sirve para aniadir ofertas
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class AniadirOferta extends PanelInterfaz implements Nombrable {

	private static final long serialVersionUID = 2220134063340646027L;

	/**
	 * El nombre del panel
	 */
	public final static String NAME = "PANEL_ANIADIR_OFERTA";

	/**
	 * La interfaz grafica
	 */
	protected final Gui gui;
	
	/**
	 * Para añadir el precio
	 */
	protected FxTextField precioTextBox;
	
	/**
	 * Para añadir la fianza
	 */
	protected FxTextField fianzaTextBox;
	
	/**
	 * Para seleccionar la fecha inicial
	 */
	protected FxDatePicker desdeDatePicker;
	
	/**
	 * Para seleccionar la fecha final
	 */
	protected FxDatePicker hastaDatePicker;
	
	/**
	 * Para añadir la descripcion
	 */
	protected JTextArea descripcionTextBox;
	
	/**
	 * Para añadir los meses
	 */
	protected FxTextField mesesTextField;
	
	/**
	 * Para marcar si es vacacional
	 */
	protected FxCheckBox checkBoxVacacional;
	
	/**
	 * Para marcar si es de vivienda
	 */
	protected FxCheckBox checkBoxVivienda;

	/**
	 * Caja en la que guardamos inmuebles
	 */
	protected JComboBox<String> comboBoxInmueble;
	
	/**
	 * Lista de inmuebles
	 */
	protected List<Integer> inmuebles = new ArrayList<>();

	/**
	 * El titulo de la interfaz
	 */
	protected JLabel titulo;
	
	/**
	 * El nombre de la interfaz
	 */
	private JLabel name;
	
	/**
	 * El layout de la interfaz
	 */
	private SpringLayout layout;
	
	/**
	 * El jlabel que imprime la fianza
	 */
	private JLabel fianza;
	
	/**
	 * El jlabel que imprime la fecha final
	 */
	protected JLabel hasta;
	
	/**
	 * El jlabel que imprime la fecha inicial
	 */
	private JLabel desde;

	/**
	 * El jlabel que imprime el precio
	 */
	private JLabel precio;
	
	/**
	 * El jlabel que imprime el simbolo de advertencia
	 */
	protected JLabel warningIcon;
	
	/**
	 * El jlabel que imprime la advertencia
	 */
	protected JLabel alarm;
	
	/**
	 * El boton para confirmar
	 */
	protected FxButton confirmar;
	
	/**
	 * El jlabel que imprime el limite
	 */
	JLabel limitLabel;
	
	/**
	 * El jlabel que imprime la descripcion
	 */
	private JLabel descripcion;
	
	/**
	 * Establecemos el limte en 300
	 */
	protected int limit = 300;

	/**
	 * Constructor de AniadirOferta
	 * @param gui interfaz grafica
	 */
	public AniadirOferta(Gui gui) {
		this.gui = gui;
		initialize();
	}

	/**
	 * Funcion que define las dimensiones de la interfaz
	 */
	@Override
	public void setDimension() {
		this.setPreferredSize(new Dimension(Gui.FRAME_WIDTH, Gui.FRAME_HEIGHT - 20));
	}

	/**
	 * Funcion que crea los componentes de la interfaz, y los añade a la interfaz grafica
	 */
	@Override
	public void crearComponentes() {

		Font font = new Font("Comic Sans", Font.PLAIN, 40);
		Font descfont = new Font("Comic Sans", Font.PLAIN, 25);
		Font conffont = new Font("Comic Sans", Font.PLAIN, 25);

		titulo = new JLabel("Añadir Oferta");
		name = new JLabel("Selecciona la vivienda que quieres ofertar");
		precioTextBox = new FxTextField(70, 25, "321.33 €");
		fianza = new JLabel("Fianza");
		fianzaTextBox = new FxTextField(70, 25, "123.00 €");
		hastaDatePicker = new FxDatePicker(100, 25, "Hasta");
		hasta = new JLabel("Hasta");
		mesesTextField = new FxTextField(100, 25, "Ej: 12");
		desdeDatePicker = new FxDatePicker(100, 25, "Desde");
		desde = new JLabel("Desde");
		descripcion = new JLabel("Descripcion");
		descripcionTextBox = new JTextArea(new LimitedDocument(limit));
		limitLabel = new JLabel(String.format("%d restantes", limit));
		confirmar = new FxButton(200, 100, "Confirmar");
		alarm = new JLabel("No puedes añadir la misma oferta dos veces");
		warningIcon = new JLabel(IconLoader.load("res/img/fa-warning.png", 10, 10));
		checkBoxVacacional = new FxCheckBox(100, 20, "Vacacional");
		checkBoxVivienda = new FxCheckBox(100, 20, "Vivienda");
		precio = new JLabel("Precio");
		comboBoxInmueble = new JComboBox<>();

		titulo.setFont(font);

		comboBoxInmueble.setPreferredSize(new Dimension(415, 25));
		comboBoxInmueble.setBackground(Color.WHITE);

		mesesTextField.setVisible(false);
		mesesTextField.setOnlyInteger();
		mesesTextField.setCharLimit(3);

		descripcion.setFont(descfont);
		descripcionTextBox.setFont(new Font("Courier New", Font.PLAIN, 15));
		descripcionTextBox.setLineWrap(true);
		descripcionTextBox.setWrapStyleWord(true);
		descripcionTextBox.setBorder(new MatteBorder(1, 1, 1, 1, Color.decode("#eeeeee")));

		confirmar.setFont(conffont);
		confirmar.setFontScale(0.3);
		checkBoxVacacional.setSelected(true);

		precioTextBox.setOnlyFloat();
		precioTextBox.setCharLimit(9);

		fianzaTextBox.setOnlyFloat();
		fianzaTextBox.setCharLimit(9);

		this.add(name);
		this.add(precio);
		this.add(precioTextBox);
		this.add(fianza);
		this.add(fianzaTextBox);
		this.add(hastaDatePicker);
		this.add(hasta);
		this.add(mesesTextField);
		this.add(desdeDatePicker);
		this.add(desde);
		this.add(descripcion);
		this.add(descripcionTextBox);
		this.add(confirmar);
		this.add(alarm);
		this.add(warningIcon);
		this.add(checkBoxVacacional);
		this.add(checkBoxVivienda);
		this.add(limitLabel);
		this.add(comboBoxInmueble);
		this.add(titulo);
	}

	/**
	 * Esta funcion  coloca los componentes en la interfaz grafica utilizando un SpringLayout
	 */
	public void colocarComponentes() {
		layout = new SpringLayout();
		this.setLayout(layout);
		layout.putConstraint(SpringLayout.NORTH, titulo, 30, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, titulo, 30, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, name, 20, SpringLayout.SOUTH, titulo);
		layout.putConstraint(SpringLayout.WEST, name, 30, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, comboBoxInmueble, 0, SpringLayout.VERTICAL_CENTER, name);
		layout.putConstraint(SpringLayout.WEST, comboBoxInmueble, 10, SpringLayout.EAST, name);
		layout.putConstraint(SpringLayout.NORTH, precio, 20, SpringLayout.SOUTH, name);
		layout.putConstraint(SpringLayout.WEST, precio, 30, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, precioTextBox, 0, SpringLayout.VERTICAL_CENTER, precio);
		layout.putConstraint(SpringLayout.WEST, precioTextBox, 10, SpringLayout.EAST, precio);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, fianza, 0, SpringLayout.VERTICAL_CENTER, precio);
		layout.putConstraint(SpringLayout.WEST, fianza, 10, SpringLayout.EAST, precioTextBox);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, fianzaTextBox, 0, SpringLayout.VERTICAL_CENTER, fianza);
		layout.putConstraint(SpringLayout.WEST, fianzaTextBox, 10, SpringLayout.EAST, fianza);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, hastaDatePicker, 0, SpringLayout.VERTICAL_CENTER, precio);
		layout.putConstraint(SpringLayout.EAST, hastaDatePicker, 0, SpringLayout.EAST, comboBoxInmueble);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, hasta, 0, SpringLayout.VERTICAL_CENTER, hastaDatePicker);
		layout.putConstraint(SpringLayout.WEST, hasta, -55, SpringLayout.WEST, hastaDatePicker);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, mesesTextField, 0, SpringLayout.VERTICAL_CENTER, precio);
		layout.putConstraint(SpringLayout.EAST, mesesTextField, 0, SpringLayout.EAST, comboBoxInmueble);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, desdeDatePicker, 0, SpringLayout.VERTICAL_CENTER,
				hastaDatePicker);
		layout.putConstraint(SpringLayout.EAST, desdeDatePicker, -40, SpringLayout.WEST, hasta);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, desde, 0, SpringLayout.VERTICAL_CENTER, precio);
		layout.putConstraint(SpringLayout.EAST, desde, -20, SpringLayout.WEST, desdeDatePicker);
		layout.putConstraint(SpringLayout.WEST, descripcion, 0, SpringLayout.WEST, precio);
		layout.putConstraint(SpringLayout.NORTH, descripcion, 20, SpringLayout.SOUTH, precio);
		layout.putConstraint(SpringLayout.WEST, descripcionTextBox, 0, SpringLayout.WEST, descripcion);
		layout.putConstraint(SpringLayout.NORTH, descripcionTextBox, 10, SpringLayout.SOUTH, descripcion);
		layout.putConstraint(SpringLayout.SOUTH, descripcionTextBox, 180, SpringLayout.SOUTH, descripcion);
		layout.putConstraint(SpringLayout.EAST, descripcionTextBox, 0, SpringLayout.EAST, comboBoxInmueble);
		layout.putConstraint(SpringLayout.NORTH, limitLabel, 2, SpringLayout.SOUTH, descripcionTextBox);
		layout.putConstraint(SpringLayout.EAST, limitLabel, 0, SpringLayout.EAST, descripcionTextBox);
		layout.putConstraint(SpringLayout.NORTH, confirmar, 40, SpringLayout.SOUTH, descripcionTextBox);
		layout.putConstraint(SpringLayout.WEST, confirmar, 0, SpringLayout.WEST, descripcionTextBox);
		layout.putConstraint(SpringLayout.WEST, alarm, 20, SpringLayout.WEST, confirmar);
		layout.putConstraint(SpringLayout.SOUTH, alarm, -20, SpringLayout.NORTH, confirmar);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, warningIcon, 0, SpringLayout.VERTICAL_CENTER, alarm);
		layout.putConstraint(SpringLayout.EAST, warningIcon, -5, SpringLayout.WEST, alarm);
		layout.putConstraint(SpringLayout.EAST, checkBoxVivienda, 0, SpringLayout.EAST, comboBoxInmueble);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, checkBoxVivienda, 0, SpringLayout.VERTICAL_CENTER, titulo);
		layout.putConstraint(SpringLayout.EAST, checkBoxVacacional, -100, SpringLayout.WEST, checkBoxVivienda);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, checkBoxVacacional, 0, SpringLayout.VERTICAL_CENTER, titulo);

	}

	/**
	 * Esta funcion registra los eventos que ocurren en la interfaz
	 */
	@Override
	public void registrarEventos() {
		confirmar.setOnAction(new AniadirOfertaListener(gui, precioTextBox, fianzaTextBox, desdeDatePicker,
				hastaDatePicker, descripcionTextBox, mesesTextField, checkBoxVacacional, comboBoxInmueble, inmuebles));

		checkBoxVacacional.setOnAction(event -> {
			if (checkBoxVacacional.isSelected()) {
				checkBoxVivienda.setSelected(false);
				hasta.setText("Hasta");
				hastaDatePicker.setVisible(true);
				mesesTextField.setVisible(false);
			} else {
				checkBoxVacacional.setSelected(true);
			}
		});

		checkBoxVivienda.setOnAction(event -> {
			if (checkBoxVivienda.isSelected()) {
				checkBoxVacacional.setSelected(false);
				hasta.setText("Meses");
				hastaDatePicker.setVisible(false);
				mesesTextField.setVisible(true);

			} else {
				checkBoxVivienda.setSelected(true);
			}
		});

		descripcionTextBox.getDocument().addDocumentListener(new LimitCounter(limitLabel, limit));

	}

	/**
	 * Esta funcion cambia la visibilidad, vuelve a cargar los datos
	 * @param state estado de visibilidad
	 */
	@Override
	public void setVisible(boolean state) {
		super.setVisible(state);
		if (state == true) {
			precioTextBox.setText("");
			fianzaTextBox.setText("");
			desdeDatePicker.setValue(null);
			hastaDatePicker.setValue(null);
			mesesTextField.setText("");
			descripcionTextBox.setText("");
			cargarListaInmuebles();
		}
	}

	/**
	 * Esta funcion carga la lista de inmuebles
	 */
	private void cargarListaInmuebles() {
		Controller c = gui.getController();
		comboBoxInmueble.removeAllItems();
		inmuebles.clear();
		inmuebles.addAll(c.ofertanteGetMisInmuebles());
		inmuebles.stream().map(i -> c.inmuebleGetDireccionCompleta(i)).forEach(d -> comboBoxInmueble.addItem(d));
	}

}

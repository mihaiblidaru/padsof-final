package gui.panels.ofertante.ofertas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import gui.Gui;
import gui.components.fx.FxButton;
import gui.components.fx.FxCheckBox;
import gui.components.fx.FxDatePicker;
import gui.components.fx.FxTextField;
import gui.controllers.Controller;
import gui.listeners.AniadirOfertaListener;

public class EditarOferta extends JPanel {

	private static final long serialVersionUID = 2220134063340646027L;

	public final static String NAME = "PANEL_EDITAR_OFERTA";

	private final Gui gui;
	private FxTextField precioTextBox;
	private FxTextField fianzaTextBox;
	private FxDatePicker desdeDatePicker;
	private FxDatePicker hastaDatePicker;
	private JTextArea descripcionTextBox;
	private FxTextField mesesTextField;
	private FxCheckBox checkBoxVacacional;

	private JComboBox<String> comboBoxInmueble;
	private List<Integer> inmuebles = new ArrayList<>();

	private JLabel aniadirOfertas;
	private JLabel name;
	private SpringLayout layout;
	private JLabel fianza;
	private JLabel hasta;
	private JLabel desde;

	private FxCheckBox checkBoxVivienda;
	private JLabel precio;
	private FxButton confirmar;
	private JLabel limitLabel;
	private JLabel descripcion;
	protected int limit = 300;

	private static EditarOferta instance = null;

	public static EditarOferta getInstance(Gui gui) {
		if (instance == null) {
			instance = new EditarOferta(gui);
		}

		return instance;

	}

	public void cargarDatos(int id) {
		SwingUtilities.invokeLater(() -> {
			Controller c = gui.getController();
			precioTextBox.setText(String.valueOf(c.ofertaGetPrecio(id)));
			fianzaTextBox.setText(String.valueOf(c.ofertaGetFianza(id)));
			descripcionTextBox.setText(c.ofertaGetDescripcion(id));
			desdeDatePicker.setValue(c.ofertaGetFechaFin(id));
			LocalDate fechaFin = c.ofertaGetFechaFin(id);
			if (fechaFin == null) {
				checkBoxVivienda.fire();
				mesesTextField.setText(String.valueOf(c.ofertaGetNumMeses(id)));
			} else {
				checkBoxVacacional.fire();
				hastaDatePicker.setValue(fechaFin);
			}
			checkBoxVacacional.setDisable(true);
			checkBoxVivienda.setDisable(true);

		});
	}

	private EditarOferta(Gui gui) {
		this.gui = gui;
		this.setName(NAME);

		SwingUtilities.invokeLater(() -> initialize());

	}

	private void initialize() {
		this.setPreferredSize(new Dimension(Gui.FRAME_WIDTH, Gui.FRAME_HEIGHT - 20));
		layout = new SpringLayout();
		this.setLayout(layout);
		this.setBackground(Color.decode("#ffffff"));

		String[] petStrings = { "Piso 1", "Piso 2", "Piso 3", "Piso 4", "Piso 5", };

		Font font = new Font("Comic Sans", Font.PLAIN, 40);
		Font descfont = new Font("Comic Sans", Font.PLAIN, 25);
		Font conffont = new Font("Comic Sans", Font.PLAIN, 25);

		aniadirOfertas = new JLabel("Editar Oferta");
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
		descripcionTextBox = new JTextArea(new CustomDocument(limit));
		limitLabel = new JLabel(String.format("%d restantes", limit));
		confirmar = new FxButton(200, 100, "Confirmar");
		checkBoxVacacional = new FxCheckBox(100, 20, "Vacacional");
		checkBoxVivienda = new FxCheckBox(100, 20, "Vivienda");
		precio = new JLabel("Precio");
		comboBoxInmueble = new JComboBox<>(petStrings);
		comboBoxInmueble.setEnabled(false);

		aniadirOfertas.setFont(font);

		comboBoxInmueble.setPreferredSize(new Dimension(415, 25));
		comboBoxInmueble.setBackground(Color.WHITE);

		mesesTextField.setVisible(false);
		mesesTextField.setOnlyInteger();

		descripcion.setFont(descfont);
		descripcionTextBox.setFont(new Font("Courier New", Font.PLAIN, 15));
		descripcionTextBox.setLineWrap(true);
		descripcionTextBox.setWrapStyleWord(true);
		descripcionTextBox.setBorder(new MatteBorder(1, 1, 1, 1, Color.decode("#eeeeee")));

		confirmar.setFont(conffont);
		confirmar.setFontScale(0.3);
		checkBoxVacacional.setSelected(true);

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
		this.add(checkBoxVacacional);
		this.add(checkBoxVivienda);
		this.add(limitLabel);
		this.add(comboBoxInmueble);
		this.add(aniadirOfertas);

		this.setContraits();
		this.setListeners();

	}

	private void setContraits() {
		layout.putConstraint(SpringLayout.NORTH, aniadirOfertas, 30, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, aniadirOfertas, 30, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, name, 20, SpringLayout.SOUTH, aniadirOfertas);
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
		layout.putConstraint(SpringLayout.EAST, checkBoxVivienda, 0, SpringLayout.EAST, comboBoxInmueble);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, checkBoxVivienda, 0, SpringLayout.VERTICAL_CENTER,
				aniadirOfertas);
		layout.putConstraint(SpringLayout.EAST, checkBoxVacacional, -100, SpringLayout.WEST, checkBoxVivienda);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, checkBoxVacacional, 0, SpringLayout.VERTICAL_CENTER,
				aniadirOfertas);

	}

	private void setListeners() {
		confirmar.setOnAction(new AniadirOfertaListener(gui, precioTextBox, fianzaTextBox, desdeDatePicker,
				hastaDatePicker, descripcionTextBox, mesesTextField, checkBoxVacacional, comboBoxInmueble, inmuebles));

		precioTextBox.setOnlyFloat();
		fianzaTextBox.setOnlyFloat();
		mesesTextField.setOnlyInteger();

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

		descripcionTextBox.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				updateLabel(e);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				updateLabel(e);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateLabel(e);
			}

			public void updateLabel(DocumentEvent e) {
				limitLabel.setText(String.format("%d restantes", limit - e.getDocument().getLength()));
			}

		});

	}

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

	private void cargarListaInmuebles() {
		Controller c = gui.getController();
		comboBoxInmueble.removeAllItems();
		inmuebles.clear();
		inmuebles.addAll(c.ofertanteGetMisInmuebles());
		inmuebles.stream().map(i -> c.inmuebleGetDireccionCompleta(i)).forEach(d -> comboBoxInmueble.addItem(d));
	}

	/**
	 * Usado para limitar el número de caracteres dentro de un Documento de
	 * textarea. Tampoco permite insertar nuevas lineas.
	 */
	private static class CustomDocument extends PlainDocument {

		private static final long serialVersionUID = -3514808888826488252L;
		private int max;

		public CustomDocument(int max) {
			this.max = max;
		}

		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			str = str.replaceAll("\n", "");
			if (getLength() + str.length() > max) {
				str = str.substring(0, max - getLength());
			}
			super.insertString(offs, str, a);
		}
	}

}

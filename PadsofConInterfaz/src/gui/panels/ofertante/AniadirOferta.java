package gui.panels.ofertante;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import gui.Gui;
import gui.components.fx.FxButton;
import gui.components.fx.FxDatePicker;
import gui.components.fx.FxTextField;
import gui.controllers.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class AniadirOferta extends JPanel {

	private static final long serialVersionUID = 2220134063340646027L;
	private final static String onlyNumbersRegex = "^[0-9]*(?:\\.[0-9]*)?";

	public final static String NAME = "PANEL_ANIADIR_OFERTA";

	private final Gui gui;
	private FxTextField precioTextBox;
	private FxTextField fianzaTextBox;
	private FxDatePicker desdeDatePicker;
	private FxDatePicker hastaDatePicker;
	private JTextArea descripcionTextBox;
	private JComboBox<String> comboBoxInmueble;

	private static AniadirOferta instance = null;

	public static AniadirOferta getInstance(Gui gui) {
		if (instance == null) {
			return (instance = new AniadirOferta(gui));
		} else {
			return instance;
		}
	}

	private AniadirOferta(Gui gui) {
		this.gui = gui;
		this.setName(NAME);
		this.setPreferredSize(new Dimension(Gui.FRAME_WIDTH, Gui.FRAME_HEIGHT - 20));
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		this.setBackground(Color.decode("#ffffff"));

		Font font = new Font("Comic Sans", Font.PLAIN, 40);
		Font descfont = new Font("Comic Sans", Font.PLAIN, 25);
		Font conffont = new Font("Comic Sans", Font.PLAIN, 25);

		JLabel aniadirOfertas = new JLabel("Añadir Oferta");
		aniadirOfertas.setFont(font);
		layout.putConstraint(SpringLayout.NORTH, aniadirOfertas, 30, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, aniadirOfertas, 30, SpringLayout.WEST, this);
		this.add(aniadirOfertas);

		JLabel name = new JLabel("Selecciona la vivienda que quieres ofertar");
		layout.putConstraint(SpringLayout.NORTH, name, 20, SpringLayout.SOUTH, aniadirOfertas);
		layout.putConstraint(SpringLayout.WEST, name, 30, SpringLayout.WEST, this);
		this.add(name);

		String[] petStrings = { "Piso 1", "Piso 2", "Piso 3", "Piso 4", "Piso 5" };

		comboBoxInmueble = new JComboBox<>(petStrings);
		this.add(comboBoxInmueble);
		comboBoxInmueble.setPreferredSize(new Dimension(415, 25));
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, comboBoxInmueble, 0, SpringLayout.VERTICAL_CENTER, name);
		layout.putConstraint(SpringLayout.WEST, comboBoxInmueble, 10, SpringLayout.EAST, name);
		comboBoxInmueble.setBackground(Color.WHITE);

		JLabel precio = new JLabel("Precio");
		layout.putConstraint(SpringLayout.NORTH, precio, 20, SpringLayout.SOUTH, name);
		layout.putConstraint(SpringLayout.WEST, precio, 30, SpringLayout.WEST, this);
		this.add(precio);

		precioTextBox = new FxTextField(100, 25, "321.33 €");
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, precioTextBox, 0, SpringLayout.VERTICAL_CENTER, precio);
		layout.putConstraint(SpringLayout.WEST, precioTextBox, 10, SpringLayout.EAST, precio);
		this.add(precioTextBox);

		JLabel fianza = new JLabel("Fianza");
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, fianza, 0, SpringLayout.VERTICAL_CENTER, precio);
		layout.putConstraint(SpringLayout.WEST, fianza, 10, SpringLayout.EAST, precioTextBox);
		this.add(fianza);

		fianzaTextBox = new FxTextField(100, 25, "123.00 €");
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, fianzaTextBox, 0, SpringLayout.VERTICAL_CENTER, fianza);
		layout.putConstraint(SpringLayout.WEST, fianzaTextBox, 10, SpringLayout.EAST, fianza);
		this.add(fianzaTextBox);

		JLabel desde = new JLabel("Desde");
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, desde, 0, SpringLayout.VERTICAL_CENTER, precio);
		layout.putConstraint(SpringLayout.WEST, desde, 50, SpringLayout.EAST, fianzaTextBox);
		this.add(desde);

		desdeDatePicker = new FxDatePicker(100, 25, "Desde");
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, desdeDatePicker, 0, SpringLayout.VERTICAL_CENTER, desde);
		layout.putConstraint(SpringLayout.WEST, desdeDatePicker, 10, SpringLayout.EAST, desde);
		this.add(desdeDatePicker);

		JLabel hasta = new JLabel("Hasta");
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, hasta, 0, SpringLayout.VERTICAL_CENTER, desde);
		layout.putConstraint(SpringLayout.WEST, hasta, 20, SpringLayout.EAST, desdeDatePicker);
		this.add(hasta);

		hastaDatePicker = new FxDatePicker(100, 25, "Hasta");
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, hastaDatePicker, 0, SpringLayout.VERTICAL_CENTER, hasta);
		layout.putConstraint(SpringLayout.WEST, hastaDatePicker, 10, SpringLayout.EAST, hasta);
		this.add(hastaDatePicker);

		JLabel descripcion = new JLabel("Descripcion");
		descripcion.setFont(descfont);
		layout.putConstraint(SpringLayout.WEST, descripcion, 0, SpringLayout.WEST, precio);
		layout.putConstraint(SpringLayout.NORTH, descripcion, 20, SpringLayout.SOUTH, precio);
		this.add(descripcion);

		int limit = 300;

		descripcionTextBox = new JTextArea(new CustomDocument(limit));
		descripcionTextBox.setFont(new Font("Courier New", Font.PLAIN, 15));
		descripcionTextBox.setLineWrap(true);
		descripcionTextBox.setWrapStyleWord(true);
		descripcionTextBox.setBorder(new MatteBorder(1, 1, 1, 1, Color.decode("#eeeeee")));
		layout.putConstraint(SpringLayout.WEST, descripcionTextBox, 0, SpringLayout.WEST, descripcion);
		layout.putConstraint(SpringLayout.NORTH, descripcionTextBox, 10, SpringLayout.SOUTH, descripcion);
		layout.putConstraint(SpringLayout.SOUTH, descripcionTextBox, 180, SpringLayout.SOUTH, descripcion);
		layout.putConstraint(SpringLayout.EAST, descripcionTextBox, 0, SpringLayout.EAST, hastaDatePicker);
		this.add(descripcionTextBox);
		JLabel limitLabel = new JLabel(String.format("%d restantes", limit));
		this.add(limitLabel);
		layout.putConstraint(SpringLayout.NORTH, limitLabel, 2, SpringLayout.SOUTH, descripcionTextBox);
		layout.putConstraint(SpringLayout.EAST, limitLabel, 0, SpringLayout.EAST, descripcionTextBox);

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

		FxButton confirmar = new FxButton(200, 100, "Confirmar");
		confirmar.setFont(conffont);
		layout.putConstraint(SpringLayout.NORTH, confirmar, 40, SpringLayout.SOUTH, descripcionTextBox);
		layout.putConstraint(SpringLayout.WEST, confirmar, 0, SpringLayout.WEST, descripcionTextBox);
		confirmar.setFontScale(0.3);
		this.add(confirmar);

		JLabel alarm = new JLabel("No puedes añadir la misma oferta dos veces");
		layout.putConstraint(SpringLayout.WEST, alarm, 20, SpringLayout.WEST, confirmar);
		layout.putConstraint(SpringLayout.SOUTH, alarm, -20, SpringLayout.NORTH, confirmar);
		this.add(alarm);

		ImageIcon image = null;
		try {
			image = new ImageIcon(
					ImageIO.read(new File("res/img/fa-warning.png")).getScaledInstance(10, 10, Image.SCALE_SMOOTH));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		JLabel warningIcon = new JLabel(image);

		this.add(warningIcon);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, warningIcon, 0, SpringLayout.VERTICAL_CENTER, alarm);
		layout.putConstraint(SpringLayout.EAST, warningIcon, -5, SpringLayout.WEST, alarm);

		precioTextBox.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches(onlyNumbersRegex)) {
					precioTextBox.setText(oldValue);
				}
			}
		});

		fianzaTextBox.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches(onlyNumbersRegex)) {
					fianzaTextBox.setText(oldValue);
				}
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
			descripcionTextBox.setText("");
			cargarListaInmuebles();
		}

	}

	private void cargarListaInmuebles() {
		Controller c = gui.getController();
		comboBoxInmueble.removeAllItems();
		c.ofertanteGetMisInmuebles().stream().map(i -> c.inmuebleGetDireccionCompleta(i))
				.forEach(d -> comboBoxInmueble.addItem(d));
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

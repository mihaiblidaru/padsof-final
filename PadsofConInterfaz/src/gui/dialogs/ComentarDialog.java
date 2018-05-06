package gui.dialogs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.border.MatteBorder;

import gui.Gui;
import gui.controllers.Controller;
import gui.util.DialogFactory;
import gui.util.LimitCounter;
import gui.util.LimitedDocument;

public class ComentarDialog extends JDialog {

	private static final long serialVersionUID = -889156607009791316L;
	private JButton btnOk;
	private JButton btnCancel;
	private int limit = 300;
	private JLabel limitLabel;
	private JTextArea comentarioTextBox;
	private Integer idOferta;
	private Integer idPadre;
	private boolean value;

	public ComentarDialog(Gui parent, Integer idOferta, Integer idPadre) {
		super(parent, "Introduce tu comentario", true);
		this.idOferta = idOferta;
		this.idPadre = idPadre;

		Point pLoc = parent.getLocation();
		this.setLocation(pLoc.x + 250, pLoc.y + 191);

		this.setResizable(false);
		Point loc = parent.getLocation();
		SpringLayout layout = new SpringLayout();
		JPanel panel = new JPanel(layout);
		this.setContentPane(panel);
		panel.setPreferredSize(new Dimension(500, 240));
		JLabel label = new JLabel("Introduce tu comentario");

		comentarioTextBox = new JTextArea(new LimitedDocument(limit));
		limitLabel = new JLabel(String.format("%d restantes", limit));

		comentarioTextBox.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
		comentarioTextBox.setPreferredSize(new Dimension(480, 150));
		comentarioTextBox.setFont(new Font("Courier New", Font.PLAIN, 15));
		comentarioTextBox.setLineWrap(true);
		comentarioTextBox.setWrapStyleWord(true);

		comentarioTextBox.getDocument().addDocumentListener(new LimitCounter(limitLabel, limit));

		panel.add(label);
		panel.add(comentarioTextBox);
		panel.add(limitLabel);

		layout.putConstraint(SpringLayout.NORTH, label, 10, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, label, 10, SpringLayout.WEST, panel);

		layout.putConstraint(SpringLayout.NORTH, comentarioTextBox, 7, SpringLayout.SOUTH, label);
		layout.putConstraint(SpringLayout.WEST, comentarioTextBox, 0, SpringLayout.WEST, label);

		layout.putConstraint(SpringLayout.EAST, limitLabel, 0, SpringLayout.EAST, comentarioTextBox);
		layout.putConstraint(SpringLayout.NORTH, limitLabel, 10, SpringLayout.SOUTH, comentarioTextBox);

		JPanel contenedorBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
		panel.add(contenedorBotones);

		JButton okBtn = new JButton("OK");
		JButton cancelarBtn = new JButton("Cancelar");

		okBtn.setPreferredSize(cancelarBtn.getPreferredSize());
		contenedorBotones.add(okBtn);
		contenedorBotones.add(cancelarBtn);

		layout.putConstraint(SpringLayout.NORTH, contenedorBotones, 20, SpringLayout.SOUTH, comentarioTextBox);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, contenedorBotones, 10, SpringLayout.HORIZONTAL_CENTER,
				panel);

		cancelarBtn.addActionListener((e) -> this.dispose());

		okBtn.addActionListener(e -> {
			if (comentarioTextBox.getText().trim().isEmpty()) {
				DialogFactory.emptyFieldError("comentario");
			} else {
				Controller c = parent.getController();
				c.ofertaComentar(comentarioTextBox.getText().trim(), idOferta, idPadre);
				this.value = true;
				this.dispose();
			}
		});

		pack();
	}

	public boolean getValue() {
		return value;
	}

}
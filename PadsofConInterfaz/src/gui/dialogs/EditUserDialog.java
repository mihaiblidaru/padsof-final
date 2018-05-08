package gui.dialogs;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import gui.Gui;
import gui.controllers.Controller;
import gui.panels.admin.usuarios.UserCard;
import gui.util.DialogFactory;
import gui.util.IconLoader;
import gui.util.LimitedDocument;

/**
 * 
 * @author Mihai
 *
 */
public class EditUserDialog extends JDialog {

	private static final long serialVersionUID = -889156607009791316L;
	private JButton btnOk;
	private JButton btnCancel;
	private int limit = 200;
	private JLabel limitLabel;

	private Gui gui;
	private boolean value;
	private Integer idUsuario;
	private JTextField tarjeta;
	private JLabel userName;
	private JLabel tipo;
	private UserCard card;

	public EditUserDialog(Gui gui, Integer idUsuario, UserCard card) {
		super(gui, "Introduce tu comentario", true);
		this.gui = gui;
		this.idUsuario = idUsuario;
		this.card = card;

		Point pLoc = gui.getLocation();
		this.setLocation(pLoc.x + 375, pLoc.y + 231);

		this.setResizable(false);
		SpringLayout layout = new SpringLayout();
		JPanel panel = new JPanel(layout);
		this.setContentPane(panel);

		panel.setPreferredSize(new Dimension(250, 150));

		JPanel intPanel = new JPanel();
		intPanel.setPreferredSize(new Dimension(200, 100));
		BoxLayout l = new BoxLayout(intPanel, BoxLayout.Y_AXIS);
		intPanel.setLayout(l);

		userName = new JLabel(card.getNombre());
		tipo = new JLabel(card.getTipo());
		JPanel filaTarjeta = new JPanel();

		userName.setAlignmentX(Component.LEFT_ALIGNMENT);
		tipo.setAlignmentX(Component.LEFT_ALIGNMENT);
		filaTarjeta.setAlignmentX(Component.LEFT_ALIGNMENT);

		intPanel.add(Box.createRigidArea(new Dimension(1, 15)));
		intPanel.add(userName);
		intPanel.add(Box.createRigidArea(new Dimension(10, 3)));
		intPanel.add(tipo);
		intPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		intPanel.add(filaTarjeta);
		intPanel.add(Box.createRigidArea(new Dimension(10, 10)));

		JLabel imgTarjeta = new JLabel(IconLoader.load("res/img/fa-cc-visa.png", 20, 20));
		tarjeta = new JTextField(16);
		tarjeta.setDocument(new LimitedDocument(16));
		tarjeta.setText(card.getTarjeta());
		tarjeta.setPreferredSize(new Dimension(tarjeta.getPreferredSize().width, 25));
		tarjeta.setMaximumSize(tarjeta.getPreferredSize());
		filaTarjeta.add(imgTarjeta);
		filaTarjeta.add(Box.createRigidArea(new Dimension(5, 1)));
		filaTarjeta.add(tarjeta);

		panel.add(intPanel);
		JPanel contenedorBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
		panel.add(contenedorBotones);

		JButton okBtn = new JButton("OK");
		JButton cancelarBtn = new JButton("Cancelar");

		okBtn.setPreferredSize(cancelarBtn.getPreferredSize());
		contenedorBotones.add(okBtn);
		contenedorBotones.add(cancelarBtn);

		filaTarjeta.setLayout(new BoxLayout(filaTarjeta, BoxLayout.X_AXIS));
		layout.putConstraint(SpringLayout.NORTH, intPanel, 5, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, intPanel, 0, SpringLayout.HORIZONTAL_CENTER, panel);
		layout.putConstraint(SpringLayout.NORTH, contenedorBotones, 10, SpringLayout.SOUTH, intPanel);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, contenedorBotones, 10, SpringLayout.HORIZONTAL_CENTER,
				panel);

		cancelarBtn.addActionListener((e) -> this.dispose());

		okBtn.addActionListener(e -> {
			cambiarTarjeta();
		});

		pack();
	}

	private void cambiarTarjeta() {
		if (tarjeta.getText().length() != 16) {
			DialogFactory.invalidValueError("Tarjeta");
		} else if (tarjeta.getText().isEmpty()) {
			DialogFactory.emptyFieldError("Tarjeta");
		} else {
			Controller c = gui.getController();
			if (c.cambiarTarjeta(idUsuario, tarjeta.getText())) {
				this.value = true;
				this.dispose();
			} else {
				DialogFactory.simpleErrorMessage("No se ha podido cambiar la tarjeta, vuelve a comprobar los datos");
			}
		}
	}

	public boolean getValue() {
		return value;
	}
}
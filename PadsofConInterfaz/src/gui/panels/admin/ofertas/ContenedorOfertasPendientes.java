package gui.panels.admin.ofertas;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import gui.Gui;

public class ContenedorOfertasPendientes extends JPanel {

	private static final long serialVersionUID = -2138438771740403776L;

	private List<JComponent> pendientes = new ArrayList<>();

	private SpringLayout layout;

	private JLabel labelActivas;
	private JSeparator separatorActivas;

	private JLabel labelNoActivas;

	private static final int SEPARACION_OFERTAS = 10;

	public ContenedorOfertasPendientes(Gui gui) {
		layout = new SpringLayout();
		this.setLayout(layout);
		Font font = new Font("Times New Roman", Font.PLAIN, 20);
		Font fontinfo = new Font("Verdana", Font.PLAIN, 15);
		labelActivas = new JLabel("Nuevas Ofertas Publicadas");

		labelActivas.setFont(font);

		labelNoActivas = new JLabel("No tienes ninguna oferta activa");

		labelNoActivas.setFont(fontinfo);

		separatorActivas = new JSeparator(SwingConstants.HORIZONTAL);

		separatorActivas.setPreferredSize(new Dimension(975, 1));

		separatorActivas.setForeground(Color.GRAY);

		pendientes.addAll(Arrays.asList(labelActivas, separatorActivas, labelNoActivas));

		this.add(labelActivas);
		this.add(labelNoActivas);
		this.add(separatorActivas);

		setContraits();

	}

	private void setContraits() {
		layout.putConstraint(SpringLayout.NORTH, labelActivas, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, labelActivas, 10, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, separatorActivas, 5, SpringLayout.SOUTH, labelActivas);
		layout.putConstraint(SpringLayout.WEST, separatorActivas, -5, SpringLayout.WEST, labelActivas);

		layout.putConstraint(SpringLayout.NORTH, labelNoActivas, 5, SpringLayout.SOUTH, separatorActivas);
		layout.putConstraint(SpringLayout.WEST, labelNoActivas, 0, SpringLayout.WEST, labelActivas);

	}

	public Component addOferta(JComponent p) {
		if (pendientes.get(pendientes.size() - 1) == labelNoActivas) {
			this.remove(pendientes.remove(pendientes.size() - 1));
		}

		this.add(p);
		layout.putConstraint(SpringLayout.NORTH, p, SEPARACION_OFERTAS, SpringLayout.SOUTH,
				pendientes.get(pendientes.size() - 1));
		layout.putConstraint(SpringLayout.WEST, p, 10, SpringLayout.WEST, this);
		pendientes.add(p);
		recalculateSize();
		return p;
	}

	private void recalculateSize() {
		layout.putConstraint(SpringLayout.SOUTH, this, 30, SpringLayout.SOUTH, pendientes.get(pendientes.size() - 1));
	}

	public void clearOfertas() {
		this.removeAll();
		this.add(labelActivas);
		this.add(labelNoActivas);
		this.add(separatorActivas);
		pendientes.clear();
		pendientes.addAll(Arrays.asList(labelActivas, separatorActivas, labelNoActivas));
		this.setContraits();
	}
}

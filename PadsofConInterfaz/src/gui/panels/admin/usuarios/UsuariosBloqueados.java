package gui.panels.admin.usuarios;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import gui.Gui;
import gui.components.ThinSolidScrollBarUi;
import gui.components.fx.FxButton;
import gui.components.fx.FxTextField;

public class UsuariosBloqueados extends JPanel {

	private static final long serialVersionUID = 7615793439529902683L;
	public static final String NAME = "USUARIOS_PROBLEMA_PAGO";

	private final Gui gui;
	private ContenedorUsuarios cu;

	public UsuariosBloqueados(Gui gui) {
		this.gui = gui;
		this.setPreferredSize(new Dimension(Gui.FRAME_WIDTH, 400));

		initialize();
	}

	private void initialize() {
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		FxTextField cuadroBusqueda = new FxTextField(200, 27, "Nombre del usuario");
		FxButton buscar = new FxButton(80, 27, "Buscar");

		JPanel contenedorBusqurda = new JPanel();
		contenedorBusqurda.setLayout(new BoxLayout(contenedorBusqurda, BoxLayout.LINE_AXIS));
		contenedorBusqurda.add(cuadroBusqueda);
		contenedorBusqurda.add(Box.createRigidArea(new Dimension(5, 0)));
		contenedorBusqurda.add(buscar);

		this.add(contenedorBusqurda);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, contenedorBusqurda, 0, SpringLayout.HORIZONTAL_CENTER,
				this);
		layout.putConstraint(SpringLayout.NORTH, contenedorBusqurda, 40, SpringLayout.NORTH, this);

		cu = new ContenedorUsuarios(gui);

		JScrollPane scrollPane = new JScrollPane(cu, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(700, 470));
		scrollPane.getVerticalScrollBar().setUI(new ThinSolidScrollBarUi(7));
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.getVerticalScrollBar().setBackground(Color.WHITE);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		this.add(scrollPane);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, scrollPane, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.SOUTH, contenedorBusqurda);

		buscar.setOnAction(e -> {
			SwingUtilities.invokeLater(() -> {
				if (cuadroBusqueda.getText().isEmpty()) {
					this.cargarUsuarios();
				} else {
					cu.cargarUsuario(cuadroBusqueda.getText());
				}

			});
		});
	}

	public void cargarUsuarios() {
		cu.cargarUsuarios();
		this.revalidate();
		this.repaint();
	}

}

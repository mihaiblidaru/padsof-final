package gui.panels.oferta;

import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import gui.Gui;
import gui.components.fx.FxButton;
import gui.controllers.Controller;
import gui.panels.demandante.MisReservas;


/**
 * Esta es nuestra clase que sirve como panel de ofertas reservadas
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class PanelOfertaReservadaInterno extends PanelOferta {

	
	private static final long serialVersionUID = -5513253061378866849L;
	
	/**
	 * Boton para contratar
	 */
	private FxButton contratar;
	
	/**
	 * Boton para cancelar
	 */
	private FxButton cancelar;
	
	/**
	 * El contenedor de botones
	 */
	private JPanel contenedorBotones;

	/**
	 * Constructor de PanelOfertaReservada
	 * @param gui interfaz grafica
	 * @param idOferta id de la oferta
	 */
	public PanelOfertaReservadaInterno(Gui gui, int idOferta) {
		super(gui, idOferta);
	}

	/**
	 * Funcion que crea los componentes de la interfaz, y los añade a la interfaz grafica
	 */
	@Override
	protected void crearComponentes() {
		super.crearComponentes();
		contenedorBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));

		contratar = new FxButton(110, 30, "Contratar");
		cancelar = new FxButton(110, 30, "Cancelar");
		contenedorBotones.add(cancelar);
		contenedorBotones.add(contratar);

		this.add(contenedorBotones);
	}

	/**
	 * Esta funcion  coloca los componentes en la interfaz grafica utilizando un SpringLayout
	 */
	@Override
	protected void colocarComponentes() {
		super.colocarComponentes();
		layout.putConstraint(SpringLayout.EAST, contenedorBotones, -10, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, contenedorBotones, -10, SpringLayout.SOUTH, this);
	}

	/**
	 * Esta funcion registra los eventos que ocurren en la interfaz
	 */
	@Override
	protected void registrarEventos() {
		contratar.setOnAction(e -> {
			SwingUtilities.invokeLater(() -> {
				Controller c = gui.getController();
				if (c.contratarOferta(this.getIdOferta())) {
					MisReservas mr = (MisReservas) gui.getComponent(MisReservas.NAME);
					mr.removeReserva(this.getIdOferta());
					mr.addContratada(this.getIdOferta());
				}
			});
		});

		cancelar.setOnAction(e -> {
			SwingUtilities.invokeLater(() -> {
				Controller c = gui.getController();
				if (c.demandanteCancelarReserva()) {
					MisReservas mr = (MisReservas) gui.getComponent(MisReservas.NAME);
					mr.removeReserva(this.getIdOferta());
				}
			});
		});
	}

}

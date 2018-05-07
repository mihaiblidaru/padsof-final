package gui.panels.oferta;

import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import gui.Gui;
import gui.components.fx.FxButton;
import gui.controllers.Controller;
import gui.panels.Header;
import gui.panels.ResultadosBusqueda;
import gui.panels.demandante.VerOferta;
import gui.util.GuiConstants;

/**
 * Esta es nuestra clase que sirve como panel de busqueda de ofertas
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class PanelOfertaBusqueda extends PanelOferta {

	private static final long serialVersionUID = -5513253061378866849L;
	
	/**
	 * Boton para rechazar
	 */
	private FxButton contratar;
	
	/**
	 * Boton para resevar
	 */
	private FxButton reservar;
	
	/**
	 * Boton para ver una oferta
	 */
	private FxButton ver;
	
	/**
	 * El contenedor de botones
	 */
	private JPanel contenedorBotones;

	/**
	 * Constructor de PanelOfertaBusqueda
	 * @param gui interfaz grafica
	 * @param idOferta id de la oferta
	 */
	public PanelOfertaBusqueda(Gui gui, int idOferta) {
		super(gui, idOferta);
	}

	/**
	 * Funcion que crea los componentes de la interfaz, y los añade a la interfaz grafica
	 */
	@Override
	protected void crearComponentes() {
		super.crearComponentes();

		contenedorBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
		contratar = new FxButton(110, 30, "CONTRATAR");
		reservar = new FxButton(110, 30, "RESERVAR");
		ver = new FxButton(150, 30, "Ver mas detalles");
		contenedorBotones.add(ver);
		contenedorBotones.add(reservar);
		contenedorBotones.add(contratar);
		contratar.setVisible(false);
		reservar.setVisible(false);
		ver.setVisible(false);
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
				if (gui.getController().contratarOferta(this.getIdOferta())) {
					ResultadosBusqueda resBusqueda = (ResultadosBusqueda) gui.getComponent(ResultadosBusqueda.NAME);
					resBusqueda.removeOferta(this.getIdOferta());
				}
			});
		});

		reservar.setOnAction(e -> {
			SwingUtilities.invokeLater(() -> {
				if (gui.getController().reservarOferta(this.getIdOferta())) {
					ResultadosBusqueda resBusqueda = (ResultadosBusqueda) gui.getComponent(ResultadosBusqueda.NAME);
					resBusqueda.removeOferta(this.getIdOferta());
				}
			});
		});

		ver.setOnAction(e -> {
			SwingUtilities.invokeLater(() -> {
				VerOferta vo = (VerOferta) gui.getComponent(VerOferta.NAME);
				vo.cargarDatos(this.getIdOferta());
				gui.showOnly(VerOferta.NAME, Header.NAME);
			});
		});
	}

	/**
	 * Esta funcion carga los datos de la ofert con el id dado
	 * @param id id de la oferta
	 */
	@Override
	public void cargarDatos(Integer id) {
		super.cargarDatos(id);
		actualizarBotones();
	}

	/**
	 * Esta funcion actualiza los botones dependiendo de los permisos del usuario
	 */
	public void actualizarBotones() {
		Controller c = this.gui.getController();
		if (c.tienePermisosDemandante()) {
			String estado = c.ofertaGetEstado(this.getIdOferta());
			reservar.setVisible(true);
			ver.setVisible(true);
			if (estado.equals(GuiConstants.ESTADO_RESERVADA)) {
				reservar.setText("Reservada");
				reservar.setDisable(true);
			} else {
				contratar.setVisible(true);
			}
		} else {
			ver.setVisible(false);
			reservar.setVisible(false);
			contratar.setVisible(false);
		}
	}
}

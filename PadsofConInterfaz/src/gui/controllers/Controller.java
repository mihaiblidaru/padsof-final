package gui.controllers;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import app.clases.MiVacaPiso;
import app.clases.inmueble.Caracteristica;
import app.clases.inmueble.Inmueble;
import app.clases.inmueble.InmuebleDuplicadoException;
import app.clases.ofertas.Estado;
import app.clases.ofertas.Oferta;
import app.clases.ofertas.OfertaNoModificableException;
import app.clases.ofertas.OfertaYaEstaReservadaException;
import app.clases.ofertas.ReservaDuplicadaException;
import app.clases.opiniones.Comentario;
import app.clases.opiniones.Numerica;
import app.clases.opiniones.Opinion;
import app.clases.users.Rol;
import app.clases.users.UsuarioNoPermisoException;
import app.clases.users.UsuarioYaTieneReservaException;
import es.uam.eps.padsof.telecard.OrderRejectedException;
import gui.Gui;
import gui.panels.oferta.comentario.PanelComentario;
import gui.util.DialogFactory;
import gui.util.GuiConstants;

/**
 * Controlador de la aplicacion
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class Controller {
	/**
	 * Modelo del controlador
	 */
	private MiVacaPiso model;
	/**
	 * Interfaz del controlador
	 */
	private Gui gui;

	/**
	 * Crea un nuevo controlador para la aplicacion
	 * 
	 * @param gui
	 *            interfaz grafica para este controlador
	 */
	public Controller(Gui gui) {
		this.gui = gui;
		try {
			this.model = MiVacaPiso.getInstance();
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
	}

	/**
	 * Devuelve la equivalencia entre la clase interna Rol y las constantes deifinas
	 * para la interfaz
	 * 
	 * @param rol
	 *            rol usado en la transformacion
	 * @return la equivalencia entre la clase interna Rol y las constantes deifinas
	 *         para la interfaz
	 */
	private String getEquivalenciaRol(Rol rol) {
		if (rol == Rol.A) {
			return GuiConstants.ROL_ADMIN;
		} else if (rol == Rol.D) {
			return GuiConstants.ROL_DEMANTANDTE;
		} else if (rol == Rol.O) {
			return GuiConstants.ROL_OFERTANTE;
		} else if (rol == Rol.OD) {
			return GuiConstants.ROL_OFERTANTE_DEMANDANTE;
		} else if (rol == null) {
			return GuiConstants.ROL_NO_REGISTRADO;
		} else {
			return GuiConstants.ROL_DESCONOCIDO;
		}
	}

	/**
	 * Devuelve la equivalencia entre la clase interna Estado y las constantes
	 * deifinas
	 * para la interfaz
	 * 
	 * @param estado
	 *            rol usado en la transformacion
	 * @return la equivalencia entre la clase interna Estado y las constantes
	 *         deifinas
	 *         para la interfaz
	 */
	private String getEquivalenciaEstado(Estado estado) {
		if (estado == Estado.ACEPTADA) {
			return GuiConstants.ESTADO_ACEPTADA;
		} else if (estado == Estado.CONTRATADA) {
			return GuiConstants.ESTADO_CONTRATADA;
		} else if (estado == Estado.PENDIENTE) {
			return GuiConstants.ESTADO_PENDIENTE;
		} else if (estado == Estado.PENDIENTE_DE_CAMBIOS) {
			return GuiConstants.ESTADO_PENDIENTE_DE_CAMBIOS;
		} else if (estado == Estado.RETIRADA) {
			return GuiConstants.ESTADO_RETIRADA;
		} else {
			return GuiConstants.ESTADO_DESCONOCIDO;
		}
	}

	/**
	 * Realiza el login en el sistema
	 * 
	 * @param user
	 *            usuario
	 * @param password
	 *            contraseña
	 * @return el rol del usuario despues del login
	 */
	public String login(String user, String password) {
		try {
			return getEquivalenciaRol(model.login(user, password));
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	/**
	 * Hace el logout del sistema
	 */
	public void logout() {
		model.logout();
	}

	/**
	 * Devuelve el numero de inmuebles del ofertante logueado
	 * 
	 * @return el numero de inmuebles del ofertante logueado
	 */
	public int getNumInmuebles() {
		return model.getOfertanteLogueado().getInmuebles().size();
	}

	/**
	 * Devuelve los ids de las ultimas ofertas publicadas
	 * 
	 * @param num
	 *            cantidad máxima de ofertas a devolver
	 * @return los ids de las ultimas num ofertas publicadas
	 */
	public List<Integer> getUltimasOfertas(int num) {
		return model.getUltimasOfertas(num);
	}

	/**
	 * Devuelve los resultados de la busqueda de viviendas
	 * 
	 * @param localidad
	 *            localidad del inmueble
	 * @param cp
	 *            codigo postal
	 * @param fechaInicio
	 *            fecha de inicio
	 * @return los resultados de la busqueda de viviendas
	 */
	public List<Integer> buscarViviendas(String localidad, Integer cp, LocalDate fechaInicio) {
		return model.buscarOfertas(localidad, cp, fechaInicio, null);
	}

	/**
	 * Devuelve los resultados de la busqueda vacacional
	 * 
	 * @param localidad
	 *            localidad del inmueble
	 * @param cp
	 *            codigo postal
	 * @param desde
	 *            fecha de inicio de la oferta
	 * @param hasta
	 *            feha final de la oferta
	 * @return resultados de la busqueda vacacional
	 */
	public List<Integer> buscarVacacional(String localidad, Integer cp, LocalDate desde, LocalDate hasta) {
		return model.buscarOfertas(localidad, cp, desde, hasta);
	}

	/**
	 * Devuelve el precio de una oferta
	 * 
	 * @param id
	 *            id de la oferta
	 * @return el precio de la oferta
	 */
	public Float ofertaGetPrecio(Integer id) {
		try {
			return model.getOfertaById(id).getPrecio();
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	/**
	 * Devuelve la fianza de una oferta
	 * 
	 * @param id
	 *            id de la oferta
	 * @return la fianza de una oferta
	 */
	public Float ofertaGetFianza(Integer id) {

		try {
			return model.getOfertaById(id).getFianza();
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	/**
	 * Devuelve la descripcion de una oferta
	 * 
	 * @param id
	 *            Id de la oferta
	 * @return la descripcion de una oferta
	 */
	public String ofertaGetDescripcion(Integer id) {
		try {
			return model.getOfertaById(id).getDescripcion();
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	/**
	 * Devuelve la fecha final de una oferta
	 * 
	 * @param id
	 *            id de la oferta
	 * @return la fecha final de una oferta
	 */
	public LocalDate ofertaGetFechaFin(Integer id) {
		try {
			return model.getOfertaById(id).getFechaFin();
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	/**
	 * Devuelve la fecha inicio de una oferta
	 * 
	 * @param id
	 *            id de la oferta
	 * @return la fecha inicio de una oferta
	 */
	public LocalDate ofertaGetFechaInicio(Integer id) {
		try {
			return model.getOfertaById(id).getFechaInicio();
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	/**
	 * Devuelve el numero de meses de una oferta
	 * 
	 * @param id
	 *            id de la oferta
	 * @return el numero se meses de una oferta
	 */
	public Integer ofertaGetNumMeses(Integer id) {
		try {
			return model.getOfertaById(id).getNumMeses();
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	/**
	 * Devuelve la direccion del inmueble de la oferta
	 * 
	 * @param id
	 *            id de la oferta
	 * @return la direccion del inmueble de la oferta
	 */
	public String ofertaGetDireccion(Integer id) {
		try {
			return inmuebleGetDireccionCompleta(model.getOfertaById(id).getInmueble());
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	/**
	 * Devuelve si la oferta se encuentra en un estado editable
	 * 
	 * @param id
	 *            id de la oferta
	 * @return si la oferta se encuentra en un estado editable
	 */
	public Boolean ofertaGetEditable(Integer id) {
		Estado estado = null;
		try {
			estado = model.getOfertaById(id).getEstado();
			return (estado == Estado.PENDIENTE || estado == Estado.PENDIENTE_DE_CAMBIOS);
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	/**
	 * Devuelve la lista de ids de ofertas del ofertante logueado
	 * 
	 * @return la lista de ids de ofertas del ofertante logueado
	 */
	public List<Integer> ofertanteGetMisOfertas() {
		List<Integer> result = new ArrayList<>();
		if (model.getOfertanteLogueado() != null) {
			List<Integer> inmuebles = model.getOfertanteLogueado().getInmuebles();
			inmuebles.stream().forEach(i -> {
				try {
					result.addAll(model.getInmuebleById(i).getOfertas());
				} catch (SQLException e) {
					DialogFactory.internalError(e.getMessage());
					System.exit(-1);
				}
			});
		}

		return result;
	}

	/**
	 * Devuelve los ids de los inmuebles del ofertante logueado
	 * 
	 * @return los ids de los inmuebles del ofertante logueado
	 */
	public List<Integer> ofertanteGetMisInmuebles() {
		return model.getOfertanteLogueado().getInmuebles();
	}

	public String ofertaGetEstado(Integer id) {
		try {
			return getEquivalenciaEstado(model.getOfertaById(id).getEstado());
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	/**
	 * Devuelve la direccion completa de un inmueble
	 * 
	 * @param id
	 *            id del inmueble
	 * @return la direccion completa de un inmueble
	 */
	public String inmuebleGetDireccionCompleta(Integer id) {
		Inmueble inmueble = null;
		try {
			inmueble = model.getInmuebleById(id);
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return String.format("%s, %d, %s", inmueble.getDireccion(), inmueble.getcodigoPostal(),
				inmueble.getLocalidad());

	}

	/**
	 * Devuelve el numero de ofertas de un inmueble
	 * 
	 * @param id
	 *            id del inmueble
	 * @return el numero de ofertas de un inmueble
	 */
	public Integer inmuebleGetNumOfertas(Integer id) {
		try {
			return model.getInmuebleById(id).getOfertas().size();
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	/**
	 * Añade un nuevo inmueble al sistema
	 * 
	 * @param localidad
	 *            localidad del inmueble
	 * @param cp
	 *            codigo postal del inmueble
	 * @param direccion
	 *            direccion del inmueble
	 * @param claves
	 *            claves de las caracteristicas
	 * @param valores
	 *            valores de las caracteristicas
	 * @return el estado de la accion
	 */
	public boolean addInmueble(String localidad, int cp, String direccion, List<String> claves, List<String> valores) {
		try {
			return model.addInmueble(localidad, cp, direccion, claves, valores) != null;
		} catch (UsuarioNoPermisoException e) {
			DialogFactory.noPermisionError();
		} catch (InmuebleDuplicadoException e) {
			DialogFactory.simpleErrorMessage("Ya tienes un inmueble con estos datos");
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return false;
	}

	/**
	 * Añade una nueva oferta de tipo Vivienda
	 * 
	 * @param fechaInicio
	 *            fecha de inicio de la oferta
	 * @param n_meses
	 *            numero de meses de la oferta
	 * @param precio
	 *            precio de la oferta
	 * @param fianza
	 *            fianza de la oferta
	 * @param descripcion
	 *            descripcion de la oferta
	 * @param idInmueble
	 *            id del inmueble de la oferta
	 * @return el estado de la accion
	 */
	public boolean addOfertaVivienda(LocalDate fechaInicio, int n_meses, float precio, float fianza, String descripcion,
			Integer idInmueble) {
		try {
			return model.addOferta(fechaInicio, n_meses, precio, fianza, descripcion, idInmueble) != null;
		} catch (UsuarioNoPermisoException e) {
			DialogFactory.noPermisionError();
		}
		return false;
	}

	/**
	 * Añade una nueva oferrta vacacional
	 * 
	 * @param fechaInicio
	 *            fecha de inicio de la oferta
	 * @param fechaFin
	 *            fecha de fin de la oferta
	 * @param precio
	 *            precio de la oferta
	 * @param fianza
	 *            fianza de la oferta
	 * @param descripcion
	 *            descripcion de la oferta
	 * @param idInmueble
	 *            id del inmueble de la oferta
	 * @return el estado de la accion
	 * 
	 */
	public boolean addOfertaVacacional(LocalDate fechaInicio, LocalDate fechaFin, float precio, float fianza,
			String descripcion, Integer idInmueble) {
		try {
			return model.addOferta(fechaInicio, fechaFin, precio, fianza, descripcion, idInmueble) != null;
		} catch (UsuarioNoPermisoException e) {
			DialogFactory.noPermisionError();
		}
		return false;
	}

	/**
	 * Cambia el precio de una oferta
	 * 
	 * @param id
	 *            id de la oferta
	 * @param parseFloat
	 *            nuevo precio de la oferta
	 * @return el resultado de esta accion
	 */
	public boolean ofertaSetPrecio(int id, float parseFloat) {
		try {
			return model.getOfertaById(id).setPrecio(parseFloat);
		} catch (OfertaNoModificableException e) {
			DialogFactory.internalError("La oferta se encuentra en un estado no modificable");
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return false;
	}

	/**
	 * Cambia la fianza de una oferta
	 * 
	 * @param id
	 *            id de la oferta
	 * @param parseFloat
	 *            el nuevo valor de la fianza
	 * @return el resultado de esta accion
	 */
	public boolean ofertaSetFianza(int id, float parseFloat) {
		try {
			return model.getOfertaById(id).setFianza(parseFloat);
		} catch (OfertaNoModificableException e) {
			DialogFactory.internalError("La oferta se encuentra en un estado no modificable");
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return false;
	}

	/**
	 * Cambia la descripcion de una oferta
	 * 
	 * @param id
	 *            id de la oferta
	 * @param descripcion
	 *            nueva descripcion
	 * @return el resultado de esta accion
	 */
	public boolean ofertaSetDescripcion(int id, String descripcion) {
		try {
			return model.getOfertaById(id).setDescripcion(descripcion);
		} catch (OfertaNoModificableException e) {
			DialogFactory.internalError("La oferta se encuentra en un estado no modificable");
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return false;
	}

	/**
	 * Devuelve el id de la oferta reservada por el demandante
	 * 
	 * @return id de la oferta reservada
	 */
	public Integer demandanteGetOfertaReservada() {
		Integer r = model.getDemandanteLogueado().getReservaActiva();
		try {
			return r == null ? null : model.getReservaById(r).getOferta();
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	/**
	 * Devuelve la lista de ofertas contratadas del demandante logueado
	 * 
	 * @return la lista de ofertas contratadas del demandante logueado
	 */
	public List<Integer> demandanteGetOfertasContratadas() {
		return model.getDemandanteLogueado().getOfertasContratadas();
	}

	/**
	 * Devuelve la lista de los ids de las ofertas que tiene que revisar el
	 * administrados
	 * 
	 * @return la lista de los ids de las ofertas que tiene que revisar el
	 *         administrados
	 */
	public List<Integer> adminGetOfertasPendientes() {
		try {
			return model.getOfertasPendientes();
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return null;

	}

	/**
	 * Devuelve si el usuario logueado tiene permisos de demandante
	 * 
	 * @return si el usuario logueado tiene permisos de demandante
	 */
	public boolean tienePermisosDemandante() {
		return model.getDemandanteLogueado() != null;
	}

	/**
	 * Devuelve una lista completa con los ids de los usuarios con problemas de pago
	 * 
	 * @return una lista completa con los ids de los usuarios con problemas de pago
	 */
	public Map<Integer, String> adminGetUsuariosProblemaPagos() {
		try {
			return model.getUsuariosProblemaPago();
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * Intenta contratar una oferta
	 * 
	 * @param idOferta
	 *            id de la oferta
	 * @return el resultado de la accion
	 */
	public boolean contratarOferta(int idOferta) {
		try {
			return model.contratar(idOferta);
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		} catch (UsuarioNoPermisoException e) {
			DialogFactory.noPermisionError();
		} catch (OrderRejectedException e) {
			DialogFactory.internalError(e.getMessage());
		} catch (OfertaNoModificableException e) {
			DialogFactory.internalError("La oferta se encuentra en un estado no modificable");
		}
		return false;
	}

	/**
	 * Cancela la reserva del demandante logueado
	 * 
	 * @return es estado de la accion
	 */
	public Boolean demandanteCancelarReserva() {
		try {
			return model.cancelarReserva();
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	/**
	 * Devuelve los comentarios de una oferta como una lista de PanelesComentario
	 * ya inicializados
	 * 
	 * @param id
	 *            id de la oferta
	 * @return los comentarios de una oferta como una lista de PanelesComentario
	 *         ya inicializados
	 */
	public List<PanelComentario> ofertaGetComentarios(Integer id) {
		Oferta oferta = null;
		try {
			oferta = model.getOfertaById(id);
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}

		Map<Integer, Opinion> opiniones = new TreeMap<>(oferta.getOpiniones());

		List<Integer> noComentarios = opiniones.keySet().stream().filter(k -> !(opiniones.get(k) instanceof Comentario))
				.collect(Collectors.toList());
		noComentarios.forEach(k -> opiniones.remove(k));

		return opiniones.values().stream().map(
				v -> new PanelComentario(gui, ((Comentario) v).getTexto(), v.getId(), ((Comentario) v).getPadre(), id))
				.collect(Collectors.toList());
	}

	/**
	 * Añade un nuevo comentario a una oferta
	 * 
	 * @param comentario
	 *            texto del comentario
	 * @param idOferta
	 *            id de la oferta
	 * @param idPadre
	 *            id del comentario padre del nuevo comentario
	 */
	public void ofertaComentar(String comentario, Integer idOferta, Integer idPadre) {

		try {
			model.addComentario(idOferta, comentario, idPadre);
		} catch (UsuarioNoPermisoException e) {
			DialogFactory.noPermisionError();
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
	}

	/**
	 * Realiza una reserva de una oferta
	 * 
	 * @param idOferta
	 *            id de la oferta
	 * @return si la reserva se ha hecho bien o no
	 */
	public boolean reservarOferta(int idOferta) {
		try {
			return model.reservar(idOferta);
		} catch (UsuarioNoPermisoException e) {
			DialogFactory.noPermisionError();
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
		} catch (UsuarioYaTieneReservaException e) {
			DialogFactory.simpleErrorMessage("Ya tienes una reserva activa");
		} catch (OfertaYaEstaReservadaException e) {
			DialogFactory.simpleErrorMessage("La oferta ya está reservada");
		} catch (OfertaNoModificableException e) {
			DialogFactory.internalError("La oferta se encuentra en un estado no modificable");
		} catch (ReservaDuplicadaException e) {
			DialogFactory.simpleErrorMessage(
					"Ya has tenido una reserva de esta oferta. Solo se puede reservar una vez cada oferta");
		}
		return false;
	}

	/**
	 * Cambia la fecha de inicio de una oferta
	 * 
	 * @param id
	 *            id de la oferta
	 * @param desde
	 *            nueva fecha de inicio
	 */
	public void ofertaSetFechaInicio(int id, LocalDate desde) {
		try {
			model.getOfertaById(id).setFechaInicio(desde);
		} catch (OfertaNoModificableException e) {
			DialogFactory.internalError("La oferta se encuentra en un estado no modificable");
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
	}

	/**
	 * Cambia la fecha final de una oferta
	 * 
	 * @param id
	 *            id de la oferta
	 * @param hasta
	 *            Fecha de fin de la oferta
	 * @return el estado de la accion
	 */
	public boolean ofertaSetFechaFin(int id, LocalDate hasta) {
		try {
			return model.getOfertaById(id).setFechaFin(hasta);
		} catch (OfertaNoModificableException e) {
			DialogFactory.internalError("La oferta se encuentra en un estado no modificable");
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return false;
	}

	/**
	 * Cambia el numero de meses de la oferta
	 * 
	 * @param id
	 *            id de la oferta
	 * @param meses
	 *            número de meses de la oferta
	 * @return el estado de la accion
	 */
	public boolean ofertaSetNumMeses(int id, int meses) {
		try {
			return model.getOfertaById(id).setNumMeses(meses);
		} catch (OfertaNoModificableException e) {
			DialogFactory.internalError("La oferta se encuentra en un estado no modificable");
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return false;
	}

	/**
	 * Marca una oferta como rechazada
	 * 
	 * @param idOferta
	 *            id de la oferta
	 */
	public void rechazarOferta(int idOferta) {
		try {
			model.getOfertaById(idOferta).setEstado(Estado.RETIRADA);
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
	}

	/**
	 * Marca una oferta como aceptada
	 * 
	 * @param idOferta
	 *            id de la oferta
	 */
	public void aceptarOferta(int idOferta) {
		try {
			model.getOfertaById(idOferta).setEstado(Estado.ACEPTADA);
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
	}

	/**
	 * Añade una valoracion numerica a una oferta
	 * 
	 * @param idOferta
	 *            id de la oferta que se va a valorar
	 * @param valor
	 *            valor numerico de la opinion
	 * @return el estado de la accion
	 */
	public boolean ofertaValorar(Integer idOferta, int valor) {
		try {
			return model.addNumerica(idOferta, valor);
		} catch (UsuarioNoPermisoException e) {
			DialogFactory.noPermisionError();
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return false;
	}

	/**
	 * Devuelve la valoracion media de una oferta
	 * 
	 * @param id
	 *            id de la oferta
	 * @return la valoracion media de una oferta
	 */
	public Integer ofertaGetValoracion(Integer id) {
		try {
			return model.getOfertaById(id).getNumericamedia();
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	/**
	 * Devuelve si una oferta dada ya ha sido valorada por el demandante logueado
	 * 
	 * @param id
	 *            id de la oferta
	 * @return si una oferta dada ya ha sido valorada por el demandante logueado
	 */
	public boolean ofertaYaTieneValoracion(Integer id) {
		Oferta o = null;
		try {
			o = model.getOfertaById(id);
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}

		Integer idUsuario = model.getDemandanteLogueado().getId();
		for (Opinion op : o.getOpiniones().values()) {
			if (op instanceof Numerica) {
				if (op.getUsuario().equals(idUsuario)) {
					return true;
				}
			}

		}
		return false;
	}

	/**
	 * Cambia la tarjeta de credito de un usuario
	 * 
	 * @param idUsuario
	 *            id del usuario
	 * @param text
	 *            la nueva tarjeta de credito
	 * @return el estado del la accion
	 */
	public boolean cambiarTarjeta(Integer idUsuario, String text) {
		try {
			return model.getClienteById(idUsuario).setCcard(text);
		} catch (UsuarioNoPermisoException e) {
			DialogFactory.noPermisionError();
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return false;
	}

	/**
	 * Devuelve el nombre de un usuario
	 * 
	 * @param id
	 *            id del usuario
	 * @return el nombre del usuario dado su id
	 */
	public String usuarioGetNombre(Integer id) {
		try {
			return model.getClienteById(id).getNombre();
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	/**
	 * Devuelve la tarjeta de un usuario
	 * 
	 * @param id
	 *            id del usuario
	 * @return la tarjeta del usuario dado su id
	 */
	public String usuarioGetTarjeta(int id) {
		try {
			return model.getClienteById(id).getCcard();
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	/**
	 * Busca por nombre a un usuario con problema de pagos
	 * 
	 * @param nombre
	 *            nombre del usuario
	 * @return el id y el rol del usuario
	 */
	public Map<Integer, String> adminGetUsuarioProblemaPagos(String nombre) {
		try {
			return model.getUsuarioProblemaPagos(nombre);
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	/**
	 * Devuelve las caracteristicas de un inmueble como cadena
	 * 
	 * @param id
	 *            id del inmueble
	 * @return las caracteristicas de un inmueble como cadena
	 */
	public String inmuebleGetCaracteristicas(Integer id) {
		List<Caracteristica> cars = null;
		try {
			cars = model.getInmuebleById(id).getCaracteristica();
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return cars.stream().map(c -> c.getKey() + ": " + c.getValor()).collect(Collectors.joining("\n"));
	}
}

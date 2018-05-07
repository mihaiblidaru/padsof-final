package gui.controllers;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.SwingUtilities;

import app.clases.MiVacaPiso;
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
import app.clases.users.Cliente;
import app.clases.users.Demandante;
import app.clases.users.Ofertante;
import app.clases.users.Rol;
import app.clases.users.UsuarioNoPermisoException;
import app.clases.users.UsuarioYaTieneReservaException;
import es.uam.eps.padsof.telecard.OrderRejectedException;
import gui.Gui;
import gui.panels.oferta.comentario.PanelComentario;
import gui.util.DialogFactory;
import gui.util.GuiConstants;
import gui.util.ParameterReference;

public class Controller {
	private final MiVacaPiso model;
	private final Gui gui;

	public Controller(Gui gui) throws SQLException {
		this.gui = gui;
		this.model = MiVacaPiso.getInstance();
	}

	//equivalencias entre la clase interna rol y la vista

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

	public String login(String user, String password) {
		try {
			return getEquivalenciaRol(model.login(user, password));
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	public void logout() {
		model.logout();
	}

	public int getNumInmuebles() {
		return model.getOfertanteLogueado().getInmuebles().size();
	}

	public List<Integer> getUltimasOfertas(int num) {
		return model.getUltimasOfertas(num);
	}

	public List<Integer> buscarViviendas(String localidad, Integer cp, LocalDate fechaInicio) {
		return model.buscarOfertas(localidad, cp, fechaInicio, null);
	}

	public List<Integer> buscarVacacional(String localidad, Integer cp, LocalDate desde, LocalDate hasta) {
		return model.buscarOfertas(localidad, cp, desde, hasta);
	}

	public Float ofertaGetPrecio(Integer id) {
		try {
			return model.getOfertaById(id).getPrecio();
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	public Float ofertaGetFianza(Integer id) {

		try {
			return model.getOfertaById(id).getFianza();
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	public String ofertaGetDescripcion(Integer id) {
		try {
			return model.getOfertaById(id).getDescripcion();
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	public LocalDate ofertaGetFechaFin(Integer id) {
		try {
			return model.getOfertaById(id).getFechaFin();
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	public LocalDate ofertaGetFechaInicio(Integer id) {
		try {
			return model.getOfertaById(id).getFechaInicio();
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	public Integer ofertaGetNumMeses(Integer id) {
		try {
			return model.getOfertaById(id).getNumMeses();
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	public String ofertaGetDireccion(Integer id) {
		try {
			return inmuebleGetDireccionCompleta(model.getOfertaById(id).getInmueble());
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

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

	public List<Integer> ofertanteGetMisOfertas() {
		List<Integer> result = new ArrayList<>();
		List<Integer> inmuebles = model.getOfertanteLogueado().getInmuebles();
		inmuebles.stream().forEach(i -> {
			try {
				result.addAll(model.getInmuebleById(i).getOfertas());
			} catch (SQLException e) {
				DialogFactory.internalError(e.getMessage());
				System.exit(-1);
			}
		});

		return result;
	}

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

	public Integer inmuebleGetNumOfertas(Integer id) {
		try {
			return model.getInmuebleById(id).getOfertas().size();
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	public Integer addInmueble(String localidad, int cp, String direccion, List<String> claves, List<String> valores) {
		try {
			return model.addInmueble(localidad, cp, direccion, claves, valores);
		} catch (UsuarioNoPermisoException e) {
			DialogFactory.noPermisionError();
		} catch (InmuebleDuplicadoException e) {
			DialogFactory.simpleErrorMessage("Ya tienes un inmueble con estos datos");
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	public Integer addOfertaVivienda(LocalDate fechaInicio, int n_meses, float precio, float fianza, String descripcion,
			Integer idInmueble) throws UsuarioNoPermisoException {
		return model.addOferta(fechaInicio, n_meses, precio, fianza, descripcion, idInmueble);
	}

	public Integer addOfertaVacacional(LocalDate fechaInicio, LocalDate fechaFin, float precio, float fianza,
			String descripcion, Integer idInmueble) throws UsuarioNoPermisoException {
		return model.addOferta(fechaInicio, fechaFin, precio, fianza, descripcion, idInmueble);
	}

	public void ofertaSetPrecio(int id, float parseFloat) {
		try {
			model.getOfertaById(id).setPrecio(parseFloat);
		} catch (OfertaNoModificableException e) {
			DialogFactory.internalError("La oferta se encuentra en un estado no modificable");
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
	}

	public void ofertaSetFianza(int id, float parseFloat) {
		try {
			model.getOfertaById(id).setFianza(parseFloat);
		} catch (OfertaNoModificableException e) {
			DialogFactory.internalError("La oferta se encuentra en un estado no modificable");
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}

	}

	public void ofertaSetDescripcion(int id, String descripcion) {
		try {
			model.getOfertaById(id).setDescripcion(descripcion);
		} catch (OfertaNoModificableException e) {
			DialogFactory.internalError("La oferta se encuentra en un estado no modificable");
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}

	}

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

	public List<Integer> demandanteGetOfertasContratadas() {
		return model.getDemandanteLogueado().getOfertasContratadas();
	}

	public List<Integer> adminGetOfertasPendientes()  {
			try{
				return model.getOfertasPendientes();
			}catch (SQLException e) {
				DialogFactory.internalError(e.getMessage());
				System.exit(-1);
			}
			return null;		
					
	}

	public boolean tienePermisosDemandante() {
		return model.getDemandanteLogueado() != null;
	}
	
	public Cliente getClienteById(int id) throws SQLException {
		return model.getClienteById(id);
	}
	
	public List<Integer> adminGetDemandantesProblemaPagos() {
		try {
			List<Integer> resultados = model.getUsuariosProblemaPago();
			List<Integer> pagos = new ArrayList<Integer>();
			for(Integer id : resultados) {
				if( model.getClienteById(resultados.get(id)) instanceof Ofertante ) {
					pagos.add(id);
				}
			}
			
			return pagos;
		}catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		
		return null;
	}
	
	public List<Integer> adminGetOfertantesProblemaCobros() {
		try {
			List<Integer> resultados = model.getUsuariosProblemaPago();
			List<Integer> cobros = new ArrayList<Integer>();
			
			for(Integer id : resultados) {
				if( model.getClienteById(resultados.get(id)) instanceof Demandante ) {
					cobros.add(id);
				}
			}
			return cobros;
		}catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		
		
		return null;
	}
	
	public List<Integer> adminGetUsuariosProblemaPagos()  {
		try{
			return model.getUsuariosProblemaPago();
		}catch(SQLException e) {
			return null;
		}
	}

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

	public Boolean demandanteCancelarReserva() {
		try {
			return model.cancelarReserva();
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	public List<PanelComentario> ofertaGetComentarios(Integer id) {
		Oferta oferta = null;
		try {
			oferta = model.getOfertaById(id);
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}

		Map<Integer, Opinion> opiniones = oferta.getOpiniones();

		List<Integer> noComentarios = opiniones.keySet().stream().filter(k -> !(opiniones.get(k) instanceof Comentario))
				.collect(Collectors.toList());
		noComentarios.forEach(k -> opiniones.remove(k));
		ParameterReference<List<PanelComentario>> contResultados = new ParameterReference<>();

		try {
			SwingUtilities.invokeAndWait(() -> {
				contResultados.setValue(
						opiniones.values().stream().map(v -> new PanelComentario(gui, ((Comentario) v).getTexto(),
								v.getId(), ((Comentario) v).getPadre(), id)).collect(Collectors.toList()));

			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
		return contResultados.getValue();
	}

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

	public void ofertaSetFechaFin(int id, LocalDate hasta) {
		try {
			model.getOfertaById(id).setFechaFin(hasta);
		} catch (OfertaNoModificableException e) {
			DialogFactory.internalError("La oferta se encuentra en un estado no modificable");
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
	}

	public void ofertaSetNumMeses(int id, int meses) {
		try {
			model.getOfertaById(id).setNumMeses(meses);
		} catch (OfertaNoModificableException e) {
			DialogFactory.internalError("La oferta se encuentra en un estado no modificable");
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
	}

	public void rechazarOferta(int idOferta) {
		try {
			model.getOfertaById(idOferta).setEstado(Estado.RETIRADA);
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
	}

	public void aceptarOferta(int idOferta) {
		try {
			model.getOfertaById(idOferta).setEstado(Estado.ACEPTADA);
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
	}

	public void ofertaValorar(Integer idOferta, int valor) {
		try {
			model.addNumerica(idOferta, valor);
		} catch (UsuarioNoPermisoException e) {
			DialogFactory.noPermisionError();
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}

	}

	public Integer ofertaGetValoracion(Integer id) {
		try {
			return model.getOfertaById(id).getNumericamedia();
		} catch (SQLException e) {
			DialogFactory.internalError(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

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
}

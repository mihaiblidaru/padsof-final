package gui.controllers;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import app.clases.MiVacaPiso;
import app.clases.inmueble.Inmueble;
import app.clases.inmueble.InmuebleDuplicadoException;
import app.clases.ofertas.Estado;
import app.clases.users.Rol;
import app.clases.users.UsuarioNoPermisoException;

public class Controller {
	private final MiVacaPiso model;

	public Controller() throws SQLException {
		this.model = MiVacaPiso.getInstance();
	}

	public Rol login(String user, String password) throws SQLException {
		return model.login(user, password);
	}

	public void logout() {
		model.logout();
	}

	public int getNumInmuebles() {
		return model.getOfertanteLogueado().getInmuebles().size();
	}

	public List<Integer> buscarViviendas(String localidad, Integer cp, LocalDate fechaInicio) {
		return model.buscarOfertas(localidad, cp, fechaInicio, null);
	}

	public List<Integer> buscarVacacional(String localidad, Integer cp, LocalDate desde, LocalDate hasta) {
		return model.buscarOfertas(localidad, cp, desde, hasta);
	}

	public float ofertaGetPrecio(Integer id) {
		return model.getOfertaById(id).getPrecio();
	}

	public float ofertaGetFianza(Integer id) {
		return model.getOfertaById(id).getFianza();
	}

	public String ofertaGetDescripcion(Integer id) {
		return model.getOfertaById(id).getDescripcion();
	}

	public LocalDate ofertaGetFechaFin(Integer id) {
		return model.getOfertaById(id).getFechaFin();
	}

	public LocalDate ofertaGetFechaInicio(Integer id) {
		return model.getOfertaById(id).getFechaInicio();
	}

	public int ofertaGetNumMeses(Integer id) {
		return model.getOfertaById(id).getNumMeses();
	}

	public String ofertaGetDireccion(Integer id) {
		return inmuebleGetDireccionCompleta(model.getOfertaById(id).getInmueble());
	}

	public boolean ofertaGetEditable(Integer id) {
		Estado estado = model.getOfertaById(id).getEstado();
		return (estado == Estado.PENDIENTE || estado == Estado.PENDIENTE_DE_CAMBIOS);
	}

	public List<Integer> ofertanteGetMisOfertas() {
		List<Integer> result = new ArrayList<>();
		List<Integer> inmuebles = model.getOfertanteLogueado().getInmuebles();
		inmuebles.stream().forEach(i -> result.addAll(model.getInmuebleById(i).getOfertas()));

		return result;
	}

	public List<Integer> ofertanteGetMisInmuebles() {
		return model.getOfertanteLogueado().getInmuebles();
	}

	public Estado ofertaGetEstado(Integer id) {
		return model.getOfertaById(id).getEstado();
	}

	public String inmuebleGetDireccionCompleta(Integer id) {
		Inmueble inmueble = model.getInmuebleById(id);
		return String.format("%s, %d, %s", inmueble.getDireccion(), inmueble.getcodigoPostal(),
				inmueble.getLocalidad());

	}

	public int inmuebleGetNumOfertas(Integer id) {
		return model.getInmuebleById(id).getOfertas().size();
	}

	public Integer addInmueble(String localidad, int cp, String direccion, List<String> claves, List<String> valores)
			throws UsuarioNoPermisoException, InmuebleDuplicadoException {
		return model.addInmueble(localidad, cp, direccion, claves, valores);
	}

	public Integer addOfertaVivienda(LocalDate fechaInicio, int n_meses, float precio, float fianza, String descripcion,
			Integer idInmueble) throws UsuarioNoPermisoException {
		return model.addOferta(fechaInicio, n_meses, precio, fianza, descripcion, idInmueble);
	}

	public Integer addOfertaVacacional(LocalDate fechaInicio, LocalDate fechaFin, float precio, float fianza,
			String descripcion, Integer idInmueble) throws UsuarioNoPermisoException {
		return model.addOferta(fechaInicio, fechaFin, precio, fianza, descripcion, idInmueble);
	}

}


package app.clases.ofertas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.clases.data.Columna;
import app.clases.data.DBManager;
import app.clases.data.Tabla;
import app.clases.data.exceptions.TablaNoTieneColumnaException;
import app.clases.data.exceptions.TiposNoCoincidenException;
import app.clases.opiniones.Comentario;
import app.clases.opiniones.Numerica;
import app.clases.opiniones.Opinion;

/**
 * Esta clase nos sirve para trabajar con las ofertas
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public abstract class Oferta {
	/**
	 * La fecha de inicio de la oferta
	 */
	private LocalDate fechaInicio;

	/**
	 * El precio de la oferta
	 */
	private float precio;

	/**
	 * La fianza de la oferta
	 */
	private float fianza;

	/**
	 * La descripción de la oferta
	 */
	private String descripcion;

	/**
	 * El estado de la oferta
	 */
	private Estado estado;

	/**
	 * El id de la oferta
	 */
	protected final int id;

	/**
	 * El id del inmueble al que pertence la oferta
	 */
	private Integer inmueble;

	/**
	 * Id de la reserva activa de esta oferta, si hay una
	 */
	protected Integer reservaActiva = null;

	/**
	 * Lista de ids de reservas que se han hecho de esta reserva
	 */
	protected final List<Integer> reservas = new ArrayList<>();

	/**
	 * Devuelve el id de la reserva activa de la oferta
	 * 
	 * @return reserva id de la reserva
	 */
	public Integer getReservaActiva() {
		return reservaActiva;
	}

	/**
	 * Añade el id de una reserva a la lista de reservas
	 * 
	 * @param idReserva
	 *            id de la reserva que se quiere añadir a la lista de reservas
	 * @return true si la reserva se ha añadido correctamente
	 */
	public boolean addReserva(Integer idReserva) {
		return this.reservas.add(idReserva);
	}

	/**
	 * Devuelve la lista de ids de reservas de esta oferta
	 * 
	 * @return reservas lista de ids de reservas de esta oferta
	 */
	public List<Integer> getReservas() {
		return reservas;
	}

	/**
	 * El id del demandante que contrata la oferta
	 */
	private Integer demandante;

	/**
	 * El conjunto de opiniones de la Oferta
	 */
	private Map<Integer, Opinion> opiniones = new HashMap<>();

	/**
	 * Cambia el id de la reserva activa
	 * 
	 * @param idReserva
	 *            el id de la nueva reserva
	 * @return true si se cambia correctamente, si no false
	 */
	public boolean setReserva(Integer idReserva) {
		try {
			DBManager.updateField(Tabla.OFERTA, Columna.OFERTA_RESERVA, this.id, idReserva);
			this.reservaActiva = idReserva;
			return true;
		} catch (SQLException e) {

		}
		return false;
	}

	/**
	 * Constructor de Oferta
	 * 
	 * @param id
	 *            id de la oferta
	 * @param fechaInicio
	 *            fecha de inicio de la oferta
	 * @param precio
	 *            precio de la oferta
	 * @param fianza
	 *            fianza de la oferta
	 * @param descripcion
	 *            descripcion de la oferta
	 * @param inmueble
	 *            inmueble de la oferta
	 * @param estado
	 *            el estado de la oferta
	 */
	public Oferta(int id, LocalDate fechaInicio, float precio, float fianza, String descripcion, int inmueble,
			Estado estado) {
		this.fechaInicio = fechaInicio;
		this.precio = precio;
		this.fianza = fianza;
		this.descripcion = descripcion;
		this.inmueble = inmueble;
		this.id = id;
		this.estado = estado;
	}

	/**
	 * Devuelve la comision que sera del 2% si es vacacional o del 0,1% si es de
	 * vivienda, esta oferta se sobreescribira
	 * 
	 * @return Comision de la oferta
	 */
	public double getComision() {
		throw new UnsupportedOperationException("Not implemented");

	}

	/**
	 * Devuelve la fecha de inicio de la oferta
	 * 
	 * @return FechaInicio fecha de inicio de la oferta
	 */
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * Cambia la fecha de inicio de la oferta
	 * 
	 * @param fechaInicio
	 *            la nueva fecha de inicio de la oferta
	 * @throws TablaNoTieneColumnaException
	 *             si la tabla no tiene la columna que queremos
	 * @throws TiposNoCoincidenException
	 *             si los tipos no coinciden
	 * @throws OfertaNoModificableException
	 *             si la oferta no es modificable
	 * @return true si se cambia correctamente si no false
	 */
	public boolean setFechaInicio(LocalDate fechaInicio)
			throws OfertaNoModificableException, TablaNoTieneColumnaException, TiposNoCoincidenException {
		if (!this.estado.editable()) { /** Con esto comprobamos si podemos editar la oferta */
			throw new OfertaNoModificableException(this);
		}

		try {
			DBManager.updateField(Tabla.OFERTA, Columna.OFERTA_FECHAINICIO, this.id,
					fechaInicio);/** Cambiamos la fecha en la base de datos */
			this.fechaInicio = fechaInicio;
			return true;
		} catch (SQLException e) {

		}
		return false;
	}

	/**
	 * Devuelve el precio de la oferta
	 * 
	 * @return precio precio de la oferta
	 */
	public float getPrecio() {
		return precio;
	}

	/**
	 * Cambia el precio de la oferta
	 * 
	 * @param precio
	 *            el nuevo precio de la oferta
	 * @throws TablaNoTieneColumnaException
	 *             si la tabla no tiene la columna que queremos
	 * @throws TiposNoCoincidenException
	 *             si los tipos no coinciden
	 * @throws OfertaNoModificableException
	 *             si la oferta no es modificable
	 * @return true si se cambia correctamente si no false
	 */
	public boolean setPrecio(float precio) throws OfertaNoModificableException {
		if (!this.estado.editable()) { /** Comprobamos si es editable */
			throw new OfertaNoModificableException(this);
		}
		if (this.precio != precio) {
			try {
				DBManager.updateField(Tabla.OFERTA, Columna.OFERTA_PRECIO, this.id,
						precio); /** Cambiamos el precio en la base de datos */
				this.precio = precio;
				return true;
			} catch (SQLException e) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Devuelve la fianza de la oferta
	 * 
	 * @return fianza fianza de la oferta
	 */
	public float getFianza() {
		return fianza;
	}

	/**
	 * Cambia la fianza de la oferta
	 * 
	 * @param fianza
	 *            la nueva fianza de la oferta
	 * @throws TablaNoTieneColumnaException
	 *             si la tabla no tiene la columna que queremos
	 * @throws TiposNoCoincidenException
	 *             si los tipos no coinciden
	 * @throws OfertaNoModificableException
	 *             si la oferta no es modificable
	 * @return true si se cambia correctamente si no false
	 */
	public boolean setFianza(float fianza) throws OfertaNoModificableException {
		if (!this.estado.editable()) { /** Comprobamos si es editable */
			throw new OfertaNoModificableException(this);
		}
		if (this.fianza != fianza) {
			try {
				DBManager.updateField(Tabla.OFERTA, Columna.OFERTA_FIANZA, this.id,
						fianza); /** Actualizamos la fianza en la base de datos */
				this.fianza = fianza;
				return true;
			} catch (SQLException e) {
				return false;
			}
		}
		return true;

	}

	/**
	 * Devuelve la descripcion de la oferta
	 * 
	 * @return la descripcion de la oferta
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Cambia la descripcion de la oferta
	 * 
	 * @param descripcion
	 *            la nueva descripcion de la oferta
	 * @throws TablaNoTieneColumnaException
	 *             si la tabla no tiene la columna que queremos
	 * @throws TiposNoCoincidenException
	 *             si los tipos no coinciden
	 * @throws OfertaNoModificableException
	 *             si la oferta no es modificable
	 * @return true si se cambia correctamente si no false
	 */
	public boolean setDescripcion(String descripcion) throws OfertaNoModificableException {
		if (!this.estado.editable()) { /** Comprobamos si es editable */
			throw new OfertaNoModificableException(this);
		}
		if (!this.descripcion.equals(descripcion)) {
			try {
				DBManager.updateField(Tabla.OFERTA, Columna.OFERTA_DESCRIPCION, this.id,
						descripcion); /** Actualizamos en la base de datos */
				this.descripcion = descripcion;
				return true;
			} catch (SQLException e) {
				return false;
			}
		}
		return true;
	}

	protected void cargarOpiniones() throws SQLException {
		Connection con = DBManager.openDbConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt
				.executeQuery("SELECT * FROM " + Tabla.OPINION + " where " + Columna.OPINION_OFERTA + " = " + this.id);

		while (rs.next()) {
			Integer id = rs.getInt(Columna.ID.toString());
			Integer valor = rs.getInt(Columna.OPINION_VALOR.toString());
			String texto = rs.getString(Columna.OPINION_COMENTARIO.toString());
			Integer padre = rs.getInt(Columna.OPINION_PADRE.toString());
			Integer usuario = rs.getInt(Columna.OPINION_USUARIO.toString());
			LocalDate fecha = rs.getDate(Columna.OPINION_FECHA.toString()).toLocalDate();
			if (texto != null) {
				Opinion op = new Comentario(id, fecha, usuario, texto, padre);
				this.opiniones.put(id, op);
			} else {
				Opinion op = new Numerica(id, fecha, usuario, valor);
				this.opiniones.put(id, op);
			}
		}
	}

	/**
	 * Devuelve el estado de la oferta
	 * 
	 * @return estado el estado de la oferta
	 */
	public Estado getEstado() {
		return estado;
	}

	/**
	 * Cambia el estado de la oferta
	 * 
	 * @param estado
	 *            el nuevo estado de la oferta
	 * @return true si se cambia correctamente si no false
	 * @throws TablaNoTieneColumnaException
	 *             si la tabla no tiene la columna que queremos
	 * @throws TiposNoCoincidenException
	 *             si los tipos no coinciden
	 */
	public boolean setEstado(Estado estado) {
		try {
			DBManager.updateField(Tabla.OFERTA, Columna.OFERTA_ESTADO, this.id,
					estado.toString()); /** Cambiamos el estado en la base de datos */
			this.estado = estado;
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * Devuelve el id del inmueble al que pertenece la oferta
	 * 
	 * @return inmueble id del inmueble al que pertenece la oferta
	 */
	public int getInmueble() {
		return inmueble;
	}

	/**
	 * Devuelve el mapa de las opiniones
	 * 
	 * @return opiniones conjunto de opiniones de la oferta
	 */
	public Map<Integer, Opinion> getOpiniones() {
		return opiniones;
	}

	/**
	 * Añade una opinion a la oferta
	 * 
	 * @param opinion
	 *            opinion que queremos añadir
	 */
	public void addOpinion(Opinion opinion) {
		opiniones.put(opinion.getId(), opinion);
	}

	/**
	 * Esta funcion nos pasa toda la informacion de la oferta a una cadena
	 */
	@Override
	public String toString() {
		return "[id = " + this.id + ", Estado:" + this.estado + ", Fecha de inicio: " + fechaInicio + ", Precio: "
				+ precio + ", fianza: " + fianza + ", descripcion: " + descripcion + ", inmueble: " + inmueble + "]";
	}

	/**
	 * Esta funcion nos da la media de las valoraciones numericas de la oferta
	 * 
	 * @return media/i media numerica de las vaoraciones de la oferta redondeada
	 */
	public Integer getNumericamedia() {
		int i = 0;
		float media = 0;
		Collection<Opinion> ops = opiniones.values();

		for (Opinion opinion : ops) {
			if (opinion.getValor() != -1) {
				media += opinion.getValor();
				i++;
			}
		}

		return Math.round(media / i);
	}

	/**
	 * Devuelve el id del demandante de la oferta
	 * 
	 * @return demandante id demandante de la oferta
	 */
	public Integer getDemandante() {
		return demandante;
	}

	/**
	 * Cambia el id del demandante
	 * 
	 * @param demandante
	 *            id del nuevo demandante
	 * @return true si se cambia correctamente, si no false
	 * @throws OfertaNoModificableException
	 *             si la oferta no es editable
	 * @throws SQLException
	 *             Si falla el SQL
	 * @throws TablaNoTieneColumnaException
	 *             si la tabla no tiene la columna que queremos
	 * @throws TiposNoCoincidenException
	 *             si los tipos de datos no coinciden
	 */
	public boolean setDemandante(Integer demandante) throws OfertaNoModificableException, SQLException {
		if (this.estado == Estado.ACEPTADA
				|| this.estado == Estado.RESERVADA) { /** Comprobamos que la oferta este en aceptada */
			try {
				DBManager.updateField(Tabla.OFERTA, Columna.OFERTA_DEMANDANTE, this.id,
						demandante); /** Actualizamos el demandante en la tabla de datos */
				this.demandante = demandante;
				return true;
			} catch (SQLException e) {

			}
			return false;
		}
		throw new OfertaNoModificableException(this); /** Lanzamos la excepcion si no es modificable */
	}

	/**
	 * Devuelve el id de la oferta
	 * 
	 * @return id id de la oferta
	 */
	public int getId() {
		return id;
	}

	/**
	 * Cargamos una Oferta desde la base de datos
	 * 
	 * @param id
	 *            id de la oferta
	 * @return la nueva oferta, creada dependiendo del tipo de la oferta o null
	 * @throws SQLException
	 *             si falla el SQL
	 */
	public static Oferta cargarOferta(int id) throws SQLException {
		Connection con = DBManager.openDbConnection();/** Establecemos la conexion con la base de datos */
		String queryTipoOferta = "Select tipo from oferta where id = " + id;/** Buscamos la oferta con id dado */

		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(queryTipoOferta); /** Ejecutamos la query */
		if (rs.next()) {
			String tipo = rs.getString(
					Columna.OFERTA_TIPO.toString()); /** Conseguimos el tipo para cargarla dependiendo del tipo */
			if (tipo.equalsIgnoreCase("vacacional")) {
				return Vacacional.cargarVacacional(id);
			} else if (tipo.equalsIgnoreCase("vivienda")) {
				return Vivienda.cargarVivienda(id);
			}
		}
		return null;
	}

	/**
	 * Cambia la fecha final de la oferta
	 * 
	 * @param date
	 *            nueva fecha final de la oferta
	 * @return true si se cambia correctamente, si no false
	 * @throws OfertaNoModificableException
	 *             si la oferta no es modificable
	 */
	public boolean setFechaFin(LocalDate date) throws OfertaNoModificableException {
		return false;
	}

	/**
	 * Cambia el número de meses de una oferta de tipo Vacacional. Cambia el valor
	 * de este objeto y también realiza el cambio dentro de la base de datos
	 * 
	 * @param numMeses
	 *            numero de meses de la oferta
	 * @return true si se ha cambiado correctamente o false en caso contrario
	 * @throws OfertaNoModificableException
	 *             la oferta se encuentra en un estado no modificable
	 */
	public boolean setNumMeses(int numMeses) throws OfertaNoModificableException {
		return false;
	}

	/**
	 * Devuelve el numero de meses
	 * 
	 * @return Numero de meses de esta oferta
	 */
	public Integer getNumMeses() {
		return null;
	}

	/**
	 * Devuelve la fecha final de la oferta
	 * 
	 * @return FechaFin fecha final de la oferta
	 */
	public LocalDate getFechaFin() {
		return null;
	}

}
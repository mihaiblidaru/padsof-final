package app.clases.ofertas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import app.clases.data.Columna;
import app.clases.data.DBManager;
import app.clases.data.Tabla;

/**
 * Esta clase nos sirve para trabajar con las reservas
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class Reserva {

	/**
	 * Id de la reserva
	 */
	private final int id;

	/**
	 * Id del usuario que hace la reserva
	 */
	private final int usuario;

	/**
	 * Id de la oferta que estamos reservando
	 */
	private final int oferta;

	/**
	 * fecha en la que se hace la reserva
	 */
	private final LocalDate fechaInicio;

	private LocalDate fechaCaducar;

	/**
	 * Con esta funcion cargamos una reserva de la base de datos
	 * 
	 * @param id
	 *            id de la reserva
	 * @return la nueva reserva o null
	 * @throws SQLException
	 *             si falla el SQL
	 */
	public static Reserva cargarReserva(int id) throws SQLException {
		Connection con = DBManager.openDbConnection();/** Establecemos la conexion con la base de datos */

		String queryReserva = "Select * from reserva where id=" + id; /** Buscamos la reserva con el id dado */

		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(queryReserva);/** Aqui ejecutamos la consulta */
		Reserva reserva = null;
		if (rs.next()) {
			LocalDate inicio = rs.getDate(Columna.RESERVA_FECHAINICIO.toString())
					.toLocalDate();/** Conseguimos la fecha de inicio de la reserva */
			Integer cliente = rs.getInt(
					Columna.RESERVA_CLIENTE.toString()); /** Conseguimos la id de demandante que hace la reserva */
			Integer oferta = rs.getInt(Columna.RESERVA_OFERTA.toString());
			reserva = new Reserva(id, cliente, oferta, inicio);
		}

		rs.close();
		stmt.close();
		con.close(); /** Cerramos la conexion con la base de datos */

		return reserva;
	}

	/**
	 * Constructor de Reserva
	 * 
	 * @param id
	 *            id de la oferta
	 * @param idUsuario
	 *            usuario que hace la reserva
	 * @param oferta
	 *            oferta que reservamos
	 * @param fechaInicio
	 *            fecha de inicio de la reserva
	 */
	public Reserva(int id, int idUsuario, int oferta, LocalDate fechaInicio) {
		if (fechaInicio == null) {
			throw new IllegalArgumentException("fechaInicio no puede ser nula");
		}

		if (id < 1 || idUsuario < 1 || oferta < 1) {
			throw new IllegalArgumentException("Las referencias a otros objetos no puede ser negativa");
		}

		this.id = id;
		this.usuario = idUsuario;
		this.oferta = oferta;
		this.fechaInicio = fechaInicio;
	}

	@Override
	/**
	 * Pasa toda la informacion de la reserva a una cadena
	 */
	public String toString() {
		return String.format("Reserva [id=%s, oferta=%s, fechaInicio=%s]", id, oferta, fechaInicio);
	}

	/**
	 * Devuelve el id de la oferta que reservamos
	 * 
	 * @return id id de la oferta
	 */
	public int getOferta() {
		return oferta;
	}

	/**
	 * Devuelve el id del usuario de la reserva
	 * 
	 * @return usuario id del usuario de la reserva
	 */
	public int getUsuario() {
		return usuario;
	}

	/**
	 * Devuelve la fecha de la reserva
	 * 
	 * @return fecha fecha de la reserva
	 */
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * Devuelve el id de la reserva
	 * 
	 * @return id id de la reserva
	 */
	public int getId() {
		return id;
	}

	public boolean setFechaCaducar(LocalDate hoy) {
		try {
			DBManager.updateField(Tabla.RESERVA, Columna.RESERVA_FECHACADUCAR, this.id,
					hoy);/** Cambiamos la fecha en la base de datos */
			this.fechaCaducar = hoy;
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
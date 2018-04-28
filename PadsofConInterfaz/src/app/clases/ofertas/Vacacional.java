package app.clases.ofertas;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import app.clases.data.Columna;
import app.clases.data.DBManager;
import app.clases.data.Tabla;
import app.clases.data.exceptions.TablaNoTieneColumnaException;
import app.clases.data.exceptions.TiposNoCoincidenException;


/**
 * Esta clase nos sirve para trabajar con las ofertas vacacionales
 * 
 * @author Mihai Blidaru 
 * @author Sergio Dominguez
 *
 */
public class Vacacional extends Oferta {
	
	@Override
	/**
	 * Pasamos toda la informacion de la oferta vacacional  a una cadena
	 */
	public String toString() {
		return "Vacacional [fechaFin=" + fechaFin + "]" + super.toString();
	}

	/** 
	 * El porcentaje de la comision
	 */
	private static final Float PORCENTAJE_COMISION = 2.0f;

	/**
	 * Fecha final de la oferta
	 */
	private LocalDate fechaFin;
	
	/**
	 * Constructor de la oferta vacacional
	 * @param id id de la oferta
	 * @param fechaInicio fecha de inicio de la oferta
	 * @param fechaFin fecha final de la oferta
	 * @param precio precio de la oferta
	 * @param fianza fianza de la oferta
	 * @param descripcion descripcion de la oferta
	 * @param idinmueble id del inmueble de la oferta
	 * @param estado estadod de la oferta
	 */
	public Vacacional(int id, LocalDate fechaInicio, LocalDate fechaFin, float precio, float fianza, String descripcion, int idinmueble, Estado estado) {
		super(id, fechaInicio, precio, fianza, descripcion, idinmueble, estado);
		
		this.fechaFin = fechaFin;
	}

	/**
	 * Devuelve la fecha final de la oferta
	 * @return FechaFin fecha final de la oferta
	 */
	public LocalDate getFechaFin() {
		return fechaFin;
	}

	/**
	 * Cambia la fecha final de la oferta
	 * @param fechaFin nueva fecha final de la oferta
	 * @return true si se cambia correctamente, si no false
	 * @throws OfertaNoModificableException si la oferta no es modificable
	 */
	public boolean setFechaFin(LocalDate fechaFin) throws OfertaNoModificableException, TablaNoTieneColumnaException, TiposNoCoincidenException {
		if(!this.getEstado().editable() ) {/**Comprobamos que la oferta no es modificable */
			throw new OfertaNoModificableException(this); 
		}
		
		try {
			DBManager.updateField(Tabla.OFERTA, Columna.OFERTA_FECHAFIN, this.id, fechaFin);/**Actualizamos la base de datos */
			this.fechaFin = fechaFin;
			return true;
		}catch(SQLException e) {
			
		}
		return false;
	}
	
	
	/**
	 * Devuelve la comision del 2%
	 * @return PORCENTAJE_COMISION el porcentaje del 2%
	 */
	public double getComision() {
		return PORCENTAJE_COMISION;
	}

	/** 
	 * Carga una oferta vacacional de la base de datos
	 * @param id id de la oferta
	 * @return oferta la oferta que se ha creado 
	 * @throws SQLException si falla el SQL
	 */
	public static Vacacional cargarVacacional(int id) throws SQLException {
		Connection con = DBManager.openDbConnection();/**Establecemos la conexion de la base de datos */
		String queryVacacional = "SELECT * FROM " + Tabla.OFERTA + " WHERE " + Columna.ID + " = " + id;/**Buscamos el vacacional con el id dado*/
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(queryVacacional);
		Vacacional oferta = null;
		if(rs.next()) { /**Vamos consiguiendo los datos para el constructor de la oferta */
			LocalDate fechaInicio = rs.getDate(Columna.OFERTA_FECHAINICIO.toString()).toLocalDate();
			LocalDate fechaFin = rs.getDate(Columna.OFERTA_FECHAFIN.toString()).toLocalDate();
			Float precio = rs.getFloat(Columna.OFERTA_PRECIO.toString());
			Float fianza = rs.getFloat(Columna.OFERTA_FIANZA.toString());
			String descripcion = rs.getString(Columna.OFERTA_DESCRIPCION.toString());
			Integer idInmueble = rs.getInt(Columna.OFERTA_INMUEBLE.toString());
			Estado estado = Estado.fromString(rs.getString(Columna.OFERTA_ESTADO.toString()));
			oferta = new Vacacional(id,fechaInicio,fechaFin,precio,fianza,descripcion,idInmueble, estado);
		}
		
		rs.close();
			
		rs = stmt.executeQuery("SELECT " + Columna.ID + ", " + Columna.RESERVA_FECHACADUCAR + " FROM " + Tabla.RESERVA + " WHERE " + Columna.RESERVA_OFERTA + " = " + oferta.getId());/** Conseguimos el id y la fecha de caducacion si tiene la oferta tiene una reserva*/
		
		while(rs.next()) {/** Conseguimos los datos para poder añadir las reservas*/
			Integer idReserva = rs.getInt(Columna.ID.toString());
			Date date = rs.getDate(Columna.RESERVA_FECHACADUCAR.toString());
			oferta.reservas.add(idReserva);
			if(date == null) {
				oferta.reservaActiva = idReserva;
			}
		}
		
		rs.close();
		stmt.close();
		con.close(); /**Cerramos la conexion con la base de datos */
		oferta.cargarOpiniones();
		
		return oferta;
	}

}

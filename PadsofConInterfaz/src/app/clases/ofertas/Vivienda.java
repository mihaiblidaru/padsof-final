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

/**
 * Esta clase nos sirve para trabajar con las ofertas vacacionales
 * 
 * @author Mihai Blidaru 
 * @author Sergio Dominguez
 *
 */
public class Vivienda extends Oferta {
	
	/**
	 * La comision de una oferta de tipo Vivienda
	 */
	private static final Float PORCENTAJE_COMISION = 0.1f;
	
	/**
	 * numero de meses de la oferta
	 */
	public int numMeses;
		

	/**
	 * Constructor de la oferta de vivienda
	 * @param id id de la oferta
	 * @param fechaInicio fecha de inicio de la oferta
	 * @param numMeses numero de meses de la oferta
	 * @param precio precio de la oferta
	 * @param fianza fianza de la oferta
	 * @param descripcion descripcion de la oferta
	 * @param idinmueble  id del inmueble de la oferta
	 * @param estado estado de la oferta
	 */
	public Vivienda(int id, LocalDate fechaInicio, int numMeses, float precio, float fianza, String descripcion, int idinmueble, Estado estado) {
		super(id, fechaInicio, precio, fianza, descripcion, idinmueble, estado);
		if(numMeses < 1) {
			throw new IllegalArgumentException("Número de meses menor que 1");
		}
		
		this.numMeses  = numMeses;
		
	}

	/**
	 * Devuelve el numero de meses
	 * @return Numero de meses de esta oferta
	 */
	public Integer getNumMeses() {
		return numMeses;
	}

	/**
	 * Cambia el número de meses de una oferta de tipo Vacacional. Cambia el valor
	 * de este objeto y también realiza el cambio dentro de la base de datos
	 * @param numMeses numero de meses de la oferta
	 */
	public boolean setNumMeses(int numMeses) throws OfertaNoModificableException {
		if(numMeses < 1) {
			throw new IllegalArgumentException("Número de meses menor que 1");
		}
		if(!this.getEstado().editable() ) {
			throw new OfertaNoModificableException(this); 
		} 
		
		
		try {
			DBManager.updateField(Tabla.OFERTA, Columna.OFERTA_NUM_MESES, this.id, numMeses);
			this.numMeses = numMeses;
			return true;
		}catch(SQLException e) {
			
		}
		return false;
	}

	
	/**
	 * Devuelve la comision de la oferta del 0,1%
	 * @return PORCENTAJE_COMISION el porcentaje
	 */
	public double getComision() {
		return PORCENTAJE_COMISION;
	}

	/** 
	 * Carga una oferta de vivienda de la base de datos
	 * @param id id de la oferta
	 * @return oferta la oferta que se ha creado 
	 * @throws SQLException si falla el SQL
	 */
	public static Vivienda cargarVivienda(int id) throws SQLException {
		Connection con = DBManager.openDbConnection();/**Establecemos la conexion de la base de datos */
		String queryVacacional = "SELECT * FROM " + Tabla.OFERTA + " WHERE " + Columna.ID + " = " + id;/**Buscamos la vivienda con el id dado*/
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(queryVacacional);
		Vivienda oferta = null;
		if(rs.next()) { /**Vamos consiguiendo los datos para el constructor de la oferta */
			LocalDate fechaInicio = rs.getDate(Columna.OFERTA_FECHAINICIO.toString()).toLocalDate();
			Integer numMeses = rs.getInt(Columna.OFERTA_NUM_MESES.toString());
			Float precio = rs.getFloat(Columna.OFERTA_PRECIO.toString());
			Float fianza = rs.getFloat(Columna.OFERTA_FIANZA.toString());
			String descripcion = rs.getString(Columna.OFERTA_DESCRIPCION.toString());
			Integer idInmueble = rs.getInt(Columna.OFERTA_INMUEBLE.toString());
			Estado estado = Estado.fromString(rs.getString(Columna.OFERTA_ESTADO.toString()));
			oferta = new Vivienda(id,fechaInicio,numMeses,precio,fianza,descripcion,idInmueble, estado);
			
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
		con.close();/**Cerramos la conexion con la base de datos */
		oferta.cargarOpiniones();
		return oferta;
	}
	
	/**
	 * Pasa toda la informacion a la cadena
	 */
	@Override
	public String toString() {
		return "Vivienda [numMeses=" + numMeses + "]" + super.toString();
	}
	
}

package app.clases.inmueble;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.clases.data.Columna;
import app.clases.data.DBManager;
import app.clases.data.Tabla;

/**
 * Guarda datos de los inmuebles que utilizara
 * la oferta
 *  
 * @author Mihai Blidaru Sergio Dominguez
 *
 */

public class Inmueble {

	/**
	 * Id del inmueble 
	 */
	private final int id;
	
	/**
	 * Localidad del inmueble
	 */
	private String localidad;
	
	/**
	 * Codigo postal del inmueble 
	 */
	private int codigoPostal;
	
	/**
	 * Direccion del inmueble
	 */
	private String direccion;

	/**
	 * Conjunto de ids de ofertas que se han hecho del inmueble
	 */
	private List<Integer> ofertas =  new ArrayList<>();
	
	/**
	 * Id del ofertante que ha añadido el inmueble al sistema
	 */
	private Integer ofertante;
	
	/**
	 * Conjunto de caracteristicas del inmueble
	 */
	private List<Caracteristica> caracteristicas;
	
	/**
	 * Carga un inmueble que tenemos en la base de datos
	 * @param id id del inmueble
	 * @return inmueble el inmueble que tenemos en la base de datos
	 * @throws SQLException si hay problemas con el SQL
	 */
	public static Inmueble cargarInmueble(int id) throws SQLException {
		Connection con = DBManager.openDbConnection();/**Establecemos la conexion con la base de datos */
		String queryInmueble = "SELECT * FROM " + Tabla.INMUEBLE + " WHERE " + Columna.ID + " = " + id;/** Conseguimos el inmueble con el id deseado */
		String queryCaracteristicas = "SELECT ID from caracteristica where inmueble = " + id; /** Conseguimos las caracteristicas del inmueble */
		String queryOfertas = "SELECT ID from oferta where id = " + id; /** Conseguimos el id de la oferta */
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(queryInmueble);
		Inmueble inmueble = null;
		if(rs.next()) {
			String localidad = rs.getString(Columna.INMUEBLE_LOCALIDAD.toString()); /** Conseguimos la localidad del inmueble */
			int codigoPostal = rs.getInt(Columna.INMUEBLE_CP.toString()); /** Conseguimos el codigo postal del inmueble */
			String dirreccion = rs.getString(Columna.INMUEBLE_DIRECCION.toString()); /** Conseguimos la direccion del inmueble */
			Integer ofertante = rs.getInt(Columna.INMUEBLE_CLIENTE.toString()); /** Conseguimos el id del ofertante del inmueble */
			
			rs = stmt.executeQuery(queryCaracteristicas);
			List<Caracteristica> caracteristicas= new ArrayList<>(); /**Vamos a cargar las caracteristicas del inmueble */
			while(rs.next()) {
				int idCaracteristica = rs.getInt(Columna.ID.toString()); /** Conseguimos la id de la caracteristica */
				caracteristicas.add(Caracteristica.cargarCaracteristica(idCaracteristica)); /** Añadimos la caracteristica al inmueble*/
			}

			inmueble = new Inmueble(id, localidad, codigoPostal, dirreccion, ofertante, caracteristicas);
			
			rs = stmt.executeQuery(queryOfertas);
			while(rs.next()) {
				int idOferta = rs.getInt(Columna.ID.toString()); /**Vamos cargando id de las ofertas del inmueble */
				inmueble.ofertas.add(idOferta); /** Añadimos las ofertas al inmueble */
			}
		}
		rs.close();
		stmt.close();
		con.close(); /** Cerramos la conexion*/
		return inmueble;
	}
	
	
	/**
	 * Constructor de Inmueble
	 * @param id id del inmueble
	 * @param localidad localidad del inmueble
	 * @param codigoPostal codigo postal del inmueble
	 * @param direccion direccion del inmueble
	 * @param ofertante id del ofertante
	 * @param caracteristicas2 caracteristicas del inmueble
	 */
	public Inmueble(int id, String localidad, int codigoPostal, String direccion, Integer ofertante, List<Caracteristica> caracteristicas2){
		this.id = id;
		this.localidad = localidad;
		this.codigoPostal = codigoPostal;
		this.direccion = direccion;
		this.ofertante = ofertante;
		this.caracteristicas = new ArrayList<>(caracteristicas2);
	}
	
	/**
	 *  Devuelve la localidad del inmueble
	 * @return localidad localidad del inmueble
	 */
	public String getLocalidad() {
		return localidad;
	}
	
	/**
	 * Devuelve el id de la inmueble
	 * @return id id del inmueble
	 */
	public int getId() {
		return this.id;
	}


	/**
	 *  Devuelve el codgio postal del inmueble
	 * @return codigo postal del inmueble
	 */
	public int getcodigoPostal() {
		return codigoPostal;
	}
	
	
	/**
	 *  Devuelve el id del ofertante del inmueble
	 * @return ofertante id del ofertante del inmueble
	 */
	public Integer getOfertante() {
		return ofertante;
	}
	
	
	/**
	 *  Devuelve la lista de oferta del inmueble
	 * @return ofertas ofertas del inmueble
	 */
	public List<Integer> getOfertas(){
		return ofertas;
	}
	

	@Override
	/**
	 * Pasa toda la informacion del inmueble a una cadena, incluyendo las ofertas y las caracteristicas
	 */
	public String toString() {
		return "Inmueble [id=" + id + ", localidad=" + localidad + ", codigoPostal=" + codigoPostal + ", direccion="
				+ direccion + "," + ofertas.size() +  " ofertas activas, caracteristicas=" + caracteristicas + "]";
	}


	/**
	 *  Devuelve la lista de caracteristicas del inmueble
	 * @return caracteristicas caracteristicas del inmueble
	 */
	public List<Caracteristica> getCaracteristica() {
		return caracteristicas;
	}

	/**
	 * Añade una oferta a la lista de ofertas del inmueble
	 * @param oferta id de la oferta a añadir
	 */
	public void addOferta(Integer oferta) {
		this.ofertas.add(oferta);
	}

	/**
	 * Devuelve la direccion del inmueble
	 * @return direciion direccion del inmueble
	 */
	public String getDireccion() {
		return this.direccion;
	}
}
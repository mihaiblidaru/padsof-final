package app.clases.users;

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
 * Esta clase nos sirve para trabajar con los ofertantes
 * 
 * @author Mihai Blidaru 
 * @author Sergio Dominguez
 *
 */
public class Ofertante extends Cliente {

	/**
	 * El importe pendiente del ofertante
	 */
	private float importePendiente;
	/**
	 * lista de ids de inmuebles del ofertante
	 */
	private List<Integer> inmuebles =  new ArrayList<>();
	
	/**
	 * Esta funcion carga un ofertante de la base de datos
	 * @param id id del ofertante
	 * @return ofertante ofertante que cargamos
	 * @throws SQLException si falla el SQL
	 */
	public static Ofertante cargarOfertante(int id) throws SQLException {
		Connection con = DBManager.openDbConnection();/**Establecemos la conexion de la base de datos */
		String queryOfertante = "Select * from cliente where (rol='O' or rol = 'OD') and id=" + id;/**Buscamos el demandante con el id dado*/
		
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(queryOfertante);
		Ofertante ofertante = null;
		if(rs.next()) { /**Vamos consiguiendo los datos para el constructor de la oferta */
			String contrasenya = rs.getString(Columna.CLIENTE_PASSWORD.toString());
			String identificador = rs.getString(Columna.CLIENTE_IDENTIFICADOR.toString());
			String ccard = rs.getString(Columna.CLIENTE_CCARD.toString());
			String nombre = rs.getString(Columna.CLIENTE_FULL_NAME.toString());
			float importePendiente = rs.getFloat(Columna.CLIENTE_IMPORTE_PENDIENTE.toString());
			boolean problemaPagos = rs.getBoolean(Columna.CLIENTE_PROBLEMA_PAGOS.toString());
			ofertante = new Ofertante(id, identificador, contrasenya, ccard, nombre, importePendiente, problemaPagos);
			String queryInmuebles = "Select id from inmueble where cliente="+id; /** Conseguimos los inmuebles del ofertante*/
			rs = stmt.executeQuery(queryInmuebles);
			while(rs.next()) {
				int idInmueble = rs.getInt(Columna.ID.toString());
				ofertante.inmuebles.add(idInmueble);
			}
		}
		rs.close();
		stmt.close();
		con.close();
		return ofertante;
	}

	/**
	 * Constructor del Ofertante
	 * @param id id del ofertante
	 * @param identificador identificador del ofertante
	 * @param contrasenya contrasenya del ofertante
	 * @param ccard tarjeta de credito del ofertante
	 * @param nombre nombre del ofertante
	 * @param importePendiente importe pendiente del ofertante
	 * @param problemaPagos problema de pagos del ofertante
	 */
	public Ofertante(int id, String identificador, String contrasenya,String ccard, String nombre, float importePendiente, boolean problemaPagos) {
		super(id, identificador, contrasenya, ccard, nombre, problemaPagos);
		this.importePendiente = importePendiente;
		
	}


	/**
	 * Devuelve el importe pendiente
	 * @return importePendiente importe pendiente
	 */
	public double getImportePendiente() {
		return importePendiente;
		
	}

	/**
	 * Cambia el importe pendiente
	 * @param importePendiente nuevo importe pendiente
	 * @return true si ha cambiado el atributo o false en caso contrario
	 */
	public boolean setImportePendiente(float importePendiente) {
		try {
			DBManager.updateField(Tabla.CLIENTE, Columna.CLIENTE_IMPORTE_PENDIENTE, this.getId(), importePendiente);/**Actualizamos la base de datos */
			this.importePendiente = importePendiente;
			setProblemaPagos(false);
			return true;
		}catch(SQLException e) {}
		return false;
	}
	
	/**
	 * Añade un importe pendiente
	 * @param importePendiente importe a añadir
	 * @return true si ha cambiado el atributo o false en caso contrario
	 */
	public boolean addImportePendiente(float importePendiente) {
		float nuevo = this.importePendiente + importePendiente;
		return this.setImportePendiente(nuevo);
	}
	
	/**
	 * Devuelve la lista de inmuebles del ofertante
	 * @return inmuebles inmuebles del ofertante
	 */
	public List<Integer> getInmuebles(){
		return inmuebles;
	}
	
	
	
	/**
	 * Añade un inmueble a la lista de inmuebles
	 * @param inmueble id del inmueble que añadimos
	 */
	public void asignarInmueble(Integer inmueble) {
		this.inmuebles.add(inmueble);
	}

}
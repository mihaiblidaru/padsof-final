package app.clases.users;

import app.clases.data.Columna;
import app.clases.data.DBManager;
import app.clases.data.Tabla;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase nos sirve para trabajar con los demandantes
 * 
 * @author Mihai Blidaru Sergio Dominguez
 *
 */
public class Demandante extends Cliente{

	/**
	 * Id de la oferta reservada
	 */
	private Integer idReservaActiva;
	/**
	 * Lista de ids de las ofertas contratadas por el demandante
	 */
	private List<Integer> ofertasContratadas = new ArrayList<>();
	
	/**
	 * Carga un demandante de la base de datos
	 * @param id id del demandante
	 * @return demandante el demandante creado
	 * @throws SQLException si falla el SQL
	 */
	public static Demandante cargarDemandante(int id) throws SQLException {
		Connection con = DBManager.openDbConnection();/**Establecemos la conexion de la base de datos */
		String queryDemandante = "Select * from cliente where (rol='D' or rol = 'OD') and id=" + id; /**Buscamos el demandante con el id dado*/
		
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(queryDemandante);
		Demandante demandante = null;
		if(rs.next()) {/**Vamos consiguiendo los datos para el constructor de la oferta */
			String contrasenya = rs.getString(Columna.CLIENTE_PASSWORD.toString());
			String identificador = rs.getString(Columna.CLIENTE_IDENTIFICADOR.toString());
			String ccard = rs.getString(Columna.CLIENTE_CCARD.toString());
			String nombre = rs.getString(Columna.CLIENTE_FULL_NAME.toString());
			boolean problemaPagos = rs.getBoolean(Columna.CLIENTE_PROBLEMA_PAGOS.toString());
			demandante = new Demandante(id, identificador, contrasenya, ccard, nombre, problemaPagos);
			String queryOfertasContratadas = "Select id from oferta where demandante=" + id;
			rs = stmt.executeQuery(queryOfertasContratadas);
			while(rs.next()) {/**Vamos consiguiendo los datos para las ofertas contratadas*/
				int idOferta = rs.getInt(Columna.ID.toString());
				demandante.ofertasContratadas.add(idOferta);
			}
			
			rs = stmt.executeQuery("Select " + Columna.ID + " from " + Tabla.RESERVA + " where " + Columna.RESERVA_CLIENTE + 
					" = " + id + " and " + Columna.RESERVA_FECHACADUCAR + " is null");
			if(rs.next()) {
				Integer idReserva = rs.getInt(Columna.ID.toString());
				demandante.idReservaActiva = idReserva;
			}
		}
		rs.close();
		stmt.close();
		con.close();
		return demandante;
	}
	
	/**
	 * Constructor del demandante
	 * @param id id del demandante
	 * @param identificador identificador del demandante
	 * @param contrasenya contrasenya del demandante
	 * @param ccard tarjeta de credito del demandante
	 * @param nombre nombre del demandante
	 * @param problemaPagos problema de pagos del demandante
	 */
	public Demandante(int id, String identificador, String contrasenya, String ccard, String nombre, boolean problemaPagos) {
		super(id, identificador, contrasenya, ccard, nombre, problemaPagos);
		
	}
	
	/**
	 * Devuelve la ista de ofertas contratadas
	 * @return this.ofertasContratadas lista de ofertas contratadas
	 */
	public List<Integer> getOfertasContratadas() {
		return this.ofertasContratadas;
	}
	
	/**
	 * Devuelve el id de la reserva activa del demandante
	 * @return idReservaActiva id de la reserva activa del demandante
	 */
	public Integer getReservaActiva() {
		return idReservaActiva;
	}

	/**
	 * Cambia la reserva activa del demandante
	 * @param idReservaActiva id de la reserva
	 */
	public void setReservaActiva(Integer idReservaActiva) {
		this.idReservaActiva = idReservaActiva;
	}

	/**
	 * Añade el id de una oferta contratada a la lista de contratadas
	 * @param oferta id de la oferta contratada
	 */
	public void addOfertaContratada(Integer oferta) {
		this.ofertasContratadas.add(oferta);
	}
}
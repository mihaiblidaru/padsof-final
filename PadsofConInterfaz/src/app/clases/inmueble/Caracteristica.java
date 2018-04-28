package app.clases.inmueble;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import app.clases.data.Columna;
import app.clases.data.DBManager;
import app.clases.data.Tabla;

/**
 * Esta clase nos permite trabajar con las caracteristicas
 * 
 * @author Mihai Blidaru Sergio Dominguez
 *
 */
public class Caracteristica {

	
		/** 
		* Id de la caracteristica
		*/
		private final int id;
		
		/**
		 * La key o de la caracteristica, por ejemplo piscina
		 */
		private final String key;
		
		/**
		 * El valor de la caracteristica, será si o no
		 */
		private final String valor;
		
		/**
		 * Constructor de Caracteristica
		 * @param id id de la caracteristica
		 * @param key clave de la caracteristica
		 * @param valor valor de la caracteristica
		 */
		public Caracteristica(int id, String key, String valor) {
			if(key == null || valor == null) {
				throw new IllegalArgumentException();
			}
			
			
			this.id = id;
			this.key = key;
			this.valor = valor;
		}

		/**
		 * Devuelve la key de la caracteristica
		 * @return key key de la caracteristica
		 */
		public String getKey() {
			return key;
		}

		
		/**
		 * Devuelve el valor de la caracteristica
		 * @return valor valor de la caracteristica
		 */
		public String getValor() {
			return valor;
		}


		
		/**
		* Pasa toda la informacion de la caracteristica a una cadena
		*/
		@Override
		public String toString() {
			return "[" + key + " = " + valor + "]";
		}

		/**
		 * Esta funcion carga una caracteristica de la base de datos, dado su id
		 * @param id id de la caracteristica
		 * @return caracteristica caracteristica cargada
		 * @throws SQLException si falla el SQL
		 */
		public static Caracteristica cargarCaracteristica(int id) throws SQLException {
			Connection con = DBManager.openDbConnection(); /**Establecemos la conexion */
			String query = "SELECT * FROM " + Tabla.CARACTERISTICA + " WHERE " + Columna.ID + " = " + id;/** Buscamos la caracteristica con el id dado*/
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			Caracteristica caracteristica = null;
			if(rs.next()) { /** Conseguimos la clave y el valor de la caracteristica y creamos la caracteristica*/ 
				String clave = rs.getString(Columna.CARACTERISTICA_CLAVE.toString());
				String valor = rs.getString(Columna.CARACTERISTICA_VALOR.toString());
				caracteristica = new Caracteristica(id, clave, valor);
			}
			rs.close();
			stmt.close();
			con.close(); /** Cerramos la conexion*/
			return caracteristica;
		}

		
		/**
		 * Devuelve el id de la caracteristica
		 * @return el id de la caracteristica
		 */
		public int getId() {
			return id;
		}

}
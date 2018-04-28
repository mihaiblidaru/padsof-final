package app.clases.data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Enumeracion de las tablas disponibles en la base de datos.
 * Cada tabla tiene asociada una serie de columnas.
 * Sirve para comprobar errores dentro del gestor de baes de datos.
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public enum Tabla {
	CLIENTE(Columna.ID,	Columna.CLIENTE_IDENTIFICADOR, Columna.CLIENTE_FULL_NAME, Columna.CLIENTE_PASSWORD,
			Columna.CLIENTE_IMPORTE_PENDIENTE,	Columna.CLIENTE_CCARD, Columna.CLIENTE_ROL, Columna.CLIENTE_PROBLEMA_PAGOS),

	INMUEBLE(Columna.ID, Columna.INMUEBLE_LOCALIDAD, Columna.INMUEBLE_CP, Columna.INMUEBLE_DIRECCION,
			Columna.INMUEBLE_CLIENTE),
	
	OFERTA(Columna.ID, Columna.OFERTA_FECHAINICIO, Columna.OFERTA_FECHAFIN, Columna.OFERTA_NUM_MESES, Columna.OFERTA_INMUEBLE,
			Columna.OFERTA_DESCRIPCION, Columna.OFERTA_ESTADO, Columna.OFERTA_FIANZA,
			Columna.OFERTA_PRECIO, Columna.OFERTA_TIPO, Columna.OFERTA_DEMANDANTE, Columna.OFERTA_RESERVA),
	
	CARACTERISTICA(Columna.ID, Columna.CARACTERISTICA_CLAVE, Columna.CARACTERISTICA_VALOR, Columna.CARACTERISTICA_INMUEBLE),
	
	OPINION(Columna.ID, Columna.OPINION_VALOR, Columna.OPINION_USUARIO,	Columna.OPINION_OFERTA,	
			Columna.OPINION_PADRE, Columna.OPINION_COMENTARIO, Columna.OPINION_FECHA),
	
	RESERVA(Columna.ID, Columna.RESERVA_FECHAINICIO, Columna.RESERVA_CLIENTE, Columna.RESERVA_OFERTA, Columna.RESERVA_FECHACADUCAR);
	
	/**
	 * Columna considerada clave primaria dentro de la tabla
	 */
	private Columna pkey;
	
	/**
	 * Lista de las columnas de la tabla
	 */
	private Set<Columna> cols;
	
	
	/**
	 * Crea una nueva tabla guarda las columnas que tiene
	 * 
	 * @param pkey Clave primaria
	 * @param columnas Demas columnas
	 */
	private Tabla(Columna pkey, Columna... columnas) {
		this.pkey = pkey;
		this.cols = new HashSet<Columna>(Arrays.asList(columnas));
	}
	
	/**
	 * Prueba si una columna pertenece a una tabla 
	 * 
	 * @param col Columna que se quiere comprobar
	 * @return true si la columna pertenece a la tabla o false en caso contrario
	 */
	public boolean hasColumn(Columna col) {
		return (col == pkey || cols.contains(col));
	}
	
	/**
	 * Devuelve el nombre de la tabla en minusculas
	 */
	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
	
}


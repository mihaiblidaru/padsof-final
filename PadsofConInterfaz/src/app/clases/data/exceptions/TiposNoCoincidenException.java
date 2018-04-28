package app.clases.data.exceptions;

import app.clases.data.Columna;

/**
 * Esta excepcion se lanza si los tipos de datos no coinciden
 * @author Mihai Blidaru Sergio Dominguez
 *
 */
public class TiposNoCoincidenException extends RuntimeException {
	
	private static final long serialVersionUID = 504200148661818905L;
	
	/**
	 * Columna cuyo tipo no coincide
	 */
	private Columna columna;
	
	/**
	 * Objeto cuyo tipo no coincide
	 */
	private Object object;
	
	/** 
	 * Constructor de la excepcion
	 * @param columna columna cuyo tipo no coincide
	 * @param object Objeto cuyo tipo no coincide
	 */
	public TiposNoCoincidenException(Columna columna, Object object) {
		super();
		this.columna = columna;
		this.object = object;
	}
	
	@Override
	/**
	 * Pasa la excepcion a una cadena
	 */
	public String toString() {
		return "El objeto {" + object.toString() + "} no coincide con el tipo de la columna " +
				columna.toString() + " = " + columna.getType().getName(); 
	}


}

package app.clases.data.exceptions;
import app.clases.data.Tabla;
import app.clases.data.Columna;

/**
 * Esta excepcion se lanza si la tabla no tiene una columna que buscamos
 * @author Mihai Blidaru 
 * @author Sergio Dominguez
 *
 */
public class TablaNoTieneColumnaException extends RuntimeException {


	private static final long serialVersionUID = -348632087748469868L;
	
	/**
	 * La tabla a la que le falta la columna
	 */
	private Tabla t;
	
	/**
	 * Columna que falta en la tabla
	 */
	private Columna c;
	
	/**
	 * Constructor de la excepcion
	 * @param t tabla a la que le falta la columna
	 * @param c columna que falta
	 */
	public TablaNoTieneColumnaException(Tabla t, Columna c) {
		this.t = t;
		this.c = c;
	}

	/** 
	 * Pasa la excepcion a una cadena
	 */
	public String toString() {
		return "La tabla " + t + " no tiene la columna " + c;
	}

}

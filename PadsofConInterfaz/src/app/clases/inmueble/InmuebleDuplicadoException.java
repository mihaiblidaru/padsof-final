package app.clases.inmueble;

/**
 * Esta excepcion se lanza si el inmueble esta duplicado
 *  
 * @author Mihai Blidaru Sergio Dominguez
 *
 */
public class InmuebleDuplicadoException extends Exception {

	private static final long serialVersionUID = -7295626645940395127L;
	
	/** 
	 * Id del inmueble duplicado
	 */
	private final int idDuplicado;

	/**
	 * Constructor de la excepcion
	 * @param idDuplicado id del inmueble duplicado
	 */
	public InmuebleDuplicadoException(int idDuplicado) {
		this.idDuplicado = idDuplicado;
	}

	@Override
	/**
	 * Pasa la excepcion a una cadena
	 */
	public String toString() {
		return "Ya tienes un inmueble con esos datos: [idDuplicado=" + idDuplicado + "]";
	}
	
}

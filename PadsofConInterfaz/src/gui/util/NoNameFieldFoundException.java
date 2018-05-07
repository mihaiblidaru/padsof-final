package gui.util;

/**
 * Esta es nuestra excepcion que salta cuando un campo de la interfaz grafica no tiene un campo llamado NAME
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class NoNameFieldFoundException extends RuntimeException {

	/**
	 * Nombre de la clase
	 */
	private String className;

	private static final long serialVersionUID = 1949577404334944372L;

	/**
	 * Constructor de la excepcion
	 * @param className nombre de la clase
	 */
	public NoNameFieldFoundException(String className) {
		super();
		this.className = className;
	}

	/**
	 * Pasa la excepcion a una string
	 */
	@Override
	public String toString() {
		return String.format(
				"NoNameFieldFoundException [className=%s] necesita un atributo no nulo de tipo String llamado NAME",
				className);
	}

}

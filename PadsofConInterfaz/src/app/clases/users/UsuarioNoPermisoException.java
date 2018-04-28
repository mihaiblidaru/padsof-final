package app.clases.users;

/**
 *  Esta excepcion se lanza cuando el usuario no tiene permisos para realizar una accion
 *  
 * @author Mihai Blidaru Sergio Dominguez
 *
 */
public class UsuarioNoPermisoException extends Exception {

	
	private static final long serialVersionUID = -6519631384023508543L;
	
	/**
	 * El permiso del usuario
	 */
	private final String permisionInfo;
	
	/**
	 * Constructor de la excepcion
	 * @param permisionInfo permiso del usuario
	 */
	public UsuarioNoPermisoException(String permisionInfo) {
		this.permisionInfo = permisionInfo;
	}
	
	/**
	 * Pasa la excepcion a una cadena
	 */
	public String toString() {
		return "No puedes realizar esta accion. Info: " + permisionInfo;		
	}
}

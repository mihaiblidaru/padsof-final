package app.clases.users;

/**
 *  Esta excepcion se lanza cuando el demandante ya tiene una reserva
 *  
 * @author Mihai Blidaru Sergio Dominguez
 *
 */
public class UsuarioYaTieneReservaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6703063671215114845L;

	/**
	 * El id de la reserva del demandante
	 */
	Integer idReserva = null;
	

	/**
	 * Constructor de la excepcion
	 * @param reservaActual id de la reserva del demandante
	 */
	public UsuarioYaTieneReservaException(Integer reservaActual) {
		this.idReserva = reservaActual;
	}
	
	public String toString() {
		return "El usuario  ya tiene una reserva con id " + idReserva ;  
	}
}

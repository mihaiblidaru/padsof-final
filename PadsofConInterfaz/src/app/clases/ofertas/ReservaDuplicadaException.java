package app.clases.ofertas;

/**
 * Esta excepcion se lanza si la oferta esta duplicada
 *  
 * @author Mihai Blidaru Sergio Dominguez
 *
 */
public class ReservaDuplicadaException extends Exception {
	
	private static final long serialVersionUID = 5091245375347724663L;
	
	/**
	 * Reserva duplicada
	 */
	private Reserva reserva;

	/**
	 * Constructor de la excepcion
	 * @param reserva reserva que esta duplicada
	 */
	public ReservaDuplicadaException(Reserva reserva) {
		this.reserva = reserva;
	}
	
	/**
	 * Pasa la excepcion a una cadena
	 */
	public String toString() {
		return "Ya has realizado una reserva antes: " + reserva; 
	}	

}

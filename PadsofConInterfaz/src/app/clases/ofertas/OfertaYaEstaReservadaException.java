package app.clases.ofertas;


/**
 * Esta excepcion se lanza si la oferta ya esta reservada
 *  
 * @author Mihai Blidaru Sergio Dominguez
 *
 */
public class OfertaYaEstaReservadaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7978682451889425066L;

	/**
	 * La oferta que esta reservada
	 */
	Oferta oferta = null;
	
	/**
	 * Consructor de la excepcion
	 * @param of oferta que esta reservada
	 */
	public OfertaYaEstaReservadaException(Oferta of) {
		this.oferta = of;
	}

	/**
	 * Pasa la excepcion a una cadena
	 */
	public String toString() {
		return "La oferta " + oferta +" ya esta reservada";
	}
	
}

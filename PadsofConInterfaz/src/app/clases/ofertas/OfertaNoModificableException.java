package app.clases.ofertas;

/**
 * Esta excepcion se lanza si la oferta no es modificable 
 *  
 * @author Mihai Blidaru Sergio Dominguez
 *
 */
public class OfertaNoModificableException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -936435539673710694L;
	
	/** 
	 * Oferta que comprobamos
	 */
	Oferta oferta = null;
	
	/** 
	 * Constructor de la excepcion
	 * @param oferta oferta que comprobamos
	 */
	public OfertaNoModificableException(Oferta oferta) {
		this.oferta = oferta;
	}
	
	/**
	 * Pasa la excepcion a una cadena
	 */
	public String toString() {
		return "La oferta " + oferta.getId() + " se encuentra en el estado " + oferta.getEstado() + " y no se puede modificar";
	}

}

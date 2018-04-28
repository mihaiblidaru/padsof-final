package app.clases.ofertas;

/**
 * Estados en los que se puede encontrar una oferta
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public enum Estado {
	/**
	 * En esta enumeracion definimos los distintos estados en los que puede estar la oferta
	 */
	PENDIENTE_DE_CAMBIOS(true),
	ACEPTADA(false),
	RESERVADA(false),
	CONTRATADA(false),
	PENDIENTE(true),
	RETIRADA(false);
	
	/** 
	 * Nos indica si la oferta es editable
	 */
	private boolean editable;
	
	/**
	 * Constructor de Estado
	 * @param editable nos indica si es editable
	 */
	private Estado(boolean editable) {
		this.editable = editable;
	}
	
	/**
	 * Devuelve el estado igual a la cadena que le pasamos por argumento, si el estado no existe devuelve null
	 * @param value String que queremos comparar
	 * @return devuelve el estado igual a la cadena que le  pasamos por argumento
	 */
	public static Estado fromString(String value) {
		Estado[] estadosPosibles = Estado.values();
		for(int i=0; i< estadosPosibles.length; i++) {
			if(value.equalsIgnoreCase(estadosPosibles[i].toString())) {
				return estadosPosibles[i];
			}
		}
		
		return null;
	}
	
	/**
	 * Nos devuelve si es editable o no
	 * @return editable si es editable o no
	 */
	public boolean editable() {
		return this.editable;
	}
	
}

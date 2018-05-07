package gui.util;

/**
 * Clase que sirver para poder hacer un paso por referencia
 * de tipos primitivos y objetos inmutables.
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 * @param <T>
 *            Tipo de dato contenido
 */
public class ParameterReference<T> {

	/**
	 * Valor pasado como parametro
	 */
	private T value;

	/**
	 * Cambia el valor del dato contenido
	 * 
	 * @param k
	 *            nuevo valor
	 */
	public void setValue(T k) {
		value = k;
	}

	/**
	 * Devuelve el valor contenido
	 * 
	 * @return el valor contenido
	 */
	public T getValue() {
		return value;
	}

	/**
	 * Crea un nuevo objeto de este tipo
	 * 
	 * @param value
	 *            valor inicial
	 */
	public ParameterReference(T value) {
		this.value = value;
	}

	/**
	 * Crea un nuevo objeto de este tipo
	 * 
	 */
	public ParameterReference() {

	}
}

package app.clases.opiniones;

import java.time.LocalDate;


/**
 * Esta clase nos sirve para trabajar con las opiniones numericas
 * 
 * @author Mihai Blidaru Sergio Dominguez
 *
 */
public class Numerica extends Opinion{
	/**
	 * Valor numerico de la opinion 
	 */
	private final int valor;
	
	/**
	 * Constructor de la clase
	 * @param id id de la opinion
	 * @param usuario id del usuario que realiza la opinion
	 * @param valor valor de la opinion
	 * @param fecha Fecha en la que se ha emitido la valoracion numerica
	 */
	public Numerica(int id, LocalDate fecha,  Integer usuario, Integer valor) {
		super(id, fecha, usuario);
		this.valor = valor;
	}
	
	/**
	 * Devuelve el valor numerico de la opinion
	 */
	public int getValor() {
		return this.valor;
	}
	
}
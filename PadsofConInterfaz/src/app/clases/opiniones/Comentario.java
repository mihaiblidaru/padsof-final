package app.clases.opiniones;

import java.time.LocalDate;

/**
 * Guarda datos de los comentarios que los usuarios 
 * publican en las ofertas.
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class Comentario extends Opinion{
	
	/**
	 * Texto que compone el comentario
	 */
	public final String texto;
		
	
	/**
	 * Id del comentario 'padre' de este comentario
	 */
	public Integer padre;
	
	
	/**
	 * Constructor de comentario. 
	 * @param padre id del comentario padre
	 * @param id id de la opinion
	 * @param fecha fehca en la que se pone el comentario 
	 * @param usuario id del usuario que ha escrito el comentario
	 * @param texto Texto que compone el comentario
	 */
	public Comentario(int id, LocalDate fecha, int usuario, String texto, Integer padre) {
		super(id, fecha, usuario);
		this.texto = texto;
		this.padre = padre;
	}
	
	
	/**
	 *  Devuelve el texto del comentario
	 * @return el texto del comentario
	 */
	public String getTexto() {
		return this.texto;
	}
	
	
	@Override
	public String toString() {
		return String.format("Comentario [id=%s, usuario=%s, texto=%s, padre=%s]", id, usuario, texto, padre);
	}
	
}
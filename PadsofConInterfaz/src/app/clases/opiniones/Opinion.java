package app.clases.opiniones;
import java.time.LocalDate;

/**
 *  Esta clase nos sirve para trabajar
 * @author Mihai Blidaru Sergio Dominguez
 * @version 06/03/2018
 *
 */
public abstract class Opinion {
	
	/**
	 * Fecha en la que se ha emitido la opinion
	 */ 
	private final LocalDate fecha;
	
	/** 
	 * Id de la opinion
	 */
	public final int id;
	
	/**
	 * Usuario que ha publicado la opinion.
	 */
	protected final int usuario;
	
	/**
	 * Constructor de Opinion
	 * @param id id de la opinion
	 * @param usuario id del usuario que hace la opinion
	 * @param fecha Fecha en la que se ha omitido la opinion
	 */
	public Opinion(int id, LocalDate fecha,int usuario) {
		this.usuario = usuario;
		this.fecha = fecha;
		this.id = id;
	}
	
	
	/**
	 * Devuelve la fecha en la que se ha publicado la opinion
	 * @return la fecha en la que se ha publicado la opinon 
	 */
	public LocalDate getFecha() {
		return this.fecha;
	}
	
	/**
	 * Devuelve el valor numerico de la opinion, se sobreescribe
	 * @return -1
	 */
	public int getValor() {
		return -1;
	}
	
	/**
	 * Devulve el id de la opinion
	 * @return id id de la opinion
	 */
	public int getId() {
		return id;
	}

	/**
	 * Devuelve el id del usuario que hace la opinion
	 * @return usuario id del usuario que hace la opinion
	 */
	public Integer getUsuario() {
		return usuario;
	}
		
}
package app.clases.users;


/**
 * Guarda la información del usuario
 * @author Mihai Blidaru Sergio Dominguez
 *
 */
public abstract class Usuario {
	/**
	 * Identificador del usuario, como, por ejemplo, el NIF.
	 */
	private final String identificador;
	
	/** 
	 * Id del usuario
	 */
	private final int id;
	
	/**
	 * Contraseña del usuario.
	 */
	private final String contrasenya;
	
	/**
	 * Constructor del Usuario
	 * @param identificador identificador del usuario
	 * @param contrasenya contrasenya del usuario
	 * @param id id del usuario
	 */
	public Usuario(String identificador, String contrasenya, int id) {
		this.identificador = identificador;
		this.contrasenya = contrasenya;
		this.id = id;
	}

	/**
	 * Devuelve el identificador del usuario
	 * @return identificador identificador del usuario
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * Devuelve el id del usuario
	 * @return id id del usuario
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Devuelve la contrasenya del usuario
	 * @return contrasenya contrasenya del usuario
	 */
	public String getContrasenya() {
		return contrasenya;
	}
	
	/**
	 * Pasa toda la informacion del usuario a una cadena
	 */
	public String toString() {
		String t;
		t = "ID: " + this.identificador + "Contraseña: " + contrasenya;
		
		return t;
	}
	
}
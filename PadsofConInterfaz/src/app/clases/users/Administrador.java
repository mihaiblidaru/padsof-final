package app.clases.users;

/**
 * Esta clase nos sirve para trabajar con el administrador
 * 
 * @author Mihai Blidaru Sergio Dominguez
 *
 */
public class Administrador extends Usuario {

	
	/**
	 * Usuario del admin
	 */
	public final static String adminLogin = "admin";
	
	/**
	 * Contrase�a del admin
	 */
	public final static String adminPassword = "admin";
	
	/**
	 * Constructor del administrador, la contrase�a y el usuario del admin son siempre los declarados arriba
	 */
	public Administrador() {
		super(adminLogin, adminPassword, 1);
	}
}
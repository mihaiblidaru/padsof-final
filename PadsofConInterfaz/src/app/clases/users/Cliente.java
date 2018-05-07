package app.clases.users;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import app.clases.MiVacaPiso;
import app.clases.data.Columna;
import app.clases.data.DBManager;
import app.clases.data.Tabla;
import es.uam.eps.padsof.telecard.TeleChargeAndPaySystem;

/**
 * Esta clase nos sirve para trabajar con los clientes
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public abstract class Cliente extends Usuario {

	/**
	 * tarjeta de credito del cliente
	 */
	private String ccard;

	/**
	 * Nombre del cliente
	 */
	private final String nombre;

	/**
	 * Nos informa de si hay problemas en los pagos
	 */
	private boolean problemaPagos;

	/**
	 * Constructor del cliente
	 * 
	 * @param id
	 *            id del cliente
	 * @param identificador
	 *            identificador del cliente
	 * @param contrasenya
	 *            contrasenya del cliente
	 * @param ccard
	 *            tarjeta de credito del cliente
	 * @param nombre
	 *            nombre del cliente
	 * @param problemaPagos
	 *            los problemas de pagos del cliente
	 */
	public Cliente(int id, String identificador, String contrasenya, String ccard, String nombre,
			boolean problemaPagos) {
		super(identificador, contrasenya, id);
		this.ccard = ccard;
		this.nombre = nombre;
		this.problemaPagos = problemaPagos;
	}

	/**
	 * Devuelve la tarjeta de credito del cliente
	 * 
	 * @return ccard tarjeta de credito del cliente
	 */
	public String getCcard() {
		return ccard;
	}

	/**
	 * Cambia la tarjeta de credito del cliente
	 * 
	 * @param ccard
	 *            nueva tarjeta de credito del cliente
	 * @return true si se cambia correctamente, si no false
	 * @throws UsuarioNoPermisoException
	 *             si el usuario no es el admin
	 * @throws SQLException
	 *             si falla el SQL
	 */
	public boolean setCcard(String ccard) throws UsuarioNoPermisoException, SQLException {
		if (MiVacaPiso.getInstance().getAdmin() == null) { /** Comprobamos que el cambio lo quiere hacer el admin */
			throw new UsuarioNoPermisoException("se necesitan permisos de administador");
		}
		if (!TeleChargeAndPaySystem.isValidCardNumber(ccard)) {
			return false;
		}

		try {
			DBManager.updateField(Tabla.CLIENTE, Columna.CLIENTE_CCARD, this.getId(), ccard);
			this.ccard = ccard;
			setProblemaPagos(false);
			return true;
		} catch (SQLException e) {

		}
		return false;
	}

	/**
	 * Devuelve el nombre del cliente
	 * 
	 * @return nombre nombre del cliente
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Devuelve el problema de pagos del cliente
	 * 
	 * @return problemaPagos los problemas de pagos del cliente
	 */
	public boolean isProblemaPagos() {
		return problemaPagos;
	}

	/**
	 * Cambia los problemas de pagos del cliente
	 * 
	 * @param problemaPagos
	 *            nuevos problemas de pagos del cliente
	 * @return true si se ha cambiado el atributo o false en caso contrario
	 */
	public boolean setProblemaPagos(boolean problemaPagos) {
		try {
			DBManager.updateField(Tabla.CLIENTE, Columna.CLIENTE_PROBLEMA_PAGOS, this.getId(),
					problemaPagos); /** Actualizamos la base de datos */
			this.problemaPagos = problemaPagos;
			return true;
		} catch (SQLException e) {

		}
		return false;
	}

	/**
	 * Devuelve el importe pendiente
	 * 
	 * @return importePendiente importe pendiente
	 */
	public double getImportePendiente() {
		return 0;
	};

	/**
	 * Añade un inmueble a la lista de inmuebles
	 * 
	 * @param inmueble
	 *            id del inmueble que añadimos
	 */
	public void asignarInmueble(Integer inmueble) {
	};

	/**
	 * Carga un usuario de la base de datos
	 * 
	 * @param id
	 *            id del usuario
	 * @return cliente cliente que se ha creado
	 * @throws SQLException
	 *             si falla el SQL
	 */
	public static Cliente cargarCliente(int id) throws SQLException {
		Connection con = DBManager.openDbConnection();/** Establecemos la conexion de la base de datos */
		Statement stmt = con.createStatement();
		Cliente cliente = null;
		String sql = "SELECT rol FROM " + Tabla.CLIENTE + " where id=" + id;/** Buscamos el cliente con el id dado */
		ResultSet rs = stmt.executeQuery(sql);

		if (rs.next()) {
			String rol = rs.getString(Columna.CLIENTE_ROL
					.toString()); /**
									 * Conseguimos el rol, dependiendo de e utilizaremos una funcion de carga u otra
									 */

			if (rol.equalsIgnoreCase("O")) {
				cliente = Ofertante.cargarOfertante(id);
			} else if (rol.equalsIgnoreCase("D")) {
				cliente = Demandante.cargarDemandante(id);
			} else if (rol.equalsIgnoreCase("OD")) {
				cliente = Ofertante.cargarOfertante(id);
			}
		}

		rs.close();
		stmt.close();
		con.close();
		return cliente;
	}

	/**
	 * Pasamos la informacion del vacacional a una cadena
	 */
	@Override
	public String toString() {
		return String.format("Cliente [Id=%s, Identificador=%s, ccard=%s, nombre=%s, problemaPagos=%s]", getId(),
				getIdentificador(), ccard, nombre, problemaPagos);
	}

}
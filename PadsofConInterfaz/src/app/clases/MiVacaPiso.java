package app.clases;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.clases.data.*;
import app.clases.inmueble.*;
import app.clases.ofertas.*;
import app.clases.opiniones.Comentario;
import app.clases.opiniones.Numerica;
import app.clases.opiniones.Opinion;
import app.clases.users.*;
import app.clases.utils.*;
import es.uam.eps.padsof.telecard.FailedInternetConnectionException;
import es.uam.eps.padsof.telecard.OrderRejectedException;
import es.uam.eps.padsof.telecard.TeleChargeAndPaySystem;

import java.sql.*;
import java.time.LocalDate;

/**
 * Esta es nuestra clase sistema, desde aqui trabajaremos con
 * las funciones a las que podremos llamar por terminal. Interconecta
 * todos los demas componentes necesarios para el correcto funcionamiento
 * de la aplicación.
 * 
 * @author Mihai Blidaru 
 * @author Sergio Dominguez
 *
 */
public class MiVacaPiso {
	
	/**
	 * Conjunto de ofertas del sistema
	 */
	private Map<Integer, Oferta> listaOfertas= new HashMap<>();
	
	/**
	 * Conjunto de usuarios del sistema
	 */
	private Map<Integer, Cliente> listaUsuarios= new HashMap<>();
	
	/**
	 * Conjunto de inmuebles del sistema
	 */
	private Map<Integer, Inmueble> listaInmuebles = new HashMap<>();
	
	/**
	 Lista de reservas presentes en el sistema 
	*/	
	private Map<Integer, Reserva> listaReservas = new HashMap<>();
	
	/**
	 * Usuario logueado como ofertante
	 */
	private Ofertante ofertanteLogueado;
	
	/**
	 * Usuario logueado como admin
	 */
	private Demandante demandanteLogueado;
	
	/**
	 * Usuario logueado como administrador del sistema
	 */
	private Administrador admin;
	
	/**
	 * Instancia de la aplicacion
	 */
	private static MiVacaPiso instance = null;
	
	/**
	 * Constructor privado del sistema
	 * @throws SQLException exepcion generada por el sistema de gestion de datos. No deberia ser capturada 
	 * 
	 */
	private MiVacaPiso() throws SQLException {
		if(DBManager.isDatabaseInitialized() == false) {
			DBManager.initializeDatabase();
		}			
	}
	
	
	/**
	 * La funcion comprueba que solo hay una instancia de la aplicacion
	 * @return instance la instancia de la app
	 * @throws SQLException exepcion generada por el sistema de gestion de datos. No deberia ser capturada
	 */
	public static MiVacaPiso getInstance() throws SQLException {
		if(MiVacaPiso.instance == null) {
			instance = new MiVacaPiso();
		}
		return instance;
	}
	

	/**
	 * Dado un id devuelve la Oferta relacionada con ese id.
	 * Si la oferta no se encuentra en el sistema, se encarga de cargarla y luego la devuelve
	 * @param id id de la oferta
	 * @return La oferta con ese id o null si no existe una oferta asociada a ese id
	 */
	public Oferta getOfertaById(int id) {
		if(!this.listaOfertas.containsKey(id)) {
			Oferta oferta = null;
			try {
				oferta = Oferta.cargarOferta(id);
				this.listaOfertas.put(id, oferta);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return oferta;
		}
		return this.listaOfertas.get(id);
	}
	
	
	/**
	 * Dado un id devuelve el Inmueble relacionada con ese id.
	 * Si el inmueble no se encuentra en el sistema, se encarga de cargarlo y luego lo devuelve
	 * @param id id del inmueble
	 * @return El inmueble con ese id o null si no existe un inmueble asociado a ese id
	 */
	public Inmueble getInmuebleById(int id) {
		if(!this.listaInmuebles.containsKey(id)) {
			Inmueble inmueble = null;
			try {
				inmueble = Inmueble.cargarInmueble(id);
				this.listaInmuebles.put(id, inmueble);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return inmueble;
		}
		return this.listaInmuebles.get(id);
	}
	
	
	/**
	 * Dado un id devuelve el Ciente relacionado con ese id.
	 * Si no se encuentra cargado en el sistema, lo carga. Si tiene doble rol
	 * lo carga como Ofertante
	 * @param id el id del cliente
	 * @return El cliente que tiene asociado ese id
	 */
	public Cliente getClienteById(int id) {
		if(!this.listaUsuarios.containsKey(id)) {
			try {
				this.listaUsuarios.put(id, Cliente.cargarCliente(id));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return this.listaUsuarios.get(id);
	}
	
	
	/**
	 * Dado un id devuelve la Reserva relacionada con ese id
	 * @param id el id de la reserva 
	 * @return La reserva que tiene ese id o null si no existe una reserva con ese id
	 */
	public Reserva getReservaById(Integer id) {
		if(!this.listaReservas.containsKey(id)) {
			Reserva reserva = null;
			try {
				reserva = Reserva.cargarReserva(id);
				this.listaReservas.put(id, reserva);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return reserva;
		}
		return this.listaReservas.get(id);
	}
	
	
	/**
	 * Cada vez que se inicia sesion en el sistema comprueba
	 * que reservas han caducado en funcion del dia actual, las
	 * marca como caducadas y cambia el estado de las ofertas afectadas 
	 * a ACEPTADA.
	 * 
	 * @throws SQLException Error de la base de datos. Puede suponer un error de conexion o de programacion
	 */
	private void actualizarReservas() throws SQLException {
		Connection con = DBManager.openDbConnection();
		String sql = "UPDATE reserva, oferta set reserva.fechaCaducar = DATE_ADD(reserva.fechaInicio, INTERVAL 5 DAY), oferta.estado = 'ACEPTADA' where reserva.oferta = oferta.id AND reserva.fechaCaducar is null and DATE_ADD(reserva.fechaInicio, INTERVAL 5 DAY) <= ?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setDate(1, Date.valueOf(FechaSimulada.getHoy()));
		stmt.execute();
		stmt.close();
		con.close();
	}
	

	/**
	 * Realiza una búsqueda ofertas.
	 *
	 * Es obligatorio especificar al menos la Localidad o el código postal.
	 * Es obligatorio fijar una fecha de inicio.
	 * El parametro fechaFin puede quedar a null, indicando que se quiere
	 * buscar una oferta que no tiene ese parametro, es decir, una oferta Vacacional
	 *
	 * @param localidad Localidad donde se encuentra el inmueble relacionado con la oferta
	 * @param cp Codigo postal de la localidad donde se encuentra el inmueble relacionado con la oferta
	 * @param fechaInicio Fecha de inicio de la oferta
	 * @param fechaFin Fecha final de la oferta
	 * @return La lista de los ids de las ofertas que coinciden con los criterios de búsqueda
	 */
	public List<Integer> buscarOfertas(String localidad, Integer cp, LocalDate fechaInicio, LocalDate fechaFin){
		int nullCount = 0;
		if(fechaInicio == null) {
			throw new IllegalArgumentException("El argumento fechaInicio tiene que ser especificado para realizar la busqueda");
		}
		
		if(localidad == null)
			nullCount++;
		if(cp==null)
			nullCount++;
		
		if(nullCount == 2) {
			throw new IllegalArgumentException("Tienes que especificar la localidad o el codigo postal");
		}
		
		String tipo = fechaFin==null ? "vivienda" : "vacacional";
				
		String sql = "Select o.id from oferta as o, inmueble as im where o.inmueble = im.id ";
	
		if(localidad != null) 
			sql += " AND im." + Columna.INMUEBLE_LOCALIDAD.toString() + "= ? ";
			
		if(cp != null) 
			sql += " AND im." + Columna.INMUEBLE_CP.toString() + "= ? ";
		
		if(fechaInicio != null) 
			sql += " AND " + Columna.OFERTA_FECHAINICIO.toString() + " >= ?";
		
		if(fechaFin != null)
			sql += " AND " + Columna.OFERTA_FECHAFIN.toString() + "<= ?";
		
		if(this.demandanteLogueado != null) {
			sql += " AND (" + Columna.OFERTA_ESTADO.toString() + " = '" + Estado.RESERVADA.toString() +"' OR ";
			sql += Columna.OFERTA_ESTADO.toString() + " = '" + Estado.ACEPTADA.toString() +"')";
		}else {
			sql += " AND " + Columna.OFERTA_ESTADO.toString() + " = '" + Estado.ACEPTADA.toString() +"'";
		}
		
		sql += " AND tipo = '" + tipo + "' order by fechaInicio";
                                                                                                                
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Integer> resultados = new ArrayList<>();
		try {
			con = DBManager.openDbConnection();
			stmt = con.prepareStatement(sql);
			int argCount = 1;
			
			if(localidad != null) {
				stmt.setString(argCount, localidad);
				argCount++;
			}
			
			if(cp != null) {
				stmt.setInt(argCount, cp);
				argCount++;
			}
			
			stmt.setDate(argCount, Date.valueOf(fechaInicio));
			argCount++;
			
			if(fechaFin != null) {
				stmt.setDate(argCount, Date.valueOf(fechaFin));
			}
			
			rs = stmt.executeQuery();
			 
			while(rs.next()) {
				Integer idOferta = rs.getInt(Columna.ID.toString());
				resultados.add(idOferta);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}finally {
			try {rs.close();} catch (SQLException e) {}
			try {stmt.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return resultados;
	}
	
	
	
	/**
	 * Hace login de usuarios. Comprueba sus datos y crea los objetos necesarios
	 * dentro del sistema para operar dependiendo del rol que tiene cada usuario.
	 * 
	 * @param identificador identificador del usuario
	 * @param contrasenya contraseña del usuario
	 * @return El rol con el que se ha logueado el usuario o null si los datos son incorrectos
	 * @throws SQLException error generado por el Driver del SGBD. Puede indicar un error de connexion o de programacion.
	 */
	public Rol login(String identificador, String contrasenya) throws SQLException {
		this.listaOfertas.clear();
		actualizarReservas();
		
		if(identificador.equals(Administrador.adminLogin) && contrasenya.equals(Administrador.adminPassword)) {
			admin = new Administrador();
			return Rol.A;
		}
		Rol retVal = null;
		Connection con=null;
		Statement stmt = null;
		ResultSet rsUsers = null;				
		try {
			con = DBManager.openDbConnection();
			stmt = con.createStatement();
			String sql = "SELECT id, rol FROM " + Tabla.CLIENTE + " where " + Columna.CLIENTE_IDENTIFICADOR + " = '" + identificador +
					"' and " + Columna.CLIENTE_PASSWORD + "='" + contrasenya + "'";
			rsUsers = stmt.executeQuery(sql);
		
			
			if(rsUsers.next()) {
				String rol = rsUsers.getString(Columna.CLIENTE_ROL.toString()); 
				int id = rsUsers.getInt(Columna.ID.toString());
		
				if(rol.equalsIgnoreCase("O")) {
					this.ofertanteLogueado = Ofertante.cargarOfertante(id);
					retVal = Rol.O;
				}else if(rol.equalsIgnoreCase("D")) {
					this.demandanteLogueado = Demandante.cargarDemandante(id);
					retVal = Rol.D;
				}else if(rol.equalsIgnoreCase("OD")) {
					this.ofertanteLogueado = Ofertante.cargarOfertante(id);
					this.demandanteLogueado = Demandante.cargarDemandante(id);
					retVal = Rol.OD;
				}else {
					System.out.println("Problema en base de datos");
				}
			} 
			con.close();
			return retVal;
		} catch (SQLException e) {
			try {rsUsers.close();} catch (SQLException e1) {}
			try {stmt.close();} catch (SQLException e1) {}
			try {con.close();} catch (SQLException e1) {}
		}
		return retVal;
	}
	
	
	/**
	 * Devuelve el usuario logueado en el sistema como Ofertante
	 * si tiene asociado ese rol.
	 * @return el usuario logueado en el sistema como Ofertantes
	 */
	public Ofertante getOfertanteLogueado() {
		return ofertanteLogueado;
	}

	
	/**
	 * Devuelve el usuario logueado en el sistema como Demandante
	 * si tiene asociado ese rol.
	 * @return el usuario logueado en el sistema como Demandante
	 */
	public Demandante getDemandanteLogueado() {
		return demandanteLogueado;
	}

	/**
	 * Devuelve el usuario logueado como Administrados
	 * si este tiene los permisos necesarios
	 * @return el administrador del sistema
	 */
	public Administrador getAdmin() {
		return admin;
	}
	

	
	/**
	 * Añade una oferta de tipo Vivienda
	 * @param fechaInicio Fecha de inicio de la oferta
	 * @param n_meses número de meses de la oferta
	 * @param precio precio de la oferta
	 * @param fianza fianza de la oferta
	 * @param descripcion descripcion de la oferta
	 * @param idInmueble id del inmueble sobre el cual se quiere añadir la oferta
	 * @return El id de la oferta añadida o null si no se puede añadir
	 * @throws UsuarioNoPermisoException ocurre cuando el usuario no tiene permisos suficientes para realizar esta accion
	 */
	public Integer addOferta(LocalDate fechaInicio, int n_meses, float precio, float fianza, String descripcion, Integer idInmueble) throws UsuarioNoPermisoException {
		if(ofertanteLogueado == null) {
			throw new UsuarioNoPermisoException("se necesitan permisos de ofertante");
		}
		
		if(!ofertanteLogueado.getInmuebles().contains(idInmueble)) {
			throw new UsuarioNoPermisoException("No tienes registrado este inmueble: " + idInmueble);
		}		
		
		try {
			int id = DBManager.insertRow(Tabla.OFERTA, Arrays.asList(Columna.OFERTA_FECHAINICIO, Columna.OFERTA_NUM_MESES,
					Columna.OFERTA_PRECIO, Columna.OFERTA_FIANZA, Columna.OFERTA_DESCRIPCION, Columna.OFERTA_ESTADO, Columna.OFERTA_INMUEBLE,
					Columna.OFERTA_TIPO),
					fechaInicio, n_meses, precio, fianza, descripcion, Estado.PENDIENTE.toString(), idInmueble, "vivienda");
			
			
			Oferta oferta = new Vivienda( id, fechaInicio, n_meses, precio, fianza,  descripcion, idInmueble, Estado.PENDIENTE);
			this.listaOfertas.put(id, oferta);
			this.getInmuebleById(idInmueble).addOferta(id);
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Añade una oferta de tipo Vacacional
	 * @param fechaInicio Fecha de inicio de la oferta
	 * @param fechaFin Fecha final de la oferta
	 * @param precio precio de la oferta
	 * @param fianza fianza de la oferta
	 * @param descripcion descripcion de la oferta
	 * @param idInmueble id del inmueble sobre el cual se quiere añadir la oferta
	 * @return El id de la oferta añadida o null si no se puede añadir
	 * @throws UsuarioNoPermisoException ocurre cuando el usuario no tiene permisos suficientes para realizar esta accion
	 */
	public Integer addOferta(LocalDate fechaInicio, LocalDate fechaFin, float precio, float fianza, String descripcion, Integer idInmueble) throws UsuarioNoPermisoException{
		if(ofertanteLogueado == null) {
			throw new UsuarioNoPermisoException("se necesitan permisos de ofertante");
		}
		
		if(!ofertanteLogueado.getInmuebles().contains(idInmueble)) {
			throw new UsuarioNoPermisoException("No tienes registrado este inmueble: " + idInmueble);
		}	
		
		try {
			int id = DBManager.insertRow(Tabla.OFERTA, Arrays.asList(Columna.OFERTA_FECHAINICIO, Columna.OFERTA_FECHAFIN,
					Columna.OFERTA_PRECIO, Columna.OFERTA_FIANZA, Columna.OFERTA_DESCRIPCION, Columna.OFERTA_ESTADO, Columna.OFERTA_INMUEBLE, Columna.OFERTA_TIPO),
					fechaInicio, fechaFin, precio, fianza, descripcion, Estado.PENDIENTE.toString(), idInmueble, "vacacional");
			
			Oferta oferta = new Vacacional(id, fechaInicio, fechaFin, precio, fianza,  descripcion, idInmueble, Estado.PENDIENTE );
			this.listaOfertas.put(id, oferta);
			this.getInmuebleById(idInmueble).addOferta(id);
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * Añade un inmueble al sistema
	 * 
	 * @param localidad localidad del inmueble
	 * @param cp cp del inmueble
	 * @param direccion direccion del inmueble
	 * @param claves lista de claves de las caracteristicas del piso
	 * @param valores lista de valores asociados a las claves de las caracteristicas del piso
	 * @return Id del inmueble añadido o null si no se ha podido añadir el inmueble
	 * @throws UsuarioNoPermisoException si el usuario no es un ofertante
	 * @throws InmuebleDuplicadoException si el ya existe ese inmueble
	 */
	public Integer addInmueble(String localidad, int cp, String direccion, List<String> claves, List<String> valores) throws UsuarioNoPermisoException, InmuebleDuplicadoException {
		if(ofertanteLogueado == null) {
				throw new UsuarioNoPermisoException("se necesitan permisos de ofertante");
		}
		
		if(claves.size() != valores.size()) {
			throw new IllegalArgumentException("La longitud de la lista de claves no coincide con la longitud de valores");
		}
		
		Ofertante ofertante = ofertanteLogueado;
		
		List<Integer> idInmuebles = ofertante.getInmuebles();
		
		
		for(Integer id: idInmuebles) {
			Inmueble i = getInmuebleById(id);
			if(i.getLocalidad().equalsIgnoreCase(localidad) && i.getcodigoPostal() == cp &&
					i.getDireccion().equalsIgnoreCase(direccion)) {
				throw new InmuebleDuplicadoException(id);
			}
		}
		
		int idInmueble = -1;
		try {
			idInmueble = DBManager.insertRow(Tabla.INMUEBLE, Arrays.asList(Columna.INMUEBLE_LOCALIDAD, 
					Columna.INMUEBLE_CP, Columna.INMUEBLE_DIRECCION, Columna.INMUEBLE_CLIENTE),
					localidad, cp, direccion, this.ofertanteLogueado.getId());
		} catch (SQLException e) {
			
		}
		
		System.out.println("ID del inmueble añadido=" + idInmueble);
		
		List<Caracteristica> caracteristicas = new ArrayList<>();
		
		for (int i = 0; i < claves.size(); i++) {
			try {
				String clave = claves.get(i);
				String valor = valores.get(i);
				
				int idCaracteristica = DBManager.insertRow(Tabla.CARACTERISTICA, Arrays.asList(Columna.CARACTERISTICA_CLAVE, Columna.CARACTERISTICA_VALOR,
						Columna.CARACTERISTICA_INMUEBLE), claves.get(i), valores.get(i), idInmueble);
				
				caracteristicas.add(new Caracteristica(idCaracteristica, clave,valor));
				
			} catch (SQLException e) {
					e.printStackTrace();
			}
		}

		
		Inmueble inmueble = new Inmueble(idInmueble, localidad, cp, direccion, this.ofertanteLogueado.getId(), caracteristicas);
		this.listaInmuebles.put(idInmueble, inmueble);
		this.ofertanteLogueado.asignarInmueble(idInmueble);
		return idInmueble;
		
	}
	
	
	/**
	 * Añade una opinion numerica
	 * @param idOferta id de la oferta que opinamos
	 * @param valor valor numerico
	 * @return true si se añade correctamente, si no false
	 * @throws UsuarioNoPermisoException si el usuario no es demandante
	 * @throws SQLException Error de la base de datos. Puede suponer un error de conexion o de programacion
	 */
	public boolean addNumerica(int idOferta, Integer valor) throws UsuarioNoPermisoException, SQLException {
		if(demandanteLogueado ==null) {
			throw new UsuarioNoPermisoException("se necesitan permisos de demandante");
		}
			
		Oferta oferta = getOfertaById(idOferta);
		
		Connection con  = DBManager.openDbConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("Select id from opinion where valor is not null and oferta = " + idOferta + " and "+
				"usuario = " + this.demandanteLogueado.getId());
		if(rs.next()) {
			stmt.close();
			con.close();
			return false;
		}
		stmt.close();
		con.close();		
		
		
		int newId = -1;
		try {
			newId = DBManager.insertRow(Tabla.OPINION, Arrays.asList(Columna.OPINION_VALOR,
					Columna.OPINION_USUARIO, Columna.OPINION_OFERTA), valor, this.demandanteLogueado.getId(),
					idOferta);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		Numerica com = new Numerica(newId, FechaSimulada.getHoy(), demandanteLogueado.getId(), valor);
		
		oferta.addOpinion(com);
		
		return true;
	}
	
	
	/**
	 * Añade un comentario a una oferta
	 * @param idOferta id de la oferta
	 * @param comentario texto de nuestro comentario
	 * @param idPadre id del comentario padre. Puede ser null
	 * @return true si se añade correctamente, si no false
	 * @throws UsuarioNoPermisoException si el usuario no es demandante
	 */
	public boolean addComentario(int idOferta, String comentario, Integer idPadre) throws UsuarioNoPermisoException {
		if(demandanteLogueado == null) {
			throw new UsuarioNoPermisoException("se necesitan permisos de demandante");
		}
	
		Oferta oferta = getOfertaById(idOferta);
		LocalDate fecha = FechaSimulada.getHoy();
		
		int newId = -1;
		Comentario com = null;
		try {
			if(idPadre == null) {
				newId = DBManager.insertRow(Tabla.OPINION, Arrays.asList(Columna.OPINION_COMENTARIO,
						Columna.OPINION_USUARIO, Columna.OPINION_OFERTA, Columna.OPINION_FECHA), comentario, 
						this.demandanteLogueado.getId(), idOferta, fecha);	
				
			}else {
				Map<Integer, Opinion> ops = oferta.getOpiniones();
				boolean padreExiste = false;
				for (Opinion op : ops.values()) {
					if(op instanceof Comentario) {
						if(op.getId() == idPadre) {
							padreExiste = true;
							break;
						}
					}
				}
				if(!padreExiste) {
					throw new IllegalArgumentException("No existe un comentario con ese id padre");
				}
				
				newId = DBManager.insertRow(Tabla.OPINION, Arrays.asList(Columna.OPINION_COMENTARIO,
						Columna.OPINION_USUARIO, Columna.OPINION_OFERTA, Columna.OPINION_PADRE, Columna.OPINION_FECHA),
						comentario, this.demandanteLogueado.getId(), idOferta, idPadre, fecha);
			}
			com = new Comentario(newId, fecha, demandanteLogueado.getId(), comentario, idPadre);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		oferta.addOpinion(com);
		
		return true;
	}
	
	
	/**
	 * Borra la instancia del usuario logueado. También vacia las listas internas
	 * para prevenir estados donde los datos de la aplicacion no coindicen con 
	 * la base de datos
	 */
	public void logout() {
		this.demandanteLogueado = null;
		this.admin = null;
		this.listaUsuarios.clear();
		this.listaOfertas.clear();
		this.listaReservas.clear();
		return;
	}
	
	
	/**
	 * Contrata una oferta
	 * @param id id de la oferta que contratamos
	 * @param conceptoPago concepto de los pagos que se van a realizar. Solo sirve para probar diferenes excepciones del sistema de pagos.
	 * @return true si se contrata correctamente, si no false 
	 * @throws SQLException si fallan las consutas SQL
	 * @throws UsuarioNoPermisoException si el usuario no es demandante
	 * @throws OrderRejectedException si el pago se deniega 
	 * @throws OfertaNoModificableException la oferta se encuentra en un estado no modificable
	 */
	public boolean contratar(int id, String conceptoPago) throws SQLException, UsuarioNoPermisoException, OrderRejectedException, OfertaNoModificableException {
		if(demandanteLogueado == null) {
			throw new UsuarioNoPermisoException("No tienes permisos para realizar esta accion");
		}
	
		Oferta oferta = getOfertaById(id);
		Inmueble inmueble = this.getInmuebleById(oferta.getInmueble());
		Ofertante ofertante = (Ofertante) getClienteById(inmueble.getOfertante());

		String ccard_ofertante = ofertante.getCcard();
		String ccard_demandante = demandanteLogueado.getCcard();
		
		//Comprobar que la oferta este en uno de los estados en los que se pueda contratar
		if(oferta.getEstado() != Estado.RESERVADA && oferta.getEstado() != Estado.ACEPTADA) {
			return false;
		}else {
			//si esta reservada hay que comprobar que el que intenta contratar
			//el el mismo cliente que ha realizado la reserva
			if(oferta.getEstado() == Estado.RESERVADA) {
				Reserva reserva = this.getReservaById(oferta.getReservaActiva());
				if(reserva == null || reserva.getUsuario() != this.demandanteLogueado.getId())
					return false;
			}
		}
		
		if(TeleChargeAndPaySystem.isValidCardNumber(ccard_demandante) == false) {
			demandanteLogueado.setProblemaPagos(true);
			return false;
		}

		float precioFinal = oferta.getFianza() + oferta.getPrecio();

		try {
			TeleChargeAndPaySystem.charge(ccard_demandante, conceptoPago, precioFinal);
		}catch(FailedInternetConnectionException ex) {
			throw ex;
		}catch(OrderRejectedException ex) {
			ofertante.setProblemaPagos(true);
			throw ex;
		}

		oferta.setDemandante(this.demandanteLogueado.getId());
		oferta.setEstado(Estado.CONTRATADA);
		this.demandanteLogueado.addOfertaContratada(oferta.getId());
		
		precioFinal *= (100 - oferta.getComision())/100;
		
		if(TeleChargeAndPaySystem.isValidCardNumber(ccard_ofertante)) {
			ofertante.setProblemaPagos(true);
		} 

		try{
			TeleChargeAndPaySystem.charge(ccard_ofertante, conceptoPago, precioFinal);	
		}catch(Exception e) {
			ofertante.setProblemaPagos(true);
			ofertante.addImportePendiente(precioFinal);
			throw e;
		}
		return true;	
		
	}	
	
	

	/**
	 * Reserva una oferta
	 * @param id id de la oferta que queremos reservar
	 * @return true si se reserva correctamente, si no false
	 * @throws UsuarioNoPermisoException si el usuario no es demandante
	 * @throws SQLException Error de la base de datos. Puede suponer un error de conexion o de programacion
	 * @throws OfertaNoModificableException La oferta no puede ser modificada
	 * @throws ReservaDuplicadaException El usuario ya ha reservado esta oferta antes y ha caducado
	 * @throws UsuarioYaTieneReservaException el usuario ya tiene una reserva activa
	 * @throws OfertaYaEstaReservadaException La oferta ya se encuentra reservada por otro usuario
	 *  
	 */
	public boolean reservar(int id) throws UsuarioNoPermisoException, SQLException, UsuarioYaTieneReservaException, OfertaYaEstaReservadaException, OfertaNoModificableException, ReservaDuplicadaException {
		if(demandanteLogueado == null) {
			throw new UsuarioNoPermisoException("No tienes permisos para realizar esta accion");
		}
	
		Integer reservaActual = this.demandanteLogueado.getReservaActiva();
		
		if(reservaActual != null) {
			throw new UsuarioYaTieneReservaException(reservaActual);			
		}
		
		Oferta oferta = getOfertaById(id);
		
		if(oferta.getReservaActiva() != null) {
			throw new OfertaYaEstaReservadaException(oferta);
		}
		
		/*Comprobar si el usuario tiene una oferta anterior de esta oferta */
		List<Integer> reservas = oferta.getReservas();
		for (Integer i : reservas) {
			Reserva r = getReservaById(i);
			if(r.getUsuario() == this.demandanteLogueado.getId()) {
				throw new ReservaDuplicadaException(r);
			}
		}
		
						
		int idReserva = DBManager.insertRow(Tabla.RESERVA, Arrays.asList(Columna.RESERVA_FECHAINICIO, Columna.RESERVA_CLIENTE, Columna.RESERVA_OFERTA), 
				FechaSimulada.getHoy(), this.demandanteLogueado.getId(), id);
		
		Reserva reserva = new Reserva(id, this.demandanteLogueado.getId(), id, FechaSimulada.getHoy());
		
		this.listaReservas.put(idReserva, reserva);
		
		oferta.setEstado(Estado.RESERVADA);
		oferta.setReserva(idReserva);
		oferta.addReserva(idReserva);
		demandanteLogueado.setReservaActiva(idReserva);
		return true;		
	}
	
	
	/**
	 * Devuelve una lista de los ids de las ofertas que se encuentran
	 * en el estado PENDIENTE o PENDIENTES_DE_CAMBIOS
	 * @return lista de los ids de las ofertas que se encuentran en el estado PENDIENTE o PENDIENTES_DE_CAMBIOS
	 * @throws SQLException Error de la base de datos. Puede suponer un error de conexion o de programacion
	 */
	public List <Integer> getOfertasPendientes() throws SQLException{
		
		Connection con = DBManager.openDbConnection();
		Statement stmt = con.createStatement();
		
		String query = "Select id from oferta where estado = '" + Estado.PENDIENTE + "' or estado = '" + Estado.PENDIENTE_DE_CAMBIOS+"'";
		
		ResultSet rs = stmt.executeQuery(query);
		
		List<Integer> listaOfertas = new ArrayList<>();
		while(rs.next()) {
			Integer id = rs.getInt(Columna.ID.toString());
			listaOfertas.add(id);
		}
		return listaOfertas;
	} 
	
	
	/**
	 * Devuelve la lista de los ids de los usuarios que han tenido
	 * problemas de pago 
	 * @return lista de los ids de los usuarios que han tenido problemas de pago
	 * @throws SQLException Error de la base de datos. Puede suponer un error de conexion o de programacion
	 */
	public List <Integer> getUsuariosProblemaPago() throws SQLException{
		Connection con = DBManager.openDbConnection();
		Statement stmt = con.createStatement();
		String query = "Select id from cliente where problema_pagos = 1";
		ResultSet rs = stmt.executeQuery(query);
		
		List<Integer> listaUsuarios = new ArrayList<>();
		while(rs.next()) {
			Integer id = rs.getInt(Columna.ID.toString());
			listaUsuarios.add(id);
		}
		return listaUsuarios;
	} 
	
}
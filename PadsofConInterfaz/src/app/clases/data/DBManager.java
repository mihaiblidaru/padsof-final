package app.clases.data;

import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import com.mysql.jdbc.JDBC4Connection;
import com.mysql.jdbc.NonRegisteringDriver;

import app.clases.data.exceptions.TablaNoTieneColumnaException;
import app.clases.data.exceptions.TiposNoCoincidenException;
import es.uam.eps.padsof.telecard.TeleChargeAndPaySystem;

public class DBManager {
	/**
	 * Nombre del driver usado para conectarse a la base de datos
	 */
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

	/**
	 * Nombre de la base de datos
	 */
	private static final String DB_NAME = "mivacapiso";

	/**
	 * Host de la base de datos
	 */
	private static final String DB_HOST = "mivacapiso.chkzdm30xkln.us-east-1.rds.amazonaws.com";
	// private static final String DB_HOST = "localhost";

	/**
	 * Puerto de la base de datos
	 */
	private static final Integer DB_PORT = 3306;

	/**
	 * Url de la base de datos
	 */
	private static final String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

	/**
	 * Usuario de la base de datos
	 */
	private static final String DB_USER = "padsof";
	// private static final String DB_USER = "root";

	/**
	 * Contraseña de la base de datos
	 */
	private static final String DB_PASS = "12345678";
	// private static final String DB_PASS = "";

	/**
	 * Ruta del script que se encarga de crear las tablas dentro de la base de datos
	 */
	private static final String CREATE_TABLE_SCRIPT = "create_tables.sql";

	/**
	 * Ruta al archivo que contiene los datos de los usuarios
	 */
	private static final String USER_DATA_FILE = "datos_usuario.txt";

	/**
	 * Pool de conexiones. Usado para minimizar el coste de crear una nueva conexion
	 * cada vez que se necesita un recurso de la base de datos.
	 */
	//private static ComboPooledDataSource connectionPool = null;

	/**
	 * Inicializa el pool de conexiones
	 * 
	 * @throws PropertyVetoException
	 *             ocurre cuando no se encuentra el driver necesario para las
	 *             conexiones
	 */
	private static void initializeConnectionPool() throws PropertyVetoException {
		/*connectionPool = new BasicDataSource();
		connectionPool.setDriverClassName(JDBC_DRIVER);
		connectionPool.setUrl(DB_URL);
		connectionPool.setInitialSize(34);
		connectionPool.setUsername(DB_USER);
		connectionPool.setPassword(DB_PASS);
		connectionPool.setMaxOpenPreparedStatements(100);
		connectionPool.setMinIdle(89);
		connectionPool.setMaxIdle(233);
		connectionPool.setValidationQuery("SELECT 1");
		connectionPool.setTestOnBorrow(true);
		connectionPool.setTimeBetweenEvictionRunsMillis(34000);
		connectionPool.setMinEvictableIdleTimeMillis(55000);*/

		/*connectionPool = new ComboPooledDataSource();
		connectionPool.setDriverClass(JDBC_DRIVER);
		connectionPool.setJdbcUrl(DB_URL);
		connectionPool.setUser(DB_USER);
		connectionPool.setPassword(DB_PASS);
		connectionPool.setMaxStatements(50);
		connectionPool.setMinPoolSize(10);
		connectionPool.setMaxPoolSize(50);
		connectionPool.setMaxStatementsPerConnection(30);
		connectionPool.setInitialPoolSize(10);
		connectionPool.setMaxIdleTime(50);
		connectionPool.setTestConnectionOnCheckin(false);
		connectionPool.setTestConnectionOnCheckout(false);
		connectionPool.setPreferredTestQuery("SELECT 1");*/
	}

	private static JDBC4Connection connection = null;

	/**
	 * Expresión regular usada para validar los datos leidos desde el fichero que
	 * contiene los datos de los clientes.
	 */
	private static String FORMATO_CLIENTE = "(OD|D|O);([0-9A-Za-z]+);([a-zA-Z0-9 ]+),([a-zA-Z0-9 ]+);([^\\s\\\\]+);([0-9]+)";

	/**
	 * Devuelve una conexion del pool de conexiones.
	 * 
	 * Si el pool no esta inicializado, lo inicializa y despues devuelve la conexion
	 * 
	 * @return una conexion abierta con la base de datos
	 * @throws SQLException
	 *             ocurre cuando hay un error de conexion
	 */
	public static Connection openDbConnection() throws SQLException {
		/*if (connectionPool == null) {
			try {
				initializeConnectionPool();
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
		}*/

		Properties prop = new Properties();
		prop.setProperty(NonRegisteringDriver.PASSWORD_PROPERTY_KEY, DB_PASS);
		prop.setProperty(NonRegisteringDriver.USER_PROPERTY_KEY, DB_USER);

		if (connection == null) {
			connection = new MyConnection(DB_HOST, DB_PORT, prop, DB_NAME, DB_URL);
			return connection;
		} else {
			if (connection.isClosed()) {
				connection = new MyConnection(DB_HOST, DB_PORT, prop, DB_NAME, DB_URL);
			}
			return connection;
		}

		/*return connectionPool.getConnection();*/
	}

	/**
	 * Comprueba si la base de datos está inicializada. Se considera inicializada
	 * cuando todas las tablas existen y la tabla de clientes contiene alguna fila
	 * 
	 * @return true si está inicializada o false en caso contrario
	 * @throws SQLException
	 *             ocurre por un error de conexion o de sintaxis SQL
	 */
	public static boolean isDatabaseInitialized() throws SQLException {
		Connection con = openDbConnection();
		Statement stmt = null;
		boolean status = false;
		stmt = con.createStatement();

		/* Comprobar que las tablas existen */

		String query = "SELECT COUNT(*) as num_tables from information_schema.tables where table_schema = 'mivacapiso' AND "
				+ "(TABLE_NAME  = '" + Tabla.CLIENTE + "' " + "OR " + "TABLE_NAME = '" + Tabla.OFERTA + "' " + "OR "
				+ "TABLE_NAME = '" + Tabla.INMUEBLE + "' " + "OR " + "TABLE_NAME = '" + Tabla.CARACTERISTICA + "' "
				+ "OR " + "TABLE_NAME = '" + Tabla.OPINION + "' " + "OR " + "TABLE_NAME = '" + Tabla.RESERVA + "')";

		ResultSet rs = stmt.executeQuery(query);

		if (rs.next()) {
			int numOfTables = rs.getInt("num_tables");
			if (numOfTables == 6) {
				status = true;
			} else {
				status = false;
			}
		} else {
			status = false;
		}

		/* Comprobar que la tabla de usuarios contiene algun dato */
		if (status == true) {
			query = "SELECT COUNT(*) as num_clientes from cliente";
			rs = stmt.executeQuery(query);
			int num_clientes = 0;
			if (rs.next()) {
				num_clientes = rs.getInt("num_clientes");
				if (num_clientes <= 0) {
					status = false;
				}
			} else {
				status = false;
			}

		}

		con.close();
		return status;
	}

	/**
	 * Inicializa la base de datos. Ejecuta el script de creacion de tablas y carga
	 * los usuarios en la tabla de clientes.
	 * 
	 * @throws SQLException
	 *             ocurre cuando hay un error de conexion
	 */
	public static void initializeDatabase() throws SQLException {
		Connection con = null;
		Statement stmt = null;
		PreparedStatement preparedStatement = null;

		try {
			con = openDbConnection();
			stmt = con.createStatement();

			BufferedReader br = new BufferedReader(new FileReader(CREATE_TABLE_SCRIPT));

			String output = "";
			String line;
			while ((line = br.readLine()) != null) {
				output += line;
			}

			br.close();

			String[] statements = output.split(";");

			for (String sqlStatement : statements) {
				stmt.addBatch(sqlStatement);
			}

			stmt.executeBatch();

			String sql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s,%s) values(?, ?, ?, ?, ?,?);", Tabla.CLIENTE,
					Columna.CLIENTE_IDENTIFICADOR, Columna.CLIENTE_FULL_NAME, Columna.CLIENTE_PASSWORD,
					Columna.CLIENTE_CCARD, Columna.CLIENTE_ROL, Columna.CLIENTE_PROBLEMA_PAGOS);

			preparedStatement = con.prepareStatement(sql);
			// System.out.println(sql);
			br = new BufferedReader(new FileReader(USER_DATA_FILE));
			String p = null;
			br.readLine();
			int usuariosInsertados = 0;
			while ((p = br.readLine()) != null) {
				if (!Pattern.matches(FORMATO_CLIENTE, p)) {
					continue;
				}
				String[] data = p.split(";");
				for (int i = 0; i < data.length; i++) {
					data[i] = data[i].trim(); // eliminar espacios innecesarios
				}

				if (data.length == 5) {
					String rol = data[0];
					String nif = data[1];
					String fullName = data[2];
					String password = data[3];
					String ccard = data[4];
					boolean problemaTarjetaCredito = false;
					preparedStatement.setString(1, nif);
					preparedStatement.setString(2, fullName);
					preparedStatement.setString(3, password);
					preparedStatement.setString(4, ccard);
					preparedStatement.setString(5, rol);

					if (!TeleChargeAndPaySystem.isValidCardNumber(ccard)) {
						problemaTarjetaCredito = true;
					}

					preparedStatement.setBoolean(6, problemaTarjetaCredito);
					int rs = preparedStatement.executeUpdate();
					if (rs > 0) {
						usuariosInsertados++;
					}
				}
			}
			br.close();
			System.out.printf("%d usuarios insertados\n", usuariosInsertados++);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			stmt.close();
			preparedStatement.close();
			con.close();
		}
	}

	/**
	 * Cambia un campo concreto dentro de una tabla basandose en un id
	 * 
	 * @param table
	 *            Tabla que se quiere actualizar
	 * @param column
	 *            Columna que se quiere cambiar
	 * @param id
	 *            Id de la fila que se quiere cambiar
	 * @param obj
	 *            El nuevo dato a insertar
	 * @return true si los cambios se han realizado con exito o false en caso
	 *         contrario
	 * @throws SQLException
	 *             ocurre por un error de conexion
	 */
	public static boolean updateField(Tabla table, Columna column, int id, Object obj) throws SQLException {
		if (!table.hasColumn(column)) {
			throw new TablaNoTieneColumnaException(table, column);
		}

		if (id < 0) {
			return false;
		}
		if (!column.matchTypes(obj)) {
			throw new TiposNoCoincidenException(column, obj);
		}

		Connection con = openDbConnection();
		PreparedStatement stmt = con
				.prepareStatement("UPDATE " + table + " SET " + column + " = ? " + "where id = " + id);
		setPreparedStatementAtribute(stmt, 1, column, obj);
		int updatedRows = stmt.executeUpdate();

		stmt.close();
		con.close();

		return updatedRows > 0;

	}

	/**
	 * Da un valor a un atributo de una consulta preparada en funcion del tipo de la
	 * columna.
	 * 
	 * @param pstmt
	 *            una consulta prepadada
	 * @param i
	 *            indice del atributo que se quiere dar valor
	 * @param col
	 *            columna para comparar los tipos
	 * @param obj
	 *            el dato que se quiere insertar en la consulta
	 * @throws SQLException
	 *             supone un error de conexion, sintaxis, etc.
	 */
	private static void setPreparedStatementAtribute(PreparedStatement pstmt, int i, Columna col, Object obj)
			throws SQLException {
		Class<?> ctype = col.getType();
		if (ctype == Integer.class) {
			pstmt.setInt(i, (Integer) obj);
		}
		if (ctype == Boolean.class) {
			pstmt.setBoolean(i, (Boolean) obj);
		} else if (ctype == String.class) {
			pstmt.setString(i, (String) obj);
		} else if (ctype == Float.class) {
			pstmt.setFloat(i, (Float) obj);
		} else if (ctype == LocalDate.class) {
			Date date = Date.valueOf((LocalDate) obj);
			pstmt.setDate(i, date);
		}
	}

	/**
	 * Inserta una nueva fila en la base de datos
	 * 
	 * @param t
	 *            Tabla en la que se quiere insertar
	 * @param insertCols
	 *            lista de las columnas que se van a insertar
	 * @param data
	 *            array compuesto por los datos que se van a insertar
	 * @return el id generado por la base de datos para la fila insertada
	 * @throws SQLException
	 *             supone un error de conexion, sintaxis, etc.
	 */
	public static int insertRow(Tabla t, List<Columna> insertCols, Object... data) throws SQLException {
		if (insertCols.size() != data.length) {
			throw new IllegalArgumentException("Número de argumentos no coincide con el número de columnas");
		}

		for (Columna col : insertCols) {
			if (!t.hasColumn(col)) {
				throw new TablaNoTieneColumnaException(t, col);
			}
		}

		for (int i = 0; i < data.length; i++) {
			if (!insertCols.get(i).matchTypes(data[i])) {
				throw new TiposNoCoincidenException(insertCols.get(i), data[i]);
			}
		}

		String sql = "INSERT INTO " + t + " (" + insertCols.get(0);

		for (int i = 1; i < insertCols.size(); i++) {
			sql += ", " + insertCols.get(i);
		}

		sql += ") VALUES (?";

		for (int i = 1; i < data.length; i++) {
			sql += ", ?";
		}

		sql += ");";

		Connection con = openDbConnection();

		PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		for (int i = 0; i < insertCols.size(); i++) {
			setPreparedStatementAtribute(pstmt, i + 1, insertCols.get(i), data[i]);
		}

		pstmt.executeUpdate();

		ResultSet rs = pstmt.getGeneratedKeys();

		int key = -1;
		if (rs.next()) {
			key = rs.getInt(1);
		}

		rs.close();
		pstmt.close();
		con.close();

		return key;
	}
}

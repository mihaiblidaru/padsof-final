package app.clases.data;

import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.JDBC4Connection;

/**
 * Tipo de conexion a la base de datos que reimplementa el metodo
 * close para no tener que cambiar la interfaz publica de DBManager
 * 
 * @author Mihai Blidaru
 * @author Sergio Dominguez
 *
 */
public class MyConnection extends JDBC4Connection {

	public MyConnection(String hostToConnectTo, int portToConnectTo, Properties info, String databaseToConnectTo,
			String url) throws SQLException {
		super(hostToConnectTo, portToConnectTo, info, databaseToConnectTo, url);

	}

	@Override
	public void close() {

	}

	private static final long serialVersionUID = 1L;

}

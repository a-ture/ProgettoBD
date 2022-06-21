package operazioni;
//import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

public class DBConnectionPool {

	private static List<Connection> freeDbConnections;

	static {
		freeDbConnections = new LinkedList<Connection>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.printf("DB driver not found!", e);
		} // catch (IOException e) {
			// System.out.printf("DB connection pool error!", e);
			// }
	}

	private static Connection createDBConnection() throws SQLException {
		Connection newConnection = null;
		String ip = "localhost";
		String port = "3306";
		String db = "progetto";
		String username = "raffaella";
		String password = "1000ciambelline";
		String params = "?serverTimezone=UTC&useLegacyDatetimeCode=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&zeroDateTimeBehavior=CONVERT_TO_NULL&autoReconnect=true";

		newConnection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + db + params, username,
				password);

		JOptionPane.showMessageDialog(null,"Connessione Ok");
		newConnection.setAutoCommit(false);
		return newConnection;
	}

	public static synchronized Connection getConnection() throws SQLException {
		Connection connection;

		if (!freeDbConnections.isEmpty()) {
			connection = (Connection) freeDbConnections.get(0);
			DBConnectionPool.freeDbConnections.remove(0);

			try {
				if (connection.isClosed())
					connection = DBConnectionPool.getConnection();
			} catch (SQLException e) {
				connection = DBConnectionPool.getConnection();
			}
		} else {
			connection = DBConnectionPool.createDBConnection();
		}

		return connection;
	}

	public static synchronized void releaseConnection(Connection connection) {
		DBConnectionPool.freeDbConnections.add(connection);
	}

	public static synchronized void releaseAllConnection() throws SQLException {
		while (freeDbConnections.size() > 0) {
			Connection connection = (Connection) freeDbConnections.remove(0);
			if (connection != null) {
				System.out.println("Release connection" + connection.toString());
				connection.close();
			}
		}
	}
}

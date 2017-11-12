package database.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {
	// jdbc:postgresql://localhost:5432/aplikacjemobilnezukowskistage1
	private static Connection connection;
	private static final String DB_DRIVER = "org.postgresql.Driver";
	private static final String DB_IP = "localhost";
	private static final String DB_PORT = "5432";
	private static final String DB_NAME = "aplikacjemobilnezukowskistage1";
	private static final String DB_CONNECTION = "jdbc:postgresql://" + DB_IP + ":" + DB_PORT + "/" + DB_NAME;
	private static final String DB_USER = "apka";
	private static final String DB_PASSWORD = "apka";

	private DatabaseConnection() {
	}

	public static Connection getDataBaseConnection() {
		if (isDriverAvailable()) {
			try {
				connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			} catch (SQLException e) {
				System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
				return null;
			}
            System.out.println("sukces łączenia z bazą danych");
            return connection;
		} else
			return null;
	}

	private static boolean isDriverAvailable() {
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
			System.out.println("Could'n find JDBC driver in library path");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void closeDataBaseConnection() {

		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}

package database.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class CrudTemplate {
	protected static Connection connection = DatabaseConnection.getDataBaseConnection();
	protected Statement statement;
	protected final static String COMMA = ", ";

	protected static String addEarsToString(String string) {
		String dbString = "'" + string + "'";
		return dbString;
	}

	public void createStatement() {
		try {
			this.statement = connection.createStatement();
		} catch (SQLException e) {
			System.out.println("statement creation error");
			e.printStackTrace();
		}
	}

	public void closeStatement() {
		if (this.statement != null) {
			try {
				this.statement.close();
			} catch (SQLException e) {
                System.out.println("statement close error");
                e.printStackTrace();
			}
		}
	}

	public void closeConnection() {
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

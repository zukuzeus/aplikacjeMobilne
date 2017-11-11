package database.util;

import static spark.Spark.exception;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import spark.Response;

public class Insert extends CrudTemplate {
	// private static Statement statement;
	// private static Connection connection =
	// DatabaseConnection.getDataBaseConnection();

	public Insert() {

	}

	public boolean insertUser(String user, String password) {

		createStatement();
		String sql = "Insert into users Values(" + addEarsToString(user) + COMMA + addEarsToString(password) + ")";

		try {
			statement.executeUpdate(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return false;
		} finally {
			closeStatement();
		}
		return true;
	}

	public void insertProduct(String user, String product, String sklep, double cena, int ilosc) {
		createStatement();

		String sql = "insert into products VALUES(" + addEarsToString(user) + COMMA + addEarsToString(product) + COMMA
				+ addEarsToString(sklep) + COMMA + cena + COMMA + ilosc + ")";
		try {
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeStatement();
	}

}

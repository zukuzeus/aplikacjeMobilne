package database.util;

import java.sql.SQLException;

public class Delete extends CrudTemplate {
	//private static Statement statement;

	public Delete() {
	}

	public void deleteProduct(String username, String product) {
		createStatement();
		String sql = "DELETE from products where username=" + addEarsToString(username) + " and product="
				+ addEarsToString(product);
		try {
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeStatement();
	}

}

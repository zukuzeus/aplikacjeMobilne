package database.util;

import java.sql.SQLException;

public class Update extends CrudTemplate {

	

	public Update() {
	}

	public void updateProductQuantity(String username, String produkt, Integer newQuantity) {
		createStatement();
		String newQ = newQuantity.toString();
		String sql = "update products set quantity = " + newQ + " where product= " + addEarsToString(produkt) + " and "
				+ "username= " + addEarsToString(username);
		try {
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeStatement();
	}


}

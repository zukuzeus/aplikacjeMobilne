package database.util;

import java.sql.SQLException;

public class Delete extends CrudTemplate {
    //private static Statement statement;

    public Delete() {
    }

    public boolean deleteProduct(String username, String product) {
        createStatement();
        String sql = "DELETE from products where username=" + addEarsToString(username) + " and product="
                + addEarsToString(product);
        try {
            statement.execute(sql);
            return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } finally {
            closeStatement();
        }


    }

}

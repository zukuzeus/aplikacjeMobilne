package database.util;

import java.sql.SQLException;

public class Update extends CrudTemplate {


    Update() {
    }

    public boolean updateProductQuantity(String username, String produkt, Integer newQuantity) {
        createStatement();
        String newQ = newQuantity.toString();
        String sql = "update products set quantity = " + newQ + " where product= " + addEarsToString(produkt) + " and "
                + "username= " + addEarsToString(username);
        try {
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } finally {
            closeStatement();
        }

    }

    public boolean updateProductQuantityInDevicesTable() {
        createStatement();
        String sql = "UPDATE products p\n" +
                "SET quantity = f.valsum\n" +
                "FROM\n" +
                "(\n" +
                "SELECT username, product, SUM(quantytiesperdevice.quantity) valsum\n" +
                "FROM quantytiesperdevice\n" +
                "GROUP BY  username, product\n" +
                ") f\n" +
                "WHERE p.username = f.username AND p.product = f.product";
        try {
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeStatement();
        }

    }


}

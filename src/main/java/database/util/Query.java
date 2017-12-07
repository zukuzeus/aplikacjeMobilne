package database.util;

import database.containers.Product;
import database.containers.ProductService;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Query extends CrudTemplate {
    private static ResultSet results;

    Query() {

    }

    private ResultSet getResultsFromSql(String SqlQuery) {
        createStatement();
        try {
            results = statement.executeQuery(SqlQuery);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return results;
    }

    public void displayResultSet(ResultSet resultSet) {
        createStatement();
        try {
            prepareResultDisplay(resultSet);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void displayResultSet() {
        createStatement();
        try {
            prepareResultDisplay();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void prepareResultDisplay(ResultSet resultSet) throws SQLException {
        ResultSetMetaData rsmd = results.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (results.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1)
                    System.out.print(",  ");
                String columnValue = results.getString(i);
                System.out.print(columnValue);
            }
            System.out.println("");
        }
        System.out.println("");
    }

    private void prepareResultDisplay() throws SQLException {
        ResultSetMetaData rsmd = results.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (results.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1)
                    System.out.print(",  ");
                String columnValue = results.getString(i);
                System.out.print(columnValue);
            }
            System.out.println("");

        }
        System.out.println("");
    }

    public ProductService getResultsAsProductService(String username) {
        createStatement();
        String sql = "SELECT * from products where username=" + addEarsToString(username) + "ORDER BY product";
        ProductService products = new ProductService();

        try {
            results = statement.executeQuery(sql);
            while (results.next()) {
                double d = results.getDouble("price");
                products.addProduct(new Product(results.getString("product"), results.getString("store"),
                        roundToTwoDecimalPlaces(d), results.getInt("quantity")));
//                System.out.println(dfRound(results.getDouble("price")));
//                System.out.println(roundToTwoDecimalPlaces(results.getDouble("price")));

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return products;
    }

    public boolean ifProductExistsInDataBase(final String product, final String username) {
        createStatement();
        String sql = "SELECT * FROM products where username=" + addEarsToString(username) + " and product ="
                + addEarsToString(product);
        getResultsFromSql(sql);
        try {
            //!results.isBeforeFirst()
            return results.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //closeStatement();
        }
        return false;
    }

    public boolean isUserExistsAndPasswordMatch(String username, String password) {
        createStatement();
        String sql = "SELECT * FROM users where username=" + addEarsToString(username) + " and password ="
                + addEarsToString(password);
        getResultsFromSql(sql);
        try {
            //!results.isBeforeFirst()
            if (!results.next()) {
                System.out.println("Login failed when user: " + username + "    ||tried to log");
                return false;
            } else
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //closeStatement();
        }
        return false;

    }

    public ProductService getOtherUsersProducts(String username, int userId) {
        String sqlOtherDevicesSum = "SELECT\n" +
                "  n1.product,\n" +
                "  n1.store,\n" +
                "  n1.price,\n" +
                "  n2.otherDeviceQuantity\n" +
                "FROM (SELECT\n" +
                "        products.username,\n" +
                "        products.product,\n" +
                "        products.store,\n" +
                "        products.price\n" +
                "      FROM products) n1\n" +
                "  INNER JOIN (SELECT\n" +
                "                quantytiesperdevice.username,\n" +
                "                quantytiesperdevice.product,\n" +
                "                SUM(quantytiesperdevice.quantity) otherDeviceQuantity\n" +
                "              FROM quantytiesperdevice\n" +
                "              WHERE quantytiesperdevice.deviceid != " + userId + "\n" +
                "              GROUP BY username, product) n2\n" +
                "    ON (n2.username = n1.username AND n2.product = n1.product)\n" +
                "WHERE n1.username = " + addEarsToString(username) + ";";
        createStatement();
        results = getResultsFromSql(sqlOtherDevicesSum);
        ProductService products = new ProductService();
        try {
            while (results.next()) {
                products.addProduct(new Product(
                        results.getString("product"),
                        results.getString("store"),
                        roundToTwoDecimalPlaces(results.getDouble("price")),
                        results.getInt("otherDeviceQuantity")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return products;
    }

    private double dfRound(double d) {
        NumberFormat df = new DecimalFormat(".##");
        return Double.parseDouble(df.format(d));
    }

    private double roundToTwoDecimalPlaces(final double d) {
        return Math.round(d * 100.0) / 100.0;
    }
}

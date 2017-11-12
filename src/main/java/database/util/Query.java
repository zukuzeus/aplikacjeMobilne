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

    public Query() {

    }

    public ResultSet getResultsFromSql(String SqlQuery) {
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

    public boolean isUserExistsAndPasswordMatch(String username, String password) {
        //createStatement();
        String sql = "select * from users where username=" + addEarsToString(username) + " and password ="
                + addEarsToString(password);
        getResultsFromSql(sql);
        try {
            //!results.isBeforeFirst()
            if (results.next() == false) {
                System.out.println("No such user exists");
                return false;
            } else
                return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            closeStatement();
        }
        return false;

    }

    private double dfRound(double d) {
        NumberFormat df = new DecimalFormat(".##");
        double rounded = Double.parseDouble(df.format(d));
        return rounded;


    }

    private double roundToTwoDecimalPlaces(final double d) {
        double rounded = Math.round(d * 100.0) / 100.0;
        return rounded;
    }
}

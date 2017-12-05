package database.util;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Insert extends CrudTemplate {
    private static ResultSet results;

    public Insert() {

    }

    public boolean insertUser(String user, String password) {

        createStatement();
        String sql = "Insert into users Values(" + addEarsToString(user) + COMMA + addEarsToString(password) + ")";

        try {
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("user already exists, cant register");
            return false;
        } finally {
            closeStatement();
        }

    }

    public boolean insertProduct(String user, String product, String sklep, double cena, int ilosc) {
        createStatement();

        String sql = "insert into products VALUES(" + addEarsToString(user) + COMMA + addEarsToString(product) + COMMA
                + addEarsToString(sklep) + COMMA + cena + COMMA + ilosc + ")";
        try {
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            System.out.println("product already exists, cant insert product");
            e.printStackTrace();
            return false;
        } finally {
            closeStatement();
        }

    }

    public boolean insertProductQtyToUserTable(String user, String product, int devceId, int iloscNaDevice) {
        createStatement();

        String sql = "insert into quantytiesperdevice VALUES(" + addEarsToString(user) + COMMA + addEarsToString(product) + COMMA
                + devceId + COMMA + iloscNaDevice + ")";
        try {
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            System.out.println("product already exists, cant insert product");
            e.printStackTrace();

            return false;
        } finally {
            closeStatement();
        }

    }

    public int generateNewIdForDevice(String username) {
        Integer newIdForDevice = 0;
        createStatement();
        String getMaxIdSQL = "select max(deviceid) from deviceidentificator where username=" + addEarsToString(username);

        //String sql = " INSERT into deviceidentificator VALUES (" + addEarsToString(username) + COMMA + newIdForDevice.intValue();
        //ProductService products = new ProductService();

        try {
            results = statement.executeQuery(getMaxIdSQL);
            if (results.next()) {
                newIdForDevice = (Integer) results.getObject("max") != null ? results.getInt("max") : 0;
            }
            if (newIdForDevice.equals(null)) {
                System.out.println("error nie ma takiego id a powinno byc w tabeli deviceidentificator");
            } else {
                newIdForDevice++;
                String sql = " INSERT into deviceidentificator VALUES (" + addEarsToString(username) + COMMA + newIdForDevice + ")";
                statement.execute(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement();
        }
        return newIdForDevice.intValue();
    }



}

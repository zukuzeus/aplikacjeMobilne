package database.util;

import java.sql.SQLException;

public class Insert extends CrudTemplate {

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

    public boolean insertProduct(String user, String product, String sklep, double cena, int ilosc) {
        createStatement();

        String sql = "insert into products VALUES(" + addEarsToString(user) + COMMA + addEarsToString(product) + COMMA
                + addEarsToString(sklep) + COMMA + cena + COMMA + ilosc + ")";
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

}

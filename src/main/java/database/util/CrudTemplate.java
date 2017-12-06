package database.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

abstract class CrudTemplate {
    final static String COMMA = ", ";
    private static Connection connection = DatabaseConnection.getDataBaseConnection();
    Statement statement;

    static String addEarsToString(String string) {
        return "'" + string + "'";
    }

    void createStatement() {
        try {
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("statement creation error");
            e.printStackTrace();
        }
    }

    void closeStatement() {
        if (this.statement != null) {
            try {
                this.statement.close();
            } catch (SQLException e) {
                System.out.println("statement close error");
                e.printStackTrace();
            }
        }
    }

    void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    boolean isExecuted(String sql) {
        createStatement();
        try {
            statement.execute(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeStatement();
        }
    }
}

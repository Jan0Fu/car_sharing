package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    final static String JDBC_DRIVER = "org.h2.Driver";
    final static String JDBC_URL = "jdbc:h2:./src/carsharing/db/carsharing";

    public static void main(String[] args) {
        String dbName = "carsharing.mv.db";
        if (args[0].equals("-databaseFileName") && args.length > 1) {
            dbName = args[1];
        }

        connectDb(dbName);
    }

    public static void connectDb(String dbName) {
        String url = JDBC_URL + dbName;
        try {
            Class.forName (JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(url);
            conn.setAutoCommit(true);

            Statement statement = conn.createStatement();
            String dropTable = "DROP TABLE IF EXISTS COMPANY";
            String createTable =  "CREATE TABLE   COMPANY " +
                    "(ID INTEGER not NULL, " +
                    " NAME VARCHAR(255) not NULL, " +
                    " PRIMARY KEY ( ID ))";
            statement.executeUpdate(dropTable);
            statement.executeUpdate(createTable);

            statement.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }
}
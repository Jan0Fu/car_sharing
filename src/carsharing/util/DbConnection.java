package carsharing.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection {

    private static Connection connection = null;
    private static final String databaseFilePath = "./src/carsharing/db/";
    private static String databaseFileName = "dbName";

    public static void setDbFileName(String databaseFileName){
        DbConnection.databaseFileName = databaseFileName;
    }

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }
        try {
            connection = DriverManager.getConnection("jdbc:h2:" + databaseFilePath+databaseFileName);
            connection.setAutoCommit(true);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connection = null;
        }
    }

    public static void createTables() {
        try (Statement statement = getConnection().createStatement()) {
            String sql = "DROP TABLE IF EXISTS CAR; " +
                    "DROP TABLE IF EXISTS COMPANY; " +
                    "CREATE TABLE IF NOT EXISTS COMPANY (" +
                    "ID INTEGER not NULL AUTO_INCREMENT, " +
                    "NAME VARCHAR(255) not NULL UNIQUE, " +
                    " PRIMARY KEY ( ID )" +
                    ");" +
                    "CREATE TABLE IF NOT EXISTS CAR (" +
                    "ID INTEGER not NULL AUTO_INCREMENT, " +
                    "NAME VARCHAR(255) not NULL UNIQUE, " +
                    "COMPANY_ID INTEGER not NULL, " +
                    "PRIMARY KEY ( ID ), " +
                    "FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID)" +
                    ");";

            statement.executeUpdate(sql);
            closeConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

package carsharing;

import carsharing.util.DbConnection;

import static carsharing.util.DbConnection.createTables;

public class Main {

    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        if (args.length > 1 && args[0].equals("-databaseFileName")) {
            DbConnection.setDbFileName(args[1]);
        }

        createTables();
        ui.start();
    }
}
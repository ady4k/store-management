package helper;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnectionHelper {
    private final static String url = "jdbc:postgresql://localhost/proiect_pao";
    private final static String user = "appuser";
    private final static String password = "P@$$w0rd";

    private static DatabaseConnectionHelper instance = null;

    public static DatabaseConnectionHelper getInstance() {
        if (instance == null) {
            instance = new DatabaseConnectionHelper();
        }
        return instance;
    }

    private Connection connection = null;

    public Connection connect() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to PostgreSQL Database");
        } catch (Exception exc) {
            System.out.println(exc.getMessage());
        }
        return connection;
    }
}
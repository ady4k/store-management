package helper;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnectionHelper {
    private final static String url = "jdbc:postgresql://localhost:5432/proiect_pao";
    private final static String user = "appuser";
    private final static String password = "P@$$w0rd";

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
package persistence;

import helper.DatabaseConnectionHelper;
import interfaces.persistence.IDatabase;
import model.Category;
import model.Employee;
import model.Headquarters;
import model.Location;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class SQLDatabase implements IDatabase {
    private final static DatabaseConnectionHelper databaseHelper = DatabaseConnectionHelper.getInstance();
    private final static Connection connection = databaseHelper.connect();

    private final PreparedStatement insertCategories;
    private final PreparedStatement selectCategories;

    private final PreparedStatement insertLocations;
    private final PreparedStatement selectLocations;

    private final PreparedStatement insertEmployees;
    private final PreparedStatement selectEmployees;

    private final PreparedStatement insertHeadquarters;
    private final PreparedStatement selectHeadquarters;

    public SQLDatabase() throws SQLException {
        createTables();

        insertCategories = connection.prepareStatement("INSERT INTO categories (name) VALUES (?)");
        selectCategories = connection.prepareStatement("SELECT * FROM categories");

        insertLocations = connection.prepareStatement("INSERT INTO locations (city, country) VALUES (?, ?)");
        selectLocations = connection.prepareStatement("SELECT * FROM locations");

        insertEmployees = connection.prepareStatement("INSERT INTO employees (cnp, firstName, lastName, city, country, email) VALUES (?, ?, ?, ?, ?, ?)");
        selectEmployees = connection.prepareStatement("SELECT * FROM employees");

        insertHeadquarters = connection.prepareStatement("INSERT INTO headquarters (city, country, postalCode, street, streetNumber, phoneNumber) VALUES (?, ?, ?, ?, ?, ?)");
        selectHeadquarters = connection.prepareStatement("SELECT * FROM headquarters");
    }

    private void createTables() throws SQLException {
        String categoriesTable = "CREATE TABLE IF NOT EXISTS categories (" +
                "categoryId SMALLSERIAL PRIMARY KEY," +
                "name VARCHAR(50) UNIQUE NOT NULL)";

        String locationsTable = "CREATE TABLE IF NOT EXISTS locations (" +
                "locationId SMALLSERIAL PRIMARY KEY," +
                "city VARCHAR(50) NOT NULL," +
                "country VARCHAR(50) NOT NULL)";

        String employeesTable = "CREATE TABLE IF NOT EXISTS employees (" +
                "employeeId SMALLSERIAL PRIMARY KEY," +
                "cnp VARCHAR(13) UNIQUE NOT NULL," +
                "firstName VARCHAR(50) NOT NULL," +
                "lastName VARCHAR(50) NOT NULL," +
                "city VARCHAR(50) NOT NULL," +
                "country VARCHAR(50) NOT NULL," +
                "email VARCHAR(100) UNIQUE NOT NULL)";

        String headquartersTable = "CREATE TABLE IF NOT EXISTS headquarters (" +
                "headquartersId SMALLSERIAL PRIMARY KEY," +
                "city VARCHAR(50) NOT NULL," +
                "country VARCHAR(50) NOT NULL," +
                "postalCode VARCHAR(6) NOT NULL," +
                "street VARCHAR(50) NOT NULL," +
                "streetNumber SMALLINT NOT NULL," +
                "phoneNumber VARCHAR(10) UNIQUE NOT NULL)";

        Statement statement = connection.createStatement();
        statement.execute(categoriesTable);
        statement.execute(locationsTable);
        statement.execute(employeesTable);
        statement.execute(headquartersTable);
    }

    @Override
    public void exportCategories(ArrayList<Category> categories) {
        for (Category category : categories) {
            try {
                insertCategories.setString(1, category.getName());
                insertCategories.executeUpdate();
            } catch (SQLException exc) {
                throw new RuntimeException(exc);
            }

        }
    }

    @Override
    public ArrayList<Category> getCategories() throws IOException {
        try {
            ResultSet resultSet = selectCategories.getResultSet();
            ArrayList<Category> categories = new ArrayList<Category>();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                categories.add(new Category(name));
            }
            return categories;
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
    }

    @Override
    public void exportLocations(ArrayList<Location> locations) throws IOException {
        for (Location location : locations) {
            try {
                insertLocations.setString(1, location.getCity());
                insertLocations.setString(2, location.getCountry());
                insertLocations.executeUpdate();
            } catch (SQLException exc) {
                throw new RuntimeException(exc);
            }
        }
    }

    @Override
    public ArrayList<Location> getLocations() throws IOException {
        try {
            ResultSet resultSet = selectLocations.getResultSet();
            ArrayList<Location> locations = new ArrayList<Location>();
            while (resultSet.next()) {
                String city = resultSet.getString("city");
                String country = resultSet.getString("country");
                locations.add(new Location(city, country));
            }
            return locations;
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
    }

    @Override
    public void exportEmployees(ArrayList<Employee> employees) throws IOException {
        for (Employee employee : employees) {
            try {
                insertEmployees.setInt(1, employee.getCnp());
                insertEmployees.setString(2, employee.getFirstName());
                insertEmployees.setString(3, employee.getLastName());
                insertEmployees.setString(4, employee.getLocation().getCity());
                insertEmployees.setString(5, employee.getLocation().getCountry());
                insertEmployees.setString(6, employee.getEmail());
                insertEmployees.executeUpdate();
            } catch (SQLException exc) {
                throw new RuntimeException(exc);
            }
        }
    }

    @Override
    public ArrayList<Employee> getEmployees() throws IOException {
        try {
            ResultSet resultSet = selectEmployees.getResultSet();
            ArrayList<Employee> employees = new ArrayList<Employee>();
            while (resultSet.next()) {
                int cnp = resultSet.getInt("cnp");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String city = resultSet.getString("city");
                String country = resultSet.getString("country");
                String email = resultSet.getString("email");
                employees.add(new Employee(cnp, firstName, lastName, new Location(city, country), email));
            }
            return employees;
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
    }

    @Override
    public void exportHeadquarters(ArrayList<Headquarters> headquarters) throws IOException {
        for (Headquarters hq : headquarters) {
            try {
                insertHeadquarters.setString(1, hq.getCity());
                insertHeadquarters.setString(2, hq.getCountry());
                insertHeadquarters.setInt(3, hq.getPostalCode());
                insertHeadquarters.setString(4, hq.getStreet());
                insertHeadquarters.setInt(5, hq.getStreetNumber());
                insertHeadquarters.setInt(6, hq.getPhoneNumber());
                insertHeadquarters.executeUpdate();
            } catch (SQLException exc) {
                throw new RuntimeException(exc);
            }
        }
    }

    @Override
    public ArrayList<Headquarters> getHeadquarters() throws IOException {
        try {
            ResultSet resultSet = selectHeadquarters.getResultSet();
            ArrayList<Headquarters> headquarters = new ArrayList<Headquarters>();
            while (resultSet.next()) {
                String city = resultSet.getString("city");
                String country = resultSet.getString("country");
                int postalCode = resultSet.getInt("postalCode");
                String street = resultSet.getString("street");
                int streetNumber = resultSet.getInt("streetNumber");
                int phoneNumber = resultSet.getInt("phoneNumber");
                headquarters.add(new Headquarters(city, country, postalCode, street, streetNumber, phoneNumber));
            }
            return headquarters;
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
    }
}

package interfaces.persistence;

import model.Category;
import model.Employee;
import model.Headquarters;
import model.Location;

import java.io.IOException;
import java.util.ArrayList;

public interface IDatabase {
    void exportCategories(ArrayList<Category> categories) throws IOException;

    ArrayList<Category> getCategories() throws IOException;

    void exportLocations(ArrayList<Location> locations) throws IOException;

    ArrayList<Location> getLocations() throws IOException;

    void exportEmployees(ArrayList<Employee> employees) throws IOException;

    ArrayList<Employee> getEmployees() throws IOException;

    void exportHeadquarters(ArrayList<Headquarters> headquarters) throws IOException;

    ArrayList<Headquarters> getHeadquarters() throws IOException;
}

package helper;

import interfaces.persistence.IDatabase;
import model.Category;
import model.Employee;
import model.Headquarters;
import model.Location;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class LogAuditHelper implements IDatabase {
    private final IDatabase database;
    private final BufferedWriter bufferedWriter;

    public LogAuditHelper(IDatabase database, String filePath) throws IOException {
        this.database = database;

        FileWriter fileWriter = new FileWriter(filePath, true);
        this.bufferedWriter = new BufferedWriter(fileWriter);
    }

    private void log(String action) throws IOException {
        LocalDateTime localDateTime = LocalDateTime.now();
        Date today = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        String actionThread = Thread.currentThread().getName();
        String csvLine = today + ": " + action + ", thread: " + actionThread + "\n";

        bufferedWriter.append(csvLine);
        bufferedWriter.flush();
    }

    @Override
    public void exportCategories(ArrayList<Category> categories) throws IOException {
        log("Exported Categories");
        database.exportCategories(categories);
    }

    @Override
    public ArrayList<Category> getCategories() throws IOException {
        log("Imported Categories");
        return database.getCategories();
    }

    @Override
    public void exportLocations(ArrayList<Location> locations) throws IOException {
        log("Exported Locations");
        database.exportLocations(locations);
    }

    @Override
    public ArrayList<Location> getLocations() throws IOException {
        log("Imported Locations");
        return database.getLocations();
    }

    @Override
    public void exportEmployees(ArrayList<Employee> employees) throws IOException {
        log("Exported Employees");
        database.exportEmployees(employees);
    }

    @Override
    public ArrayList<Employee> getEmployees() throws IOException {
        log("Imported Employees");
        return database.getEmployees();
    }

    @Override
    public void exportHeadquarters(ArrayList<Headquarters> headquarters) throws IOException {
        log("Exported Headquarters");
        database.exportHeadquarters(headquarters);
    }

    @Override
    public ArrayList<Headquarters> getHeadquarters() throws IOException {
        log("Imported Headquarters");
        return database.getHeadquarters();
    }

    @Override
    public String toString() {
        return "Logs: " + database.toString();
    }
}

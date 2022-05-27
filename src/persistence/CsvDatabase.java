package persistence;

import input.CsvReader;
import interfaces.csv.ICsvTypeFactory;
import interfaces.persistence.IDatabase;
import model.Category;
import model.Employee;
import model.Headquarters;
import model.Location;
import output.CsvWriter;

import java.io.*;
import java.util.ArrayList;


public final class CsvDatabase implements IDatabase {
    private final static String categories = "database/categories.csv";
    private final static String locations = "database/locations.csv";
    private final static String employees = "database/employees.csv";
    private final static String headquarters = "database/headquarters.csv";

    private static <T> ArrayList<T> importFromCsv(String filePath, ICsvTypeFactory<T> typeFactory) throws IOException {
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        CsvReader<T> csvReader = new CsvReader<T>(bufferedReader, typeFactory);
        return csvReader.readAllItems();
    }

    private static <T> void exportToCsv(String filePath, ICsvTypeFactory<T> typeFactory, ArrayList<T> items) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        CsvWriter<T> csvWriter = new CsvWriter<T>(bufferedWriter, typeFactory);
        csvWriter.writeAllItems(items);
    }

    @Override
    public void exportCategories(ArrayList<Category> items) throws IOException {
        exportToCsv(categories, Category.FACTORY, items);
    }

    @Override
    public ArrayList<Category> getCategories() throws IOException {
        return importFromCsv(categories, Category.FACTORY);
    }

    @Override
    public void exportLocations(ArrayList<Location> items) throws IOException {
        exportToCsv(locations, Location.FACTORY, items);
    }

    @Override
    public ArrayList<Location> getLocations() throws IOException {
        return importFromCsv(locations, Location.FACTORY);
    }

    @Override
    public void exportEmployees(ArrayList<Employee> items) throws IOException {
        exportToCsv(employees, Employee.FACTORY, items);
    }

    @Override
    public ArrayList<Employee> getEmployees() throws IOException {
        return importFromCsv(employees, Employee.FACTORY);
    }

    @Override
    public void exportHeadquarters(ArrayList<Headquarters> items) throws IOException {
        exportToCsv(headquarters, Headquarters.FACTORY, items);
    }

    @Override
    public ArrayList<Headquarters> getHeadquarters() throws IOException {
        return importFromCsv(headquarters, Headquarters.FACTORY);
    }
}

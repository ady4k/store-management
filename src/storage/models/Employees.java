package storage.models;

import helper.LogAuditHelper;
import interfaces.IStorable;
import model.Employee;
import persistence.CsvDatabase;

import java.io.IOException;
import java.util.ArrayList;

public class Employees implements IStorable<Employee> {
    private final static CsvDatabase csvDatabase = new CsvDatabase();
    private final static LogAuditHelper database;
    // all employee entities are kept in an Arraylist
    private static ArrayList<Employee> employees = new ArrayList<Employee>();

    static {
        try {
            database = new LogAuditHelper(csvDatabase, "src/database/audit.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // CRUD methods
    @Override
    public void addNewItem(Employee item) {
        employees.add(item);
    }

    @Override
    public ArrayList<Employee> getItem() {
        return employees;
    }

    @Override
    public Employee getItemByIndex(int index) {
        return employees.get(index);
    }

    @Override
    public void updateItem(int index, Employee item) {
        employees.set(index, item);
    }

    @Override
    public void deleteItem(int index) {
        employees.remove(index);
    }

    public void importFromCsv() throws IOException {
        employees = database.getEmployees();
    }

    public void exportToCsv() throws IOException {
        database.exportEmployees(employees);
    }
}

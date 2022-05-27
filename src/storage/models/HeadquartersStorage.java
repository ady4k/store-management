package storage.models;

import helper.LogAuditHelper;
import interfaces.IStorable;
import model.Headquarters;
import persistence.CsvDatabase;

import java.io.IOException;
import java.util.ArrayList;

public class HeadquartersStorage implements IStorable<Headquarters> {
    private final static CsvDatabase csvDatabase = new CsvDatabase();
    private final static LogAuditHelper database;
    // all headquarters entities are kept in an ArrayList collection
    private static ArrayList<Headquarters> headquarters = new ArrayList<Headquarters>();

    static {
        try {
            database = new LogAuditHelper(csvDatabase, "database/audit.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // CRUD methods
    @Override
    public void addNewItem(Headquarters item) {
        headquarters.add(item);
    }

    @Override
    public ArrayList<Headquarters> getItem() {
        return headquarters;
    }

    @Override
    public Headquarters getItemByIndex(int index) {
        return headquarters.get(index);
    }

    @Override
    public void updateItem(int index, Headquarters item) {
        headquarters.set(index, item);
    }

    @Override
    public void deleteItem(int index) {
        headquarters.remove(index);
    }

    public void importFromCsv() throws IOException {
        headquarters = database.getHeadquarters();
    }

    public void exportToCsv() throws IOException {
        database.exportHeadquarters(headquarters);
    }
}

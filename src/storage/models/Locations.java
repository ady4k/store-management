package storage.models;

import helper.LogAuditHelper;
import interfaces.IStorable;
import model.Location;
import persistence.CsvDatabase;

import java.io.IOException;
import java.util.ArrayList;

public class Locations implements IStorable<Location> {
    private static final CsvDatabase csvDatabase = new CsvDatabase();
    private final static LogAuditHelper database;
    // all the location entities will be kept in an ArrayList
    private static ArrayList<Location> locations = new ArrayList<Location>();

    static {
        try {
            database = new LogAuditHelper(csvDatabase, "src/database/audit.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // CRUD methods
    @Override
    public void addNewItem(Location item) {
        locations.add(item);
    }

    @Override
    public ArrayList<Location> getItem() {
        return locations;
    }

    @Override
    public Location getItemByIndex(int index) {
        return locations.get(index);
    }

    @Override
    public void updateItem(int index, Location item) {
        locations.set(index, item);
    }

    @Override
    public void deleteItem(int index) {
        locations.remove(index);
    }

    public void importFromCsv() throws IOException {
        locations = database.getLocations();
    }

    public void exportToCsv() throws IOException {
        database.exportLocations(locations);
    }
}

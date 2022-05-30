package storage.models;

import helper.LogAuditHelper;
import interfaces.IStorable;
import model.Category;
import persistence.CsvDatabase;

import java.io.IOException;
import java.util.ArrayList;

public class Categories implements IStorable<Category> {

    private final static CsvDatabase csvDatabase = new CsvDatabase();
    private final static LogAuditHelper database;
    // all the different types of category entities will be kept in an ArrayList
    private static ArrayList<Category> categories = new ArrayList<Category>();

    static {
        try {
            database = new LogAuditHelper(csvDatabase, "src/database/audit.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // CRUD methods
    @Override
    public void addNewItem(Category item) {
        categories.add(item);
    }

    @Override
    public ArrayList<Category> getItem() {
        return categories;
    }

    @Override
    public Category getItemByIndex(int index) {
        return categories.get(index);
    }

    @Override
    public void updateItem(int index, Category item) {
        categories.set(index, item);
    }

    @Override
    public void deleteItem(int index) {
        categories.remove(index);
    }

    public void importFromCsv() throws IOException {
        categories = database.getCategories();
    }

    public void exportToCsv() throws IOException {
        database.exportCategories(categories);
    }
}

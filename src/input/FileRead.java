package input;

import storage.models.Categories;
import storage.models.Employees;
import storage.models.HeadquartersStorage;
import storage.models.Locations;

import java.io.File;
import java.io.IOException;

public class FileRead {
    private static final Employees employees = new Employees();
    private static final Locations locations = new Locations();
    private static final Categories categories = new Categories();
    private static final HeadquartersStorage headquartersStorage = new HeadquartersStorage();

    private static final File categoriesFile = new File("database/categories.csv");
    private static final File employeesFile = new File("database/employees.csv");
    private static final File headquartersFile = new File("database/headquarters.csv");
    private static final File locationsFile = new File("database/locations.csv");

    public static void readFromFiles() throws IOException {
        if (categoriesFile.length() != 0) {
            categories.importFromCsv();
        }
        if (employeesFile.length() != 0) {
            employees.importFromCsv();
        }
        if (headquartersFile.length() != 0) {
            headquartersStorage.importFromCsv();
        }
        if (locationsFile.length() != 0) {
            locations.importFromCsv();
        }
    }
}

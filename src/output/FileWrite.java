package output;

import storage.models.Categories;
import storage.models.Employees;
import storage.models.HeadquartersStorage;
import storage.models.Locations;

import java.io.IOException;

public class FileWrite {
    private static final Employees employees = new Employees();
    private static final Locations locations = new Locations();
    private static final Categories categories = new Categories();
    private static final HeadquartersStorage headquartersStorage = new HeadquartersStorage();

    public static void writeToFiles() throws IOException {
        categories.exportToCsv();
        employees.exportToCsv();
        headquartersStorage.exportToCsv();
        locations.exportToCsv();
    }
}

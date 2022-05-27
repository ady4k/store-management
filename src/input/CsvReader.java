package input;

import interfaces.csv.ICsvTypeFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class CsvReader<T> {
    private final BufferedReader csvReader;
    private final ICsvTypeFactory<T> typeFactory;

    public CsvReader(BufferedReader csvReader, ICsvTypeFactory<T> typeFactory) throws IOException {
        this.csvReader = csvReader;
        this.typeFactory = typeFactory;

        String[] headers = readLine();
    }

    public T readItem() throws IOException {
        return typeFactory.fromStringArray(readLine());
    }

    public ArrayList<T> readAllItems() throws IOException {
        ArrayList<T> items = new ArrayList<T>();
        while (csvReader.ready()) {
            items.add(readItem());
        }
        return items;
    }

    private String[] readLine() throws IOException {
        String line = csvReader.readLine();
        return line.split(",");
    }
}

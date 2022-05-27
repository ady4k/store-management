package output;

import interfaces.csv.ICsvTypeFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CsvWriter<T> {
    private final BufferedWriter csvWrite; // destination file
    private final ICsvTypeFactory<T> typeFactory; // types of columns

    public CsvWriter(BufferedWriter csvWriter, ICsvTypeFactory<T> typeFactory) throws IOException {
        this.csvWrite = csvWriter;
        this.typeFactory = typeFactory;

        String[] columnNames = typeFactory.getColumnNames();
        writeLine(columnNames);
    }

    public void writeItem(T item) throws IOException {
        String[] strings = typeFactory.toStringArray(item);
        writeLine(strings);
    }

    public void writeAllItems(ArrayList<T> items) throws IOException {
        for (T item : items) {
            writeItem(item);
        }
    }

    private void writeLine(String[] strings) throws IOException {
        String line = String.join(",", strings) + "\n";
        csvWrite.write(line);
    }
}

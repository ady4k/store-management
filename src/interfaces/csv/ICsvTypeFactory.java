package interfaces.csv;

public interface ICsvTypeFactory<T> {
    // gets the column names of csv files returning an array
    String[] getColumnNames();

    // transforms the object into an array of strings which will be used for writing csv files
    String[] toStringArray(T item);

    // transforms the string from a csv file into objects
    T fromStringArray(String[] item);
}

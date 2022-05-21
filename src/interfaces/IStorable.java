package interfaces;

import java.util.ArrayList;

public interface IStorable<T> {
    void addNewItem(T item);
    ArrayList<T> getItem();
    T getItemByIndex(int index);
    void updateItem(int index, T item);
    void deleteItem(int index);
}

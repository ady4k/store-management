package interfaces;

import java.util.ArrayList;

public interface IStorable<T> {
    // typical implementation for an item that can be stored in a collection, (all entities for example)
    // adds for an easier implementation of the storage of the product itself
    // CRUD methods
    void addNewItem(T item);

    ArrayList<T> getItem();

    T getItemByIndex(int index);

    void updateItem(int index, T item);

    void deleteItem(int index);
}

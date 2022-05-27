package interfaces;

public interface IMaintainable<T> {
    // implementation for items that can be maintained, one encapsulation level up from the storable items
    // this will be the typically used methods for modifying the storage of different entities
    // CRUD methods
    T createNewItem(boolean updating);

    void showItems();

    void updateItem();

    void deleteItem();
}

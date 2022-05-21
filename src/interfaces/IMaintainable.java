package interfaces;

public interface IMaintainable<T> {
    T createNewItem(boolean updating);
    void showItems();
    void updateItem();
    void deleteItem();
}

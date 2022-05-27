package interfaces;

public interface ICountable<T> {
    // implementation for entities that have a collection variable, for example the employee list in the shop entity
    // adding and removing methods for countable are very general and will probably not be used as often (if at all)
    // basically one encapsulation level up from the storable form
    // CRUD methods
    void addItemToCountable();

    void removeItemFromCountable();

    void setCountable(T countable);

    void showCountableItems(T countable);

    T getCountable(int index);
}

package interfaces;

public interface ICountable<C> {
    // implementation for entities that have a collection variable, for example the employee list in the shop entity
    // adding and removing methods for countable are very general and will probably not be used as often (if at all)
    // basically one encapsulation level up from the storable form
    // CRUD methods
    void addItemToCountable();

    void removeItemFromCountable();

    void setCountable(C countable);

    void showCountableItems(C countable);

    C getCountable(int index);
}

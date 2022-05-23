package interfaces;

public interface ICountable<C> {
    void addItemToCountable();

    void removeItemFromCountable();

    void setCountable(C countable);

    void showCountableItems(C countable);

    C getCountable(int index);
}

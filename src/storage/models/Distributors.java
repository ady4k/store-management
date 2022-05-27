package storage.models;

import interfaces.IStorable;
import model.Distributor;

import java.util.ArrayList;

public class Distributors implements IStorable<Distributor> {
    // all distributor entities are kept in an ArrayList collection
    private final static ArrayList<Distributor> distributors = new ArrayList<Distributor>();

    // CRUD methods
    @Override
    public void addNewItem(Distributor item) {
        distributors.add(item);
    }

    @Override
    public ArrayList<Distributor> getItem() {
        return distributors;
    }

    @Override
    public Distributor getItemByIndex(int index) {
        return distributors.get(index);
    }

    @Override
    public void updateItem(int index, Distributor item) {
        distributors.set(index, item);
    }

    @Override
    public void deleteItem(int index) {
        distributors.remove(index);
    }
}

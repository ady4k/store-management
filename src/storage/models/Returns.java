package storage.models;

import interfaces.IStorable;
import model.Return;

import java.util.ArrayList;

public class Returns implements IStorable<Return> {
    // all the return entities will be kept in an ArrayList
    private final static ArrayList<Return> returns = new ArrayList<Return>();

    // CRUD methods
    @Override
    public void addNewItem(Return item) {
        returns.add(item);
    }

    @Override
    public ArrayList<Return> getItem() {
        return returns;
    }

    @Override
    public Return getItemByIndex(int index) {
        return returns.get(index);
    }

    @Override
    public void updateItem(int index, Return item) {
        returns.set(index, item);
    }

    @Override
    public void deleteItem(int index) {
        returns.remove(index);
    }
}

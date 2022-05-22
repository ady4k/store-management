package storage;

import interfaces.IStorable;
import model.Headquarters;

import java.util.ArrayList;

public class HeadquartersStorage implements IStorable<Headquarters> {
    private final static ArrayList<Headquarters> headquarters = new ArrayList<Headquarters>();

    @Override
    public void addNewItem(Headquarters item) {
        headquarters.add(item);
    }

    @Override
    public ArrayList<Headquarters> getItem() {
        return headquarters;
    }

    @Override
    public Headquarters getItemByIndex(int index) {
        return headquarters.get(index);
    }

    @Override
    public void updateItem(int index, Headquarters item) {
        headquarters.set(index, item);
    }

    @Override
    public void deleteItem(int index) {
        headquarters.remove(index);
    }
}

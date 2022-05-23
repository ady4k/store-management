package storage;

import interfaces.IStorable;
import model.Location;

import java.util.ArrayList;

public class Locations implements IStorable<Location> {
    private static final ArrayList<Location> locations = new ArrayList<Location>();

    @Override
    public void addNewItem(Location item) {
        locations.add(item);
    }

    @Override
    public ArrayList<Location> getItem() {
        return locations;
    }

    @Override
    public Location getItemByIndex(int index) {
        return locations.get(index);
    }

    @Override
    public void updateItem(int index, Location item) {
        locations.set(index, item);
    }

    @Override
    public void deleteItem(int index) {
        locations.remove(index);
    }
}

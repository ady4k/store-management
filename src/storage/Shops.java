package storage;

import interfaces.IStorable;
import model.Shop;

import java.util.ArrayList;

public class Shops implements IStorable<Shop> {
    private final static ArrayList<Shop> shops = new ArrayList<Shop>();

    @Override
    public void addNewItem(Shop item) {
        shops.add(item);
    }

    @Override
    public ArrayList<Shop> getItem() {
        return shops;
    }

    @Override
    public Shop getItemByIndex(int index) {
        return shops.get(index);
    }

    @Override
    public void updateItem(int index, Shop item) {
        shops.set(index, item);
    }

    @Override
    public void deleteItem(int index) {
        shops.remove(index);
    }
}

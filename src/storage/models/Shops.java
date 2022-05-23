package storage.models;

import interfaces.IStorable;
import model.Employee;
import model.Shop;

import java.util.ArrayList;

public class Shops implements IStorable<Shop> {
    private final static ArrayList<Shop> shops = new ArrayList<Shop>();

    public boolean isEmpty() {
        return shops.isEmpty();
    }

    public ArrayList<Employee> getEmployeesByShop(Shop shop) {
        int index = shops.indexOf(shop);
        return shops.get(index).getEmployees();
    }

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

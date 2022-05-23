package storage.models;

import interfaces.IStorable;
import model.Order;

import java.util.ArrayList;

public class Orders implements IStorable<Order> {
    private final static ArrayList<Order> orders = new ArrayList<Order>();

    @Override
    public void addNewItem(Order item) {
        orders.add(item);
    }

    @Override
    public ArrayList<Order> getItem() {
        return orders;
    }

    @Override
    public Order getItemByIndex(int index) {
        return orders.get(index);
    }

    @Override
    public void updateItem(int index, Order item) {
        orders.set(index, item);
    }

    @Override
    public void deleteItem(int index) {
        orders.remove(index);
    }
}

package storage.models;

import interfaces.IStorable;
import model.Order;
import model.Product;

import java.util.ArrayList;
import java.util.Map;

public class Orders implements IStorable<Order> {
    // all the order entities will be kept in an ArrayList collection
    private final static ArrayList<Order> orders = new ArrayList<Order>();

    // CRUD methods
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

    // returns the product list based on the order given
    public Map<Product, Integer> getProductsByOrder(Order order) {
        int index = orders.indexOf(order);
        return getItemByIndex(index).getProducts();
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

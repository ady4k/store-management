package storage.custom;

import model.Order;
import model.Shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopOrder {
    // a HashMap will be used to store the list of orders made by a specific shop
    private final static Map<Shop, ArrayList<Order>> ordersByShop = new HashMap<Shop, ArrayList<Order>>();

    // methods have a similar implementation to those used in the IStorable interface
    // CRUD methods
    public void addNewItem(Shop key, ArrayList<Order> item) {
        ordersByShop.put(key, item);
    }

    public Map<Shop, ArrayList<Order>> getItem() {
        return ordersByShop;
    }

    public ArrayList<Order> getItemByKey(Shop key) {
        return ordersByShop.get(key);
    }

    public void updateItem(Shop key, ArrayList<Order> item) {
        // if the shop doesn't yet have an order added to its name, the map doesn't have an entry with the specific shop-key
        // therefore, a new entry will be put
        if (ordersByShop.containsKey(key)) {
            // if the shop is already in the map, the entry will be replaced with the new list of orders
            ordersByShop.replace(key, item);
            return;
        }
        addNewItem(key, item);
    }

    public void deleteItem(Shop key) {
        ordersByShop.remove(key);
    }
}

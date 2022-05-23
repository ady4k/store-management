package storage.custom;

import model.Order;
import model.Shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopOrder {
    // map
    private final static Map<Shop, ArrayList<Order>> ordersByShop = new HashMap<Shop, ArrayList<Order>>();

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
        if (ordersByShop.containsKey(key)) {
            ordersByShop.replace(key, item);
            return;
        }
        addNewItem(key, item);
    }

    public void deleteItem(Shop key) {
        ordersByShop.remove(key);
    }
}

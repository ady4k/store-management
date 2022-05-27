package storage.custom;

import model.Return;
import model.Shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopReturn {
    // a HashMap is used to store all the returns of a specific shop
    private final static Map<Shop, ArrayList<Return>> returnsByShop = new HashMap<Shop, ArrayList<Return>>();

    // methods have a similar functionality with the IStorable interface
    public void addNewItem(Shop key, ArrayList<Return> item) {
        returnsByShop.put(key, item);
    }

    public Map<Shop, ArrayList<Return>> getItem() {
        return returnsByShop;
    }

    public ArrayList<Return> getItemByKey(Shop key) {
        return returnsByShop.get(key);
    }

    // typically used method for adding/modifying the list of returns of a specific shop
    public void updateItem(Shop key, ArrayList<Return> item) {
        // if the shop doesn't have any return, therefore is not in the map, a new entry will be added
        if (returnsByShop.containsKey(key)) {
            // replaces the return list with the new one
            returnsByShop.replace(key, item);
            return;
        }
        addNewItem(key, item);
    }

    public void deleteItem(Shop key) {
        returnsByShop.remove(key);
    }
}

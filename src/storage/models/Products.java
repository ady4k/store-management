package storage.models;

import interfaces.IStorable;
import model.Product;

import java.util.ArrayList;

public class Products implements IStorable<Product> {
    // all product entities will be stored in an ArrayList
    private final static ArrayList<Product> products = new ArrayList<Product>();

    // CRUD methods
    @Override
    public void addNewItem(Product item) {
        products.add(item);
    }

    @Override
    public ArrayList<Product> getItem() {
        return products;
    }

    @Override
    public Product getItemByIndex(int index) {
        return products.get(index);
    }

    @Override
    public void updateItem(int index, Product item) {
        products.set(index, item);
    }

    @Override
    public void deleteItem(int index) {
        products.remove(index);
    }


}

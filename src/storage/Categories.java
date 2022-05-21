package storage;

import interfaces.IStorable;
import model.Category;

import java.util.ArrayList;

public class Categories implements IStorable<Category> {
    private static final ArrayList<Category> categories = new ArrayList<Category>();
    @Override
    public void addNewItem(Category item) {
        categories.add(item);
    }

    @Override
    public ArrayList<Category> getItem() {
        return categories;
    }

    @Override
    public Category getItemByIndex(int index) {
        return categories.get(index);
    }

    @Override
    public void updateItem(int index, Category item) {
        categories.set(index, item);
    }

    @Override
    public void deleteItem(int index) {
        categories.remove(index);
    }
}

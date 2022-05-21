package service;

import interfaces.IMaintainable;
import model.Category;
import model.Location;
import storage.Categories;

import java.util.ArrayList;
import java.util.Scanner;

public class CategoryService implements IMaintainable<Category> {
    private final static Categories storage = new Categories();
    private final static Scanner scanner = new Scanner(System.in);
    @Override
    public Category createNewItem(boolean updating) {
        System.out.print("Category name: ");
        String name = scanner.next();

        Category toAdd = new Category(name);
        if (updating) {
            return toAdd;
        }

        storage.addNewItem(toAdd);
        System.out.println("Item successfully created and added to the storage.");
        return toAdd;
    }

    @Override
    public void showItems() {
        ArrayList<Category> categories = storage.getItem();
        System.out.println("Full list of categories:");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println("Category " + (i + 1) + " - " + categories.get(i));
        }
        System.out.println();
    }

    @Override
    public void updateItem() {
        System.out.println("Choose an item from the following list:");
        showItems();

        int index = scanner.nextInt() - 1;
        Category category = storage.getItemByIndex(index);

        System.out.println("New category name:");
        String name = scanner.next();

        category.setName(name);
        storage.updateItem(index, category);

        System.out.println("Item successfully updated.");
    }

    @Override
    public void deleteItem() {
        System.out.println("Choose an item from the following list:");
        showItems();

        storage.deleteItem(scanner.nextInt() - 1);

        System.out.println("Item successfully deleted!");
    }
}

package service.models;

import interfaces.IMaintainable;
import model.Category;
import storage.models.Categories;

import java.util.ArrayList;
import java.util.Scanner;

public class CategoryService implements IMaintainable<Category> {
    private final static Categories storage = new Categories();
    private final static Scanner scanner = new Scanner(System.in);

    // encapsulated CRUD methods for the storage of each entity
    @Override
    public Category createNewItem(boolean updating) {
        System.out.print("Category name: ");
        String name = scanner.next();

        Category toAdd = new Category(name);

        // category doesn't exist => gets added to the storage
        storage.addNewItem(toAdd);
        System.out.println("Item successfully created and added to the storage.");
        return toAdd;
    }

    @Override
    public void showItems() {
        // all the categories are stored in the respective variable
        ArrayList<Category> categories = storage.getItem();

        // shows all categories
        System.out.println("Full list of categories:");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println("Category " + (i + 1) + " - " + categories.get(i));
        }
        // new line
        System.out.println();
    }

    @Override
    public void updateItem() {
        System.out.println("Choose an item from the following list:");
        // calls the method to show all the categories
        showItems();

        // index variable for the chosen item
        int index = scanner.nextInt() - 1;
        // chosen item gets selected from the storage and stored in the following variable
        Category category = storage.getItemByIndex(index);

        System.out.println("New category name:");
        String name = scanner.next();

        // category's name is updated
        category.setName(name);

        // updates the category in the storage
        storage.updateItem(index, category);

        System.out.println("Item successfully updated.");
    }

    @Override
    public void deleteItem() {
        System.out.println("Choose an item from the following list:");
        // calls the method to show all the categories
        showItems();

        // deleted the selected category from the storage
        storage.deleteItem(scanner.nextInt() - 1);

        System.out.println("Item successfully deleted!");
    }

    // method that returns the category based on its index in the storage
    public Category getCategoryByIndex(int index) {
        return storage.getItemByIndex(index);
    }
}

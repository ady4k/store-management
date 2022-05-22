package service;

import interfaces.ICountable;
import interfaces.IMaintainable;
import model.Inventory;
import model.Product;
import storage.InventoryStorage;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class InventoryService implements IMaintainable<Inventory>, ICountable<Map<Product, Integer>> {
    private final static InventoryStorage storage = new InventoryStorage();
    private final static ProductService productService = new ProductService();
    private final static Scanner scanner = new Scanner(System.in);

    @Override
    public void addItemToCountable() {
        System.out.println("Select an item from the following list:");
        productService.showItems();
        Product product = productService.getProductByIndex(scanner.nextInt() - 1);
        System.out.println("Product quantity (units):");
        int quantity = scanner.nextInt();

        Map<Product, Integer> countable = getCountable();

        countable.put(product, quantity);
        System.out.println("Product - quantity successfully added to the list");
    }

    @Override
    public void removeItemFromCountable() {
        Map<Product, Integer> countable = getCountable();
        System.out.println("Choose an item from the following list:");
        showCountableItems(countable);
        ArrayList<Product> keyList = new ArrayList<Product>(countable.keySet());
        Product toRemove = keyList.get(scanner.nextInt() - 1);

        countable.remove(toRemove);
        System.out.println("Item successfully removed from the list.");
    }

    @Override
    public void setCountable(Map<Product, Integer> countable) {
        System.out.println("Choose an item from the following list:");
        showItems();
        storage.getItemByIndex(scanner.nextInt() - 1).setProducts(countable);
    }

    @Override
    public void showCountableItems(Map<Product, Integer> countable) {
        for (Product key : countable.keySet()) {
            System.out.println(key.getName() + ":" + countable.get(key) + "\n");
        }
    }

    @Override
    public Map<Product, Integer> getCountable() {
        System.out.println("Choose an item from the following list:");
        showItems();
        return storage.getItemByIndex(scanner.nextInt() - 1).getProducts();
    }

    @Override
    public Inventory createNewItem(boolean updating) {
        System.out.print("Total inventory capacity: ");
        int totalCapacity = scanner.nextInt();

        Inventory toAdd = new Inventory(totalCapacity);

        storage.addNewItem(toAdd);
        System.out.println("Item successfully created and added to the storage.");
        return toAdd;
    }

    @Override
    public void showItems() {
        ArrayList<Inventory> inventories = storage.getItem();
        System.out.println("Full list of inventories:");
        for (int i = 0; i < inventories.size(); i++) {
            System.out.println("Inventory " + (i + 1) + " - " + inventories.get(i));
        }
        System.out.println();
    }

    @Override
    public void updateItem() {
        System.out.println("Choose an item from the following list:");
        showItems();
        int index = scanner.nextInt() - 1;
        Inventory inventory = storage.getItemByIndex(index);
        System.out.println("New total capacity:");
        int totalCapacity = scanner.nextInt();

        inventory.setTotalCapacity(totalCapacity);
        storage.updateItem(index, inventory);
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

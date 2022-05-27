package service.models;

import interfaces.ICountable;
import interfaces.IMaintainable;
import model.Inventory;
import model.Product;
import storage.models.InventoryStorage;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class InventoryService implements IMaintainable<Inventory>, ICountable<Map<Product, Integer>> {
    private final static InventoryStorage storage = new InventoryStorage();
    private final static ProductService productService = new ProductService();
    private final static Scanner scanner = new Scanner(System.in);

    // add an item to the inventory's list of products (countable)
    @Override
    public void addItemToCountable() {
        System.out.println("Select the inventory you want to add an item to:");
        showItems(); // shows the list of all inventories available

        // the inventory's index, item and list of products gets stored in the following variables;
        int index = scanner.nextInt() - 1;
        Inventory inventory = storage.getItemByIndex(index);
        Map<Product, Integer> countable = getCountable(index);

        System.out.println("Select an item from the following list:");
        productService.showItems(); // shows the list of all available products to choose from

        // the chosen product gets stored in a variable
        Product product = productService.getProductByIndex(scanner.nextInt() - 1);

        // the quantity for the product
        System.out.println("Product quantity (units):");
        int quantity = scanner.nextInt();

        // modifies the capacity while also checking if there is enough space in the inventory
        // the new item will not be added if there is no space available
        if (inventory.modifyCapacity(product.getWeight() * quantity, true)) {

            // after modifying the capacity successfully, the item gets mapped to the inventory
            countable.put(product, quantity);
            System.out.println("Product - quantity successfully added to the list");
        }
    }

    // removes an item from the inventory's list of products (countable)
    @Override
    public void removeItemFromCountable() {
        System.out.println("Select the inventory you want to remove an item from:");
        showItems(); // shows the list of all inventories available

        // the inventory's index, item and list of products gets stored in the following variables
        int index = scanner.nextInt() - 1;
        Inventory inventory = storage.getItemByIndex(index);
        Map<Product, Integer> countable = getCountable(index);

        System.out.println("Choose an item from the following list:");
        showCountableItems(countable); // shows the list of all available products in the inventory

        // stores all the products available in the inventory in the following variable
        ArrayList<Product> keyList = new ArrayList<Product>(countable.keySet());

        // the product's chosen index and its quantity is being stored for modification purposes
        index = scanner.nextInt() - 1;
        Product toRemove = keyList.get(index);
        int quantity = countable.get(toRemove);

        // inventory's capacity is being modified
        inventory.modifyCapacity(toRemove.getWeight() * quantity, false);

        // removes the product from the inventory
        countable.remove(toRemove);
        System.out.println("Item successfully removed from the list.");
    }

    // sets a list of products to an inventory
    @Override
    public void setCountable(Map<Product, Integer> countable) {
        System.out.println("Choose an item from the following list:");
        showItems(); // shows the list of available inventories

        // sets the list of products to the selected inventory
        storage.getItemByIndex(scanner.nextInt() - 1).setProducts(countable);
    }

    // shows all lists of products available (from all inventories)
    @Override
    public void showCountableItems(Map<Product, Integer> countable) {
        // goes through all keys of the map and shows the product : quantity
        for (Product key : countable.keySet()) {
            System.out.println(key.getName() + ":" + countable.get(key) + "\n");
        }
    }

    // returns the list of products based on the inventory index
    @Override
    public Map<Product, Integer> getCountable(int index) {
        return storage.getItemByIndex(index).getProducts();
    }

    // creates a new inventory with only the total capacity
    @Override
    public Inventory createNewItem(boolean updating) {
        System.out.print("Total inventory capacity: ");
        int totalCapacity = scanner.nextInt();

        // creates the inventory using the constructor
        Inventory toAdd = new Inventory(totalCapacity);

        // the new item gets added to the storage and returned
        storage.addNewItem(toAdd);
        System.out.println("Item successfully created and added to the storage.");
        return toAdd;
    }

    // shows all the available inventories
    @Override
    public void showItems() {
        // the list of all inventories gets stored in the following variable
        ArrayList<Inventory> inventories = storage.getItem();

        // all inventories gets shown
        System.out.println("Full list of inventories:");
        for (int i = 0; i < inventories.size(); i++) {
            System.out.println("Inventory " + (i + 1) + " - " + inventories.get(i));
        }
        System.out.println();
    }

    // updates an existing inventory
    @Override
    public void updateItem() {
        System.out.println("Choose an item from the following list:");
        showItems(); // shows the list of all inventories

        // the chosen inventory's index and item gets stored in the following variables
        int index = scanner.nextInt() - 1;
        Inventory inventory = storage.getItemByIndex(index);

        // only the total capacity will be able to be modified
        System.out.println("New total capacity:");
        int totalCapacity = scanner.nextInt();

        // inventory's total capacity will be modified based by the restrictions in the entity class
        inventory.setTotalCapacity(totalCapacity);

        // storage gets updated with the new inventory
        storage.updateItem(index, inventory);
        System.out.println("Item successfully updated.");
    }

    @Override
    public void deleteItem() {
        System.out.println("Choose an item from the following list:");
        showItems(); // shows the list of all available inventories

        // deleted the inventory based on the index
        storage.deleteItem(scanner.nextInt() - 1);

        System.out.println("Item successfully deleted!");
    }


}

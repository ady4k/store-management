package service.models;

import interfaces.IMaintainable;
import model.Category;
import model.Distributor;
import model.Product;
import storage.models.Products;

import java.util.ArrayList;
import java.util.Scanner;

public class ProductService implements IMaintainable<Product> {
    private final static DistributorService distributorService = new DistributorService();
    private final static CategoryService categoryService = new CategoryService();
    private final static Products storage = new Products();

    private final static Scanner scanner = new Scanner(System.in);

    @Override
    public Product createNewItem(boolean updating) {
        System.out.print("Product name: ");
        String name = scanner.nextLine();

        System.out.print("Select a distributor: ");
        distributorService.showItems(); // shows all the distributors available

        // the chosen distributor gets kept in the following variable
        Distributor distributor = distributorService.getDistributorByIndex(scanner.nextInt() - 1);

        System.out.print("Select a product category: ");
        categoryService.showItems(); // shows all the categories available

        // the chosen category gets ket in the following variable
        Category category = categoryService.getCategoryByIndex(scanner.nextInt() - 1);

        System.out.print("Enter the product price in $: ");
        float price = scanner.nextFloat();

        System.out.print("Enter the product weight in kg: ");
        float weight = scanner.nextFloat();

        // calls the constructors to create a new product
        // restrictions are not applied here
        Product toAdd = new Product(name, distributor, category, price, weight);

        // if we are creating a new product in order to update a current one, the item will not get added to the storage
        // the item will only be returned
        if (updating) {
            return toAdd;
        }

        // add the newly created item to the storage and returns it
        storage.addNewItem(toAdd);
        System.out.println("Item successfully created and added to the storage.");
        return toAdd;
    }

    @Override
    public void showItems() {
        // stores all the available products in the following variable
        ArrayList<Product> products = storage.getItem();

        // shows all the products available
        System.out.println("Full list of products:");
        for (int i = 0; i < products.size(); i++) {
            System.out.println("Product " + (i + 1) + " - " + products.get(i));
        }
        System.out.println();
    }

    @Override
    public void updateItem() {
        System.out.println("Choose an item from the following list:");
        showItems(); // shows all the products

        // stores the index of the chosen product in a variable and the select product based on the index
        int index = scanner.nextInt() - 1;
        Product product = storage.getItemByIndex(index);

        // shows the options available to modify the product
        System.out.println("1 name / 2 price / 3 weight / 4 entire item");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> {
                System.out.println("New name:");
                String name = scanner.nextLine();
                product.setName(name);
            }
            case 2 -> {
                System.out.println("New price in $:");
                float price = scanner.nextFloat();
                product.setPrice(price); // restrictions apply
            }
            case 3 -> {
                System.out.println("New weight in kg:");
                float weight = scanner.nextFloat();
                product.setWeight(weight); // restrictions apply
            }

            // entire item gets recreated
            case 4 -> product = createNewItem(true);
            default -> System.out.println("Invalid option selected, returning to last menu.");
        }

        // after finishing the operations, the storage is updated with the new item
        storage.updateItem(index, product);
        System.out.println("Item successfully updated.");

    }

    @Override
    public void deleteItem() {
        System.out.println("Choose an item from the following list:");
        showItems(); // shows the list of all products

        // deleted from the storage the selected product
        storage.deleteItem(scanner.nextInt() - 1);

        System.out.println("Item successfully deleted!");
    }

    // return the product from the storage based on its index
    public Product getProductByIndex(int index) {
        return storage.getItemByIndex(index);
    }
}

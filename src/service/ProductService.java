package service;

import interfaces.IMaintainable;
import model.Category;
import model.Distributor;
import model.Product;
import storage.Products;

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
        distributorService.showItems();

        Distributor distributor = distributorService.getDistributorByIndex(scanner.nextInt() - 1);

        System.out.print("Select a product category: ");
        categoryService.showItems();
        Category category = categoryService.getCategoryByIndex(scanner.nextInt() - 1);

        System.out.print("Enter the product price in $: ");
        float price = scanner.nextFloat();

        System.out.print("Enter the product weight in kg: ");
        float weight = scanner.nextFloat();

        Product toAdd = new Product(name, distributor, category, price, weight);
        if (updating) {
            return toAdd;
        }

        storage.addNewItem(toAdd);
        System.out.println("Item successfully created and added to the storage.");
        return toAdd;
    }

    @Override
    public void showItems() {
        ArrayList<Product> products = storage.getItem();
        System.out.println("Full list of products:");
        for (int i = 0; i < products.size(); i++) {
            System.out.println("Product " + (i + 1) + " - " + products.get(i));
        }
        System.out.println();
    }

    @Override
    public void updateItem() {
        System.out.println("Choose an item from the following list:");
        showItems();
        int index = scanner.nextInt() - 1;
        Product product = storage.getItemByIndex(index);
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
                product.setPrice(price);
            }
            case 3 -> {
                System.out.println("New weight in kg:");
                float weight = scanner.nextFloat();
                product.setWeight(weight);
            }
            case 4 -> product = createNewItem(true);
            default -> System.out.println("Invalid option selected, returning to last menu.");
        }
        storage.updateItem(index, product);
        System.out.println("Item successfully updated.");

    }

    @Override
    public void deleteItem() {
        System.out.println("Choose an item from the following list:");
        showItems();

        storage.deleteItem(scanner.nextInt() - 1);

        System.out.println("Item successfully deleted!");
    }

    public Product getProductByIndex(int index) {
        return storage.getItemByIndex(index);
    }
}

package service.models;

import interfaces.IMaintainable;
import model.Distributor;
import model.Headquarters;
import storage.models.Distributors;

import java.util.ArrayList;
import java.util.Scanner;

public class DistributorService implements IMaintainable<Distributor> {
    private final static Distributors storage = new Distributors();
    private final static HeadquartersService headquarters = new HeadquartersService();
    private final static Scanner scanner = new Scanner(System.in);

    @Override
    public Distributor createNewItem(boolean updating) {
        System.out.print("Distributor name: ");
        String name = scanner.nextLine();

        // creates a new headquarters item without initializing it
        Headquarters hq;
        if (updating) {
            // if updating, the new headquarters is being updated using the create method
            hq = headquarters.createNewItem(true);
        } else {
            // the headquarters is being created from its service class method
            hq = headquarters.createNewItem(false);
        }

        // creates the distributor using the constructor
        Distributor toAdd = new Distributor(name, hq);

        // if we are updating an already existent distributor, we are not adding it anymore to the storage
        // we are only returning the product
        if (updating) {
            return toAdd;
        }

        // the newly created item gets added to its storage and then returned
        storage.addNewItem(toAdd);
        System.out.println("Item successfully created and added to the storage.");
        return toAdd;
    }

    @Override
    public void showItems() {
        // the list of all the distributors is being kept in the following variable
        ArrayList<Distributor> distributors = storage.getItem();

        // shows the list of all distributors
        System.out.println("Full list of distributors:");
        for (int i = 0; i < distributors.size(); i++) {
            System.out.println("Distributor " + (i + 1) + " - " + distributors.get(i));
        }
        System.out.println();
    }

    @Override
    public void updateItem() {
        System.out.println("Choose an item from the following list:");
        showItems(); // shows the list of all distributors

        // the index of the chosen distributor is being stored in a variable and the distributor is selected using the index
        int index = scanner.nextInt() - 1;
        Distributor distributor = storage.getItemByIndex(index);

        // list of operations that can be done to the ite,
        System.out.println("1 name / 2 entire item");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> {
                System.out.println("New name:");
                String name = scanner.nextLine();
                distributor.setName(name);
            }
            case 2 -> distributor = createNewItem(true);
            default -> System.out.println("Invalid option selected, returning to last menu.");
        }

        // the updated item gets updated in the storage as well
        storage.updateItem(index, distributor);
        System.out.println("Item successfully updated.");
    }

    @Override
    public void deleteItem() {
        System.out.println("Choose an item from the following list:");
        showItems(); // shows the list of all distributors

        // the selected distributor gets deleted from the storage
        storage.deleteItem(scanner.nextInt() - 1);

        System.out.println("Item successfully deleted!");
    }

    // returns the distributor from the storage based on its index
    public Distributor getDistributorByIndex(int index) {
        return storage.getItemByIndex(index);
    }
}

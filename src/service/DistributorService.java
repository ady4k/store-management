package service;

import interfaces.IMaintainable;
import model.Distributor;
import model.Headquarters;
import storage.Distributors;

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

        Headquarters hq = new Headquarters();
        if (updating) {
            hq = headquarters.createNewItem(true);
        } else {
            hq = headquarters.createNewItem(false);
        }

        Distributor toAdd = new Distributor(name, hq);
        if (updating) {
            return toAdd;
        }

        storage.addNewItem(toAdd);
        System.out.println("Item successfully created and added to the storage.");
        return toAdd;
    }

    @Override
    public void showItems() {
        ArrayList<Distributor> distributors = storage.getItem();
        System.out.println("Full list of distributors:");
        for (int i = 0; i < distributors.size(); i++) {
            System.out.println("Distributor " + (i + 1) + " - " + distributors.get(i));
        }
        System.out.println();
    }

    @Override
    public void updateItem() {
        System.out.println("Choose an item from the following list:");
        showItems();
        int index = scanner.nextInt() - 1;
        Distributor distributor = storage.getItemByIndex(index);
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
        storage.updateItem(index, distributor);
        System.out.println("Item successfully updated.");
    }

    @Override
    public void deleteItem() {
        System.out.println("Choose an item from the following list:");
        showItems();

        storage.deleteItem(scanner.nextInt() - 1);

        System.out.println("Item successfully deleted!");
    }

    public Distributor getDistributorByIndex(int index) {
        return storage.getItemByIndex(index);
    }
}

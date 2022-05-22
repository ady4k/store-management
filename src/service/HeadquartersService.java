package service;

import interfaces.IMaintainable;
import model.Headquarters;
import storage.HeadquartersStorage;

import java.util.ArrayList;
import java.util.Scanner;

public class HeadquartersService implements IMaintainable<Headquarters> {
    private final static HeadquartersStorage storage = new HeadquartersStorage();
    private final static Scanner scanner = new Scanner(System.in);
    @Override
    public Headquarters createNewItem(boolean updating) {
        System.out.print("City:");
        String city = scanner.next();

        System.out.print("Country:");
        String country = scanner.next();

        System.out.print("Postal code:");
        int postalCode = scanner.nextInt();

        System.out.print("Street name and number:");
        String streetName = scanner.next();
        int streetNumber = scanner.nextInt();

        System.out.print("Phone number:");
        int phoneNumber = scanner.nextInt();

        Headquarters toAdd = new Headquarters(city, country, postalCode, streetName, streetNumber, phoneNumber);
        if (updating) {
            return toAdd;
        }

        storage.addNewItem(toAdd);
        System.out.println("Item successfully created and added to the storage.");
        return toAdd;
    }

    @Override
    public void showItems() {
        ArrayList<Headquarters> headquarters = storage.getItem();
        System.out.println("Full list of headquarters:");
        for (int i = 0; i < headquarters.size(); i++) {
            System.out.println("Headquarters " + (i + 1) + " - " + headquarters.get(i));
        }
        System.out.println();
    }

    @Override
    public void updateItem() {
        System.out.println("Choose an item from the following list:");
        showItems();
        int index = scanner.nextInt() - 1;
        Headquarters headquarters = storage.getItemByIndex(index);
        System.out.println("1 phone number / 2 headquarters location");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> {
                System.out.println("New phone number:");
                int phoneNumber = scanner.nextInt();
                headquarters.setPhoneNumber(phoneNumber);
            }
            case 2 -> headquarters = createNewItem(true);
            default -> System.out.println("Invalid option selected, returning to last menu.");
        }
        storage.updateItem(index, headquarters);
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

package service.models;

import interfaces.IMaintainable;
import model.Headquarters;
import storage.models.HeadquartersStorage;

import java.util.ArrayList;
import java.util.Scanner;

public class HeadquartersService implements IMaintainable<Headquarters> {
    private final static LocationService locationService = new LocationService();
    private final static HeadquartersStorage storage = new HeadquartersStorage();
    private final static Scanner scanner = new Scanner(System.in);

    // creates a new headquarters
    @Override
    public Headquarters createNewItem(boolean updating) {
        // country is selected using the special location method
        String country = locationService.continentSelection();

        System.out.print("City:");
        String city = scanner.next();

        System.out.print("Postal code:");
        int postalCode = scanner.nextInt();

        System.out.print("Street name and number:");
        String streetName = scanner.next();
        int streetNumber = scanner.nextInt();

        System.out.print("Phone number:");
        int phoneNumber = scanner.nextInt();

        // headquarters full constructor called and creates a new item
        Headquarters toAdd = new Headquarters(city, country, postalCode, streetName, streetNumber, phoneNumber);

        // if the method is called because of an update, the item is no longer added to the storage, it's only returned
        if (updating) {
            return toAdd;
        }

        // adds the item to the storage and returns it
        storage.addNewItem(toAdd);

        System.out.println("Item successfully created and added to the storage.");
        return toAdd;
    }

    @Override
    public void showItems() {
        // all headquarters gets stored in the following variable
        ArrayList<Headquarters> headquarters = storage.getItem();

        // shows all of the headquarters
        System.out.println("Full list of headquarters:");

        for (int i = 0; i < headquarters.size(); i++) {
            System.out.println("Headquarters " + (i + 1) + " - " + headquarters.get(i));
        }
        System.out.println();
    }

    @Override
    public void updateItem() {
        System.out.println("Choose an item from the following list:");
        showItems(); // shows all the headquarters

        // the index of the selected headquarters is stored and used to get the referred headquarters
        int index = scanner.nextInt() - 1;
        Headquarters headquarters = storage.getItemByIndex(index);

        // which property to be updated
        System.out.println("1 phone number / 2 headquarters location");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> {
                System.out.println("New phone number:");
                int phoneNumber = scanner.nextInt();

                // phone number restrictions from the entity class are applied in this case
                headquarters.setPhoneNumber(phoneNumber);
            }
            case 2 -> headquarters = createNewItem(true);
            default -> System.out.println("Invalid option selected, returning to last menu.");
        }

        // updates the item in the storage
        storage.updateItem(index, headquarters);
        System.out.println("Item successfully updated.");
    }

    @Override
    public void deleteItem() {
        System.out.println("Choose an item from the following list:");
        showItems(); // shows the list of headquarters

        // deleted the selected item from the storage
        storage.deleteItem(scanner.nextInt() - 1);

        System.out.println("Item successfully deleted!");
    }
}

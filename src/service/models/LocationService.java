package service.models;

import interfaces.IMaintainable;
import model.Location;
import storage.models.Locations;
import storage.predefined.Continents;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LocationService implements IMaintainable<Location> {
    private static final List<List<String>> continents = Continents.getContinents();
    private static final Locations storage = new Locations();
    private static final Scanner scanner = new Scanner(System.in);

    // method to check if the location is already in the storage
    // if it is, it will return it instead of adding a new one
    public Location searchForLocation(Location item) {
        ArrayList<Location> locations = storage.getItem();
        for (Location location : locations) {
            if (location == item) {
                return location;
            }
        }
        return item;
    }

    @Override
    public Location createNewItem(boolean updating) {
        // country is selected from the collection of all countries-continents
        String country = continentSelection();

        // city name is free to write as it doesn't influence anything
        System.out.print("City:");
        String city = scanner.next();

        // location gets created
        Location toAdd = new Location(city, country);

        // if updating, it isn't added to the storage, it's only returned
        if (updating) {
            return toAdd;
        }

        // adds the newly created ite in the storage
        storage.addNewItem(toAdd);
        System.out.println("Item successfully created and added to the storage.");
        return toAdd;
    }

    @Override
    public void showItems() {
        // gets the list of all the locations from its collection
        ArrayList<Location> locations = storage.getItem();

        // shows the locations
        System.out.println("Full list of locations:");
        for (int i = 0; i < locations.size(); i++) {
            System.out.println("Location " + (i + 1) + " - " + locations.get(i));
        }
        System.out.println();
    }

    @Override
    public void updateItem() {
        System.out.println("Choose an item from the following list:");
        // shows all the locations
        showItems();

        // select and store the index and the location based on the index
        int index = scanner.nextInt() - 1;
        Location location = storage.getItemByIndex(index);

        // what characteristic should be updated
        System.out.println("1 city / 2 country / 3 entire item");
        // the choice will be stored and used in a switch
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> {
                System.out.println("New city:");
                String city = scanner.next();
                location.setCity(city);
            }
            case 2 -> {
                // country gets selected using the special method
                String country = continentSelection();
                location.setCountry(country);
            }
            // if the entire item is updated, a new one is created to take the place of the current one
            case 3 -> location = createNewItem(true);
            default -> System.out.println("Invalid option selected, returning to last menu.");
        }
        // updates are applied to the storage
        storage.updateItem(index, location);
        System.out.println("Item successfully updated.");
    }

    @Override
    public void deleteItem() {
        System.out.println("Choose an item from the following list:");
        showItems();
        storage.deleteItem(scanner.nextInt() - 1);
        System.out.println("Item successfully deleted!");
    }

    // helper method that helps you select a country
    public String continentSelection() {
        // the list of available continents is shown
        System.out.println("Select a continent:");
        System.out.println("1 asia / 2 africa / 3 north america / 4 south america / 5 europe / 6 australia");

        // after choosing a continent, all its countries are being kept in the following variable
        List<String> continentSelected = continents.get(scanner.nextInt() - 1);

        // shows all the countries from the specific continent selected
        for (int i = 0; i < continentSelected.size(); i++) {
            System.out.print((i + 1) + ":" + continentSelected.get(i) + " / ");
            if (i % 10 == 0 && i > 0) {
                System.out.println();
            }
        }
        System.out.println();

        // returns the selected country
        System.out.println("Select a country from the upper list:");
        return continentSelected.get(scanner.nextInt() - 1);
    }


}

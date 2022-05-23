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
        String country = continentSelection();

        System.out.print("City:");
        String city = scanner.next();

        Location toAdd = new Location(city, country);
        if (updating) {
            return toAdd;
        }

        storage.addNewItem(toAdd);
        System.out.println("Item successfully created and added to the storage.");
        return toAdd;
    }

    @Override
    public void showItems() {
        ArrayList<Location> locations = storage.getItem();
        System.out.println("Full list of locations:");
        for (int i = 0; i < locations.size(); i++) {
            System.out.println("Location " + (i + 1) + " - " + locations.get(i));
        }
        System.out.println();
    }

    @Override
    public void updateItem() {
        System.out.println("Choose an item from the following list:");
        showItems();
        int index = scanner.nextInt() - 1;
        Location location = storage.getItemByIndex(index);
        System.out.println("1 city / 2 country / 3 entire item");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> {
                System.out.println("New city:");
                String city = scanner.next();
                location.setCity(city);
            }
            case 2 -> {
                String country = continentSelection();
                location.setCountry(country);
            }
            case 3 -> location = createNewItem(true);
            default -> System.out.println("Invalid option selected, returning to last menu.");
        }
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

    public String continentSelection() {
        System.out.println("Select a continent:");
        System.out.println("1 asia / 2 africa / 3 north america / 4 south america / 5 europe / 6 australia");
        List<String> continentSelected = continents.get(scanner.nextInt() - 1);

        for (int i = 0; i < continentSelected.size(); i++) {
            System.out.print((i + 1) + ":" + continentSelected.get(i) + " / ");
            if (i % 10 == 0 && i > 0) {
                System.out.println();
            }
        }
        System.out.println();

        System.out.println("Select a country from the upper list:");
        return continentSelected.get(scanner.nextInt() - 1);
    }


}

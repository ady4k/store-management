package service;

import interfaces.ICountable;
import interfaces.IMaintainable;
import model.Employee;
import model.Inventory;
import model.Location;
import model.Shop;
import storage.Shops;

import java.util.ArrayList;
import java.util.Scanner;

public class ShopService implements IMaintainable<Shop>, ICountable<ArrayList<Employee>> {
    private final static Shops storage = new Shops();
    private final static LocationService locationService = new LocationService();
    private final static EmployeeService employeeService = new EmployeeService();
    private final static Scanner scanner = new Scanner(System.in);

    @Override
    public void addItemToCountable() {
        System.out.println("Select the shop you want to add an employee to:");
        showItems();
        int index = scanner.nextInt() - 1;

        Shop shop = storage.getItemByIndex(index);
        ArrayList<Employee> countable = getCountable(index);

        System.out.println("Select an employee from the following list:");
        employeeService.showItems();
        Employee employee = employeeService.getEmployeeByIndex(scanner.nextInt() - 1);

        if (shop.checkIfEmployeeExists(employee)) {
            System.out.println("Employee is already asigned to this shop!");
        } else {
            countable.add(employee);
        }

        System.out.println("Employee successfully added to the list");
    }

    @Override
    public void removeItemFromCountable() {
        System.out.println("Select the shop you want to remove an employee from:");
        showItems();
        ArrayList<Employee> countable = getCountable(scanner.nextInt() - 1);

        System.out.println("Select an employee from the following list:");
        showCountableItems(countable);

        countable.remove(scanner.nextInt() - 1);
        System.out.println("Item successfully removed from the list.");
    }

    @Override
    public void setCountable(ArrayList<Employee> countable) {
        System.out.println("Choose an item from the following list:");
        showItems();
        storage.getItemByIndex(scanner.nextInt() - 1).setEmployees(countable);
    }

    @Override
    public void showCountableItems(ArrayList<Employee> countable) {
        for (Employee employee : countable) {
            System.out.println((countable.indexOf(employee) + 1) + ":" + employee.toString());
        }
    }

    @Override
    public ArrayList<Employee> getCountable(int index) {
        return storage.getItemByIndex(index).getEmployees();
    }

    @Override
    public Shop createNewItem(boolean updating) {
        System.out.print("Shop name: ");
        String name = scanner.nextLine();

        System.out.print("Initial capital in $: ");
        float money = scanner.nextInt();

        String country = locationService.continentSelection();

        System.out.print("Location - city:");
        String city = scanner.next();
        Location location = locationService.searchForLocation(new Location(city, country));

        System.out.print("Total capacity for the shop's inventory in kg:");
        float totalCapacity = scanner.nextFloat();
        Inventory inventory = new Inventory(totalCapacity);


        Shop toAdd = new Shop(name, location, money, inventory);
        if (updating) {
            return toAdd;
        }

        storage.addNewItem(toAdd);
        System.out.println("Item successfully created and added to the storage.");
        return toAdd;
    }

    @Override
    public void showItems() {
        ArrayList<Shop> shops = storage.getItem();
        System.out.println("Full list of shops:");
        for (int i = 0; i < shops.size(); i++) {
            System.out.println("Shop " + (i + 1) + " - " + shops.get(i));
        }
        System.out.println();
    }

    @Override
    public void updateItem() {
        System.out.println("Choose an item from the following list:");
        showItems();
        int index = scanner.nextInt() - 1;
        Shop shop = storage.getItemByIndex(index);
        System.out.println("1 name / 2 money / 3 location / 4 entire item");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> {
                System.out.println("New name:");
                String name = scanner.nextLine();
                shop.setName(name);
            }
            case 2 -> {
                System.out.println("New money amount in $:");
                float money = scanner.nextFloat();
                shop.setMoney(money);
            }
            case 3 -> {
                String country = locationService.continentSelection();

                System.out.println("New city:");
                String city = scanner.next();
                Location location = locationService.searchForLocation(new Location(city, country));

                shop.setLocation(location);
            }
            case 4 -> shop = createNewItem(true);
            default -> System.out.println("Invalid option selected, returning to last menu.");
        }
        storage.updateItem(index, shop);
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

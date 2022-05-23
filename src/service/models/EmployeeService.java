package service.models;

import interfaces.IMaintainable;
import model.Employee;
import model.Location;
import storage.models.Employees;

import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeService implements IMaintainable<Employee> {
    private final static Employees storage = new Employees();
    private final static LocationService locationService = new LocationService();
    private final static Scanner scanner = new Scanner(System.in);

    @Override
    public Employee createNewItem(boolean updating) {
        System.out.print("CNP:");
        int cnp = scanner.nextInt();

        System.out.print("First Name:");
        String firstName = scanner.next();

        System.out.print("Last Name:");
        String lastName = scanner.next();

        System.out.print("Location - city:");
        String city = scanner.next();

        System.out.print("Location - country:");
        String country = scanner.next();
        Location location = locationService.searchForLocation(new Location(city, country));

        System.out.print("Email:");
        String email = scanner.next();

        Employee toAdd = new Employee(cnp, firstName, lastName, location, email);
        if (updating) {
            return toAdd;
        }

        storage.addNewItem(toAdd);
        System.out.println("Item successfully created and added to the storage.");
        return toAdd;
    }

    @Override
    public void showItems() {
        ArrayList<Employee> employees = storage.getItem();
        System.out.println("Full list of employees:");
        for (int i = 0; i < employees.size(); i++) {
            System.out.println("Employee " + (i + 1) + " - " + employees.get(i));
        }
        System.out.println();
    }

    @Override
    public void updateItem() {
        System.out.println("Choose an item from the following list:");
        showItems();
        int index = scanner.nextInt() - 1;
        Employee employee = storage.getItemByIndex(index);
        System.out.println("1 name / 2 email / 3 entire item");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> {
                System.out.print("New first name:");
                String firstName = scanner.next();
                employee.setFirstName(firstName);

                System.out.print("New last name:");
                String lastName = scanner.next();
                employee.setLastName(lastName);
            }
            case 2 -> {
                System.out.println("New email:");
                String email = scanner.next();
                employee.setEmail(email);
            }
            case 3 -> employee = createNewItem(true);
            default -> System.out.println("Invalid option selected, returning to last menu.");
        }
        storage.updateItem(index, employee);
        System.out.println("Item successfully updated.");
    }

    @Override
    public void deleteItem() {
        System.out.println("Choose an item from the following list:");
        showItems();
        storage.deleteItem(scanner.nextInt() - 1);
        System.out.println("Item successfully deleted!");
    }

    public Employee getEmployeeByIndex(int index) {
        return storage.getItemByIndex(index);
    }
}

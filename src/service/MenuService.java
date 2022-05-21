package service;

import java.util.Scanner;

/*
xx locatie - 0
distribuitori - locatie, pers de cont
metode de transp - 0
produse - distribuitor, categorie
xx categorie - 0
comanda - produs, metoda de trasnp
retur - produs, metoda de transp
magazin - locatie, angajat
xx angajat - locatie

 */

public class MenuService {
    private static final Scanner scanner = new Scanner(System.in);
    private static final LocationService locationService = new LocationService();
    private static final CategoryService categoryService = new CategoryService();
    private static final EmployeeService employeeService = new EmployeeService();
    private static int choice;
    private static String itemSelected;

    public void begin() {
        mainMenu();
    }

    private void mainMenu() {

        System.out.print("Item selected: ");
        if (itemSelected == null) {
            System.out.println("(none)");
            System.out.println("1. Select an item");
        } else {
            System.out.println(itemSelected);
            crudMenu();
        }

        //exitMenu();
        choice = scanner.nextInt();
        if (choice != 0)
            middlemanChoice(choice);

    }

    private void crudMenu() {
        System.out.println("""
                1. Switch selected item
                2. Add a new item
                3. Show all items
                4. Update an item
                5. Delete an item
                """);
    }

    private void exitMenu() {
        System.out.println("0. Return");
    }

    private void middlemanChoice(int option) {
        switch (option) {
            case 1 -> itemsChoice();
            case 2 -> {
                if (itemSelected == "Location") locationChoice(1);
                if (itemSelected == "Category") categoryChoice(1);
                if (itemSelected == "Employee") employeeChoice(1);
            }
            case 3 -> {
                if (itemSelected == "Location") locationChoice(2);
                if (itemSelected == "Category") categoryChoice(2);
                if (itemSelected == "Employee") employeeChoice(2);
            }
            case 4 -> {
                if (itemSelected == "Location") locationChoice(3);
                if (itemSelected == "Category") categoryChoice(3);
                if (itemSelected == "Employee") employeeChoice(3);
            }
            case 5 -> {
                if (itemSelected == "Location") locationChoice(4);
                if (itemSelected == "Category") categoryChoice(4);
                if (itemSelected == "Employee") employeeChoice(4);
            }
            default -> System.out.println("Invalid option selected!");
        }
    }


    private void itemsChoice() {
        System.out.println("""
                1. Location
                2. Category
                3. Employee
                """);
        choice = scanner.nextInt();
        switch (choice) {
            case 1 -> itemSelected = "Location";
            case 2 -> itemSelected = "Category";
            case 3 -> itemSelected = "Employee";
            default -> System.out.println("Invalid option selected!");
        }
        mainMenu();
    }

    private void locationChoice(int option) {
        switch (option) {
            case 1 -> locationService.createNewItem(false);
            case 2 -> locationService.showItems();
            case 3 -> locationService.updateItem();
            case 4 -> locationService.deleteItem();
            default -> {
                System.out.println("Invalid option selected!");
            }
        }
        mainMenu();
    }

    private void categoryChoice(int option) {
        switch (option) {
            case 1 -> categoryService.createNewItem(false);
            case 2 -> categoryService.showItems();
            case 3 -> categoryService.updateItem();
            case 4 -> categoryService.deleteItem();
            default -> {
                System.out.println("Invalid option selected!");
            }
        }
        mainMenu();
    }

    private void employeeChoice(int option) {
        switch (option) {
            case 1 -> employeeService.createNewItem(false);
            case 2 -> employeeService.showItems();
            case 3 -> employeeService.updateItem();
            case 4 -> employeeService.deleteItem();
            default -> {
                System.out.println("Invalid option selected!");
            }
        }
        mainMenu();
    }
}

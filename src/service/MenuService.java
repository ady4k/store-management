package service;

import java.util.Scanner;

/*
xx locatie - 0
xx distribuitori - locatie, pers de cont
xx metode de transp - 0
xx produse - distribuitor, categorie
xx categorie - 0
comanda - produs, metoda de trasnp
retur - produs, metoda de transp
xx magazin - locatie, angajat
xx angajat - locatie

 */

public class MenuService {
    private static final Scanner scanner = new Scanner(System.in);
    private static final LocationService locationService = new LocationService();
    private static final CategoryService categoryService = new CategoryService();
    private static final EmployeeService employeeService = new EmployeeService();
    private static final HeadquartersService headquartersService = new HeadquartersService();
    private static final DistributorService distributorService = new DistributorService();
    private static final ProductService productService = new ProductService();
    private static final ShopService shopService = new ShopService();
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
                if (itemSelected == "Headquarters") headquartersChoice(1);
                if (itemSelected == "Distributor") distributorChoice(1);
                if (itemSelected == "Product") productChoice(1);
                if (itemSelected == "Shop") shopChoice(1);
            }
            case 3 -> {
                if (itemSelected == "Location") locationChoice(2);
                if (itemSelected == "Category") categoryChoice(2);
                if (itemSelected == "Employee") employeeChoice(2);
                if (itemSelected == "Headquarters") headquartersChoice(2);
                if (itemSelected == "Distributor") distributorChoice(2);
                if (itemSelected == "Product") productChoice(2);
                if (itemSelected == "Shop") shopChoice(2);
            }
            case 4 -> {
                if (itemSelected == "Location") locationChoice(3);
                if (itemSelected == "Category") categoryChoice(3);
                if (itemSelected == "Employee") employeeChoice(3);
                if (itemSelected == "Headquarters") headquartersChoice(3);
                if (itemSelected == "Distributor") distributorChoice(3);
                if (itemSelected == "Product") productChoice(3);
                if (itemSelected == "Shop") shopChoice(3);
            }
            case 5 -> {
                if (itemSelected == "Location") locationChoice(4);
                if (itemSelected == "Category") categoryChoice(4);
                if (itemSelected == "Employee") employeeChoice(4);
                if (itemSelected == "Headquarters") headquartersChoice(4);
                if (itemSelected == "Distributor") distributorChoice(4);
                if (itemSelected == "Product") productChoice(4);
                if (itemSelected == "Shop") shopChoice(4);
            }
            default -> System.out.println("Invalid option selected!");
        }
    }


    private void itemsChoice() {
        System.out.println("""
                1. Location
                2. Category
                3. Employee
                4. Headquarters
                5. Distributor
                6. Product
                7. Shop
                """);
        choice = scanner.nextInt();
        switch (choice) {
            case 1 -> itemSelected = "Location";
            case 2 -> itemSelected = "Category";
            case 3 -> itemSelected = "Employee";
            case 4 -> itemSelected = "Headquarters";
            case 5 -> itemSelected = "Distributor";
            case 6 -> itemSelected = "Product";
            case 7 -> itemSelected = "Shop";
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

    private void headquartersChoice(int option) {
        switch (option) {
            case 1 -> headquartersService.createNewItem(false);
            case 2 -> headquartersService.showItems();
            case 3 -> headquartersService.updateItem();
            case 4 -> headquartersService.deleteItem();
            default -> {
                System.out.println("Invalid option selected!");
            }
        }
        mainMenu();
    }

    private void distributorChoice(int option) {
        switch (option) {
            case 1 -> distributorService.createNewItem(false);
            case 2 -> distributorService.showItems();
            case 3 -> distributorService.updateItem();
            case 4 -> distributorService.deleteItem();
            default -> {
                System.out.println("Invalid option selected!");
            }
        }
        mainMenu();
    }

    private void productChoice(int option) {
        switch (option) {
            case 1 -> productService.createNewItem(false);
            case 2 -> productService.showItems();
            case 3 -> productService.updateItem();
            case 4 -> productService.deleteItem();
            default -> {
                System.out.println("Invalid option selected!");
            }
        }
        mainMenu();
    }

    private void shopChoice(int option) {
        switch (option) {
            case 1 -> shopService.createNewItem(false);
            case 2 -> shopService.showItems();
            case 3 -> shopService.updateItem();
            case 4 -> shopService.deleteItem();
            default -> {
                System.out.println("Invalid option selected!");
            }
        }
        mainMenu();
    }
}

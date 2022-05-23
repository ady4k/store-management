package service.interactive;

import model.Shop;
import service.models.*;

import java.util.Scanner;

/*
xx locatie - 0
xx distribuitori - locatie, pers de cont
xx metode de transp - 0
xx produse - distribuitor, categorie
xx categorie - 0
xx comanda - produs, metoda de trasnp
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
    private static Shop shopSelected;
    private static int orderSelected = -1;

    public void begin() {
        mainMenu();
    }

    private void mainMenu() {

        System.out.print("Item selected: ");
        if (itemSelected == null) {
            System.out.println("(none)");
            shopMenu();
            System.out.println("1. Select an item");
        } else {
            System.out.println(itemSelected);
            shopMenu();
            crudMenu();
        }

        if (shopSelected != null) {
            System.out.println("6. Administrate shop");
        }

        //exitMenu();
        choice = scanner.nextInt();
        if (choice != 9)
            middlemanChoice(choice);

    }

    private void shopMenu() {
        System.out.print("Shop selected: ");
        if (shopSelected == null) {
            System.out.println("(none)");
            System.out.println("0. Select a shop");
        } else {
            System.out.println(shopSelected.getName() + " " + "money: " + shopSelected.getMoney());
        }
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

    private void shopAdministrationMenu() {
        System.out.println("""
                1. Check shop inventory
                2. Show shop details
                3. Check all orders placed
                4. Orders administration
                5. Employees administration
                6. Check all returnals placed
                7. Returnals administrations
                8. Return to last menu
                """);
        shopAdministrationChoice(scanner.nextInt());
    }

    private void shopAdministrationChoice(int option) {
        switch (option) {
            case 1 -> System.out.println(shopSelected.getInventory());
            case 2 -> System.out.println(shopSelected);
            case 3 -> shopService.showOrders(shopSelected);
            case 4 -> orderAdministrationMenu();
            case 5 -> employeesAdministrationMenu();
            //case 6 -> shopService.showReturnals(shopSelected);
            //case 7 -> returnalsAdministrationMenu();
            case 8 -> mainMenu();
            case default -> System.out.println("Invalid option selected!");
        }
    }

    private void orderAdministrationMenu() {
        System.out.print("Current order: ");

        if (orderSelected == -1) {
            System.out.println("(none)");
        } else {
            System.out.println(orderSelected);
        }
        System.out.println("""
                1. Select an order
                2. Place a new order
                """);
        if (orderSelected != -1) {
            System.out.println("3. Modify selected order");
        }
        System.out.println("4. Return to last menu");
        orderAdministrationChoice(scanner.nextInt());
    }

    private void orderAdministrationChoice(int option) {
        switch (option) {
            case 1 -> orderSelected = shopService.selectOrder(shopSelected);
            case 2 -> shopService.createOrder(shopSelected);
            case 3 ->
                    shopService.modifyOrder(shopSelected, shopService.getOrderByIndex(shopSelected, orderSelected - 1));
            case 4 -> shopAdministrationMenu();
            case default -> System.out.println("Invalid option selected");
        }
    }

    private void employeesAdministrationMenu() {
        System.out.println("""
                1. Show all employees
                2. Assign an employee to selected shop
                3. Remove an employee from selected shop
                4. Return to last menu
                """);
        employeesAdministrationChoice(scanner.nextInt());
    }

    private void employeesAdministrationChoice(int option) {
        switch (option) {
            case 1 -> shopService.showCountableItems(shopService.getCountableByShop(shopSelected));
            case 2 -> shopService.assignEmployeeToShop(shopSelected);
            case 3 -> shopService.removeEmployeeFromShop(shopSelected);
            case 4 -> shopAdministrationMenu();
            case default -> System.out.println("Invalid option selected");
        }
    }

    private void exitMenu() {
        System.out.println("0. Return");
    }

    private void middlemanChoice(int option) {
        switch (option) {
            case 0 -> shopSelected = shopService.chooseShop();
            case 1 -> itemsChoice();
            case 2, 3, 4, 5 -> {
                if (itemSelected == "Location") locationChoice(option - 1);
                if (itemSelected == "Category") categoryChoice(option - 1);
                if (itemSelected == "Employee") employeeChoice(option - 1);
                if (itemSelected == "Headquarters") headquartersChoice(option - 1);
                if (itemSelected == "Distributor") distributorChoice(option - 1);
                if (itemSelected == "Product") productChoice(option - 1);
                if (itemSelected == "Shop") shopChoice(option - 1);
            }
            default -> System.out.println("Invalid option selected!");
        }
        mainMenu();
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

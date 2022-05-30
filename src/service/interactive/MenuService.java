package service.interactive;

import model.Shop;
import service.models.*;

import java.util.Scanner;

public class MenuService {
    private static final Scanner scanner = new Scanner(System.in);
    private static final LocationService locationService = new LocationService();
    private static final CategoryService categoryService = new CategoryService();
    private static final EmployeeService employeeService = new EmployeeService();
    private static final HeadquartersService headquartersService = new HeadquartersService();
    private static final DistributorService distributorService = new DistributorService();
    private static final ProductService productService = new ProductService();
    private static final ShopService shopService = new ShopService();
    private static int choice; // I don't know why this isn't declared locally
    private static String itemSelected; // variable to show the item selected for modification in the menu
    private static Shop shopSelected; // the shop that will be administrated
    private static int orderSelected = -1; // index of an order selected based by a shop
    private static int returnSelected = -1; // index of a return selected based by a shop

    // starting function
    public void begin() {
        mainMenu();
    }

    // the main interactive menu that will show when the program starts
    private void mainMenu() {

        // if there isn't an item selected, the choices will be limited
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

        // item CRUD menu is different from the shop administration menu, therefore will be shown even if there is no item selected
        if (shopSelected != null) {
            System.out.println("6. Administrate shop");
        }

        //exitMenu();
        // exit method do the next step
        choice = scanner.nextInt();
        if (choice != 9)
            middlemanChoice(choice);

    }

    // method that shows the shop selected (if any) alongside the detail of how much capital it has
    // shows the option to select a shop
    private void shopMenu() {
        System.out.print("Shop selected: ");
        if (shopSelected == null) {
            System.out.println("(none)");
            System.out.println("0. Select a shop");
        } else {
            System.out.println(shopSelected.getName() + " " + "money: " + shopSelected.getMoney());
        }
    }

    // method that shows the main CRUD operations for the selected item
    private void crudMenu() {
        System.out.println("""
                1. Switch selected item
                2. Add a new item
                3. Show all items
                4. Update an item
                5. Delete an item
                """);
    }

    // method that shows all the shop administration options once the "Administrate shop" is selected
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
        // exit to next step
        shopAdministrationChoice(scanner.nextInt());
    }

    // middleman method that selects the correct method based on the option selected from the administration menu
    private void shopAdministrationChoice(int option) {
        switch (option) {
            case 1 ->
                    System.out.println(shopSelected.getInventory()); // prints all the details about the shop's inventory
            case 2 -> System.out.println(shopSelected);                // prints all the details about the shop
            case 3 -> shopService.showOrders(shopSelected);            // shows all the orders made by the selected shop
            case 4 -> orderAdministrationMenu();                       // opens the menu for administrating orders
            case 5 ->
                    employeesAdministrationMenu();                   // opens the menu for administrating shop employees
            case 6 ->
                    shopService.showReturnals(shopSelected);         // shows all the returns made by the selected shop
            case 7 -> returnalsAdministrationMenu();                   // opens the menu for administrating returnals
            case 8 -> mainMenu();                                      // returns to the main menu
            case default -> System.out.println("Invalid option selected!");
        }
    }

    // orders administration menu
    private void orderAdministrationMenu() {
        // shows the current order selected
        System.out.print("Current order: ");

        // if there isn't one selected (default value)
        if (orderSelected == -1) {
            System.out.println("(none)");
        } else {
            System.out.println(orderSelected);
        }
        System.out.println("""
                1. Select an order
                2. Place a new order
                """);

        // if there is an order selected, you can select to modify the order
        if (orderSelected != -1) {
            System.out.println("3. Modify selected order");
        }
        // return to last menu (which is shop administration menu)
        System.out.println("4. Return to last menu");

        // exit to next step
        orderAdministrationChoice(scanner.nextInt());
    }

    // middleman method that selects the correct method based on the option given
    private void orderAdministrationChoice(int option) {
        switch (option) {
            case 1 ->
                    orderSelected = shopService.selectOrder(shopSelected); // selects a new order from the selected shop (if any)
            case 2 -> shopService.createOrder(shopSelected);  // creates a new order based on the shop selected
            case 3 ->
                    shopService.modifyOrder(shopSelected, shopService.getOrderByIndex(shopSelected, orderSelected - 1)); // modifies the selected order
            case 4 -> shopAdministrationMenu(); // returns to the shop administration menu
            case default -> System.out.println("Invalid option selected");
        }
    }

    // employees administration menu
    private void employeesAdministrationMenu() {
        System.out.println("""
                1. Show all employees
                2. Assign an employee to selected shop
                3. Remove an employee from selected shop
                4. Return to last menu
                """);
        // exits to next step
        employeesAdministrationChoice(scanner.nextInt());
    }

    // middleman method that selects the correct method based on the option given
    private void employeesAdministrationChoice(int option) {
        switch (option) {
            case 1 ->
                    shopService.showCountableItems(shopService.getCountableByShop(shopSelected)); // show all the employees assigned to the selected shop
            case 2 -> shopService.assignEmployeeToShop(shopSelected); // assigns a new employee to the shop
            case 3 -> shopService.removeEmployeeFromShop(shopSelected); // removes an assigned employee from the shop
            case 4 -> shopAdministrationMenu(); // returns to the shop administration menu
            case default -> System.out.println("Invalid option selected");
        }
    }

    // returnals administration menu
    private void returnalsAdministrationMenu() {
        System.out.print("Current return: ");

        if (returnSelected == -1) {
            System.out.println("(none)");
        } else {
            System.out.println(returnSelected);
        }
        System.out.println("""
                1. Select a return
                2. Place a new return
                """);
        if (returnSelected != -1) {
            System.out.println("3. Modify selected return");
        }
        System.out.println("4. Return to last menu");
        // exit to next step
        returnAdministrationChoice(scanner.nextInt());
    }

    // middleman method that chooses the correct method based on the option given
    private void returnAdministrationChoice(int option) {
        switch (option) {
            case 1 -> returnSelected = shopService.selectReturn(shopSelected); // selects a return to be administrated
            case 2 -> shopService.createReturn(shopSelected); // creates a new return based on the shop given
            case 3 ->
                    shopService.modifyReturn(shopSelected, shopService.getReturnByIndex(shopSelected, returnSelected - 1)); // modifies the selected return
            case 4 -> shopAdministrationMenu(); // returns to the shop administration menu
            case default -> System.out.println("Invalid option selected");
        }
    }

    // method that will be used to shop the exit choice of the menu
    private void exitMenu() {
        System.out.println("0. Return");
    }

    // middleman method that chooses the correct method based on the option given on the main menu,
    private void middlemanChoice(int option) {
        switch (option) {
            case 0 -> shopSelected = shopService.chooseShop(); // chooses a new shop to administrate
            case 1 -> itemsChoice(); // selects an item for CRUD operations
            case 2, 3, 4, 5 -> {
                if (itemSelected == "Location") locationChoice(option - 1);
                if (itemSelected == "Category") categoryChoice(option - 1);
                if (itemSelected == "Employee") employeeChoice(option - 1);
                if (itemSelected == "Headquarters") headquartersChoice(option - 1);
                if (itemSelected == "Distributor") distributorChoice(option - 1);
                if (itemSelected == "Product") productChoice(option - 1);
                if (itemSelected == "Shop") shopChoice(option - 1);
            } // based on the item and option selected, exits to the next step with the option given
            case 6 -> shopAdministrationMenu();
            default -> System.out.println("Invalid option selected!");
        }
        // returns to the main menu once the operation is done
        mainMenu();
    }

    // method to shop all the item that can be selected for CRUD operations
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
        } // "selects" an item based on the option given
        // returns to the main menu after the operation is done
        mainMenu();
    }

    // all the following methods are used to select the correct method based on the option given
    // each one is used based on the item selected and the option given
    // 1. creates a new selected item
    // 2. shows all the items based on which one is selected
    // 3. updates a specific item from the list of the item selected
    // 4. deleted a specific item from the list of the item selected
    // after the operation is done, returns to the main menu
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

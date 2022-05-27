package service.models;

import helper.DateFormatterHelper;
import interfaces.ICountable;
import interfaces.IMaintainable;
import model.Order;
import model.Product;
import model.Return;
import storage.models.Returns;
import storage.predefined.TransportationMethods;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

public class ReturnService implements IMaintainable<Return>, ICountable<Map<Product, Integer>> {
    private final static Returns storage = new Returns();
    private final static Scanner scanner = new Scanner(System.in);
    private final static EmployeeService employeeService = new EmployeeService();
    private final static OrderService orderService = new OrderService();

    public void cancelReturn(Return ret) {
        ret.setStatusCode(3); // sets the status code to "cancelled"
        ret.setEstimatedTimeOfArrival(null); // time of arrival gets set to null, as it's never going to arrive

        System.out.println("Return has been successfully cancelled!");
    }

    // launch an already processed order
    public void launchReturn(Return ret, float price, int days) {
        // stores the date of today
        LocalDateTime today = LocalDateTime.now();
        // converts the date of today to a general date based on the zone id
        Date todayConvert = Date.from(today.atZone(ZoneId.systemDefault()).toInstant());

        // calculated and sets the returnal price based on the transportation method
        ret.setReturnalPrice(price * TransportationMethods.getTransportMultiplier(ret.getTransportMethod()));

        ret.setDateLaunched(todayConvert); // sets the date launched to today
        ret.setStatusCode(1); // sets the status code to "launched"
        ret.setEstimatedTimeOfArrival(DateFormatterHelper.addDays(todayConvert, days)); // sets the time of arrival to the launch date + days


        System.out.println("Return has been successfully launched!");
    }

    // not used
    @Override
    public void addItemToCountable() {
//        System.out.println("Select the return you want to add an item to:");
//        showItems();
//        int index = scanner.nextInt() - 1;
//        Return ret = storage.getItemByIndex(index);
//        Map<Product, Integer> countable = getCountable(index);
//
//        System.out.println("Select an item from the following list:");
//        orderService.showCountableItems(orderService.getCountableByOrder(ret.getOrder()));
//        ArrayList<Product> keyList = new ArrayList<Product>(ret.getOrder().getProducts().keySet());
//        Product product = keyList.get(scanner.nextInt() - 1);
//
//        System.out.println("Product quantity (units):");
//        int quantity = scanner.nextInt();
//
//        System.out.println("Product - quantity successfully added to the list");
    }

    // not used
    @Override
    public void removeItemFromCountable() {
//        System.out.println("Select the return you want to remove an item from:");
//        showItems();
//        int index = scanner.nextInt() - 1;
//        Return ret = storage.getItemByIndex(index);
//        Map<Product, Integer> countable = getCountable(index);
//
//        System.out.println("Choose an item from the following list:");
//        showCountableItems(countable);
//
//        ArrayList<Product> keyList = new ArrayList<Product>(countable.keySet());
//        countable.remove(keyList.get(scanner.nextInt() - 1));
//        System.out.println("Item successfully removed from the list.");
    }

    @Override
    public void setCountable(Map<Product, Integer> countable) {
        System.out.println("Choose an item from the following list:");
        showItems(); // shows all the returns

        // sets the map of products to the return
        storage.getItemByIndex(scanner.nextInt() - 1).setProductsReturned(countable);
    }

    @Override
    public void showCountableItems(Map<Product, Integer> countable) {
        // shows all the products returned based on the countable given
        for (Product key : countable.keySet()) {
            System.out.println(key.getName() + ":" + countable.get(key) + "\n");
        }
    }

    @Override
    public Map<Product, Integer> getCountable(int index) {
        return storage.getItemByIndex(index).getProductsReturned();
    }

    @Override
    public Return createNewItem(boolean updating) {
        // creates the new order with only the operator and the order the return is based on
        // as the other properties are modified while processing the order

        // shows the list of all available orders
        System.out.print("Choose an order: ");
        orderService.showItems();

        // stores the selected order in the variable
        Order order = orderService.getOrderByIndex(scanner.nextInt() - 1);

        System.out.print("Return operator: ");
        employeeService.showItems(); // shows the list of all employees

        // the new return is created using the constructor with order and employee
        Return toAdd = new Return(order, employeeService.getEmployeeByIndex(scanner.nextInt() - 1));

        storage.addNewItem(toAdd);
        System.out.println("Item successfully created and added to the storage.");
        return toAdd;
    }

    @Override
    public void showItems() {
        // the list of all returns is taken from the storage
        ArrayList<Return> returns = storage.getItem();

        // shows the list of all returns
        System.out.println("Full list of returns:");
        for (int i = 0; i < returns.size(); i++) {
            System.out.println("Return " + (i + 1) + " - " + returns.get(i));
        }
        System.out.println();
    }

    @Override
    public void updateItem() {
        System.out.println("Choose an item from the following list:");
        showItems(); // shows the list of all returns

        // the return's index and item gets stored in the following variables
        int index = scanner.nextInt() - 1;
        Return ret = storage.getItemByIndex(index);

        // the list of modifications available
        // the full item cannot be replaced
        System.out.println("1 operator / 2 status code / 3 time of arrival");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> {
                System.out.println("New employee as operator:");
                employeeService.showItems(); // shows the list of all available employees (should only show from specific shop)
                ret.setOperator(employeeService.getEmployeeByIndex(scanner.nextInt() - 1)); // new employee selected gets set
            }
            case 2 -> ret.setStatusCode(changeStatusCode(ret));
            case 3 -> {
                // should be used once the return "arrived" at the distributor
                System.out.println("Day: ");
                int day = scanner.nextInt();
                System.out.print("Month:");
                int month = scanner.nextInt();
                System.out.print("Year:");
                int year = scanner.nextInt();
                Date date = new Date(year, month, day);

                ret.setEstimatedTimeOfArrival(date);
            }
            default -> System.out.println("Invalid option selected, returning to last menu.");
        }

        // new item gets updated in the storage
        storage.updateItem(index, ret);
        System.out.println("Item successfully updated.");
    }

    @Override
    public void deleteItem() {
        System.out.println("Choose an item from the following list:");
        showItems(); // shows the list of returns

        // deletes the return from the storage based on its index
        storage.deleteItem(scanner.nextInt() - 1);

        System.out.println("Item successfully deleted!");
    }

    // shows all the status codes available to be chosen
    public int changeStatusCode(Return ret) {
        System.out.println("""
                Status codes:
                1. In preparation
                2. Launched
                3. Arrived
                4. Cancelled
                5. Status not available
                """);
        return scanner.nextInt() - 1;
    }
}

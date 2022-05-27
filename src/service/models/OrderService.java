package service.models;

import helper.DateFormatterHelper;
import interfaces.ICountable;
import interfaces.IMaintainable;
import model.Order;
import model.Product;
import model.Shop;
import storage.models.Orders;
import storage.predefined.TransportationMethods;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

public class OrderService implements IMaintainable<Order>, ICountable<Map<Product, Integer>> {
    private final static EmployeeService employeeService = new EmployeeService();
    private final static ShopService shopService = new ShopService();
    private final static Orders storage = new Orders();
    private final static Scanner scanner = new Scanner(System.in);


    // cancels the given order
    public void cancelOrder(Order order) {
        order.setStatusCode(3); // sets the status code to "cancelled"
        order.setEstimatedTimeOfArrival(null); // time of arrival gets set to null, as it's never going to arrive

        System.out.println("Order has been successfully cancelled!");
    }

    // launch an already processed order
    public void launchOrder(Shop shop, Order order, float price, int days) {
        // stores the date of today
        LocalDateTime today = LocalDateTime.now();
        // converts the date of today to a general date based on the zone id
        Date todayConvert = Date.from(today.atZone(ZoneId.systemDefault()).toInstant());

        // calculates and sets the final price of the order based on the transportation method
        order.setFinalPrice(price * TransportationMethods.getTransportMultiplier(order.getTransportationMethod()));

        // uses a method to see if the shop has enough capital for the delivery fees in order to launch the order
        if (!shopService.checkIfEnoughMoneyAndWeight(shop, order, order.getFinalPrice() - price, 0)) {
            System.out.println("""
                    There is not enough money to pay for shipping fees!
                    Consider removing a product or reducing it's quantity to accommodate for the fees.
                    Nothing has been changed, the order has not been launched.
                    """);
            // if there isn't enough money, the order is not changed, the function returns nothing
            return;
        }

        order.setDateLaunched(todayConvert); // sets the date launched to today
        order.setStatusCode(1); // sets the status code to "launched"
        order.setEstimatedTimeOfArrival(DateFormatterHelper.addDays(todayConvert, days)); // sets the time of arrival to the launch date + days


        System.out.println("Order has been successfully launched!");
    }

    // not used
    @Override
    public void addItemToCountable() {
//        System.out.println("Select the order you want to add an item to:");
//        showItems();
//        int index = scanner.nextInt() - 1;
//        Order order = storage.getItemByIndex(index);
//        Map<Product, Integer> countable = getCountable(index);
//
//        System.out.println("Select an item from the following list:");
//        productService.showItems();
//        Product product = productService.getProductByIndex(scanner.nextInt() - 1);
//        System.out.println("Product quantity (units):");
//        int quantity = scanner.nextInt();
//
//
//        System.out.println("Product - quantity successfully added to the list");
    }

    // not used
    @Override
    public void removeItemFromCountable() {
//        System.out.println("Select the order you want to remove an item from:");
//        showItems();
//        int index = scanner.nextInt() - 1;
//        Order order = storage.getItemByIndex(index);
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
        showItems(); // shows all the orders

        // sets the map of products to the order
        storage.getItemByIndex(scanner.nextInt() - 1).setProducts(countable);
    }

    @Override
    public void showCountableItems(Map<Product, Integer> countable) {
        // shows all the products ordered based on the countable given
        for (Product key : countable.keySet()) {
            System.out.println(key.getName() + ":" + countable.get(key) + "\n");
        }
    }

    // returns the list of products ordered based by ndex
    @Override
    public Map<Product, Integer> getCountable(int index) {
        return storage.getItemByIndex(index).getProducts();
    }

    // returns the list of products ordered based by order
    public Map<Product, Integer> getCountableByOrder(Order order) {
        return storage.getProductsByOrder(order);
    }

    @Override
    public Order createNewItem(boolean updating) {
        // creates the new order with only the operator as the other properties are modified while processing the order
        System.out.print("Order operator: ");
        employeeService.showItems(); // shows the list of all employees

        // the new order is created using the constructor with employee
        Order toAdd = new Order(employeeService.getEmployeeByIndex(scanner.nextInt() - 1));

        // new item gets added to the storage
        storage.addNewItem(toAdd);
        System.out.println("Item successfully created and added to the storage.");
        return toAdd;
    }

    @Override
    public void showItems() {
        // the list of all orders is taken from the storage
        ArrayList<Order> orders = storage.getItem();

        // shows the list of all orders
        System.out.println("Full list of orders:");
        for (int i = 0; i < orders.size(); i++) {
            System.out.println("Order " + (i + 1) + " - " + orders.get(i));
        }
        System.out.println();
    }

    @Override
    public void updateItem() {
        System.out.println("Choose an item from the following list:");
        showItems(); // shows the list of all orders

        // the order's index and item gets stored in the following variables
        int index = scanner.nextInt() - 1;
        Order order = storage.getItemByIndex(index);

        // the list of modifications available
        // the full item cannot be replaced
        System.out.println("1 operator / 2 status code / 3 time of arrival");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> {
                System.out.println("New employee as operator:");
                employeeService.showItems(); // shows the list of all available employees (should only show from specific shop)
                order.setOperator(employeeService.getEmployeeByIndex(scanner.nextInt() - 1)); // new employee selected gets set
            }
            case 2 -> order.setStatusCode(changeStatusCode(order));
            case 3 -> {
                // should be used once the order "arrived" at the shop
                System.out.println("Day: ");
                int day = scanner.nextInt();
                System.out.print("Month:");
                int month = scanner.nextInt();
                System.out.print("Year:");
                int year = scanner.nextInt();
                Date date = new Date(year, month, day);

                order.setEstimatedTimeOfArrival(date);
            }
            default -> System.out.println("Invalid option selected, returning to last menu.");
        }

        // new item gets updated in the storage
        storage.updateItem(index, order);
        System.out.println("Item successfully updated.");
    }

    @Override
    public void deleteItem() {
        System.out.println("Choose an item from the following list:");
        showItems(); // shows the list of all orders

        // deletes the order from the storage based on its index
        storage.deleteItem(scanner.nextInt() - 1);

        System.out.println("Item successfully deleted!");
    }

    // returns the order from the storage based on its index
    public Order getOrderByIndex(int index) {
        return storage.getItemByIndex(index);
    }

    // shows all the status codes available to be chosen
    public int changeStatusCode(Order order) {
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

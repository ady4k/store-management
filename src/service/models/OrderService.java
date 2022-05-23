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
    private final static ProductService productService = new ProductService();
    private final static ShopService shopService = new ShopService();
    private final static Map<String, Float> transportMethods = TransportationMethods.getTransports();
    private final static Orders storage = new Orders();
    private final static Scanner scanner = new Scanner(System.in);


    public void cancelOrder(Order order) {
        order.setStatusCode(3);
        order.setEstimatedTimeOfArrival(null);

        System.out.println("Order has been successfully cancelled!");
    }

    public void launchOrder(Shop shop, Order order, float price, int days) {
        LocalDateTime today = LocalDateTime.now();
        Date todayConvert = Date.from(today.atZone(ZoneId.systemDefault()).toInstant());

        order.setFinalPrice(price * TransportationMethods.getTransportMultiplier(order.getTransportationMethod()));
        if (!shopService.checkIfEnoughMoneyAndWeight(shop, order, order.getFinalPrice() - price, 0)) {
            System.out.println("""
                    There is not enough money to pay for shipping fees!
                    Consider removing a product or reducing it's quantity to accommodate for the fees.
                    Nothing has been changed, the order has not been launched.
                    """);
            return;
        }

        order.setDateLaunched(todayConvert);
        order.setStatusCode(1);
        order.setEstimatedTimeOfArrival(DateFormatterHelper.addDays(todayConvert, days));


        System.out.println("Order has been successfully launched!");
    }

    @Override
    public void addItemToCountable() {
        System.out.println("Select the order you want to add an item to:");
        showItems();
        int index = scanner.nextInt() - 1;
        Order order = storage.getItemByIndex(index);
        Map<Product, Integer> countable = getCountable(index);

        System.out.println("Select an item from the following list:");
        productService.showItems();
        Product product = productService.getProductByIndex(scanner.nextInt() - 1);
        System.out.println("Product quantity (units):");
        int quantity = scanner.nextInt();

        System.out.println("Product - quantity successfully added to the list");
    }

    @Override
    public void removeItemFromCountable() {
        System.out.println("Select the order you want to remove an item from:");
        showItems();
        int index = scanner.nextInt() - 1;
        Order order = storage.getItemByIndex(index);
        Map<Product, Integer> countable = getCountable(index);

        System.out.println("Choose an item from the following list:");
        showCountableItems(countable);

        ArrayList<Product> keyList = new ArrayList<Product>(countable.keySet());
        countable.remove(keyList.get(scanner.nextInt() - 1));
        System.out.println("Item successfully removed from the list.");
    }

    @Override
    public void setCountable(Map<Product, Integer> countable) {
        System.out.println("Choose an item from the following list:");
        showItems();
        storage.getItemByIndex(scanner.nextInt() - 1).setProducts(countable);
    }

    @Override
    public void showCountableItems(Map<Product, Integer> countable) {
        for (Product key : countable.keySet()) {
            System.out.println(key.getName() + ":" + countable.get(key) + "\n");
        }
    }

    @Override
    public Map<Product, Integer> getCountable(int index) {
        return storage.getItemByIndex(index).getProducts();
    }

    @Override
    public Order createNewItem(boolean updating) {
        System.out.print("Order operator: ");
        employeeService.showItems();
        Order toAdd = new Order(employeeService.getEmployeeByIndex(scanner.nextInt() - 1));

        storage.addNewItem(toAdd);
        System.out.println("Item successfully created and added to the storage.");
        return toAdd;
    }

    @Override
    public void showItems() {
        ArrayList<Order> orders = storage.getItem();
        System.out.println("Full list of orders:");
        for (int i = 0; i < orders.size(); i++) {
            System.out.println("Order " + (i + 1) + " - " + orders.get(i));
        }
        System.out.println();
    }

    @Override
    public void updateItem() {
        System.out.println("Choose an item from the following list:");
        showItems();
        int index = scanner.nextInt() - 1;
        Order order = storage.getItemByIndex(index);
        System.out.println("1 operator / 2 status code / 3 time of arrival");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> {
                System.out.println("New employee as operator:");
                employeeService.showItems();
                order.setOperator(employeeService.getEmployeeByIndex(scanner.nextInt() - 1));
            }
            case 2 -> order.setStatusCode(changeStatusCode(order));
            case 3 -> {
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
        storage.updateItem(index, order);
        System.out.println("Item successfully updated.");
    }

    @Override
    public void deleteItem() {
        System.out.println("Choose an item from the following list:");
        showItems();

        storage.deleteItem(scanner.nextInt() - 1);

        System.out.println("Item successfully deleted!");
    }

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

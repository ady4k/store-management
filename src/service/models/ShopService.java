package service.models;

import interfaces.ICountable;
import interfaces.IMaintainable;
import model.*;
import service.LocalizationService;
import storage.custom.ShopOrder;
import storage.models.Shops;
import storage.predefined.TransportationMethods;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class ShopService implements IMaintainable<Shop>, ICountable<ArrayList<Employee>> {
    private final static Shops storage = new Shops();
    private final static ShopOrder shopOrder = new ShopOrder();
    private final static LocationService locationService = new LocationService();
    private final static EmployeeService employeeService = new EmployeeService();
    private final static OrderService orderService = new OrderService();
    private final static ProductService productService = new ProductService();
    private final static LocalizationService localizationService = new LocalizationService();
    private final static Scanner scanner = new Scanner(System.in);

    public void createOrder(Shop shop) {
        Order order = orderService.createNewItem(false);
        ArrayList<Order> orders = shopOrder.getItemByKey(shop);
        orders.add(order);

        shopOrder.updateItem(shop, orders);
    }

    public void processOrder(Shop shop, Order order) {
        // price
        // days for transport
        Map<Product, Integer> products = order.getProducts();
        float price = 0;
        for (Product product : products.keySet()) {
            price = price + product.getPrice() * products.get(product);
        }

        int daysForTransport = TransportationMethods.calculateTransportTime(order.getTransportationMethod());
        System.out.println("""
                This is the last chance to revise the order.
                Are you sure you want to launch this order?
                1 (yes) / 0 (no)
                """);

        if (scanner.nextInt() == 1) {
            orderService.launchOrder(shop, order, price, daysForTransport);
        } else {
            System.out.println("Order has been processed but not launched, waiting for launching instructions.");
        }
    }

    public void cancelOrder(Order order) {
        System.out.println("""
                Are you sure you want to cancel this order?
                Once cancelled this cannot be undone and you will have to create a new order.
                1 (yes) / 0 (no)
                """);
        if (scanner.nextInt() == 1) {
            orderService.cancelOrder(order);
        } else {
            System.out.println("Nothing has been changed, order is not cancelled.");
        }

    }

    public void modifyOrder(Shop shop, Order order) {
        System.out.println("""
                Select a modification:
                1. Add a product to the order
                2. Remove a product from the order
                3. Modify a product quantity from the order
                4. Choose another transport method
                5. Process and launch the order
                6. Cancel the order (cannot be undone)
                """);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> addProductToOrder(shop, order);
            case 2 -> removeProductFromOrder(order);
            case 3 -> changeProductFromOrder(shop, order);
            case 4 -> chooseTransportMethod(shop, order);
            case 5 -> processOrder(shop, order);
            case 6 -> cancelOrder(order);
        }
    }

    public boolean checkIfEnoughMoneyAndWeight(Shop shop, Order order, float price, float weight) {
        Map<Product, Integer> products = order.getProducts();
        Inventory inventory = shop.getInventory();
        float shopMoney = shop.getMoney();
        float remainingCapacity = inventory.getRemainingCapacity();

        float usedCapacity = 0;
        float usedMoney = 0;
        for (Product product : products.keySet()) {
            usedCapacity = usedCapacity + product.getWeight() * products.get(product);
            usedMoney = usedMoney + product.getPrice() * products.get(product);
        }
        if (usedCapacity > remainingCapacity || usedMoney > shopMoney) {
            return false;
        }
        return !(usedCapacity + weight > remainingCapacity) && !(usedMoney + price > shopMoney);
    }

    public void addProductToOrder(Shop shop, Order order) {
        Map<Product, Integer> products = order.getProducts();

        System.out.println("Select a product to be added:");
        productService.showItems();
        Product product = productService.getProductByIndex(scanner.nextInt() - 1);

        System.out.println("Quantity of product (units):");
        int quantity = scanner.nextInt();

        if (checkIfEnoughMoneyAndWeight(shop, order, product.getPrice() * quantity, product.getWeight() * quantity)) {
            products.put(product, quantity);
            System.out.println("Product - quantity successfully added to order.");
            return;
        }
        System.out.println("There is not enough money or capacity left in your shop / inventory!");
    }

    public void removeProductFromOrder(Order order) {
        Map<Product, Integer> products = order.getProducts();

        System.out.println("Select a product to be removed:");
        for (Product product : products.keySet()) {
            System.out.println(product.getName());
        }

        ArrayList<Product> keyList = new ArrayList<Product>(products.keySet());
        products.remove(keyList.get(scanner.nextInt() - 1));
        System.out.println("Product successfully removed from the order.");
    }

    public void changeProductFromOrder(Shop shop, Order order) {
        Map<Product, Integer> products = order.getProducts();

        System.out.println("Select a product to be changed:");
        for (Product product : products.keySet()) {
            System.out.println(product.getName());
        }

        ArrayList<Product> keyList = new ArrayList<Product>(products.keySet());
        Product product = keyList.get(scanner.nextInt() - 1);

        System.out.println("New quantity (units):");
        int quantity = scanner.nextInt();


        if (checkIfEnoughMoneyAndWeight(shop, order, product.getPrice() * quantity, product.getWeight() * quantity)) {
            products.replace(product, quantity);
            System.out.println("Product - quantity successfully changed.");
            return;
        }

        System.out.println("There is not enough money or capacity left in your shop / inventory!");
    }

    public void chooseTransportMethod(Shop shop, Order order) {
        ArrayList<Product> products = new ArrayList<>(order.getProducts().keySet());
        String orderCountry = products.get(0).getDistributor().getHeadquarters().getCountry();
        ArrayList<String> transporationMethods = localizationService.availableTransportMethods(shop.getLocation().getCountry(), orderCountry);
        System.out.println("Choose the preferred transportation method:");
        for (String transport : transporationMethods) {
            System.out.println((transporationMethods.indexOf(transport) + 1) + ": " + transport);
        }
        order.setTransportationMethod(transporationMethods.get(scanner.nextInt() - 1));
        System.out.println("Transportation method successfully chosen.");
    }

    public void showOrders(Shop shop) {
        ArrayList<Order> orders = shopOrder.getItemByKey(shop);
        for (Order order : orders) {
            System.out.print(orders.indexOf(order) + 1 + ": ");
            System.out.println(order);
        }
    }

    public int selectOrder(Shop shop) {
        System.out.println("Select an order: ");
        showOrders(shop);
        return scanner.nextInt() - 1;
    }

    public Order getOrderByIndex(Shop shop, int index) {
        return shopOrder.getItemByKey(shop).get(index);
    }

    @Override
    public void addItemToCountable() {
        System.out.println("Select the shop you want to add an employee to:");
        showItems();
        int index = scanner.nextInt() - 1;
        Shop shop = storage.getItemByIndex(index);
        assignEmployeeToShop(shop);
    }

    public void assignEmployeeToShop(Shop shop) {
        ArrayList<Employee> countable = getCountableByShop(shop);

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
        int index = scanner.nextInt() - 1;
        Shop shop = storage.getItemByIndex(index);
        removeEmployeeFromShop(shop);
    }

    public void removeEmployeeFromShop(Shop shop) {
        ArrayList<Employee> countable = getCountableByShop(shop);

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

    public ArrayList<Employee> getCountableByShop(Shop shop) {
        return storage.getEmployeesByShop(shop);
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

    public Shop chooseShop() {
        if (storage.isEmpty()) {
            System.out.println("There is no shop to select from!");
            return null;
        }
        System.out.println("Select a shop:");
        showItems();
        return storage.getItemByIndex(scanner.nextInt());
    }
}

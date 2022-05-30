package service.models;

import interfaces.ICountable;
import interfaces.IMaintainable;
import model.*;
import service.LocalizationService;
import storage.custom.ShopOrder;
import storage.custom.ShopReturn;
import storage.models.Shops;
import storage.predefined.TransportationMethods;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class ShopService implements IMaintainable<Shop>, ICountable<ArrayList<Employee>> {
    private final static Shops storage = new Shops();
    private final static ShopOrder shopOrder = new ShopOrder();
    private final static ShopReturn shopReturn = new ShopReturn();
    private final static InventoryService inventoryService = new InventoryService();
    private final static LocationService locationService = new LocationService();
    private final static EmployeeService employeeService = new EmployeeService();
    private final static OrderService orderService = new OrderService();
    private final static ProductService productService = new ProductService();
    private final static LocalizationService localizationService = new LocalizationService();
    private final static ReturnService returnService = new ReturnService();
    private final static Scanner scanner = new Scanner(System.in);

    // shows the list of returns based on the shop
    public void showReturnals(Shop shop) {
        // list of returns gets kept in the variable
        ArrayList<Return> returns = shopReturn.getItemByKey(shop);

        // shows all the returns
        for (Return ret : returns) {
            System.out.print(returns.indexOf(ret) + 1 + ": ");
            System.out.println(ret);
        }
    }

    public void createReturn(Shop shop) {
        // creates the new return
        Return ret = returnService.createNewItem(false);

        // gets the list of returns based on the specific shop
        ArrayList<Return> returns = shopReturn.getItemByKey(shop);

        // the return gets added to the list of returns
        returns.add(ret);

        // the storage of returns based on the shop gets updated with the new values
        shopReturn.updateItem(shop, returns);
    }

    // returns the return with the index that is in the specific shop
    public Return getReturnByIndex(Shop shop, int index) {
        return shopReturn.getItemByKey(shop).get(index);
    }

    // interface menu + middleman menu to modify a specific chosen return from a chosen shop
    public void modifyReturn(Shop shop, Return ret) {
        System.out.println("""
                Select a modification:
                1. Add a product to return
                2. Remove a product from return
                3. Modify a product quantity from the return
                4. Choose another transport method
                5. Process and launch the return
                6. Cancel the return (cannot be undone)
                """);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> addProductToReturn(ret);
            case 2 -> removeProductFromReturn(ret);
            case 3 -> changeProductFromReturn(ret);
            case 4 -> changeTransportMethodR(shop, ret);
            case 5 -> processReturn(ret);
            case 6 -> cancelReturn(ret);
            case default -> System.out.println("Invalid option selected!");
        }
    }

    // processes and launches the return
    public void processReturn(Return ret) {
        // the products of the return is being stored
        Map<Product, Integer> products = ret.getProductsReturned();

        // money returned is being calculated based on the list of products
        float moneyReturned = 0;
        for (Product product : products.keySet()) {
            moneyReturned = moneyReturned + product.getPrice() * products.get(product);
        }

        // days for transport that gets added for the estimated time of arrival
        int daysForTransport = TransportationMethods.calculateTransportTime(ret.getTransportMethod());

        // warning
        System.out.println("""
                This is the last chance to revise the return.
                Are you sure you want to launch this return?
                1 (yes) / 0 (no)
                """);

        // if yes, the return gets launched
        if (scanner.nextInt() == 1) {
            returnService.launchReturn(ret, moneyReturned, daysForTransport);
        } else {
            System.out.println("Return has been processed but not launched, waiting for launching instructions.");
        }
    }

    // cancels a return
    public void cancelReturn(Return ret) {
        System.out.println("""
                Are you sure you want to cancel this return?
                Once cancelled this cannot be undone and you will have to create a new return.
                1 (yes) / 0 (no)
                """);
        if (scanner.nextInt() == 1) {
            returnService.cancelReturn(ret);
        } else {
            System.out.println("Nothing has been changed, return is not cancelled.");
        }

    }

    public void addProductToReturn(Return ret) {
        // stores the map of products returned from the return
        Map<Product, Integer> products = ret.getProductsReturned();

        // shows a list of all the products from the order from return
        System.out.println("Select a product to be added:");
        orderService.showCountableItems(ret.getOrder().getProducts());

        // stores a list of all products from the order
        ArrayList<Product> keyList = new ArrayList<Product>(ret.getOrder().getProducts().keySet());

        // stores the selected product
        Product product = keyList.get(scanner.nextInt() - 1);

        // variable for the quantity limit of the product, based on the order
        int limit = ret.getOrder().getProducts().get(product);

        // scans the quantity number and checks if it's within range
        System.out.format("Quantity of product (1 - %d units):", limit);
        int quantity = scanner.nextInt();
        if (quantity > limit || quantity < 1) {
            // if it isn't display an error message and return from the method
            System.out.println("Invalid quantity selected!");
            return;
        }

        // puts the new product with quantity in the map of products
        products.put(product, quantity);

        // sets the new map to the return
        ret.setProductsReturned(products);

        System.out.println("There is not enough money or capacity left in your shop / inventory!");
    }

    public void removeProductFromReturn(Return ret) {
        // stores the list of all products from the return
        Map<Product, Integer> products = ret.getProductsReturned();

        // shows the list of all products from the return
        System.out.println("Select a product to be removed:");

        // stores the list the products
        ArrayList<Product> keyList = new ArrayList<Product>(products.keySet());
        for (Product product : keyList) {
            returnService.setCountable(products);
        }

        // stores the selected product to be removed in the following variable
        Product toRemove = keyList.get(scanner.nextInt() - 1);

        // removes the product from the map of products
        products.remove(toRemove);

        // sets the new map to the return
        ret.setProductsReturned(products);

        System.out.println("Product successfully removed from the return.");
    }

    public void changeProductFromReturn(Return ret) {
        // stores the list of all currently returned products from the return
        Map<Product, Integer> products = ret.getProductsReturned();

        // shows the list of all products from the return
        System.out.println("Select a product to be changed:");
        for (Product product : products.keySet()) {
            System.out.println(product.getName());
        }

        // stores all the products from the return in the list
        ArrayList<Product> keyList = new ArrayList<Product>(products.keySet());

        // chosen product gets stored
        Product product = keyList.get(scanner.nextInt() - 1);

        // the maximum amount of the specific product you can return is the amount of items ordered in the specific order
        int limit = products.get(product);

        // sets and verifies the new quantity selected
        System.out.format("New quantity (1 - %d units):", limit);
        int quantity = scanner.nextInt();
        if (quantity > limit || quantity < 1) {
            System.out.println("Invalid quantity selected!");
            return;
        }

        // replaces the quantity of the selected product
        products.replace(product, quantity);

        // updates the list of products returned to the return
        ret.setProductsReturned(products);
        System.out.println("Product successfully changed!");
    }

    // same method for changing the transportation method as the order, but it's for returns
    public void changeTransportMethodR(Shop shop, Return ret) {
        // takes the list of all returned products from the return
        ArrayList<Product> products = new ArrayList<>(ret.getProductsReturned().keySet());

        // takes the first item's distributor's country
        String orderCountry = products.get(0).getDistributor().getHeadquarters().getCountry();

        // the list of all available transportation methods based on the 2 countries given
        ArrayList<String> transportationMethods = localizationService.availableTransportMethods(shop.getLocation().getCountry(), orderCountry);

        // shows the list of all transportation methods available
        System.out.println("Choose the preferred transportation method:");
        for (String transport : transportationMethods) {
            System.out.println((transportationMethods.indexOf(transport) + 1) + ": " + transport);
        }

        // sets the chosen transportation method to the return
        ret.setTransportMethod(transportationMethods.get(scanner.nextInt() - 1));
        System.out.println("Transport method successfully chosen.");
    }

    // interface menu + middleman choice for the modification of the order
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
            case default -> System.out.println("Invalid option selected!");
        }
    }

    public void createOrder(Shop shop) {
        // creates a new order for the specific shop
        Order order = orderService.createNewItem(false);

        // gets the current list of orders from the specific shop
        ArrayList<Order> orders = shopOrder.getItemByKey(shop);

        // the current order gets added to the list
        orders.add(order);

        // updates the storage that keeps all the orders based by the shop
        shopOrder.updateItem(shop, orders);
    }

    // processes the order
    public void processOrder(Shop shop, Order order) {
        // price
        // days for transport
        // takes the list of products from the order
        Map<Product, Integer> products = order.getProducts();

        // variable for current price to compare later
        float price = 0;

        // calculates the price from all the products
        for (Product product : products.keySet()) {
            price = price + product.getPrice() * products.get(product);
        }

        // how much the transportation will last
        int daysForTransport = TransportationMethods.calculateTransportTime(order.getTransportationMethod());

        // last warning confirmation message
        System.out.println("""
                This is the last chance to revise the order.
                Are you sure you want to launch this order?
                1 (yes) / 0 (no)
                """);

        if (scanner.nextInt() == 1) {
            // main method to launch order called
            orderService.launchOrder(shop, order, price, daysForTransport);
        } else {
            System.out.println("Order has been processed but not launched, waiting for launching instructions.");
        }
    }

    // cancels the specific order
    public void cancelOrder(Order order) {
        // warning message
        System.out.println("""
                Are you sure you want to cancel this order?
                Once cancelled this cannot be undone and you will have to create a new order.
                1 (yes) / 0 (no)
                """);
        if (scanner.nextInt() == 1) {
            // calls the main function from the order
            orderService.cancelOrder(order);
        } else {
            System.out.println("Nothing has been changed, order is not cancelled.");
        }

    }

    public boolean checkIfEnoughMoneyAndWeight(Shop shop, Order order, float price, float weight) {
        // takes the list of products from the order
        Map<Product, Integer> products = order.getProducts();

        // takes the inventory of the shop alongside with its current money and the remaining capacity of the inventory
        Inventory inventory = shop.getInventory();
        float shopMoney = shop.getMoney();
        float remainingCapacity = inventory.getRemainingCapacity();

        // new variable to compare to the existing variables
        float usedCapacity = 0;
        float usedMoney = 0;

        // calculates the used capacity and price of all the products in the specific order
        for (Product product : products.keySet()) {
            usedCapacity = usedCapacity + product.getWeight() * products.get(product);
            usedMoney = usedMoney + product.getPrice() * products.get(product);
        }

        // if there is not enough capacity left and/or there is not enough money to buy the products, returns false
        if (usedCapacity > remainingCapacity || usedMoney > shopMoney) {
            return false;
        }

        // same condition but with current item (don't know why I implemented this like this)
        return !(usedCapacity + weight > remainingCapacity) && !(usedMoney + price > shopMoney);
    }

    public void addProductToOrder(Shop shop, Order order) {
        // takes the list of all products from the order
        Map<Product, Integer> products = order.getProducts();

        System.out.println("Select a product to be added:");
        productService.showItems(); // shows the list of all products

        // the chosen product gets stored in the following variable
        Product product = productService.getProductByIndex(scanner.nextInt() - 1);

        System.out.println("Quantity of product (units):");
        int quantity = scanner.nextInt();

        // checks if the shop has enough capital to buy the following products and if there is enough capacity in the shop's inventory
        if (checkIfEnoughMoneyAndWeight(shop, order, product.getPrice() * quantity, product.getWeight() * quantity)) {
            // if there is, the product and its quantity is added to the list of products
            products.put(product, quantity);

            // sets the new list of products to the order
            order.setProducts(products);

            System.out.println("Product - quantity successfully added to order.");
            return; // exits the function
        }
        System.out.println("There is not enough money or capacity left in your shop / inventory!");
    }

    public void removeProductFromOrder(Order order) {
        // takes the list of all products from the order
        Map<Product, Integer> products = order.getProducts();

        // shows the list of all products from the order
        System.out.println("Select a product to be removed:");
        for (Product product : products.keySet()) {
            System.out.println(product.getName());
        }

        // makes a list with all the products from the map of products from the order
        ArrayList<Product> keyList = new ArrayList<Product>(products.keySet());

        // removes entirely the selected product from the product list
        products.remove(keyList.get(scanner.nextInt() - 1));

        // sets the new list of products to the order
        order.setProducts(products);
        System.out.println("Product successfully removed from the order.");
    }

    public void changeProductFromOrder(Shop shop, Order order) {
        // takes the list of all products from the order
        Map<Product, Integer> products = order.getProducts();

        // shows the list of all products from the order
        System.out.println("Select a product to be changed:");
        for (Product product : products.keySet()) {
            System.out.println(product.getName());
        }

        // makes a list with all the products from the map of products from the order
        ArrayList<Product> keyList = new ArrayList<Product>(products.keySet());

        // stores the chosen product in the variable
        Product product = keyList.get(scanner.nextInt() - 1);

        System.out.println("New quantity (units):");
        int quantity = scanner.nextInt();

        // check if there is enough money to buy the product with the selected quantity
        // also checks if there is enough capacity left in the shop's inventory
        if (checkIfEnoughMoneyAndWeight(shop, order, product.getPrice() * quantity, product.getWeight() * quantity)) {
            // if both conditions are true, replaces the old product's quantity with the new quantity
            products.replace(product, quantity);

            // sets the new list of products to the order
            order.setProducts(products);

            System.out.println("Product - quantity successfully changed.");
            return; // exits from funtion
        }

        // if there isn't enough money or space, nothing is changed and the message is shown
        System.out.println("There is not enough money or capacity left in your shop / inventory!");
    }

    public void chooseTransportMethod(Shop shop, Order order) {
        // stores all the order's products
        ArrayList<Product> products = new ArrayList<>(order.getProducts().keySet());

        // stores the string of the country from the first product ordered
        String orderCountry = products.get(0).getDistributor().getHeadquarters().getCountry();

        // makes the list of all available transportation methods based on the shop location and the first product's distributor location
        ArrayList<String> transportationMethods = localizationService.availableTransportMethods(shop.getLocation().getCountry(), orderCountry);

        // shows the list of all available transportation methods
        System.out.println("Choose the preferred transportation method:");
        for (String transport : transportationMethods) {
            System.out.println((transportationMethods.indexOf(transport) + 1) + ": " + transport);
        }

        // the order's transportation method gets set with the chosen one
        order.setTransportationMethod(transportationMethods.get(scanner.nextInt() - 1));
        System.out.println("Transportation method successfully chosen.");
    }

    public void showOrders(Shop shop) {
        // all the orders from a specific shop gets stored
        ArrayList<Order> orders = shopOrder.getItemByKey(shop);

        // shows all the orders
        for (Order order : orders) {
            System.out.print(orders.indexOf(order) + 1 + ": ");
            System.out.println(order);
        }
    }

    // method to be used in main service
    public int selectOrder(Shop shop) {
        System.out.println("Select an order: ");
        showOrders(shop); // shows the list of all the orders from a shop

        // returns only the index of the shop
        return scanner.nextInt() - 1;
    }

    // returns the order based on it's index from a specific shop
    public Order getOrderByIndex(Shop shop, int index) {
        return shopOrder.getItemByKey(shop).get(index);
    }

    @Override
    public void addItemToCountable() {
        System.out.println("Select the shop you want to add an employee to:");
        showItems(); // shows the list of all shops

        // the selected shop's index and item gets stored in the variables
        int index = scanner.nextInt() - 1;
        Shop shop = storage.getItemByIndex(index);

        // shop is sent further down to the next method
        assignEmployeeToShop(shop);
    }


    public void assignEmployeeToShop(Shop shop) {
        // stores the lit of employees from a shop
        ArrayList<Employee> countable = getCountableByShop(shop);

        System.out.println("Select an employee from the following list:");
        employeeService.showItems(); // shows the list of all employees available

        // the selected employee gets stored in the variable
        Employee employee = employeeService.getEmployeeByIndex(scanner.nextInt() - 1);

        // if the shop already has the following employee assigned, the execution stops as it cannot double assign an employee
        if (shop.checkIfEmployeeExists(employee)) {
            System.out.println("Employee is already asigned to this shop!");
        } else {
            // adds the employee to the list
            countable.add(employee);
        }

        System.out.println("Employee successfully added to the list");
    }

    @Override
    public void removeItemFromCountable() {
        System.out.println("Select the shop you want to remove an employee from:");
        showItems(); // shows the list of all shops

        // the index and item of the shop are being stored
        int index = scanner.nextInt() - 1;
        Shop shop = storage.getItemByIndex(index);

        // the shop selected gets transferred further to the next method
        removeEmployeeFromShop(shop);
    }

    public void removeEmployeeFromShop(Shop shop) {
        // stores the list of employees from a shop
        ArrayList<Employee> countable = getCountableByShop(shop);

        System.out.println("Select an employee from the following list:");
        showCountableItems(countable); // shows the list of all employees

        // removes the selected employee from the shop
        countable.remove(scanner.nextInt() - 1);
        System.out.println("Item successfully removed from the list.");
    }

    // sets a list of employees to a specific shop
    @Override
    public void setCountable(ArrayList<Employee> countable) {
        System.out.println("Choose an item from the following list:");
        showItems(); // shows the list of all shops

        // the list of employees gets set to the selected shop
        storage.getItemByIndex(scanner.nextInt() - 1).setEmployees(countable);
    }

    // shows a list of employees
    @Override
    public void showCountableItems(ArrayList<Employee> countable) {
        // shows the list
        for (Employee employee : countable) {
            System.out.println((countable.indexOf(employee) + 1) + ":" + employee.toString());
        }
    }

    // returns the list of employees based by the shop's index
    @Override
    public ArrayList<Employee> getCountable(int index) {
        return storage.getItemByIndex(index).getEmployees();
    }

    // returns the list of employees based by the shop
    public ArrayList<Employee> getCountableByShop(Shop shop) {
        return storage.getEmployeesByShop(shop);
    }

    @Override
    public Shop createNewItem(boolean updating) {
        System.out.print("Shop name: ");
        String name = scanner.nextLine();

        System.out.print("Initial capital in $: ");
        float money = scanner.nextInt();

        // location special method
        String country = locationService.continentSelection();

        System.out.print("Location - city:");
        String city = scanner.next();
        Location location = locationService.searchForLocation(new Location(city, country));

        // new inventory for the shop gets created
        Inventory inventory = inventoryService.createNewItem(false);

        // new item gets created using its main constructor
        Shop toAdd = new Shop(name, location, money, inventory);

        // if we are updating an already existing object, we are not adding it to the storage anymore
        // instead, we only return it
        if (updating) {
            return toAdd;
        }

        // new item gets added to the storage
        storage.addNewItem(toAdd);
        System.out.println("Item successfully created and added to the storage.");
        return toAdd;
    }

    @Override
    public void showItems() {
        // the list of all shops is being stored in the variable
        ArrayList<Shop> shops = storage.getItem();

        // shows the list of all shops
        System.out.println("Full list of shops:");
        for (int i = 0; i < shops.size(); i++) {
            System.out.println("Shop " + (i + 1) + " - " + shops.get(i));
        }
        System.out.println();
    }

    @Override
    public void updateItem() {
        System.out.println("Choose an item from the following list:");
        showItems(); // shows the list of shops

        // the index and item of the selected shop gets stored
        int index = scanner.nextInt() - 1;
        Shop shop = storage.getItemByIndex(index);

        // list of available modifications
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
                // country gets selected using the specific location method
                String country = locationService.continentSelection();

                System.out.println("New city:");
                String city = scanner.next();
                Location location = locationService.searchForLocation(new Location(city, country));

                shop.setLocation(location);
            }
            case 4 -> shop = createNewItem(true);
            default -> System.out.println("Invalid option selected, returning to last menu.");
        }

        // the newly updated item gets put to the storage
        storage.updateItem(index, shop);
        System.out.println("Item successfully updated.");
    }

    // deleted an item from the shop storage
    @Override
    public void deleteItem() {
        System.out.println("Choose an item from the following list:");
        showItems(); // shows the list of shops

        // deleted the selected item from the storage
        storage.deleteItem(scanner.nextInt() - 1);

        System.out.println("Item successfully deleted!");
    }

    // for usage in the menu service
    public Shop chooseShop() {
        // if there isn't any shop, selection ends with the message
        if (storage.isEmpty()) {
            System.out.println("There is no shop to select from!");
            return null;
        }
        System.out.println("Select a shop:");
        showItems(); // shows the list of shops

        // returns the selected shop
        return storage.getItemByIndex(scanner.nextInt() - 1);
    }

    // for usage in the menu service
    public int selectReturn(Shop shop) {
        System.out.println("Select an return: ");
        showReturnals(shop); // shows the returns of a specific shop

        // returns the selected return
        return scanner.nextInt() - 1;
    }
}

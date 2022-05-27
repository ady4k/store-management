package model;

import java.util.Map;

public class Inventory {
    private float totalCapacity;
    private float remainingCapacity = totalCapacity;
    private Map<Product, Integer> products;


    // constructors
    // in general we use the variant without products because they get set over time
    public Inventory(float totalCapacity) {
        this.totalCapacity = totalCapacity;
        this.products = null;
    }

    public Inventory(float totalCapacity, Map<Product, Integer> products) {
        this.totalCapacity = totalCapacity;
        this.products = products;
    }


    // getters and setters
    public float getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(float totalCapacity) {
        // the total capacity cannot be a negative number
        // also if total capacity is already set and we have items in the inventory, we cannot set the total lower than what is occupied
        if (totalCapacity < 0 || totalCapacity - this.remainingCapacity < 0) {
            System.out.println("Invalid Total Capacity entered!\nTotal Capacity cannot be lower than 0 or the occupied capacity in the inventory.");
            return;
        }
        // automatically adjusts the remaining capacity when changing the total capacity of the inventory
        setRemainingCapacity(totalCapacity - this.totalCapacity + getRemainingCapacity());
        this.totalCapacity = totalCapacity;
    }

    public float getRemainingCapacity() {
        return remainingCapacity;
    }

    private void setRemainingCapacity(float remainingCapacity) {
        this.remainingCapacity = remainingCapacity;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    // class methods
    public void setProducts(Map<Product, Integer> products) {
        // calculates the space the products need to use when setting them
        float capacityOccupied = 0;
        for (Product key : products.keySet()) {
            capacityOccupied = capacityOccupied + key.getWeight() * products.get(key);
        }
        if (capacityOccupied > totalCapacity) {
            System.out.println("Cannot add this product list to the selected inventory because it's weight is higher than the current inventory's capacity.");
            return;
        }
        this.products = products;
    }

    public boolean modifyCapacity(float capacity, boolean adding) {
        // method that modifies the remaining capacity of the inventory based whether we are adding a product or removing it
        if (capacity > this.remainingCapacity && adding) {
            System.out.println("Remaining capacity is too low to fit the item selected!");
            return false;
        }
        if (adding) {
            setRemainingCapacity(this.remainingCapacity - capacity);
        } else {
            setRemainingCapacity(this.remainingCapacity + capacity);
        }
        return true;
    }

    @Override
    public String toString() {
        String productString = "";
        for (Product key : products.keySet()) {
            productString = productString.concat(key.getName() + ":" + products.get(key) + "\n");
        }
        return "totalCapacity: " + totalCapacity + ", remainingCapacity: " + remainingCapacity
                + "products in stock: " + productString;
    }
}

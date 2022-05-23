package model;

import java.util.Map;

public class Inventory {
    private float totalCapacity;
    private float remainingCapacity = totalCapacity;
    private Map<Product, Integer> products;

    public Inventory(float totalCapacity) {
        this.totalCapacity = totalCapacity;
        this.products = null;
    }

    public Inventory(float totalCapacity, Map<Product, Integer> products) {
        this.totalCapacity = totalCapacity;
        this.products = products;
    }

    public float getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(float totalCapacity) {
        if (totalCapacity < 0 || totalCapacity - this.remainingCapacity < 0) {
            System.out.println("Invalid Total Capacity entered!\nTotal Capacity cannot be lower than 0 or the occupied capacity in the inventory.");
            return;
        }
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

    public void setProducts(Map<Product, Integer> products) {
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

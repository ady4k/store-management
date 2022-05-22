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
        this.products = products;
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

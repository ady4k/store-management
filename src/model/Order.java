package model;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import static helper.DateFormatterHelper.dateFormat;

public class Order {

    private Map<Product, Integer> products;
    private String transportationMethod;
    private float finalPrice;
    private Employee operator;
    private int statusCode;
    private Date dateLaunched;
    private Date estimatedTimeOfArrival;

    // constructors
    // typically a new created order is going to have only the operator that manages it
    public Order(Employee operator) {
        this.operator = operator;
    }

    public Order(Map<Product, Integer> products, String transportationMethod,
                 float finalPrice, Employee operator, int statusCode, Date dateLaunched,
                 Date estimatedTimeOfArrival) {
        this.products = products;
        this.transportationMethod = transportationMethod;
        this.finalPrice = finalPrice;
        this.operator = operator;
        this.statusCode = statusCode;
        this.dateLaunched = dateLaunched;
        this.estimatedTimeOfArrival = estimatedTimeOfArrival;
    }

    // getters and setters
    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public String getTransportationMethod() {
        return transportationMethod;
    }

    public void setTransportationMethod(String transportationMethod) {
        this.transportationMethod = transportationMethod;
    }

    public float getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(float finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Employee getOperator() {
        return operator;
    }

    public void setOperator(Employee operator) {
        this.operator = operator;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Date getDateLaunched() {
        return dateLaunched;
    }

    public void setDateLaunched(Date dateLaunched) {
        this.dateLaunched = dateLaunched;
    }

    public Date getEstimatedTimeOfArrival() {
        return estimatedTimeOfArrival;
    }

    public void setEstimatedTimeOfArrival(Date estimatedTimeOfArrival) {
        this.estimatedTimeOfArrival = estimatedTimeOfArrival;
    }

    // class methods
    @Override
    public String toString() {
        String productString = "";
        // the entire products list is inside of the productString variable
        for (Product product : this.products.keySet()) {
            productString = productString.concat(product.getName() + ":" + this.products.get(product) + "\n");
        }

        // status code needs to be decoded to a readable form
        String status;
        switch (this.statusCode) {
            case 0 -> status = "in preparation";
            case 1 -> status = "launched";
            case 2 -> status = "arrived";
            case 3 -> status = "cancelled";
            case default -> status = "status not available";
        }

        // dates are going to be formatted to a simple "DD-MM-YYYY" format for easier readability
        // more specific times, like hh:mm:ss are not required
        String dateLaunchedString = null;
        try {
            dateLaunchedString = dateFormat(this.dateLaunched);
        } catch (ParseException exc) {
            throw new RuntimeException(exc);
        }

        String estimatedTimeOfArrivalString = null;
        try {
            estimatedTimeOfArrivalString = dateFormat(this.estimatedTimeOfArrival);
        } catch (ParseException exc) {
            throw new RuntimeException(exc);
        }

        // formatted string for a better readability
        return String.format("""
                Order details:
                    - productString:
                    %s
                    - price: %f
                    - status: %s
                    - date launched: %s
                    - estimated arrival date: %s
                    - operator: %s
                """, productString, finalPrice, status, dateLaunchedString, estimatedTimeOfArrivalString, operator.getFirstName() + " " + operator.getLastName());
    }
}

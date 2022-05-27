package model;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import static helper.DateFormatterHelper.dateFormat;

public class Return {
    private Order order;
    private String transportMethod;
    private float returnalPrice;
    private Map<Product, Integer> productsReturned;
    private Employee operator;
    private int statusCode;
    private Date dateLaunched;
    private Date estimatedTimeOfArrival;

    // constructors
    public Return() {
    }

    // typically we will use the constructor where we specify the order that we are referring the return to
    // and a operator to manage the return
    public Return(Order order, Employee operator) {
        this.order = order;
        this.operator = operator;
    }

    public Return(Order order, String transportMethod, float returnalPrice, Map<Product, Integer> productsReturned,
                  Employee operator, Date dateLaunched, Date estimatedTimeOfArrival) {
        this.order = order;
        this.transportMethod = transportMethod;
        this.returnalPrice = returnalPrice;
        this.productsReturned = productsReturned;
        this.operator = operator;
        this.dateLaunched = dateLaunched;
        this.estimatedTimeOfArrival = estimatedTimeOfArrival;
    }

    // getters and setters
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getTransportMethod() {
        return transportMethod;
    }

    public void setTransportMethod(String transportMethod) {
        this.transportMethod = transportMethod;
    }

    public float getReturnalPrice() {
        return returnalPrice;
    }

    public void setReturnalPrice(float returnalPrice) {
        this.returnalPrice = returnalPrice;
    }

    public Map<Product, Integer> getProductsReturned() {
        return productsReturned;
    }

    public void setProductsReturned(Map<Product, Integer> productsReturned) {
        this.productsReturned = productsReturned;
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
    // return toString is similar to the one we used in the order class, difference being that we also show the order details
    @Override
    public String toString() {
        String productString = "";
        for (Product product : this.productsReturned.keySet()) {
            productString = productString.concat(product.getName() + ":" + this.productsReturned.get(product) + "\n");
        }

        String status;
        switch (this.statusCode) {
            case 0 -> status = "in preparation";
            case 1 -> status = "launched";
            case 2 -> status = "arrived";
            case 3 -> status = "cancelled";
            case default -> status = "status not available";
        }
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
        return String.format("""
                Return details:
                    - order details:
                    %s
                    - products returned:
                    %s
                    - price: %f
                    - status: %s
                    - date launched: %s
                    - estimated arrival date: %s
                    - operator: %s
                """, order, productString, returnalPrice, status, dateLaunchedString, estimatedTimeOfArrivalString, operator.getFirstName() + " " + operator.getLastName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Return aReturn = (Return) o;

        if (returnalPrice != aReturn.returnalPrice) return false;
        if (!order.equals(aReturn.order)) return false;
        if (!transportMethod.equals(aReturn.transportMethod)) return false;
        if (!productsReturned.equals(aReturn.productsReturned)) return false;
        if (!operator.equals(aReturn.operator)) return false;
        if (!dateLaunched.equals(aReturn.dateLaunched)) return false;
        return estimatedTimeOfArrival.equals(aReturn.estimatedTimeOfArrival);
    }
}

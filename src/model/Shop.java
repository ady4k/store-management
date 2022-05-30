package model;

import java.util.ArrayList;

public class Shop {
    private String name;
    private Location location;
    private ArrayList<Employee> employees;
    private float money;
    private Inventory inventory;

    // constructors
    // typically we use the shop with a null employees array and an empty inventory specified
    public Shop(String name, Location location, float money, Inventory inventory) {
        this.name = name;
        this.location = location;
        this.money = money;
        this.employees = null;
    }

    public Shop(String name, Location location, ArrayList<Employee> employees, float money, Inventory inventory) {
        this.name = name;
        this.location = location;
        this.employees = employees;
        this.money = money;
        this.inventory = inventory;
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        // money can be theoretically equal to 0 or negative but we will use a minimum of 1 penny
        if (money < 0.01F) {
            System.out.println("Invalid capital amount entered!");
            this.money = 0.01F;
            return;
        }
        this.money = money;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    // class methods

    public boolean checkIfEmployeeExists(Employee employee) {
        // check if the employee is already assigned to the shop, so they cannot get double-assigned
        return this.employees.contains(employee);
    }

    @Override
    public String toString() {
        String employeesString = "";
        if (employees == null) {
            employeesString = "none";
        } else {
            for (Employee employee : employees) {
                employeesString = employeesString.concat(employee.toString());
            }
        }
        return "name:" + name + ", location details:" + location + ", money: " + money + ", inventory details:" + inventory +
                ", employees:" + employeesString;
    }
}

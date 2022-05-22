package model;

import java.util.ArrayList;

public class Shop {
    private String name;
    private Location location;
    private ArrayList<Employee> employees;
    private float money;
    private Inventory inventory;

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
        this.money = money;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public String toString() {
        String employeesString = "";
        for (Employee employee : employees) {
            employeesString = employeesString.concat(employee.toString());
        }
        return "name:" + name + ", location details:" + location + ", money: " + money + ", inventory details:" + inventory +
                ", employees:" + employeesString;
    }
}

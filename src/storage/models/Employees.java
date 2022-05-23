package storage.models;

import interfaces.IStorable;
import model.Employee;

import java.util.ArrayList;

public class Employees implements IStorable<Employee> {
    private final static ArrayList<Employee> employees = new ArrayList<Employee>();

    @Override
    public void addNewItem(Employee item) {
        employees.add(item);
    }

    @Override
    public ArrayList<Employee> getItem() {
        return employees;
    }

    @Override
    public Employee getItemByIndex(int index) {
        return employees.get(index);
    }

    @Override
    public void updateItem(int index, Employee item) {
        employees.set(index, item);
    }

    @Override
    public void deleteItem(int index) {
        employees.remove(index);
    }
}

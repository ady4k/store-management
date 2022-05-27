package model;

public class Category {
    private String name;

    // constructors
    public Category(String name) {
        this.name = name;
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // class methods
    @Override
    public String toString() {
        return "name: " + name;
    }
}

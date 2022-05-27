package model;

import interfaces.csv.ICsvTypeFactory;

public class Category {
    public final static ICsvTypeFactory<Category> FACTORY = new ICsvTypeFactory<Category>() {
        @Override
        public String[] getColumnNames() {
            return new String[]{"name"};
        }

        @Override
        public String[] toStringArray(Category item) {
            return new String[]{
                    item.name
            };
        }

        @Override
        public Category fromStringArray(String[] item) {
            String name = item[0];
            return new Category(name);
        }
    };
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

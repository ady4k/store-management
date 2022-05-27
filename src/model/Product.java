package model;

public class Product {
    private String name;
    private Distributor distributor;
    private Category category;
    private float price;
    private float weight;

    // constructors
    public Product() {
    }

    public Product(String name, Distributor distributor, Category category, float price, float weight) {
        this.name = name;
        this.distributor = distributor;
        this.category = category;
        this.price = price;
        this.weight = weight;
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Distributor getDistributor() {
        return distributor;
    }

    public void setDistributor(Distributor distributor) {
        this.distributor = distributor;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        // price will have a minimum, products cannot be free or lower than 0.01$
        if (price < 0.01F) {
            System.out.println("Invalid product price entered!");
            // default value
            this.price = 0.01F;
            return;
        }
        this.price = price;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        // weight will have a minimum, products cannot weight nothing or lower than 1 gram
        if (weight < 0.001F) {
            System.out.println("Invalid product weight entered!");
            // default value
            this.weight = 0.001F;
            return;
        }
        this.weight = weight;
    }

    // class methods
    @Override
    public String toString() {
        return "name: " + name + ", distributor details: " + distributor + ", category: " + category
                + ", price: " + price + ", weight: " + weight + "kg";
    }
}

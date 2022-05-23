package model;

public class Product {
    private String name;
    private Distributor distributor;
    private Category category;
    private float price;
    private float weight;

    public Product() {
    }

    public Product(String name, Distributor distributor, Category category, float price, float weight) {
        this.name = name;
        this.distributor = distributor;
        this.category = category;
        this.price = price;
        this.weight = weight;
    }

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
        if (price < 0.01F) {
            System.out.println("Invalid product price entered!");
            this.price = 0.01F;
            return;
        }
        this.price = price;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        if (weight < 0.001F) {
            System.out.println("Invalid product weight entered!");
            this.weight = 0.001F;
            return;
        }
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "name: " + name + ", distributor details: " + distributor + ", category: " + category
                + ", price: " + price + ", weight: " + weight + "kg";
    }
}

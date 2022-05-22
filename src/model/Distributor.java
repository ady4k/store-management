package model;

public class Distributor {
    private String name;
    private Headquarters headquarters;

    public Distributor(String name, Headquarters headquarters) {
        this.name = name;
        this.headquarters = headquarters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Headquarters getHeadquarters() {
        return headquarters;
    }

    public void setHeadquarters(Headquarters headquarters) {
        this.headquarters = headquarters;
    }

    @Override
    public String toString() {
        return "name: " + name + ", headquarters details: " + headquarters;
    }
}

package model;

public class Employee {
    private int cnp;
    private String firstName;
    private String lastName;
    private Location location;
    private String email;

    public Employee(int cnp, String firstName, String lastName, Location location, String email) {
        this.cnp = cnp;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.email = email;
    }

    public int getCnp() {
        return cnp;
    }

    public void setCnp(int cnp) {
        if (String.valueOf(cnp).length() != 13 || cnp < 0) {
            System.out.println("Invalid CNP entered");
            return;
        }
        this.cnp = cnp;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "cnp: " + cnp + ", "
                + "first name: " + firstName + ", " + "last name: " + lastName + ", "
                + "location: " + location + ", " + "email: " + email;
    }
}

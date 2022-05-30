package model;

import interfaces.csv.ICsvTypeFactory;

import java.util.Arrays;

public class Employee {
    public final static ICsvTypeFactory<Employee> FACTORY = new ICsvTypeFactory<Employee>() {
        @Override
        public String[] getColumnNames() {
            return new String[]{"cnp", "firstName", "lastName", "city", "country", "email"};
        }

        @Override
        public String[] toStringArray(Employee item) {
            return new String[]{
                    Integer.toString(item.cnp),
                    item.firstName,
                    item.lastName,
                    item.location.getCity(),
                    item.location.getCountry(),
                    item.email
            };
        }

        @Override
        public Employee fromStringArray(String[] item) {
            int cnp = Integer.parseInt(item[0]);
            String firstName = item[1];
            String lastName = item[2];
            Location location = Location.FACTORY.fromStringArray(Arrays.copyOfRange(item, 3, 4));
            String email = item[5];
            return new Employee(cnp, firstName, lastName, location, email);
        }
    };
    private int cnp;
    private String firstName;
    private String lastName;
    private Location location;
    private String email;

    // constructors
    public Employee(int cnp, String firstName, String lastName, Location location, String email) {
        this.cnp = cnp;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.email = email;
    }

    // getters and setters
    public int getCnp() {
        return cnp;
    }

    public void setCnp(int cnp) {
        // checks for input length to be equal to romanian CNP length
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

    // class methods
    @Override
    public String toString() {
        return "cnp: " + cnp + ", "
                + "first name: " + firstName + ", " + "last name: " + lastName + ", "
                + "location: " + location + ", " + "email: " + email;
    }
}

package model;

public class Headquarters extends Location{
    private int postalCode;
    private String street;
    private int streetNumber;
    private int phoneNumber;

    public Headquarters() {
        super();
    }
    public Headquarters(String city, String country, int postalCode, String street, int streetNumber, int phoneNumber) {
        super(city, country);
        this.postalCode = postalCode;
        this.street = street;
        this.streetNumber = streetNumber;
        this.phoneNumber = phoneNumber;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return super.toString() + "postal code: " + postalCode + ", " + "street: " + street + ", " + streetNumber
                + ", phone number" + phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Headquarters that = (Headquarters) o;

        if (postalCode != that.postalCode) return false;
        if (streetNumber != that.streetNumber) return false;
        if (phoneNumber != that.phoneNumber) return false;
        return street.equals(that.street);
    }
}

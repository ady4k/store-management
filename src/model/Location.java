package model;


import interfaces.csv.ICsvTypeFactory;

public class Location {
    public final static ICsvTypeFactory<Location> FACTORY = new ICsvTypeFactory<Location>() {
        @Override
        public String[] getColumnNames() {
            return new String[]{"city", "country"};
        }

        @Override
        public String[] toStringArray(Location item) {
            return new String[]{
                    item.city,
                    item.country
            };
        }

        @Override
        public Location fromStringArray(String[] item) {
            String city = item[0];
            String country = item[1];
            return new Location(city, country);
        }
    };
    private String city;
    private String country;

    // constructors
    public Location() {
    }

    public Location(String city, String country) {
        this.city = city;
        this.country = country;
    }

    // getters and setters
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    // class methods
    @Override
    public String toString() {
        return "city: " + city + ", " + "country: " + country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (!city.equals(location.city)) return false;
        return country.equals(location.country);
    }

}

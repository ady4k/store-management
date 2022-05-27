package service;

import storage.predefined.Continents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LocalizationService {
    // copy of the continents
    List<List<String>> continents = Continents.getContinents();

    // method that returns the continent of a given country
    // brute forces through all the sub-lists of each continent
    public String checkCountry(String country) {
        for (List<String> continent : continents) {
            if (continent.contains(country)) {
                return continent.get(0);
            }
        }
        return "undefined";
    }

    // method that return the list of all the available transportation methods based on 2 given countries
    public ArrayList<String> availableTransportMethods(String country1, String country2) {
        ArrayList<String> transportMethods = new ArrayList<String>();

        // shows the different "Areas" that a continent can be in
        // based on these, a set of transport methods can be selected
        List<String> us = Arrays.asList("northAmerica", "southAmerica");
        List<String> middle = Arrays.asList("europe", "asia", "africa");

        // naive implementation of the given problem
        // different checks of each continent
        if (us.contains(country1) && us.contains(country2) || middle.contains(country1) && middle.contains(country2) || Objects.equals(country1, country2)) {
            // if the two countries are in the same area, all the transport methods can be used
            // another naive hardcoded implementation
            transportMethods.add("road");
            transportMethods.add("ocean");
            transportMethods.add("railways");
            transportMethods.add("air");
            return transportMethods;
        }
        // if one country is on a different "area" than the other, only these 2 methods can be used
        transportMethods.add("ocean");
        transportMethods.add("air");
        return transportMethods;
    }

}

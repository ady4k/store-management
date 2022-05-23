package service;

import storage.predefined.Continents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LocalizationService {

    List<List<String>> continents = Continents.getContinents();

    public String checkCountry(String country) {
        for (List<String> continent : continents) {
            if (continent.contains(country)) {
                return continent.get(0);
            }
        }
        return "undefined";
    }

    /*          transports.put("road", 1.35F);
                transports.put("ocean", 1.15F);
                transports.put("railways", 1F);
                transports.put("air", 1.45F);

     */
    public ArrayList<String> availableTransportMethods(String country1, String country2) {
        ArrayList<String> transportMethods = new ArrayList<String>();
        List<String> us = Arrays.asList("northAmerica", "southAmerica");
        List<String> middle = Arrays.asList("europe", "asia", "africa");

        if (us.contains(country1) && us.contains(country2) || middle.contains(country1) && middle.contains(country2) || Objects.equals(country1, country2)) {
            transportMethods.add("road");
            transportMethods.add("ocean");
            transportMethods.add("railways");
            transportMethods.add("air");
            return transportMethods;
        }
        transportMethods.add("ocean");
        transportMethods.add("air");
        return transportMethods;
    }

}

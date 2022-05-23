package service;

import storage.predefined.Continents;

import java.util.List;

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

}

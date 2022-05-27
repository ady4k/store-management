package storage.predefined;

import java.util.Arrays;
import java.util.List;

public final class Continents {
    // pointless to keep inside a class, but here are kept all the countries and the continents inside of Lists (except Antarctica and a couple of countries)
    private final static List<String> asia = Arrays.asList("asia", "Armenia", "Azerbaijan", "Bahrain", "Bangladesh", "Bhutan", "Brunei", "Cambodia", "China", "Cyprus", "Georgia", "India", "Indonesia",
            "Iran", "Iraq", "Israel", "Japan", "Jordan", "Kazakhstan", "Kuwait", "Kyrgyzstan", "Laos", "Lebanon", "Malaysia", "Maldives", "Mongolia", "Myanmar", "Nepal", "North Korea", "Oman",
            "Pakistan", "Palestine", "Philippines", "Qatar", "Russia", "Saudi Arabia", "Singapore", "South Korea", "Sri Lanka", "Syria", "Taiwan", "Tajikistan", "Thailand", "Timor Leste", "Turkey",
            "Turkmenistan", "United Arab Emirates", "Uzbekistan", "Vietnam", "Yemen");
    private final static List<String> africa = Arrays.asList("africa", "Algeria", "Angola", "Benin", "Botswana", "Burkina Faso", "Burundi", "Cabo Verde", "Cameroon", "Central African Republic", "Chad",
            "Comoros", "Democratic Republic of the Congo", "Republic of the Congo", "Cote d'Ivoire", "Djibouti", "Egypt", "Equatorial Guinea", "Eritrea", "Ethiopia", "Gabon", "Gambia", "Ghana",
            "Guinea", "Guinea Bissau", "Kenya", "Lesotho", "Liberia", "Libya", "Madagascar", "Malawi", "Mali", "Mauritania", "Mauritius", "Morocco", "Mozambique", "Namibia", "Niger", "Nigeria",
            "Rwanda", "Sao Tome and Principe", "Senegal", "Seychelles", "Sierra Leone", "Somalia", "South Africa", "South Sudan", "Sudan", "Swaziland", "Tanzania", "Togo", "Tunisia", "Uganda",
            "Zambia", "Zimbabwe");
    private final static List<String> northAmerica = Arrays.asList("northAmerica", "Antigua and Barbuda", "Bahamas", "Barbados", "Belize", "Canada", "Costa Rica", "Cuba", "Dominica", "Dominican Republic",
            "El Salvador", "Grenada", "Guatemala", "Haiti", "Honduras", "Jamaica", "Mexico", "Nicaragua", "Panama", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines",
            "Trinidad and Tobago", "United States of America");
    private final static List<String> southAmerica = Arrays.asList("southAmerica", "Argentina", "Bolivia", "Brazil", "Chile", "Colombia", "Ecuador", "Guyana", "Paraguay", "Peru", "Suriname", "Uruguay", "Venezuela");
    private final static List<String> europe = Arrays.asList("europe", "Albania", "Andorra", "Armenia", "Austria", "Azerbaijan", "Belarus", "Belgium", "Bosnia and Herzegovina", "Bulgaria", "Croatia", "Cyprus",
            "Czech Republic", "Denmark", "Estonia", "Finland", "France", "Georgia", "Germany", "Greece", "Iceland", "Ireland", "Italy", "Kazakhstan", "Kosovo", "Latvia", "Liechtenstein", "Lithuania",
            "Luxembourg", "Macedonia", "Malta", "Moldova", "Monaco", "Montenegro", "Netherlands", "Norway", "Poland", "Portugal", "Romania", "Russia", "San Marino", "Serbia", "Slovakia", "Slovenia",
            "Spain", "Sweden", "Switzerland", "Turkey", "Ukraine", "United Kingdom", "Vatican City");
    private final static List<String> australia = Arrays.asList("australia", "Australia", "Federated Islands of Micronesia", "Fiji", "French Polynesia", "Guam", "Kiribati", "Marshall Islands", "Nauru", "New Zealand",
            "Paulau", "Papua New Guinea", "Samoa", "Solomon Islands", "Tonga", "Tuvala", "Vanuata");
    private final static List<List<String>> continents = Arrays.asList(asia, africa, northAmerica, southAmerica, europe, australia);

    // constructor private as there is no point to instantiate the class
    private Continents() {
        throw new UnsupportedOperationException();
    }

    // get method
    public static List<List<String>> getContinents() {
        return continents;
    }
}

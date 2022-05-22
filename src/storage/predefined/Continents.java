package storage.predefined;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Continents {

    private Continents() {
        throw new UnsupportedOperationException();
    }
    private final static List<String> asia = Arrays.asList("asia", "tie", "sal");
    private final static List<String> africa = Arrays.asList("africa", "tie", "sal");
    private final static List<String> northAmerica = Arrays.asList("northAmerica", "tie", "sal");
    private final static List<String> southAmerica = Arrays.asList("southAmerica", "tie", "sal");
    private final static List<String> europe = Arrays.asList("europe", "tie", "sal");
    private final static List<String> australia = Arrays.asList("australia", "tie", "sal");
    private final static List<List<String>> continents = Arrays.asList(asia, africa, europe, northAmerica, southAmerica, australia);

    public static List<List<String>> getContinents() {
        return continents;
    }
}

package storage.predefined;

import java.util.HashMap;
import java.util.Map;

public final class TransportationMethods {
    private TransportationMethods() {
        throw new UnsupportedOperationException();
    }
    private final static Map<String, Float> transports = new HashMap<String, Float>() {
        static {
            transports.put("road", 1.35F);
            transports.put("ocean", 1.15F);
            transports.put("railways", 1F);
            transports.put("air", 1.45F);
        }
    };

    public static Map<String, Float> getTransports() {
        return transports;
    }
}

package storage.predefined;

import java.util.HashMap;
import java.util.Map;

public final class TransportationMethods {
    private static final Map<String, Float> transports = new HashMap<String, Float>();

    static {
        transports.put("road", 1.35F);
        transports.put("ocean", 1.15F);
        transports.put("railways", 1F);
        transports.put("air", 1.45F);
    }

    private TransportationMethods() {
        throw new UnsupportedOperationException();
    }

    public static Map<String, Float> getTransports() {
        return transports;
    }

    public static float getTransportMultiplier(String transport) {
        return transports.get(transport);
    }


    public static int calculateTransportTime(String transport) {
        switch (transport) {
            case "road" -> {
                return 25;
            }
            case "ocean" -> {
                return 90;
            }
            case "railways" -> {
                return 45;
            }
            case "air" -> {
                return 7;
            }
            case default -> {
                System.out.println("Invalid transport method");
                return -1;
            }
        }
    }


}

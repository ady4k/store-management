package storage.predefined;

import java.util.HashMap;
import java.util.Map;

public final class TransportationMethods {
    // HashMap that keeps all the different types of transport methods
    private static final Map<String, Float> transports = new HashMap<String, Float>();

    // map contains the transport method name, with the price multiplier as its value
    // for example, if an order costs 100$ and the transport used is by air, the final value of the order will be
    // 100 * 1.45 = 145$
    static {
        transports.put("road", 1.35F);
        transports.put("ocean", 1.15F);
        transports.put("railways", 1F);
        transports.put("air", 1.45F);
    }

    // class will not be able to be instantiated, as there is no point to do so
    private TransportationMethods() {
        throw new UnsupportedOperationException();
    }

    // get methods
    public static Map<String, Float> getTransports() {
        return transports;
    }

    public static float getTransportMultiplier(String transport) {
        return transports.get(transport);
    }

    // this method is used to give an approximation of the estimated time of arrival date
    // based on the transport given, there will be returned a "number of days" that gets added to the order launch date
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

package main.java.com.deliveryoptimizer.utils;

import main.java.com.deliveryoptimizer.model.GeoLocation;

public class HaversineUtils {
    private static final double EARTH_RADIUS = 6371;
    private static final double AVERAGE_SPEED_KMH = 20.0;

    public static double calculateDistance(GeoLocation loc1, GeoLocation loc2) {
        double latDistance = Math.toRadians(loc2.getLatitude() - loc1.getLatitude());
        double lonDistance = Math.toRadians(loc2.getLongitude() - loc1.getLongitude());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(loc1.getLatitude())) * Math.cos(Math.toRadians(loc2.getLatitude())) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;

        return Math.round(distance * 1000.0) / 1000.0;
    }

    public static double calculateTravelTime(GeoLocation loc1, GeoLocation loc2) {
        double time = calculateDistance(loc1, loc2) / AVERAGE_SPEED_KMH;
        return Math.round(time * 1000.0) / 1000.0;
    }
}

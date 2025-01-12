package main.java.com.deliveryoptimizer.service;

import main.java.com.deliveryoptimizer.model.GeoLocation;
import main.java.com.deliveryoptimizer.model.Order;

import java.util.List;
import java.util.Scanner;

public class ValidationUtils {
    public static double validateLatitude(Scanner scanner) {
        while (true) {
            double lat = scanner.nextDouble();
            if (lat >= -90 && lat <= 90) {
                return lat;
            }
            System.out.println("Invalid latitude. Please enter a value between -90 and 90:");
        }
    }

    public static double validateLongitude(Scanner scanner) {
        while (true) {
            double lon = scanner.nextDouble();
            if (lon >= -180 && lon <= 180) {
                return lon;
            }
            System.out.println("Invalid longitude. Please enter a value between -180 and 180:");
        }
    }

    public static int validatePositiveInteger(Scanner scanner) {
        while (true) {
            int value = scanner.nextInt();
            if (value > 0) {
                return value;
            }
            System.out.println("Invalid input. Please enter a positive integer:");
        }
    }

    public static boolean locationsAreEqual(GeoLocation loc1, GeoLocation loc2) {
        return loc1.getLatitude() != loc2.getLatitude() || loc1.getLongitude() != loc2.getLongitude();
    }

    public static boolean allLocationsAreSame(GeoLocation amanLocation, List<Order> orders) {
        for (Order order : orders) {
            if (locationsAreEqual(amanLocation, order.getRestaurantLocation()) ||
                    locationsAreEqual(amanLocation, order.getConsumerLocation())) {
                return false;
            }
        }
        return true;
    }
}

package main.java.com.deliveryoptimizer.controller;

import main.java.com.deliveryoptimizer.model.GeoLocation;
import main.java.com.deliveryoptimizer.model.Order;
import main.java.com.deliveryoptimizer.service.RouteService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static main.java.com.deliveryoptimizer.service.ValidationUtils.*;

public class RouteController {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Aman’s location (latitude and longitude) (Example - 12.971598 77.594566) : ");

        double amanLat = validateLatitude(scanner);
        double amanLon = validateLongitude(scanner);
        GeoLocation amanLocation = new GeoLocation(amanLat, amanLon);

        System.out.println("Enter the number of orders:");
        int orderCount = validatePositiveInteger(scanner);

        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < orderCount; i++) {
            System.out.println("Enter details for order " + (i + 1) + ":");

            System.out.println("    Restaurant location (latitude and longitude):");
            double restLat = validateLatitude(scanner);
            double restLon = validateLongitude(scanner);
            GeoLocation restaurant = new GeoLocation(restLat, restLon);

            System.out.println("    Consumer location (latitude and longitude):");
            double consLat = validateLatitude(scanner);
            double consLon = validateLongitude(scanner);
            GeoLocation consumer = new GeoLocation(consLat, consLon);

            if (restLat == consLat && restLon == consLon) {
                System.out.println("Warning: Restaurant and consumer locations are the same for order " + (i + 1) + ".");
            }

            orders.add(new Order(restaurant, consumer));
        }

        // Check for identical locations across all inputs
        if (allLocationsAreSame(amanLocation, orders)) {
            System.out.println("Warning: All locations provided are identical. The path may be trivial.");
        }

        RouteService routeService = new RouteService();
        String result = routeService.findShortestTimeAndPath(amanLocation, orders);

        System.out.println(result);
    }
}

package main.java.com.deliveryoptimizer.service;

import main.java.com.deliveryoptimizer.model.GeoLocation;
import main.java.com.deliveryoptimizer.model.Order;
import main.java.com.deliveryoptimizer.utils.HaversineUtils;

import java.util.ArrayList;
import java.util.List;

public class RouteService {
    public String findShortestTimeAndPath(GeoLocation amanLocation, List<Order> orders) {
        int n = orders.size();
        int fullMask = (1 << (2 * n)) - 1;
        double[][] dp = new double[1 << (2 * n)][2 * n];
        int[][] parent = new int[1 << (2 * n)][2 * n];

        for (int mask = 0; mask < (1 << (2 * n)); mask++) {
            for (int i = 0; i < 2 * n; i++) {
                dp[mask][i] = Double.MAX_VALUE;
                parent[mask][i] = -1;
            }
        }

        List<GeoLocation> locations = new ArrayList<>();
        for (Order order : orders) {
            locations.add(order.getRestaurantLocation());
            locations.add(order.getConsumerLocation());
        }

        // Base case: Starting from Aman’s location to each restaurant
        for (int i = 0; i < n; i++) {
            dp[1 << i][i] = HaversineUtils.calculateTravelTime(amanLocation, locations.get(i));
        }

        for (int mask = 1; mask < (1 << (2 * n)); mask++) {
            for (int i = 0; i < 2 * n; i++) {
                if ((mask & (1 << i)) == 0) continue; // Skip if location `i` is not visited

                for (int j = 0; j < 2 * n; j++) {
                    if ((mask & (1 << j)) != 0) continue; // Skip if location `j` is already visited
                    if (j >= n && (mask & (1 << (j - n))) == 0) continue; // Consumer can't be visited before its restaurant

                    double time = dp[mask][i] + HaversineUtils.calculateTravelTime(locations.get(i), locations.get(j));
                    if (time < dp[mask | (1 << j)][j]) {
                        dp[mask | (1 << j)][j] = time;
                        parent[mask | (1 << j)][j] = i;
                    }
                }
            }
        }

        double shortestTime = Double.MAX_VALUE;
        int lastNode = -1;
        for (int i = 0; i < 2 * n; i++) {
            double time = dp[fullMask][i] + HaversineUtils.calculateTravelTime(locations.get(i), amanLocation);
            if (time < shortestTime) {
                shortestTime = time;
                lastNode = i;
            }
        }

        List<String> path = new ArrayList<>();
        int currentMask = fullMask;
        while (lastNode != -1) {
            GeoLocation location = locations.get(lastNode);
            path.add(0, (lastNode < n ? "Restaurant " : "Consumer ") + (lastNode % n + 1) +
                    " (" + location.getLatitude() + ", " + location.getLongitude() + ")");
            int temp = lastNode;
            lastNode = parent[currentMask][lastNode];
            currentMask ^= (1 << temp);
        }

        path.add(0, "Aman (" + amanLocation.getLatitude() + ", " + amanLocation.getLongitude() + ")");
        path.add("Aman (" + amanLocation.getLatitude() + ", " + amanLocation.getLongitude() + ")");
        return "Shortest time: " + Math.round(shortestTime * 100.0) / 100.0 + " hours\nPath: " + String.join(" -> ", path);
    }
}

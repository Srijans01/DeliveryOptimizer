package test.com.deliveryoptimizer.service;

import main.java.com.deliveryoptimizer.model.GeoLocation;
import main.java.com.deliveryoptimizer.model.Order;
import main.java.com.deliveryoptimizer.service.RouteService;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouteServiceTest {
    @Test
    public void testFindShortestTimeAndPath_BangaloreLocations() {
        GeoLocation amanLocation = new GeoLocation(12.971598, 77.594566); // Bangalore City Center
        List<Order> orders = Arrays.asList(
                new Order(
                        new GeoLocation(12.935242, 77.624828), // Restaurant 1 (Koramangala)
                        new GeoLocation(12.937345, 77.622678)  // Consumer 1 (BTM Layout)
                ),
                new Order(
                        new GeoLocation(12.934637, 77.610116), // Restaurant 2 (Indiranagar)
                        new GeoLocation(12.938567, 77.626789)  // Consumer 2 (HSR Layout)
                )
        );
        RouteService routeService = new RouteService();

        String result = routeService.findShortestTimeAndPath(amanLocation, orders);

        double actualShortestTime = Double.parseDouble(result.split(":")[1].split(" ")[1]);

        double expectedShortestTime = 0.59;
        assertEquals(expectedShortestTime, Math.round(actualShortestTime * 100.0) / 100.0,
                0.01, "The calculated shortest time is incorrect.");

        String actualPath = result.split("Path: ")[1].trim();
        List<String> actualLocations = Arrays.asList(actualPath.split(" -> "));

        List<String> expectedLocations = Arrays.asList(
                "Aman (12.971598, 77.594566)",
                "Restaurant 2 (12.937345, 77.622678)",
                "Consumer 2 (12.938567, 77.626789)",
                "Restaurant 1 (12.935242, 77.624828)",
                "Consumer 1 (12.934637, 77.610116)",
                "Aman (12.971598, 77.594566)"
        );

        assertEquals(expectedLocations, actualLocations, "The calculated path is incorrect.");
    }
}

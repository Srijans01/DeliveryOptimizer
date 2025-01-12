package main.java.com.deliveryoptimizer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {
    private GeoLocation restaurantLocation;
    private GeoLocation consumerLocation;
}

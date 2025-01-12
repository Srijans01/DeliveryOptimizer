package main.java.com.deliveryoptimizer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeoLocation {
    private double latitude;
    private double longitude;
}

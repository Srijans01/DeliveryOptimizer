package main.java.com.deliveryoptimizer;

import main.java.com.deliveryoptimizer.controller.RouteController;

public class DeliveryOptimizerApplication {
    public static void main(String[] args) {
        RouteController controller = new RouteController();
        controller.run();
    }
}
package com.smartparking.factory;

import com.smartparking.model.*;

public class VehicleFactory {
    public static Vehicle getVehicle(String type, int number) {
        switch (type.toLowerCase()) {
            case "car":  return new Car(number);
            case "bike": return new Bike(number);
            case "bus":  return new Bus(number);
            default:     return null;
        }
    }
}

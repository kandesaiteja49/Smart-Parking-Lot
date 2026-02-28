package com.smartparking.model;

import com.smartparking.enums.SpotType;

public class Car extends Vehicle {
    public Car(int number) { super(number); }

    @Override
    public String getvehicleType() { return "car"; }

    @Override
    public SpotType getRequiredSpotType() { return SpotType.MEDIUM; }
}

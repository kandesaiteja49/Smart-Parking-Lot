package com.smartparking.model;

import com.smartparking.enums.SpotType;

public class Bike extends Vehicle {
    public Bike(int number) { super(number); }

    @Override
    public String getvehicleType() { return "bike"; }

    @Override
    public SpotType getRequiredSpotType() { return SpotType.SMALL; }
}

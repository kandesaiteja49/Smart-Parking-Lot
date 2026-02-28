package com.smartparking.model;

import com.smartparking.enums.SpotType;

public class Bus extends Vehicle {
    public Bus(int number) { super(number); }

    @Override
    public String getvehicleType() { return "bus"; }

    @Override
    public SpotType getRequiredSpotType() { return SpotType.LARGE; }
}

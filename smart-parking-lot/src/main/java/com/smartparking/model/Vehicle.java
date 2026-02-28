package com.smartparking.model;

import com.smartparking.enums.SpotType;

public abstract class Vehicle {
    public int number;

    public Vehicle(int number) {
        this.number = number;
    }

    public abstract String getvehicleType();
    public abstract SpotType getRequiredSpotType();
}

package com.smartparking.model;

import com.smartparking.enums.SpotType;

public class ParkingSpot {
    public int id;
    public Vehicle vehicle;
    public boolean isFree = true;
    public int floorno;
    public SpotType spotType;

    public ParkingSpot(int id, int floorno, SpotType spotType) {
        this.id = id;
        this.floorno = floorno;
        this.spotType = spotType;
    }

    public synchronized void park(Vehicle vehicle) {
        this.vehicle = vehicle;
        isFree = false;
    }

    public synchronized void unpark() {
        this.vehicle = null;
        isFree = true;
    }
}

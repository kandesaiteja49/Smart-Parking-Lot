package com.smartparking.ticket;

import com.smartparking.model.ParkingSpot;
import com.smartparking.model.Vehicle;

public class Ticket {
    public long entryTime;
    public Vehicle vehicle;
    public ParkingSpot spot;

    public Ticket(Vehicle vehicle, ParkingSpot spot) {
        this.vehicle = vehicle;
        this.spot = spot;
        this.entryTime = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "VehicleType:" + vehicle.getvehicleType()
             + " VehicleNo:" + vehicle.number
             + " Floor:" + spot.floorno
             + " SpotId:" + spot.id
             + " SpotType:" + spot.spotType;
    }
}

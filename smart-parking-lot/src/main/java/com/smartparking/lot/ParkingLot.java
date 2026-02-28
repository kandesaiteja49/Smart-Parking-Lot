package com.smartparking.lot;

import com.smartparking.enums.SpotType;
import com.smartparking.model.*;
import com.smartparking.strategy.*;
import com.smartparking.ticket.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ParkingLot {

    // Thread-safe Singleton using volatile + double-checked locking
    private static volatile ParkingLot instance;
    private List<Floor> floors = new ArrayList<>();
    private Map<Integer, Ticket> activeTickets = new ConcurrentHashMap<>();
    private int ticketCounter = 1;

    private ParkingLot() {}

    public static ParkingLot getInstance() {
        if (instance == null) {
            synchronized (ParkingLot.class) {
                if (instance == null) {
                    instance = new ParkingLot();
                }
            }
        }
        return instance;
    }

    public void createFloor(int floorno, int smallSpots, int mediumSpots, int largeSpots) {
        floors.add(new Floor(floorno, smallSpots, mediumSpots, largeSpots));
    }

    public synchronized Ticket parTicket(Vehicle vehicle) {
        SpotType required = vehicle.getRequiredSpotType();
        ParkingSpot spot = null;
        for (Floor floor : floors) {
            spot = floor.getFreeSpot(required);
            if (spot != null) {
                spot.park(vehicle);
                break;
            }
        }
        if (spot == null) {
            System.out.println("No available " + required + " spot for vehicle " + vehicle.number);
            return null;
        }
        Ticket ticket = new Ticket(vehicle, spot);
        activeTickets.put(ticketCounter++, ticket);
        return ticket;
    }

    public synchronized double exitvechile(Ticket ticket) {
        ParkingSpot spot = ticket.spot;
        spot.unpark();
        activeTickets.values().remove(ticket);

        long duration = System.currentTimeMillis() - ticket.entryTime;
        Payment payment;
        switch (ticket.vehicle.getvehicleType().toLowerCase()) {
            case "car":  payment = new CarPay();  break;
            case "bike": payment = new BikePay(); break;
            case "bus":  payment = new BusPay();  break;
            default:     payment = new CarPay();  break;
        }
        return payment.pay(duration);
    }

    public void printAvailability() {
        System.out.println("\n--- Real-Time Parking Availability ---");
        for (Floor floor : floors) {
            System.out.println("Floor " + floor.floorno
                + " | SMALL(Bike): " + floor.getAvailableCount(SpotType.SMALL)
                + " | MEDIUM(Car): " + floor.getAvailableCount(SpotType.MEDIUM)
                + " | LARGE(Bus): "  + floor.getAvailableCount(SpotType.LARGE));
        }
        System.out.println("Active tickets: " + activeTickets.size());
        System.out.println("--------------------------------------\n");
    }
}

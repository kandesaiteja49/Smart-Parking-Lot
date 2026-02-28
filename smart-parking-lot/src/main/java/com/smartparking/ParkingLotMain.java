package com.smartparking;

import com.smartparking.factory.VehicleFactory;
import com.smartparking.lot.ParkingLot;
import com.smartparking.model.Vehicle;
import com.smartparking.ticket.Ticket;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotMain {
    public static void main(String[] args) throws InterruptedException {
        ParkingLot lot = ParkingLot.getInstance();

        // Create floors: (floorNo, smallSpots, mediumSpots, largeSpots)
        lot.createFloor(0, 2, 3, 1);
        lot.createFloor(1, 1, 2, 1);

        lot.printAvailability();

        // Create vehicles via Factory Pattern
        Vehicle car1  = VehicleFactory.getVehicle("car",  11234);
        Vehicle car2  = VehicleFactory.getVehicle("car",  343);
        Vehicle bike1 = VehicleFactory.getVehicle("bike", 143);
        Vehicle bus1  = VehicleFactory.getVehicle("bus",  999);

        // Check-in
        Ticket t1 = lot.parTicket(car1);
        Ticket t2 = lot.parTicket(car2);
        Ticket t3 = lot.parTicket(bike1);
        Ticket t4 = lot.parTicket(bus1);

        System.out.println("Checked in: " + t1);
        System.out.println("Checked in: " + t2);
        System.out.println("Checked in: " + t3);
        System.out.println("Checked in: " + t4);

        lot.printAvailability();

        Thread.sleep(2000);

        // Check-out + Fee
        System.out.println(t1 + " | Fee: Rs." + lot.exitvechile(t1));
        System.out.println(t2 + " | Fee: Rs." + lot.exitvechile(t2));
        System.out.println(t3 + " | Fee: Rs." + lot.exitvechile(t3));
        System.out.println(t4 + " | Fee: Rs." + lot.exitvechile(t4));

        lot.printAvailability();

        // Concurrency Test
        System.out.println("--- Concurrency Test: 4 vehicles entering simultaneously ---");
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            final int num = i;
            threads.add(new Thread(() -> {
                Vehicle v = VehicleFactory.getVehicle(num % 2 == 0 ? "car" : "bike", 5000 + num);
                Ticket t = lot.parTicket(v);
                if (t != null) System.out.println("Thread-" + num + " parked: " + t);
            }));
        }
        threads.forEach(Thread::start);
        for (Thread th : threads) th.join();
        lot.printAvailability();
    }
}

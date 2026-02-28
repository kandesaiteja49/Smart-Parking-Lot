package com.smartparking.model;

import com.smartparking.enums.SpotType;
import java.util.ArrayList;
import java.util.List;

public class Floor {
    public int floorno;
    public List<ParkingSpot> spots = new ArrayList<>();

    public Floor(int floorno, int smallSpots, int mediumSpots, int largeSpots) {
        this.floorno = floorno;
        int id = 1;
        for (int i = 0; i < smallSpots;  i++) spots.add(new ParkingSpot(id++, floorno, SpotType.SMALL));
        for (int i = 0; i < mediumSpots; i++) spots.add(new ParkingSpot(id++, floorno, SpotType.MEDIUM));
        for (int i = 0; i < largeSpots;  i++) spots.add(new ParkingSpot(id++, floorno, SpotType.LARGE));
    }

    public synchronized ParkingSpot getFreeSpot(SpotType required) {
        for (ParkingSpot sp : spots) {
            if (sp.isFree && sp.spotType == required) return sp;
        }
        return null;
    }

    public long getAvailableCount(SpotType type) {
        return spots.stream().filter(s -> s.isFree && s.spotType == type).count();
    }
}

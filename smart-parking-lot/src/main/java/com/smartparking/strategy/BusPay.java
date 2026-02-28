package com.smartparking.strategy;

public class BusPay implements Payment {
    @Override
    public double pay(long durationMillis) {
        // Rs.100 per hour, minimum 1 hour
        long hours = Math.max(1, (long) Math.ceil(durationMillis / 3600000.0));
        return hours * 100.0;
    }
}

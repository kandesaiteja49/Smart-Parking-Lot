package com.smartparking.strategy;

public class BikePay implements Payment {
    @Override
    public double pay(long durationMillis) {
        // Rs.20 per hour, minimum 1 hour
        long hours = Math.max(1, (long) Math.ceil(durationMillis / 3600000.0));
        return hours * 20.0;
    }
}

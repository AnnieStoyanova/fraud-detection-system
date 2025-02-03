package com.fraudDetectionBankSystem.fraud_detection_system.utils;

public class GeoUtils {

    private static final double EARTH_RADIUS_KM = 6371; // km

    // Haversine formula
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {

        // distance between latitudes and longitudes
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        // convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // calculate the value under the square root
        double underSquareRoot = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                        Math.cos(lat1) *
                        Math.cos(lat2);

        // the shortest distance between two points on a sphere:
        return 2 * EARTH_RADIUS_KM * Math.asin(Math.sqrt(underSquareRoot));
    }
}

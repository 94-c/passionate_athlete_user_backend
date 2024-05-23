package com.backend.athlete.global.util;

public class PhysicalUtils {
    public PhysicalUtils() {
    }


    public static double calculateBMI(double weight, double heightInCm) {
        double heightInMeters = heightInCm / 100;
        return weight / (heightInMeters * heightInMeters);
    }

    public static double calculateBodyFatPercentage(double bodyFatMass, double weight) {
        return (bodyFatMass / weight) * 100;
    }

    public static double calculateVisceralFatPercentage(double bodyFatPercentage) {
        return bodyFatPercentage * 0.1; // Simplified assumption
    }

    public static double calculateBMR(double weight, double heightInCm, int age, String gender) {
        if (gender.equalsIgnoreCase("male")) {
            return 88.362 + (13.397 * weight) + (4.799 * heightInCm) - (5.677 * age);
        } else {
            return 447.593 + (9.247 * weight) + (3.098 * heightInCm) - (4.330 * age);
        }
    }

}

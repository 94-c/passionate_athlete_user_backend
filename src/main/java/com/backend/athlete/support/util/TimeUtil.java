package com.backend.athlete.support.util;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    public static Duration parseDuration(String time) {
        return Duration.parse("PT" + time.replace(":", "H").replace("H", "M") + "S");
    }

    public static LocalTime parseTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return LocalTime.parse(time, formatter);
    }

    public static boolean isTimeShorter(String time1, String time2) {
        Duration duration1 = parseDuration(time1);
        Duration duration2 = parseDuration(time2);
        return duration1.compareTo(duration2) < 0;
    }
}

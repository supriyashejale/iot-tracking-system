package com.example.iottrackingsystem.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Utils {
    static Logger logger = LogManager.getLogger(Utils.class.getCanonicalName());
    public static Long getCurrentDatetimeUTCFormatToMilliseconds(){
        // Get current instant in milliseconds from the system clock
        long currentMillis = Instant.now().toEpochMilli();
        logger.debug("Get current timestamp in milliseconds if timestamp not available in request parameter: {} : {}" , currentMillis, convertToUTCDatetime(String.valueOf(currentMillis)));
        return currentMillis;
    }


    public static String convertToUTCDatetime(String milliseconds ){

        // Parse the String to a long
        long epochMillis = Long.parseLong(milliseconds);

        // Convert to Instant object representing UTC time
        Instant instant = Instant.ofEpochMilli(epochMillis);

        // Format the Instant to a specific UTC date and time String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); // Customize format as needed
        String utcDateTime = formatter.format(instant.atZone(ZoneId.of("UTC")));

        logger.debug("milliseconds {}  converted to UTC date time milliseconds {} ",  milliseconds, utcDateTime);
        return utcDateTime;

    }
}

package com.example.iottrackingsystem.common.util;

import com.example.iottrackingsystem.common.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;
@Component
public class DateUtil {
    static Logger logger = LogManager.getLogger(DateUtil.class.getCanonicalName());
    public static String getCurrentDatetimeUTC(){
        // Get the current date and time in UTC
        ZonedDateTime utcDateTime = ZonedDateTime.now(ZoneId.of(Constant.DATETIME_ZONE_UTC));

        // Define a formatter for ISO 8601 date-time format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constant.DATE_FORMAT);

        // Format the date and time
        String formattedDateTime = utcDateTime.format(formatter);

        // Print the formatted date and time
        logger.debug("Current date and time in UTC: {} ", formattedDateTime);
        return formattedDateTime;
    }


    public static String ConvertMillisToUTC(String milliseconds ){
        // Parse the String to a long
        long epochMillis = Long.parseLong(milliseconds);

        // Convert to Instant object representing UTC time
        Instant instant = Instant.ofEpochMilli(epochMillis);

        // Convert Instant to ZonedDateTime in UTC
        ZonedDateTime utcDateTime = instant.atZone(ZoneId.of("UTC"));

        // Format the Instant to a specific UTC date and time String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constant.DATE_FORMAT); // Customize format as needed
        String formattedDateTime  = utcDateTime.format(formatter);

        logger.debug("milliseconds {}  converted to UTC date time milliseconds {} ",milliseconds, formattedDateTime);
        return formattedDateTime;

    }

    public static String compareDate(String dateTimeStr1,String dateTimeStr2){
        // Define the custom formatter matching the date-time strings
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern(Constant.DATE_FORMAT)
                .withZone(ZoneId.of(Constant.DATETIME_ZONE_UTC));

        // Parse the date-time strings into ZonedDateTime objects
        ZonedDateTime dateTime1 = ZonedDateTime.parse(dateTimeStr1, customFormatter);
        ZonedDateTime dateTime2 = ZonedDateTime.parse(dateTimeStr2, customFormatter);
        // Compare the date-time objects
        if (dateTime1.isBefore(dateTime2)) {
            logger.debug(dateTimeStr1 + " is before " + dateTimeStr2);
            return "BEFORE";
        } else if (dateTime1.isAfter(dateTime2)) {
            logger.debug(dateTimeStr1 + " is after " + dateTimeStr2);
            return "AFTER";
        } else {
            logger.debug(dateTimeStr1 + " is equal to " + dateTimeStr2);
            return "EQUAL";
        }

    }
}

package com.example.iottrackingsystem.util;

import com.example.iottrackingsystem.repository.ProductDetailsRepositoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class IotTrackingSystemConstants {

    static final Logger logger = LogManager.getLogger(IotTrackingSystemConstants.class.getCanonicalName());

    public static final String DATETIME_ZONE_UTC = "UTC";
    /**
     * Battery life constants
     */
    public static final String BATTERY_FULL = "Full";
    public static final String BATTERY_HIGH = "High";
    public static final String BATTERY_MEDIUM = "Medium";
    public static final String BATTERY_LOW = "Low";
    public static final String BATTERY_CRITICAL = "Critical";

    /**
     * Status flag
     */
    public static final String FLAG_ACTIVE = "Active";
    public static final String FLAG_INACTIVE = "Inactive";

}

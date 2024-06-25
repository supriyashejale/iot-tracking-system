package com.iottrackingsystem.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Constant {

    static final Logger logger = LogManager.getLogger(Constant.class.getCanonicalName());

    public static final String DATETIME_ZONE_UTC = "UTC";
    public static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
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

    public static final String FLAF_NA = "N/A";

}

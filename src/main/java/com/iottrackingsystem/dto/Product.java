package com.iottrackingsystem.dto;

import com.iottrackingsystem.common.Constant;
import lombok.Data;

@Data
public class Product {

    private String DateTime;
    private Long EventId;
    private String ProductId;
    private String Latitude;
    private String Longitude;
    private Double Battery;
    private String Light;
    private String AirplaneMode;

    /**
     * - Differentiate between products: The two supported products are:
     * 	CyclePlusTracker (product ID starts with 'WG') is used on bicycles.
     * 	GeneralTracker (product ID starts with '69').
     */
    public String getName() {
        if (ProductId.startsWith("WG")) {
            return "CyclePlusTracker";
        } else if (ProductId.startsWith("69")) {
            return "GeneralTracker";
        } else {
            return "Unknown Device";
        }
    }

    /**
     * 	Report battery life: Report battery life as:
     * 	'Full' if it is ≥98%.
     * 	'High' if it is ≥60%.
     * 	'Medium' if it is ≥40%.
     * 	'Low' if it is ≥10%.
     * 	'Critical' if it is <10%.
     */
    public String getBattery() {
        if (Battery >= 98)
            return Constant.BATTERY_FULL;
        else if (Battery >= 60 && Battery < 98)
            return Constant.BATTERY_HIGH;
        else if (Battery >= 40 && Battery < 60)
            return Constant.BATTERY_MEDIUM;
        else if (Battery >= 10 && Battery < 40)
            return Constant.BATTERY_LOW;
        else if (Battery < 10)
            return Constant.BATTERY_CRITICAL;
        return null;
    }
}

package com.example.iottrackingsystem.dto;

import lombok.Data;

/**
 * Response DTO for device details and location
 */
@Data
public class DeviceReportResponseDTO {

    private String id;
    private String name;
    private String datetime;
    private String longi;
    private String lat;
    private String status;
    private String battery;
    private String description;

}

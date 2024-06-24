package com.example.iottrackingsystem.dto;

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
}

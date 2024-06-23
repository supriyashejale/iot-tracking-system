package com.example.iottrackingsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorMessageDTO {

    @JsonProperty("description")
    private String description;

    public ErrorMessageDTO(String description) {
        super();
        this.description = description;
    }
}

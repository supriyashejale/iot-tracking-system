package com.example.iottrackingsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FileDetailsRequestDTO {

    @JsonProperty("filepath")
    private String filepath;

}

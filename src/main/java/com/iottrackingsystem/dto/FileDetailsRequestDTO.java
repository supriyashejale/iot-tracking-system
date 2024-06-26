package com.iottrackingsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FileDetailsRequestDTO {

    @JsonProperty("filepath")
    private String filepath;

}

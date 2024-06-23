package com.example.iottrackingsystem.exception;

import com.example.iottrackingsystem.dto.ErrorMessageDTO;

@FunctionalInterface
public interface CustomException {
    public ErrorMessageDTO getErrorMessageDTO();
}

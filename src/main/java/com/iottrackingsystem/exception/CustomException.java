package com.iottrackingsystem.exception;

import com.iottrackingsystem.dto.ErrorMessageDTO;

@FunctionalInterface
public interface CustomException {
    public ErrorMessageDTO getErrorMessageDTO();
}

package com.example.iottrackingsystem.service;

import com.example.iottrackingsystem.dto.DeviceReportResponseDTO;
import com.example.iottrackingsystem.dto.FileDetailsResponseDTO;
import com.example.iottrackingsystem.exception.TechnicalIssueException;

import java.io.IOException;

/**
 * @author Supriya Shejale : 19/06/2024
 * @interface DataProcessingService
 */
public interface DataProcessingService {
    FileDetailsResponseDTO iotBatchDataLoading(String fileDetails) throws IOException, TechnicalIssueException;

    DeviceReportResponseDTO getDeviceAndLocationDetails(String productId, String tstmp);
}

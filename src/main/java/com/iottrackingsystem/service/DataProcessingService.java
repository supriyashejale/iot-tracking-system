package com.iottrackingsystem.service;

import com.iottrackingsystem.dto.DeviceReportResponseDTO;
import com.iottrackingsystem.dto.FileDetailsResponseDTO;
import com.iottrackingsystem.exception.TechnicalIssueException;

import java.io.IOException;

/**
 * @author Supriya Shejale : 19/06/2024
 * @interface DataProcessingService
 */
public interface DataProcessingService {
    FileDetailsResponseDTO iotBatchDataLoading(String fileDetails) throws IOException, TechnicalIssueException;

    DeviceReportResponseDTO getDeviceAndLocationDetails(String productId, String tstmp);
}

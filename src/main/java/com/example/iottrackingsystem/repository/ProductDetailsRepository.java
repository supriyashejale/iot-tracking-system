package com.example.iottrackingsystem.repository;

import com.example.iottrackingsystem.dto.DeviceReportResponseDTO;
import com.example.iottrackingsystem.dto.FileDetailsResponseDTO;
import com.example.iottrackingsystem.exception.TechnicalIssueException;

import java.io.IOException;

/**
 * @interface ProductDetailsRepository
 * @author Supriya Shejale : 19/06/2024
 */
public interface ProductDetailsRepository {

    FileDetailsResponseDTO iotBatchDataLoading(String fileDetails) throws IOException, TechnicalIssueException;
    DeviceReportResponseDTO getDeviceAndLocationDetails(String productId, String tstmp);
}

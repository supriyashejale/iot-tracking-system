package com.iottrackingsystem.repository;

import com.iottrackingsystem.dto.DeviceReportResponseDTO;
import com.iottrackingsystem.dto.FileDetailsResponseDTO;
import com.iottrackingsystem.exception.TechnicalIssueException;

import java.io.IOException;

/**
 * @interface ProductDetailsRepository
 * @author Supriya Shejale : 19/06/2024
 */
public interface ProductDetailsRepository {

    FileDetailsResponseDTO iotBatchDataLoading(String fileDetails) throws IOException, TechnicalIssueException;
    DeviceReportResponseDTO getDeviceAndLocationDetails(String productId, String tstmp);
}

package com.iottrackingsystem.service;

import com.iottrackingsystem.dto.DeviceReportResponseDTO;
import com.iottrackingsystem.dto.FileDetailsResponseDTO;
import com.iottrackingsystem.exception.DataNotFoundException;
import com.iottrackingsystem.repository.ProductDetailsRepository;
import com.iottrackingsystem.common.util.DateUtil;
import io.micrometer.common.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

/**
 * @author Supriya Shejale : 19/06/2024
 * @Class DataProcessingServiceImpl
 */
@Service
public class DataProcessingServiceImpl implements DataProcessingService {

    static Logger logger = LogManager.getLogger(DataProcessingServiceImpl.class.getCanonicalName());
    @Autowired
    ProductDetailsRepository productDetailsRepository;

    /**
     * @param fileDetails
     * @return FileDetailsResponseDTO
     * @throws IOException
     */
    @Override
    public FileDetailsResponseDTO iotBatchDataLoading(String fileDetails) throws IOException {

        return productDetailsRepository.iotBatchDataLoading(fileDetails);
    }

    /**
     * @param productId
     * @param tstmp
     * @return DeviceReportResponseDTO
     */
    @Override
    public DeviceReportResponseDTO getDeviceAndLocationDetails(String productId, String tstmp) {

        // If no timestamp parameter is provided, default to the current UTC time
        String timestamp = tstmp;
        if (StringUtils.isEmpty(timestamp)) {
            timestamp = DateUtil.getCurrentDatetimeUTC();
        } else {
            timestamp = DateUtil.ConvertMillisToUTC(tstmp);
        }
        logger.info("Searching records for ProductId : " + productId + "  Timestamp : " + timestamp);

        //	If the user provides an Id that is not present in the csv file then a 404 should result:(ID not found)
        return Optional.ofNullable(productDetailsRepository.getDeviceAndLocationDetails(productId, timestamp))
                .orElseThrow(() -> new DataNotFoundException("ERROR: Id " + productId + "  not found"));
    }
}

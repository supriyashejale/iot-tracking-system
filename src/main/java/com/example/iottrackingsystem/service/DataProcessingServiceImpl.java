package com.example.iottrackingsystem.service;

import com.example.iottrackingsystem.dto.DeviceReportResponseDTO;
import com.example.iottrackingsystem.dto.FileDetailsResponseDTO;
import com.example.iottrackingsystem.exception.DataNotFoundException;
import com.example.iottrackingsystem.repository.ProductDetailsRepository;
import com.example.iottrackingsystem.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

/**
 * @interface DataProcessingServiceImpl
 * @author Supriya Shejale : 19/06/2024
 */
@Service
public class DataProcessingServiceImpl implements DataProcessingService {

    static Logger logger = LogManager.getLogger(DataProcessingServiceImpl.class.getCanonicalName());
    @Autowired
    ProductDetailsRepository productDetailsRepository;

    /**
     *
     * @param fileDetails
     * @return FileDetailsResponseDTO
     * @throws IOException
     */
    @Override
    public FileDetailsResponseDTO iotBatchDataLoading(String fileDetails) throws IOException {

        return productDetailsRepository.iotBatchDataLoading(fileDetails);
    }

    /**
     *
     * @param productId
     * @param tstmp
     * @return DeviceReportResponseDTO
     */
    @Override
    public DeviceReportResponseDTO getDeviceAndLocationDetails(String productId, Long tstmp) {


        // If no timestamp parameter is provided, default to the current UTC time
        Long timestamp = Optional.ofNullable(tstmp).orElse(Utils.getCurrentDatetimeUTCFormatToMilliseconds());
        logger.info("Sarching records for ProductId : "+ productId +"  Timestamp : "+Utils.convertToUTCDatetime(timestamp.toString()));

//        DeviceReportResponseDTO details = productDetailsRepository.getDeviceAndLocationDetails(productId, timestamp);
        //	If the user provides an Id that is not present in the csv file then a 404 should result:(ID not found)

//        if (details == null) {
//            throw new DataNotFoundException("ERROR: Id " + productId + "  not found");
//        }
        return  Optional.ofNullable(productDetailsRepository.getDeviceAndLocationDetails(productId, timestamp))
                .orElseThrow(() -> new DataNotFoundException("ERROR: Id " + productId + "  not found"));
    }
}

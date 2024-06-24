package com.example.iottrackingsystem.controller;

import com.example.iottrackingsystem.dto.DeviceReportResponseDTO;
import com.example.iottrackingsystem.dto.FileDetailsRequestDTO;
import com.example.iottrackingsystem.dto.FileDetailsResponseDTO;
import com.example.iottrackingsystem.exception.InvalidInputException;
import com.example.iottrackingsystem.service.DataProcessingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * <h1> Class: DataProcessingController
 * <p>
 * The DataProcessingController class controller to Control the complete process of :
 * 1. batch data loading
 * 2.
 * 3.
 *
 * @author Supriya Shejale : 19/06/2024
 * @version 1.1.0
 */

@RestController
@CrossOrigin(origins = "http://localhost:8081", methods = {RequestMethod.GET, RequestMethod.POST})
public class DataProcessingController {

    static final Logger logger = LogManager.getLogger(DataProcessingController.class.getCanonicalName());

    @Autowired
    DataProcessingService dataProcessingService;

    /**
     *
     * @param productId
     * @param tstmp
     * @return DeviceReportResponseDTO
     */
    @GetMapping("/iot/event/v1")
    public ResponseEntity<DeviceReportResponseDTO> getDeviceAndLocationDetails(@RequestParam(value = "productId") String productId,
                                                                               @RequestParam(value = "tstmp", required = false) String tstmp) {
        logger.info("Received request to get details by timestamp: {} and device id: {}", tstmp, productId);
        //if productId parameter is null throw exception InvalidInputException
        String id = Optional.of(productId)
                .filter(Predicate.not((String::isBlank)))
                .orElseThrow(()->new InvalidInputException("Error: Invalid Input parameter productId"));

        DeviceReportResponseDTO response = dataProcessingService.getDeviceAndLocationDetails(id, tstmp);
        logger.info("Successfully loaded details by location and device id: {} : {}","200", productId);
        return ResponseEntity.ok().body(response);
    }


    /**
     *
     * @param fileDetails
     * @return
     * @throws ParseException
     * @throws IOException
     */
    @PostMapping("/iot/event/v1")
    public ResponseEntity<FileDetailsResponseDTO> iotBatchDataLoading(@RequestBody FileDetailsRequestDTO fileDetails) throws IOException {

        Optional.of(fileDetails.getFilepath())
                .filter(Predicate.not((String::isBlank)))
                .orElseThrow(() -> new InvalidInputException("ERROR: no data file found"));
        logger.info("Received request to data loading iotTrackingSystem: {}", fileDetails.getFilepath());
        FileDetailsResponseDTO response = dataProcessingService.iotBatchDataLoading(fileDetails.getFilepath());

        logger.info("Successfully loaded data the iotTrackingSystem. {} : {}", "200", response.getDescription());
        return ResponseEntity.ok().body(response);

    }


}

package com.iottrackingsystem.repository;

import com.iottrackingsystem.dto.DeviceData;
import com.iottrackingsystem.dto.DeviceReportResponseDTO;
import com.iottrackingsystem.dto.Product;
import com.iottrackingsystem.dto.FileDetailsResponseDTO;
import com.iottrackingsystem.exception.DeviceNotFoundException;
import com.iottrackingsystem.exception.TechnicalIssueException;
import com.iottrackingsystem.common.Constant;
import com.iottrackingsystem.common.util.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Supriya Shejale : 19/06/2024
 * @Class ProductDetailsRepositoryImpl
 */
@Repository
public class ProductDetailsRepositoryImpl implements ProductDetailsRepository {

    static final Logger logger = LogManager.getLogger(ProductDetailsRepositoryImpl.class.getCanonicalName());

    @Autowired
    InMemoryDataService dataService;

    @Override
    public FileDetailsResponseDTO iotBatchDataLoading(String fileDetails) throws IOException, TechnicalIssueException {
        List<List<String>> records;
        List<Product> productList;
        //the data completely replace the existing data with the contents every time
        if (!IDataService.checkMemoryHasDetails(dataService.getDataMap())) {
            logger.info("In Memory is cleared before insert new details. ");
            IDataService.cleanMemory(dataService.getDataMap());
        }
        logger.info("Started loading data.....");
        /**
         * newBufferedReader() is the best way to go when working with large files
         *
         */
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileDetails))) {
            records = reader.lines()
                    .map(line -> Arrays.asList(line.split(",")))
                    .collect(Collectors.toList());

            productList = new ArrayList<>();
            for (int i = 1; i < records.size(); i++) {
                Product product = new Product();
                product.setDateTime(DateUtil.ConvertMillisToUTC(records.get(i).get(0)));
                product.setEventId(Long.valueOf(records.get(i).get(1)));
                product.setProductId(records.get(i).get(2));
                product.setLatitude(records.get(i).get(3));
                if (null != records.get(i).get(4))
                    product.setLongitude(records.get(i).get(4));
                if (null != records.get(i).get(5))
                    product.setBattery(Double.valueOf(records.get(i).get(5)));
                product.setLight(records.get(i).get(6));
                product.setAirplaneMode(records.get(i).get(7));

                String key = product.getEventId().toString();
                dataService.saveProject(key, product);
                productList.add(product);
            }

        } catch (IOException e) {
            logger.info("Issue while reading file data : " + e.getMessage());
            throw new TechnicalIssueException("ERROR: A technical exception occurred");
        }

        // Group by product ID and filter top 3 records
        FileDetailsResponseDTO responseFileDetailsDTO = new FileDetailsResponseDTO();
        if (productList.size() > 0) {
            Map<String, List<Product>> filteredProductMap = productList.stream()
                            .sorted(Comparator.comparing(Product::getProductId)
                                    .thenComparing(Product::getDateTime).reversed())
                            .collect(Collectors.groupingBy(Product::getProductId,
                                    Collectors.collectingAndThen(Collectors.toList(),
                                            list -> list.stream().limit(3)
                                                    .collect(Collectors.toList()))));

            logger.info("Filtered product map:");
            filteredProductMap.forEach((productId, products) -> {
                logger.info("Product ID: " + productId);
                products.forEach(System.out::println);
            });
            dataService.setDeviceDataMap(DeviceData.convertProductMapToDeviceDataMap(filteredProductMap));
            logger.info("Finished loading data.....");
            responseFileDetailsDTO.setDescription("data refreshed");
        }

        return responseFileDetailsDTO;
    }

    @Override
    public DeviceReportResponseDTO getDeviceAndLocationDetails(String productId, String tstmp) {

        DeviceReportResponseDTO responseDTO = new DeviceReportResponseDTO();
        Product product = dataService.getProductDetails(productId, tstmp);

        logger.info("Product details received from in memory : " + product);
        responseDTO.setId(product.getProductId());
        responseDTO.setDatetime(product.getDateTime());
        responseDTO.setBattery(product.getBattery());
        responseDTO.setName(product.getName());

        /**
         * -Set status flag:
         * 	The status flag should be 'Active' when latitude and longitude data are present.
         * 	When no GPS data is available, such as when airplane mode is on, the status should be 'Inactive.'
         *
         *
         * -Handle airplane mode:
         * 	If airplane mode is on, the GPS data will not be available. The response should still be 200 OK and include the description "SUCCESS: Location not available: Please turn off airplane mode." Provide other details about the product, with latitude and longitude left blank.
         * 	If airplane mode is off, provide the GPS data and a 200 OK response with a success message.
         */

        // Update status based on activity tracking logic
        DeviceData data = dataService.getDeviceDataMap().get(product.getProductId());
        if(product.getProductId().equals(data.getProductId())){
            if (data.getProductId().startsWith("WG")) {
                // Check for CyclePlusTracker
                if (!data.hasIdenticalLastCoordinates() ){
                    responseDTO.setStatus(Constant.FLAG_NA);
                } else if ( product.getAirplaneMode().toUpperCase().equals("ON")) {
                    responseDTO.setDescription("SUCCESS: Location not available: Please turn off airplane mode.");
                    responseDTO.setStatus(Constant.FLAG_INACTIVE);
                } else {
                    //	If the AirplaneMode is off and there is no GPS data available in the csv file the status should be 400:BAD_REQUEST
                    Optional.of(product.getLongitude())
                            .filter(Predicate.not((String::isBlank)))
                            .orElseThrow(() -> new DeviceNotFoundException("ERROR: Device could not be located)"));
                    Optional.of(product.getLatitude())
                            .filter(Predicate.not((String::isBlank)))
                            .orElseThrow(() -> new DeviceNotFoundException("ERROR: Device could not be located)"));

                    responseDTO.setLongi(product.getLongitude());
                    responseDTO.setLat(product.getLatitude());
                    responseDTO.setDescription("SUCCESS: Location identified.");
                    responseDTO.setStatus(Constant.FLAG_ACTIVE);
                }

//                data.updateLastReadings(data.getLatitude(), data.getLongitude());  // Update last readings
            } else {
                // Existing logic for GeneralTracker
                if (product.getAirplaneMode().toUpperCase().equals("OFF")) {
                    //	If the AirplaneMode is off and there is no GPS data available in the csv file the status should be 400:BAD_REQUEST
                    Optional.of(product.getLongitude())
                            .filter(Predicate.not((String::isBlank)))
                            .orElseThrow(() -> new DeviceNotFoundException("ERROR: Device could not be located)"));
                    Optional.of(product.getLatitude())
                            .filter(Predicate.not((String::isBlank)))
                            .orElseThrow(() -> new DeviceNotFoundException("ERROR: Device could not be located)"));

                    responseDTO.setLongi(product.getLongitude());
                    responseDTO.setLat(product.getLatitude());
                    responseDTO.setDescription("SUCCESS: Location identified.");
                    responseDTO.setStatus(Constant.FLAG_ACTIVE);

                } else {
                    responseDTO.setDescription("SUCCESS: Location not available: Please turn off airplane mode.");
                    responseDTO.setStatus(Constant.FLAG_INACTIVE);
                }

            }

        }
        return responseDTO;
    }

}

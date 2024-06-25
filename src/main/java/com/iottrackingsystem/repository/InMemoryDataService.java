package com.iottrackingsystem.repository;

import com.iottrackingsystem.dto.DeviceData;
import com.iottrackingsystem.dto.Product;
import com.iottrackingsystem.exception.DataNotFoundException;
import com.iottrackingsystem.common.util.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Supriya Shejale : 19/06/2024
 * @Class InMemoryDataService
 * @Description This class provides an in-memory data store using a HashMap.
 * It offers methods to save data (saveData) and retrieve data by ID (getDataById).
 */
@Service
public class InMemoryDataService implements IDataService {

    static final Logger logger = LogManager.getLogger(InMemoryDataService.class.getCanonicalName());
    private Map<String, Product> dataMap = new HashMap<>();
    private Map<String, DeviceData> deviceDataMap = new ConcurrentHashMap<>();

    public Product getProductDetails(String productId, String tstmp) {

        Product response = null;
//        dataMap.entrySet().stream().forEach(e-> System.out.println("Recorde available in memory : "+e.getKey() +" "+e.getValue()));

        logger.info("Fetching details started from in memory.....");
//        Product exactMatchedDetails = dataMap.get(searchByKey);

        Optional<Product> exactMatchedDetails = dataMap.entrySet().stream()
                .map(Map.Entry::getValue) // Get only the values (Product)
                .filter((value -> value.getProductId().equals(productId) &&
                        DateUtil.compareDate(value.getDateTime(), tstmp).equals("EQUAL")))
                .findFirst();

        logger.debug("Sorted data" + exactMatchedDetails);

        //Check no data available for specified data time
        Product closeToDateProduce = exactMatchedDetails.orElseGet(() -> {
                    logger.info("Provided timestamp {} is not an exact match ", tstmp);
                    logger.info("Start getting the data closest to it in the past for the given productId {}", productId);
                    logger.info("Started retrieve closest record.....");

                    Optional<Product> closestDetails = dataMap.entrySet().stream()
                            .sorted(Comparator.comparing(entry -> entry.getValue(),
                                    Comparator.comparing(Product::getProductId)
                                            .thenComparing(Product::getDateTime).reversed()))
                            .map(Map.Entry::getValue) // Get only the values (Product)
                            .filter(value -> value.getProductId().equals(productId) &&
                                    DateUtil.compareDate(value.getDateTime(), tstmp).equals("BEFORE"))
                             // Filter by online status
                            .findFirst(); //Get the first element

                   if(closestDetails.isPresent()){
                       logger.info("Closest product details is : {}", closestDetails.get());
                       logger.info("Finished retrieve closest record.....");
                       return closestDetails.get();
                   }else {
                       throw new DataNotFoundException("ERROR: Id " + productId + "  not found");
                   }
                }
        );
        response = exactMatchedDetails.orElse(closeToDateProduce);
        logger.info("Fetching details finished in-memory.....");
        return response;
    }


    public void saveProject(String id, Product data) {
        logger.info("Save details {} = {} ", id, data);
        dataMap.put(id, data);

    }

    public Map<String, DeviceData> getDeviceDataMap() {
        return deviceDataMap;
    }

    public void setDeviceDataMap(Map<String, DeviceData> deviceDataMap) {
        this.deviceDataMap = deviceDataMap;
    }

    public Map<String, Product> getDataMap() {
        return dataMap;
    }

}

package com.example.iottrackingsystem.repository;

import com.example.iottrackingsystem.dto.Product;
import com.example.iottrackingsystem.exception.DataNotFoundException;
import com.example.iottrackingsystem.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @interface ProductDetailsRepository
 * @author Supriya Shejale : 19/06/2024
 * @Description This class provides an in-memory data store using a HashMap.
 * It offers methods to save data (saveData) and retrieve data by ID (getDataById).
 */
@Service
public class InMemoryDataService implements IDataService{

    static final Logger logger = LogManager.getLogger(InMemoryDataService.class.getCanonicalName());
    public Map<String, Product> dataMap = new HashMap<>();

    public Product getProductDetails(String productId, String tstmp) {
        String searchByKey = tstmp+productId;
        Product response = null;
//        dataMap.entrySet().stream().forEach(e-> System.out.println("Recorde available in memory : "+e.getKey() +" "+e.getValue()));

        logger.info("Fetching details started from in memory.....");
        Product exactMatchedDetails = dataMap.get(searchByKey);

        logger.info("Provided timestamp {} is not an exact match ", Utils.convertToUTCDatetime(tstmp));
        logger.info("Start getting the data closest to it in the past for the given productId {}", productId);
        //Check no data available for specified data time
        exactMatchedDetails = Optional.ofNullable(exactMatchedDetails).orElseGet(() -> {
            logger.info("Started retrieve closest record.....");

                        Optional<Product> closestDetails = dataMap.entrySet().stream()
                                .sorted(Comparator.comparing(entry -> entry.getValue(),
                                        Comparator.comparing(Product::getProductId).reversed()
                                                .thenComparing(Product::getDateTime)))
                                .map(Map.Entry::getValue) // Get only the values (Product)
                                .filter(value  -> value.getProductId().equals(productId)) // Filter by online status
                                .findFirst(); //Get the first element

                               Map<String, Product> sortedMap = dataMap.entrySet().stream()
                                .sorted(Comparator.comparing(entry -> entry.getValue(),
                                        Comparator.comparing(Product::getProductId).reversed()
                                                .thenComparing(Product::getDateTime)))
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            sortedMap.forEach((key, value) -> System.out.println(key + ": " + value.getProductId() +""+Utils.convertToUTCDatetime(value.getDateTime().toString())));


            logger.info("Closest product details is : {}", closestDetails.get() );
                        logger.info("Finished retrieve closest record.....");
                return closestDetails.get();
                }
        );
        response = Optional.ofNullable(exactMatchedDetails).orElseThrow(() -> new DataNotFoundException("ERROR: Id " + productId + "  not found"));
        logger.info("Fetching details end from in memory.....");
        return response;
    }


    public void saveProject(String id, Product data) {
        dataMap.put(id, data);
    }

}

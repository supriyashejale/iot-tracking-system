package com.example.iottrackingsystem.repository;

import com.example.iottrackingsystem.dto.DeviceReportResponseDTO;
import com.example.iottrackingsystem.dto.Product;
import com.example.iottrackingsystem.dto.FileDetailsResponseDTO;
import com.example.iottrackingsystem.exception.DeviceNotFoundException;
import com.example.iottrackingsystem.exception.TechnicalIssueException;
import com.example.iottrackingsystem.common.Constant;
import com.example.iottrackingsystem.common.util.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.StrRegEx;
import org.supercsv.cellprocessor.ift.CellProcessor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Supriya Shejale : 19/06/2024
 * @interface ProductDetailsRepositoryImpl
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
        if (!IDataService.checkMemoryHasDetails(dataService.dataMap)) {
            logger.info("In Memory is cleared before insert new details. ");
            IDataService.cleanMemory(dataService.dataMap);
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

//            productListModel = new ProductListModel();
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

//                String key = product.getDateTime()+product.getProductId();
                String key = product.getEventId().toString();
                dataService.saveProject(key, product);
                productList.add(product);
            }

        } catch (IOException e) {
            logger.info("Issue while reading file data : " + e.getMessage());
            throw new TechnicalIssueException("ERROR: A technical exception occurred");
        }

//            records.stream().flatMap(List::stream).forEach(System.out::println);
//            records .stream().forEach((x) -> x.stream().forEach(System.out::println));
        FileDetailsResponseDTO responseFileDetailsDTO = new FileDetailsResponseDTO();
        if (productList.size() > 0) {
            logger.info("Finished loading data.....");
            responseFileDetailsDTO.setDescription("data refreshed");
        }

       /* InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileDetails);
        BufferedReader csvReader = new BufferedReader(new InputStreamReader(inputStream));
        String row;
        ProductListModel productListModel = new ProductListModel();
        List<Product> productsModules = new ArrayList<>();
        try {
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                Product product = new Product();
                if (data.length > 0)
                    product.setDateTime(Long.valueOf(data[0]));
                if (data.length > 1)
                    product.setEventId(Integer.valueOf(data[1]));
                if (data.length > 2)
                    product.setProductId(data[2]);
                if (data.length > 3)
                    product.setLatitude(Double.valueOf(data[3]));
                if (data.length > 4)
                    product.setLongitude(Double.valueOf(data[4]));
                if (data.length > 5) {
                    product.setBattery(Double.valueOf(data[5]));
                }
                if (data.length > 6) {
                    product.setLight(data[6]);
                }
                if (data.length > 7) {
                    product.setAirplaneMode(data[6]);
                }
                productsModules.add(product);
                System.out.println(productsModules);
            }
            csvReader.close();
        } catch (IOException e) {

            //logger.error("error :" + e.getMessage());
            return null;
        } finally {
            if (csvReader != null) {

                try {
                    csvReader.close();
                } catch (IOException e) {
                    //logger.error("error :" + e.getMessage());
                }
            }

        }
        productListModel.setProducts(productsModules);*/



       /* try(ICsvBeanReader beanReader
                    = new CsvBeanReader(new FileReader(fileDetails), CsvPreference.STANDARD_PREFERENCE))
        {
            // the header elements are used to map the values to the bean
            final String[] headers = beanReader.getHeader(true);
            //final String[] headers = new String[]{"CustomerId","CustomerName","Country","PinCode","Email"};
            final CellProcessor[] processors = getProcessors();
            Product product;
            while ((product = beanReader.read(Product.class, headers, processors)) != null) {
                System.out.println(product);

            }
        }*/
        return responseFileDetailsDTO;
    }

    @Override
    public DeviceReportResponseDTO getDeviceAndLocationDetails(String productId, String tstmp) {

        DeviceReportResponseDTO responseDTO = new DeviceReportResponseDTO();
        Product product = dataService.getProductDetails(productId, tstmp);

        logger.info("Product details received from in memory : " + product);
        responseDTO.setId(product.getProductId());

        /**
         * - Differentiate between products: The two supported products are:
         * 	CyclePlusTracker (product ID starts with 'WG') is used on bicycles.
         * 	GeneralTracker (product ID starts with '69').
         */
        if (product.getProductId().startsWith("WG"))
            responseDTO.setName("CyclePlusTracker");
        else if (product.getProductId().startsWith("69")) {
            responseDTO.setName("GeneralTracker");
        }
        responseDTO.setDatetime(product.getDateTime());

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

        /**
         * 	Report battery life: Report battery life as:
         * 	'Full' if it is ≥98%.
         * 	'High' if it is ≥60%.
         * 	'Medium' if it is ≥40%.
         * 	'Low' if it is ≥10%.
         * 	'Critical' if it is <10%.
         */
        Double batteryLife = product.getBattery();
        if (batteryLife >= 98)
            responseDTO.setBattery(Constant.BATTERY_FULL);
        else if (batteryLife >= 60 && batteryLife < 98)
            responseDTO.setBattery(Constant.BATTERY_HIGH);
        else if (batteryLife >= 40 && batteryLife < 60)
            responseDTO.setBattery(Constant.BATTERY_MEDIUM);
        else if (batteryLife >= 10 && batteryLife < 40)
            responseDTO.setBattery(Constant.BATTERY_LOW);
        else if (batteryLife < 10)
            responseDTO.setBattery(Constant.BATTERY_CRITICAL);

        return responseDTO;
    }

    /**
     * Sets up the processors used for the examples.
     */
    private static CellProcessor[] getProcessors() {
        final String emailRegex = "[a-z0-9\\._]+@[a-z0-9\\.]+";
        StrRegEx.registerMessage(emailRegex, "must be a valid email address");

        final CellProcessor[] processors = new CellProcessor[]{
                new NotNull(), // dateTime
                new NotNull(), // eventId
                new NotNull(), // productId
                new NotNull(), // latitude
                new NotNull(), // Longitude
                new NotNull(), // battery
                new NotNull(), // light
                new NotNull(), // airplaneMode
        };
        return processors;
    }

}

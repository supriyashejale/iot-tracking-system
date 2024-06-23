package com.example.iottrackingsystem.repository;

import com.example.iottrackingsystem.dto.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@FunctionalInterface
public interface IDataService {

    Product getProductDetails(String productId, String tstmp);
    /**
     * Clean map before insert new records.
     */
    static void cleanMemory(Map<String, Product> dataMap){
        dataMap.clear();
    }
    static Boolean checkMemoryHasDetails(Map<String, Product> dataMap){
        return dataMap.isEmpty();
    }
}



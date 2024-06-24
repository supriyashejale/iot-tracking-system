package com.iottrackingsystem.repository;

import com.iottrackingsystem.dto.Product;

import java.util.Map;

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



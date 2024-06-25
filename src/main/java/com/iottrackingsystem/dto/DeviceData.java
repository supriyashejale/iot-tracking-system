package com.iottrackingsystem.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class DeviceData {

    private String productId;
    private List<String> lastLatitudes;  // Stores last few latitude readings
    private List<String> lastLongitudes; // Stores last few longitude readings


    public DeviceData(String productId, List<String> lastLatitudes, List<String> lastLongitudes) {
        this.productId = productId;
        this.lastLatitudes = lastLatitudes;
        this.lastLongitudes = lastLongitudes;
    }

    @Override
    public String toString() {
        return "DeviceData{" +
                "productId='" + productId + '\'' +
                ", lastLatitudes=" + lastLatitudes +
                ", lastLongitudes=" + lastLongitudes +
                '}';
    }

    public static Map<String, DeviceData> convertProductMapToDeviceDataMap(Map<String, List<Product>> productMap) {
        return productMap.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey(), // Convert Long key to String
                        entry -> createDeviceData(entry.getKey(), entry.getValue())));
    }

    private static DeviceData createDeviceData(String productId, List<Product> products) {
        List<String> latitudes = products.stream().map(Product::getLatitude).collect(Collectors.toList());
        List<String> longitudes = products.stream().map(Product::getLongitude).collect(Collectors.toList());
        return new DeviceData(String.valueOf(productId), latitudes, longitudes);
    }

    public boolean hasIdenticalLastCoordinates() {
        if (lastLatitudes.size() < 3) {
            return false; // Not enough data for comparison
        }
        return lastLatitudes.stream().allMatch(lat -> lat == lastLatitudes.get(0))
                && lastLongitudes.stream().allMatch(lon -> lon == lastLongitudes.get(0));
    }
}

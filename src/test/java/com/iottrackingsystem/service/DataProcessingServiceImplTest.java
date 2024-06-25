package com.iottrackingsystem.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import com.iottrackingsystem.common.util.DateUtil;
import com.iottrackingsystem.dto.DeviceReportResponseDTO;
import com.iottrackingsystem.dto.FileDetailsResponseDTO;
import com.iottrackingsystem.repository.ProductDetailsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class DataProcessingServiceImplTest {

    @InjectMocks
    private DataProcessingServiceImpl dataProcessingService;

    @Mock
    private ProductDetailsRepository productDetailsRepository;

    @Test
    public void testIotBatchDataLoading_Success() throws IOException {
        String fileDetails = "path/to/file.txt";
        FileDetailsResponseDTO expectedResponse = new FileDetailsResponseDTO();

        // Mock behavior of productDetailsRepository
        Mockito.when(productDetailsRepository.iotBatchDataLoading(fileDetails)).thenReturn(expectedResponse);

        // Call the service method
        FileDetailsResponseDTO response = dataProcessingService.iotBatchDataLoading(fileDetails);

        // Verify results
        Assertions.assertEquals(expectedResponse, response);
    }

    @Test
    public void testGetDeviceAndLocationDetails_Success() {
        String productId = "1234";
        String timestamp = "1582605137000";  // Example UTC timestamp
        DeviceReportResponseDTO expectedResponse = new DeviceReportResponseDTO();

        // Mock behavior of productDetailsRepository
        Mockito.when(productDetailsRepository.getDeviceAndLocationDetails(productId, timestamp)).thenReturn(expectedResponse);

        // Call the service method
        DeviceReportResponseDTO response = dataProcessingService.getDeviceAndLocationDetails(productId, timestamp);

        // Verify results
        Assertions.assertEquals(expectedResponse, response);
    }

    @Test
    public void testGetDeviceAndLocationDetails_EmptyTimestamp_UsesCurrentUTC() {
        String productId = "1234";
        DeviceReportResponseDTO expectedResponse = new DeviceReportResponseDTO();

        // Mock behavior of productDetailsRepository (assuming it uses Mockito inline)
        Mockito.when(productDetailsRepository.getDeviceAndLocationDetails(any(), eq(DateUtil.getCurrentDatetimeUTC()))).thenReturn(expectedResponse);

        // Call the service method with empty timestamp
        DeviceReportResponseDTO response = dataProcessingService.getDeviceAndLocationDetails(productId, null);

        // Verify results
        Assertions.assertEquals(expectedResponse, response);
    }

//    @Test(expected = DataNotFoundException.class)
//    public void testGetDeviceAndLocationDetails_NotFound() {
//        String productId = "9999";
//        String timestamp = "2024-06-25T00:00:00.000Z";
//
//        // Mock behavior of productDetailsRepository to return null
//        Mockito.when(productDetailsRepository.getDeviceAndLocationDetails(productId, timestamp)).thenReturn(null);
//
//        // Call the service method (expecting DataNotFoundException)
//        dataProcessingService.getDeviceAndLocationDetails(productId, timestamp);
//    }
}
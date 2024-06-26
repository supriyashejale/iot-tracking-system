package com.iottrackingsystem.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import com.iottrackingsystem.common.util.DateUtil;
import com.iottrackingsystem.dto.DeviceReportResponseDTO;
import com.iottrackingsystem.dto.FileDetailsResponseDTO;
import com.iottrackingsystem.exception.DataNotFoundException;
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
        // Arrange
        String fileDetails = "path/to/file.csv";
        FileDetailsResponseDTO expectedResponse = new FileDetailsResponseDTO();
        Mockito.when(productDetailsRepository.iotBatchDataLoading(fileDetails)).thenReturn(expectedResponse);

        // Act
        FileDetailsResponseDTO response = dataProcessingService.iotBatchDataLoading(fileDetails);

        // Assert
        Assertions.assertEquals(expectedResponse, response);
    }

    @Test
    public void testIotBatchDataLoading_IOException_ThrowsException() throws IOException {
        // Arrange
        String fileDetails = "path/to/file.csv";
        Mockito.when(productDetailsRepository.iotBatchDataLoading(fileDetails)).thenThrow(new IOException("File processing error"));

        // Act & Assert (expected exception)
        Assertions.assertThrows(IOException.class, () -> dataProcessingService.iotBatchDataLoading(fileDetails));
    }

    @Test
    public void testGetDeviceAndLocationDetails_EmptyTimestamp_UsesCurrentUTC() {
        // Arrange
        String productId = "1234";
        DeviceReportResponseDTO expectedResponse = new DeviceReportResponseDTO();
        Mockito.when(productDetailsRepository.getDeviceAndLocationDetails(any(), eq(DateUtil.getCurrentDatetimeUTC()))).thenReturn(expectedResponse);

        // Act
        DeviceReportResponseDTO response = dataProcessingService.getDeviceAndLocationDetails(productId, null);

        // Assert
        Assertions.assertEquals(expectedResponse, response);
    }

  }
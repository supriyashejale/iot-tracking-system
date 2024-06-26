package com.iottrackingsystem.controller;

import com.iottrackingsystem.dto.DeviceReportResponseDTO;
import com.iottrackingsystem.dto.FileDetailsRequestDTO;
import com.iottrackingsystem.dto.FileDetailsResponseDTO;
import com.iottrackingsystem.exception.InvalidInputException;
import com.iottrackingsystem.service.DataProcessingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@ExtendWith(MockitoExtension.class)
public class DataProcessingControllerUnitTest {

	@InjectMocks
    private DataProcessingController controller;

    @Mock
    private DataProcessingService dataProcessingService;

    @Test
    public void testGetDeviceAndLocationDetails_Success() throws Exception {
        // Arrange
        String productId = "1234";
        String timestamp = null; // Test without timestamp
        DeviceReportResponseDTO expectedResponse = new DeviceReportResponseDTO();
        Mockito.when(dataProcessingService.getDeviceAndLocationDetails(productId, timestamp)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<DeviceReportResponseDTO> response = controller.getDeviceAndLocationDetails(productId, timestamp);

        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions. assertEquals(expectedResponse, response.getBody());
    }
    @Test
    public void testIotBatchDataLoading_Success() throws Exception {
        // Arrange
        String filePath = "path/to/file.csv";
        FileDetailsRequestDTO request = new FileDetailsRequestDTO(filePath);
        FileDetailsResponseDTO expectedResponse = new FileDetailsResponseDTO();
        Mockito.when(dataProcessingService.iotBatchDataLoading(filePath)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<FileDetailsResponseDTO> response = controller.iotBatchDataLoading(request);

        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(expectedResponse, response.getBody());
    }

}

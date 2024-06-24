package com.example.iottrackingsystem.controller;

import static org.mockito.Mockito.when;
import com.example.iottrackingsystem.dto.DeviceReportResponseDTO;
import com.example.iottrackingsystem.dto.FileDetailsRequestDTO;
import com.example.iottrackingsystem.dto.FileDetailsResponseDTO;
import com.example.iottrackingsystem.exception.InvalidInputException;
import com.example.iottrackingsystem.service.DataProcessingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

@AutoConfigureMockMvc
@SpringBootTest
public class DataProcessingControllerUnitTest {

    @InjectMocks
    private DataProcessingController dataProcessingController;

    @Mock
    private DataProcessingService dataProcessingService;

    @BeforeAll
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetDeviceAndLocationDetails_Success() {
        String productId = "validProductId";
        DeviceReportResponseDTO expectedResponse = new DeviceReportResponseDTO();
        when(dataProcessingService.getDeviceAndLocationDetails(productId, null)).thenReturn(expectedResponse);

        ResponseEntity<DeviceReportResponseDTO> response = dataProcessingController.getDeviceAndLocationDetails(productId, null);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testGetDeviceAndLocationDetails_EmptyProductId() {
        Assertions.assertThrows(InvalidInputException.class, () -> dataProcessingController.getDeviceAndLocationDetails("", null));
    }

    @Test
    public void testIotBatchDataLoading_Success() throws IOException {
        String filePath = "valid/filepath";
        FileDetailsRequestDTO requestDTO = new FileDetailsRequestDTO();
        FileDetailsResponseDTO expectedResponse = new FileDetailsResponseDTO();
        when(dataProcessingService.iotBatchDataLoading(filePath)).thenReturn(expectedResponse);

        ResponseEntity<FileDetailsResponseDTO> response = dataProcessingController.iotBatchDataLoading(requestDTO);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testIotBatchDataLoading_EmptyFilePath() throws IOException {
        FileDetailsRequestDTO requestDTO = new FileDetailsRequestDTO();
        Assertions.assertThrows(InvalidInputException.class, () -> dataProcessingController.iotBatchDataLoading(requestDTO));
    }
}

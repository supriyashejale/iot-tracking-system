package com.example.iottrackingsystem.controller;

//import com.example.iottrackingsystem.util.Utils;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertThrows;
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//import com.example.iottrackingsystem.controller.DataProcessingController;
//import com.example.iottrackingsystem.dto.DeviceReportResponseDTO;
//import com.example.iottrackingsystem.dto.FileDetailsRequestDTO;
//import com.example.iottrackingsystem.dto.FileDetailsResponseDTO;
//import com.example.iottrackingsystem.exception.InvalidInputException;
//import com.example.iottrackingsystem.service.DataProcessingService;
//import com.example.iottrackingsystem.util.Utils;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.ResponseEntity;

public class DataProcessingControllerUnitTest {

    /*@InjectMocks
    private DataProcessingController dataProcessingController;

    @Mock
    private DataProcessingService dataProcessingService;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetDeviceAndLocationDetails_Success() {
        String productId = "validProductId";
        DeviceReportResponseDTO expectedResponse = new DeviceReportResponseDTO();
        when(dataProcessingService.getDeviceAndLocationDetails(productId, null)).thenReturn(expectedResponse);

        ResponseEntity<DeviceReportResponseDTO> response = dataProcessingController.getDeviceAndLocationDetails(productId, null);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testGetDeviceAndLocationDetails_EmptyProductId() {
        assertThrows(InvalidInputException.class, () -> dataProcessingController.getDeviceAndLocationDetails("", null));
    }

    @Test
    public void testIotBatchDataLoading_Success() throws IOException {
        String filePath = "valid/filepath";
        FileDetailsRequestDTO requestDTO = new FileDetailsRequestDTO(filePath);
        FileDetailsResponseDTO expectedResponse = new FileDetailsResponseDTO();
        when(dataProcessingService.iotBatchDataLoading(filePath)).thenReturn(expectedResponse);

        ResponseEntity<FileDetailsResponseDTO> response = dataProcessingController.iotBatchDataLoading(requestDTO);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testIotBatchDataLoading_EmptyFilePath() throws IOException {
        FileDetailsRequestDTO requestDTO = new FileDetailsRequestDTO();
        assertThrows(InvalidInputException.class, () -> dataProcessingController.iotBatchDataLoading(requestDTO));
    }*/
}

package com.example.iottrackingsystem.service;

//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertThrows;
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//import com.example.iottrackingsystem.dto.DeviceReportResponseDTO;
//import com.example.iottrackingsystem.dto.FileDetailsResponseDTO;
//import com.example.iottrackingsystem.exception.DataNotFoundException;
//import com.example.iottrackingsystem.repository.ProductDetailsRepository;
//import com.example.iottrackingsystem.service.DataProcessingService;
//import com.example.iottrackingsystem.util.IotTrackingSystemConstants;
//import com.example.iottrackingsystem.util.Utils;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;

public class DataProcessingServiceImplTest {

  /*  @InjectMocks
    private DataProcessingService dataProcessingService;

    @Mock
    private ProductDetailsRepository productDetailsRepository;

    @Mock
    private Utils utils;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIotBatchDataLoading_Success() throws IOException {
        String filePath = "valid/filepath";
        FileDetailsResponseDTO expectedResponse = new FileDetailsResponseDTO();
        when(productDetailsRepository.iotBatchDataLoading(filePath)).thenReturn(expectedResponse);

        FileDetailsResponseDTO response = dataProcessingService.iotBatchDataLoading(filePath);

        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testGetDeviceAndLocationDetails_Success() {
        String productId = "validProductId";
        String currentDateTime = "currentDateTime";
        DeviceReportResponseDTO expectedResponse = new DeviceReportResponseDTO();
        when(utils.getCurrentDatetimeUTCFormat()).thenReturn(currentDateTime);
        when(productDetailsRepository.getDeviceAndLocationDetails(productId, currentDateTime)).thenReturn(expectedResponse);

        DeviceReportResponseDTO response = dataProcessingService.getDeviceAndLocationDetails(productId, null);

        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testGetDeviceAndLocationDetails_EmptyProductId() {
        assertThrows(DataNotFoundException.class, () -> dataProcessingService.getDeviceAndLocationDetails("", null));
    }

    @Test
    public void testGetDeviceAndLocationDetails_ProductIdNotFound() {
        String productId = "invalidProductId";
        String currentDateTime = "currentDateTime";
        when(utils.getCurrentDatetimeUTCFormat()).thenReturn(currentDateTime);
        when(productDetailsRepository.getDeviceAndLocationDetails(productId, currentDateTime)).thenReturn(null);

        assertThrows(DataNotFoundException.class, () -> dataProcessingService.getDeviceAndLocationDetails(productId, null));
    }*/
}

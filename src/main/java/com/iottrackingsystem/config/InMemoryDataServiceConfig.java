package com.iottrackingsystem.config;

import com.iottrackingsystem.repository.InMemoryDataService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This configuration class creates a bean for the InMemoryDataService
 */
@Configuration
public class InMemoryDataServiceConfig {

    @Bean
    public InMemoryDataService inMemoryDataService() {
        return new InMemoryDataService();
    }
}

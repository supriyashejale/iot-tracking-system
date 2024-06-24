package com.example.iottrackingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

/**
 * This is the main Spring Boot application class that bootstraps the application
 */
@Profile("dev")
@SpringBootApplication
public class IotTrackingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotTrackingSystemApplication.class, args);
    }

}

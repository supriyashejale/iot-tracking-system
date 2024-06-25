package com.iottrackingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

/**
 * This is the main Spring Boot application class that bootstraps the application
 * @author Supriya Shejale : 19/06/2024
 * @version 1.1.0
 */
@Profile("dev")
@SpringBootApplication
public class IotTrackingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotTrackingSystemApplication.class, args);
    }

}

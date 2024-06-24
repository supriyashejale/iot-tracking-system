package com.example.iottrackingsystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <h1> Class: IOTTrackingSystemConfig
 * <p>
 * The IOTTrackingSystemConfig class is a configuration class which responsible for configure properties file value in to the application on startup
 *
 * @author Supriya Shejale : 19/06/2024
 * @version 1.0
 */

@Configuration
public class IOTTrackingSystemConfig implements WebMvcConfigurer {

    @Value("${server.port}")
    String serverport;

    //try to solve cors issue for swagger file
    String url = "http://localhost:8081";//+ serveport;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(url) // Adjust this as needed
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

}

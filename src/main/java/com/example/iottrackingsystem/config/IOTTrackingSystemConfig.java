package com.example.iottrackingsystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * <h1> Class: IOTTrackingSystemConfig
 * <p>
 * The IOTTrackingSystemConfig class is a configuration class which responsible for configure properties file value in to the application on startup
 *
 * @author Supriya Shejale : 19/06/2024
 * @version 1.0
 */

@Configuration
@Profile("dev")
public class IOTTrackingSystemConfig {

//    @Autowired
//    private RestTemplateBuilder builder;
//
//    @Autowired
//    RestTemplate restTemplate;
//
//    @Value("${keycloak.username}")
//    String uiBEUsername;
//
//    @Value("${keycloak.password}")
//    String uiBEPassword;
//
//    @Value("${keycloak.tokenUrl}")
//    String uiBEAuthUrl;
//
//    @Value("${keycloak.grantType}")
//    String grantType;
//
//    @Value("${keycloak.clientId}")
//    String clientId;
//
//    @Value("${ui.be.getDaqs}")
//    String uiBEDaqsUrl;
//
//    @Value("${registry.command.version}")
//    String version;

//    public IOTTrackingSystemConfig() {
//        setProperties();
//    }
//	static final Logger logger = LogManager.getLogger(IOTTrackingSystemConfig.class);
//
//
//    /*
//    * Set properties value and version for further uibe and authentication api access process
//    * @return void
//    *
//    * */
//    public void setProperties(){
//        new UIBEDaqsService(restTemplate, uiBEDaqsUrl,  uiBEAuthUrl, uiBEPassword, uiBEUsername,grantType,clientId);
//        new DaqsRequestApiService(version);
//    }
}
